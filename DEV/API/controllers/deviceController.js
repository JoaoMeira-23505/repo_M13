'use strict'
const deviceService = require('../services/deviceService');

const getDevices = async (req, res) => {
    try {
        const devices = await deviceService.listDevices();
        res.send(devices);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getDeviceById = async (req, res)=> {
    try {
        const deviceID = req.params.ID;
        const oneDevice = await deviceService.listDeviceById(deviceID);
        res.send(oneDevice);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addDevice = async (req, res)=> {
    try {
        const data = req.body;
        const created = await deviceService.createDevice(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateDevice = async (req, res)=> {
    try {
        const deviceID = req.params.ID;
        const data = req.body;
        const created = await deviceService.updateDevice(deviceID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteDevice = async (req, res)=> {
    try {
        const deviceID = req.params.ID;
        const deleted = await deviceService.deleteDevice(deviceID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getDevices,
    getDeviceById,
    addDevice,
    updateDevice,
    deleteDevice
}