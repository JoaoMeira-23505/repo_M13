'use strict';
const utils = require('../utils/enums');
const Application = require('../models/applicationModel');
const User = require('../models/userModel');
const RoomType = require("../models/roomTypeModel");
const Residence = require("../models/residenceModel");
const {Sequelize} = require("sequelize");

Application.belongsTo(User, { foreignKey: 'UserID' });
Application.belongsTo(RoomType, { foreignKey: 'RoomTypeID' });
Application.belongsTo(Residence, { foreignKey: 'ResidenceID' });

const listApplications = async () => {
    try {
        return await Application.findAll({
            include: [{
                model: User,
                attributes: { exclude: ['password'] },
                required: true,
            },{
                model: RoomType,
                required: true,
            },{
                model: Residence,
                attributes: ['name','location'],
                required: true,
            }]
        });
    } catch (error) {
        return error.message;
    }
}

const listApplicationById = async (Id)=> {
    try {
        return await Application.findByPk(Id, {
            include: [{
                model: User,
                attributes: { exclude: ['password'] },
                required: true,
            },{
                model: RoomType,
                required: true,
            },{
                model: Residence,
                attributes: ['name','location'],
                required: true,
            }]
        });
    }
    catch (error) {
        return  error.message;
    }
}

const createApplication = async (applicationData) => {
    try {
        const newApplication = await Application.create({
            dateSubmitted: Sequelize.literal('CURRENT_TIMESTAMP'),
            status: utils.applicationStatus.Waiting,
            sosNumber: applicationData.sosNumber,
            courseName: applicationData.courseName,
            courseYearAttended: applicationData.courseYearAttended,
            courseYearStarted: applicationData.courseYearStarted,
            lastYearStatus: applicationData.lastYearStatus,
            socialBenefits: applicationData.socialBenefits,
            observations: applicationData.observations,
            UserID: applicationData.UserID,
            RoomTypeID: applicationData.RoomTypeID,
            ResidenceID: applicationData.ResidenceID
        });

        return newApplication.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateApplication = async (ID, applicationData) => {
    try {
        const [updatedCount] = await Application.update(applicationData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedApplication = await Application.findByPk(ID);
        return updatedApplication.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteApplication = async (ID) => {
    try {
        const deletedCount = await Application.destroy({
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
    listApplications,
    listApplicationById,
    createApplication,
    updateApplication,
    deleteApplication
}