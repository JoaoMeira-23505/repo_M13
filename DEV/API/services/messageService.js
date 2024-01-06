'use strict';
const utils = require('../utils/enums');
const Message = require('../models/messageModel');
const User = require("../models/userModel");
const Chat = require("../models/chatModel");
const {Sequelize} = require("sequelize");

Message.belongsTo(User, { foreignKey: 'UserID' });
Message.belongsTo(Chat, { foreignKey: 'ChatID' });

const listMessages = async () => {
    try {
        return await Message.findAll({
            include: [{
                model: User,
                attributes: ['name'],
                required: false,
            },{
                model: Chat,
                required: true,
            }]
        });
    } catch (error) {
        return error.message;
    }
}

const listMessagesByChatId = async (ChatID)=> {
    try {
        return await Message.findAll({
            include: [{
                model: User,
                attributes: ['name'],
                required: false,
            },{
                model: Chat,
                required: true,
            }],
            where: { ChatID : ChatID},
            order: [['dateSended', 'ASC']],
        });
    }
    catch (error) {
        return  error.message;
    }
}

const listMessageById = async (Id)=> {
    try {
        return await Message.findByPk(Id,{
            include: [{
                model: User,
                attributes: ['name'],
                required: false,
            },{
                model: Chat,
                required: true,
            }]
        });
    }
    catch (error) {
        return  error.message;
    }
}

const createMessage = async (messageData) => {
    try {
        const newMessage = await Message.create({
            text: messageData.text,
            dateSended: Sequelize.literal('CURRENT_TIMESTAMP'),
            ChatID: messageData.ChatID,
            UserID: messageData.UserID
        });

        return newMessage.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateMessage = async (ID, messageData) => {
    try {
        const [updatedCount] = await Message.update(messageData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedMessage = await Message.findByPk(ID);
        return updatedMessage.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteMessage = async (ID) => {
    try {
        const deletedCount = await Message.destroy({
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
    listMessages,
    listMessagesByChatId,
    listMessageById,
    createMessage,
    updateMessage,
    deleteMessage
}