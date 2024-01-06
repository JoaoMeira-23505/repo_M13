'use strict'
const messageService = require('../services/messageService');

const getMessages = async (req, res) => {
    try {
        const messages = await messageService.listMessages();
        res.send(messages);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getMessagesByChatId = async (req, res)=> {
    try {
        const messageID = req.params.ID;
        const messages = await messageService.listMessagesByChatId(messageID);
        res.send(messages);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getMessageById = async (req, res)=> {
    try {
        const messageID = req.params.ID;
        const oneMessage = await messageService.listMessageById(messageID);
        res.send(oneMessage);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addMessage = async (req, res)=> {
    try {
        const data = req.body;
        const created = await messageService.createMessage(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateMessage = async (req, res)=> {
    try {
        const messageID = req.params.ID;
        const data = req.body;
        const created = await messageService.updateMessage(messageID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteMessage = async (req, res)=> {
    try {
        const messageID = req.params.ID;
        const deleted = await messageService.deleteMessage(messageID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getMessages,
    getMessagesByChatId,
    getMessageById,
    addMessage,
    updateMessage,
    deleteMessage
}