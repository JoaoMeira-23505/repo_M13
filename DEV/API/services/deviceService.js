'use strict';
const utils = require('../utils/enums');
const Device = require('../models/deviceModel');
const Room = require('../models/roomModel');
const DeviceType = require('../models/deviceTypeModel');

Device.belongsTo(Room, { foreignKey: 'RoomID' });
Device.belongsTo(DeviceType, { foreignKey: 'DeviceTypeID' });

const listDevices = async () => {
    try {
        return await Device.findAll({
            include: [{
                model: Room,
                attributes: ['doorNumber', 'floor'],
                required: true,
            },{
                model: DeviceType,
                attributes: ['name'],
                required: true,
            }]
        });
    } catch (error) {
        return error.message;
    }
}

const listDeviceById = async (Id)=> {
    try {
        return await Device.findByPk(Id, {
            include: [{
                model: Room,
                attributes: ['doorNumber', 'floor'],
                required: true,
            },{
                model: DeviceType,
                attributes: ['name'],
                required: true,
            }]
        });
    }
    catch (error) {
        return  error.message;
    }
}

const createDevice = async (deviceData) => {
    try {
        const newDevice = await Device.create({
            accessCode: deviceData.accessCode,
            RoomID: deviceData.PaymentTypeID,
            DeviceTypeID: deviceData.UserID
        });

        return newDevice.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateDevice = async (ID, deviceData) => {
    try {
        const [updatedCount] = await Device.update(deviceData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedDevice = await Device.findByPk(ID);
        return updatedDevice.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteDevice = async (ID) => {
    try {
        const deletedCount = await Device.destroy({
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
    listDevices,
    listDeviceById,
    createDevice,
    updateDevice,
    deleteDevice
}