'use strict';
const utils = require('../utils/enums');
const RoomType = require('../models/roomTypeModel');

const listRoomTypes = async () => {
    try {
        return await RoomType.findAll();
    } catch (error) {
        return error.message;
    }
}

const listRoomTypeById = async (Id)=> {
    try {
        return await RoomType.findByPk(Id);
    }
    catch (error) {
        return  error.message;
    }
}


const createRoomType = async (roomTypeData) => {
    try {
        const newRoomType = await RoomType.create({
            name: roomTypeData.name,
            description: roomTypeData.description
        });

        return newRoomType.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateRoomType = async (ID, roomTypeData) => {
    try {
        const [updatedCount] = await RoomType.update(roomTypeData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedRoomType = await RoomType.findByPk(ID);
        return updatedRoomType.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteRoomType = async (ID) => {
    try {
        const deletedCount = await RoomType.destroy({
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
    listRoomTypes,
    listRoomTypeById,
    createRoomType,
    updateRoomType,
    deleteRoomType
}