'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin, checkRoleNormal} =
    require("../middleware/rolesAuthorization");
const {getMessages, getMessagesByChatId, getMessageById, addMessage, updateMessage, deleteMessage} =
    require("../controllers/messageController");

router.get('/listMessages', checkRoleAdmin, getMessages);
router.get('/listMessagesByChatId/:ID', checkRoleNormal, getMessagesByChatId);
router.get('/listMessage/:ID', checkRoleNormal, getMessageById);

router.post('/createMessage', checkRoleNormal, addMessage);

router.put('/updateMessage/:ID', checkRoleNormal, updateMessage);

router.delete('/deleteMessage/:ID', checkRoleAdmin, deleteMessage);

module.exports = {
    routes: router
}