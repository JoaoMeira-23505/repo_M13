'use strict'
const chatTypeService = require('../services/chatTypeService');

const getChatTypes = async (req, res) => {
    try {
        const chatTypes = await chatTypeService.listChatTypes();
        res.send(chatTypes);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getChatTypeById = async (req, res)=> {
    try {
        const chatTypeID = req.params.ID;
        const oneChatType = await chatTypeService.listChatTypeById(chatTypeID);
        res.send(oneChatType);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addChatType = async (req, res)=> {
    try {
        const data = req.body;
        const created = await chatTypeService.createChatType(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateChatType = async (req, res)=> {
    try {
        const chatTypeID = req.params.ID;
        const data = req.body;
        const created = await chatTypeService.updateChatType(chatTypeID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteChatType = async (req, res)=> {
    try {
        const chatTypeID = req.params.ID;
        const deleted = await chatTypeService.deleteChatType(chatTypeID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getChatTypes,
    getChatTypeById,
    addChatType,
    updateChatType,
    deleteChatType
}