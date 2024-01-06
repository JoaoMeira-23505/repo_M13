'use strict'
const roomTypeService = require('../services/roomTypeService');

const getRoomTypes = async (req, res) => {
    try {
        const roomTypes = await roomTypeService.listRoomTypes();
        res.send(roomTypes);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getRoomTypeById = async (req, res)=> {
    try {
        const roomTypeID = req.params.ID;
        const oneRoomType = await roomTypeService.listRoomTypeById(roomTypeID);
        res.send(oneRoomType);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addRoomType = async (req, res)=> {
    try {
        const data = req.body;
        const created = await roomTypeService.createRoomType(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateRoomType = async (req, res)=> {
    try {
        const roomTypeID = req.params.ID;
        const data = req.body;
        const created = await roomTypeService.updateRoomType(roomTypeID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteRoomType = async (req, res)=> {
    try {
        const roomTypeID = req.params.ID;
        const deleted = await roomTypeService.deleteRoomType(roomTypeID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getRoomTypes,
    getRoomTypeById,
    addRoomType,
    updateRoomType,
    deleteRoomType
}