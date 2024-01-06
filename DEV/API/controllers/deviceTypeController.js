'use strict'
const deviceTypeService = require('../services/deviceTypeService');

const getDeviceTypes = async (req, res) => {
    try {
        const deviceTypes = await deviceTypeService.listDeviceTypes();
        res.send(deviceTypes);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getDeviceTypeById = async (req, res)=> {
    try {
        const deviceTypeID = req.params.ID;
        const oneDeviceType = await deviceTypeService.listDeviceTypeById(deviceTypeID);
        res.send(oneDeviceType);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addDeviceType = async (req, res)=> {
    try {
        const data = req.body;
        const created = await deviceTypeService.createDeviceType(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateDeviceType = async (req, res)=> {
    try {
        const deviceTypeID = req.params.ID;
        const data = req.body;
        const created = await deviceTypeService.updateDeviceType(deviceTypeID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteDeviceType = async (req, res)=> {
    try {
        const deviceTypeID = req.params.ID;
        const deleted = await deviceTypeService.deleteDeviceType(deviceTypeID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getDeviceTypes,
    getDeviceTypeById,
    addDeviceType,
    updateDeviceType,
    deleteDeviceType
}