'use strict'
const roomService = require('../services/roomService');

const getRooms = async (req, res) => {
    try {
        const rooms = await roomService.listRooms();
        res.send(rooms);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getRoomById = async (req, res)=> {
    try {
        const roomID = req.params.ID;
        const oneRoom = await roomService.listRoomById(roomID);
        res.send(oneRoom);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addRoom = async (req, res)=> {
    try {
        const data = req.body;
        const created = await roomService.createRoom(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateRoom = async (req, res)=> {
    try {
        const roomID = req.params.ID;
        const data = req.body;
        const created = await roomService.updateRoom(roomID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteRoom = async (req, res)=> {
    try {
        const roomID = req.params.ID;
        const deleted = await roomService.deleteRoom(roomID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getRooms,
    getRoomById,
    addRoom,
    updateRoom,
    deleteRoom
}