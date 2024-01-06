'use strict';
const utils = require('../utils/enums');
const DeviceType = require('../models/deviceTypeModel');

const listDeviceTypes = async () => {
    try {
        return await DeviceType.findAll();
    } catch (error) {
        return error.message;
    }
}

const listDeviceTypeById = async (Id)=> {
    try {
        return await DeviceType.findByPk(Id);
    }
    catch (error) {
        return  error.message;
    }
}


const createDeviceType = async (deviceTypeData) => {
    try {
        const newDeviceType = await DeviceType.create({
            name: deviceTypeData.name,
        });

        return newDeviceType.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateDeviceType = async (ID, deviceTypeData) => {
    try {
        const [updatedCount] = await DeviceType.update(deviceTypeData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedDeviceType = await DeviceType.findByPk(ID);
        return updatedDeviceType.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteDeviceType = async (ID) => {
    try {
        const deletedCount = await DeviceType.destroy({
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
    listDeviceTypes,
    listDeviceTypeById,
    createDeviceType,
    updateDeviceType,
    deleteDeviceType
}