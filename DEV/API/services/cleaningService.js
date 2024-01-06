'use strict';
const utils = require('../utils/enums');
const Cleaning = require('../models/cleaningModel');
const Room = require('../models/roomModel');
const User = require('../models/userModel');
const {Sequelize} = require("sequelize");

Cleaning.belongsTo(Room, { foreignKey: 'RoomID' });
Cleaning.belongsTo(User, { foreignKey: 'UserID' });

const listCleanings = async () => {
    try {
        return await Cleaning.findAll({
            include: [{
                model: Room,
                attributes: ['doorNumber', 'floor'],
                required: true,
            },{
                model: User,
                attributes: ['ID', 'name'],
                required: true,
            }]
        });
    } catch (error) {
        return error.message;
    }
}

const listCleaningByUserId = async (UserID) => {
    try {
        return await Cleaning.findAll({
            include: [{
                model: Room,
                attributes: ['doorNumber', 'floor'],
                required: true,
            },{
                model: User,
                attributes: ['ID', 'name'],
                required: true,
            }],
            where: { UserID: UserID },
            order: [['date', 'DESC']],
        });
    } catch (error) {
        return error.message;
    }
}

const listCleaningById = async (Id)=> {
    try {
        return await Cleaning.findByPk(Id, {
            include: [{
                model: Room,
                attributes: ['doorNumber', 'floor'],
                required: true,
            },{
                model: User,
                attributes: ['ID', 'name'],
                required: true,
            }]
        });
    }
    catch (error) {
        return  error.message;
    }
}

const createCleaning = async (cleaningData) => {
    try {
        const date = new Date(cleaningData.date).toISOString(); // Convert the date string to a Date object

        const newCleaning = await Cleaning.create({
            date: date,
            status: utils.cleaningStatus.NotPaid,
            RoomID: cleaningData.RoomID,
            UserID: cleaningData.UserID
        });

        return newCleaning.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateCleaning = async (ID, cleaningData) => {
    try {
        const [updatedCount] = await Cleaning.update(cleaningData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedCleaning = await Cleaning.findByPk(ID);
        return updatedCleaning.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteCleaning = async (ID) => {
    try {
        const deletedCount = await Cleaning.destroy({
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
    listCleanings,
    listCleaningByUserId,
    listCleaningById,
    createCleaning,
    updateCleaning,
    deleteCleaning
}