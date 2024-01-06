'use strict';
const utils = require('../utils/enums');
const Residence = require('../models/residenceModel');

const listResidences = async () => {
    try {
        return await Residence.findAll();
    } catch (error) {
        return error.message;
    }
}

const listResidenceById = async (Id)=> {
    try {
        return await Residence.findByPk(Id);
    }
    catch (error) {
        return  error.message;
    }
}

const listResidenceByLocation = async (Location)=> {
    try {
        return await Residence.findOne({
            where: { location: Location }
        });
    }
    catch (error) {
        return  error.message;
    }
}

const createResidence = async (residenceData) => {
    try {
        const newResidence = await Residence.create({
            name: residenceData.name,
            location: residenceData.location,
            phoneNumber: residenceData.phoneNumber,
        });

        return newResidence.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateResidence = async (ID, residenceData) => {
    try {
        const [updatedCount] = await Residence.update(residenceData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedResidence = await Residence.findByPk(ID);
        return updatedResidence.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteResidence = async (ID) => {
    try {
        const deletedCount = await Residence.destroy({
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
    listResidences,
    listResidenceById,
    listResidenceByLocation,
    createResidence,
    updateResidence,
    deleteResidence
}