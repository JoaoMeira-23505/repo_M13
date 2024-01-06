'use strict';
const utils = require('../utils/enums');
const ChatType = require('../models/chatTypeModel');

const listChatTypes = async () => {
    try {
        return await ChatType.findAll();
    } catch (error) {
        return error.message;
    }
}

const listChatTypeById = async (Id)=> {
    try {
        return await ChatType.findByPk(Id);
    }
    catch (error) {
        return  error.message;
    }
}


const createChatType = async (chatTypeData) => {
    try {
        const newChatType = await ChatType.create({
            name: chatTypeData.name
        });

        return newChatType.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateChatType = async (ID, chatTypeData) => {
    try {
        const [updatedCount] = await ChatType.update(chatTypeData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedChatType = await ChatType.findByPk(ID);
        return updatedChatType.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteChatType = async (ID) => {
    try {
        const deletedCount = await ChatType.destroy({
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
    listChatTypes,
    listChatTypeById,
    createChatType,
    updateChatType,
    deleteChatType
}