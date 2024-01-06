'use strict';
const utils = require('../utils/enums');
const ResidenceAdmin = require('../models/residenceAdminModel');
const User = require('../models/userModel');
const Residence = require("../models/residenceModel");
const {Sequelize} = require("sequelize");

ResidenceAdmin.belongsTo(User, { foreignKey: 'UserID' });
ResidenceAdmin.belongsTo(Residence, { foreignKey: 'ResidenceID' });

const listResidenceAdmins = async () => {
    try {
        return await ResidenceAdmin.findAll({
            include: [{
                model: User,
                attributes: ['name', 'email'],
                required: true,
            },{
                model: Residence,
                attributes: ['name'],
                required: true,
            }]
        });
    } catch (error) {
        return error.message;
    }
}

const listResidenceAdminById = async (Id)=> {
    try {
        return await ResidenceAdmin.findByPk(Id,{
            include: [{
                model: User,
                attributes: ['name', 'email'],
                required: true,
            },{
                model: Residence,
                attributes: ['name'],
                required: true,
            }]
        });
    }
    catch (error) {
        return  error.message;
    }
}

const createResidenceAdmin = async (residenceAdminData) => {
    try {
        const newResidenceAdmin = await ResidenceAdmin.create({
            startDate: Sequelize.literal('CURRENT_TIMESTAMP'),
            UserID: residenceAdminData.UserID,
            ResidenceID: residenceAdminData.ResidenceID
        });

        return newResidenceAdmin.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateResidenceAdmin = async (ID, residenceAdminData) => {
    try {
        const [updatedCount] = await ResidenceAdmin.update(residenceAdminData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedResidenceAdmin = await ResidenceAdmin.findByPk(ID);
        return updatedResidenceAdmin.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteResidenceAdmin = async (ID) => {
    try {
        const deletedCount = await ResidenceAdmin.destroy({
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
    listResidenceAdmins,
    listResidenceAdminById,
    createResidenceAdmin,
    updateResidenceAdmin,
    deleteResidenceAdmin
}