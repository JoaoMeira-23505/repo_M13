'use strict'
const chatService = require('../services/chatService');

const getChats = async (req, res) => {
    try {
        const chats = await chatService.listChats();
        res.send(chats);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getChatById = async (req, res)=> {
    try {
        const chatID = req.params.ID;
        const oneChat = await chatService.listChatById(chatID);
        res.send(oneChat);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getChatsByUserId = async (req, res)=> {
    try {
        const userID = req.params.UserID;
        const usersChats = await chatService.listChatsByUserId(userID);
        res.send(usersChats);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addChat = async (req, res)=> {
    try {
        const data = req.body;
        const created = await chatService.createChat(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateChat = async (req, res)=> {
    try {
        const chatID = req.params.ID;
        const data = req.body;
        const created = await chatService.updateChat(chatID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteChat = async (req, res)=> {
    try {
        const chatID = req.params.ID;
        const deleted = await chatService.deleteChat(chatID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getChats,
    getChatById,
    getChatsByUserId,
    addChat,
    updateChat,
    deleteChat
}