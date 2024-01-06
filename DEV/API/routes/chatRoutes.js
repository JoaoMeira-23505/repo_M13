'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin, checkRoleNormal} =
    require("../middleware/rolesAuthorization");
const {getChats, getChatById, getChatsByUserId, addChat, updateChat, deleteChat} =
    require("../controllers/chatController");

router.get('/listChats', checkRoleAdmin, getChats);
router.get('/listChat/:ID', checkRoleNormal, getChatById);
router.get('/listChatsByUserID/:UserID', checkRoleNormal, getChatsByUserId);

router.post('/createChat', checkRoleAdmin, addChat);

router.put('/updateChat/:ID', checkRoleAdmin, updateChat);

router.delete('/deleteChat/:ID', checkRoleAdmin, deleteChat);

module.exports = {
    routes: router
}