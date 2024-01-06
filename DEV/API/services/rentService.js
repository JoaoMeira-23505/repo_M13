'use strict';
const utils = require('../utils/enums');
const Rent = require('../models/rentModel');
const User = require('../models/userModel');
const Room = require('../models/roomModel');
const Residence = require("../models/residenceModel");
const {Sequelize} = require("sequelize");

Rent.belongsTo(User, { foreignKey: 'UserID' });
Rent.belongsTo(Room, { foreignKey: 'RoomID' });
Room.belongsTo(Residence, { foreignKey: 'ResidenceID' });

const listRents = async () => {
    try {
        return await Rent.findAll({
            include: [{
                model: User,
                attributes: ['name', 'email'],
                required: true,
            },{
                model: Room,
                attributes: ['doorNumber'],
                required: true,
                include: [{
                    model: Residence,
                    attributes: ['name'],
                    required: true,
                }]
            }]
        });
    } catch (error) {
        return error.message;
    }
}

const listRentById = async (Id)=> {
    try {
        return await Rent.findByPk(Id,{
            include: [{
                model: User,
                attributes: ['name', 'email'],
                required: true,
            },{
                model: Room,
                attributes: ['doorNumber'],
                required: true,
                include: [{
                    model: Residence,
                    attributes: ['name'],
                    required: true,
                }]
            }]
        });
    }
    catch (error) {
        return  error.message;
    }
}

const createRent = async (rentData) => {
    try {
        const newRent = await Rent.create({
            entryDate: Sequelize.literal('CURRENT_TIMESTAMP'),
            status: utils.rentStatus.Active,
            doorAccessCode: "12345", //TODO ADD A CODE RANDOMYZER
            UserID: rentData.UserID,
            RoomID: rentData.RoomID
        });

        return newRent.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateRent = async (ID, rentData) => {
    try {
        const [updatedCount] = await Rent.update(rentData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedRent = await Rent.findByPk(ID);
        return updatedRent.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteRent = async (ID) => {
    try {
        const deletedCount = await Rent.destroy({
            where: { ID: ID },
        });

        if (deletedCount === 0)
            return { message: 'Something went wrong! Record not deleted' };

        return { message: 'Record deleted successfully' };
    }
    catch (error) {
        return error.message;
    }
}

module.exports = {
    listRents,
    listRentById,
    createRent,
    updateRent,
    deleteRent
}