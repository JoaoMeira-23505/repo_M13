'use strict';
const utils = require('../utils/enums');
const Chat = require('../models/chatModel');
const ChatType = require('../models/chatTypeModel');
const Participant = require("../models/participantModel");

Chat.belongsTo(ChatType, { foreignKey: 'ChatTypeID' });
Chat.hasMany(Participant)

const listChats = async () => {
    try {
        return await Chat.findAll({
            include: [{
                model: ChatType,
                attributes: ['name'],
                required: true,
            }]
        });
    } catch (error) {
        return error.message;
    }
}

const listChatById = async (Id)=> {
    try {
        return await Chat.findByPk(Id, {
            include: [{
                model: ChatType,
                attributes: ['name'],
                required: true,
            }]
        });
    }
    catch (error) {
        return  error.message;
    }
}

const listChatsByUserId = async (UserId)=> {
    try {
        return await Chat.findAll({
            include: [{
                model: ChatType,
                attributes: ['name'],
                required: true,
            },{
                model: Participant,
                required: true,
                where: { UserID: UserId }
            }]
        });
    }
    catch (error) {
        return  error.message;
    }
}

const createChat = async (chatData) => {
    try {
        const newChat = await Chat.create({
            status: utils.chatStatus.Active,
            ChatTypeID: chatData.ChatTypeID
        });

        return newChat.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateChat = async (ID, chatData) => {
    try {
        const [updatedCount] = await Chat.update(chatData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedChat = await Chat.findByPk(ID);
        return updatedChat.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteChat = async (ID) => {
    try {
        const deletedCount = await Chat.destroy({
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
    listChats,
    listChatById,
    listChatsByUserId,
    createChat,
    updateChat,
    deleteChat
}