'use strict';
const utils = require('../utils/enums');
const Room = require('../models/roomModel');
const Residence = require('../models/residenceModel');
const RoomType = require('../models/roomTypeModel');

Room.belongsTo(Residence, { foreignKey: 'ResidenceID' });
Room.belongsTo(RoomType, { foreignKey: 'RoomTypeID' });

const listRooms = async () => {
    try {
        return await Room.findAll({
            include: [{
                model: Residence,
                attributes: ['name'],
                required: true,
            },{
                model: RoomType,
                attributes: ['name'],
                required: true,
            }]
        });
    } catch (error) {
        return error.message;
    }
}

const listRoomById = async (Id)=> {
    try {
        return await Room.findByPk(Id, {
            include: [{
                model: Residence,
                attributes: ['name'],
                required: true,
            },{
                model: RoomType,
                attributes: ['name'],
                required: true,
            }]
        });
    }
    catch (error) {
        return  error.message;
    }
}

const createRoom = async (roomData) => {
    try {
        const newRoom = await Room.create({
            doorNumber: roomData.doorNumber,
            floor: roomData.floor,
            status: utils.roomStatus.Unoccupied,
            roomPhoto: roomData.roomPhoto,
            ResidenceID: roomData.ResidenceID,
            RoomTypeID: roomData.RoomTypeID
        });

        return newRoom.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateRoom = async (ID, roomData) => {
    try {
        const [updatedCount] = await Room.update(roomData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedRoom = await Room.findByPk(ID);
        return updatedRoom.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteRoom = async (ID) => {
    try {
        const deletedCount = await Room.destroy({
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
    listRooms,
    listRoomById,
    createRoom,
    updateRoom,
    deleteRoom
}