'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin} =
    require("../middleware/rolesAuthorization");
const {getChatTypes, getChatTypeById, addChatType, updateChatType, deleteChatType} =
    require('../controllers/chatTypeController');

router.get('/listChatTypes', getChatTypes);
router.get('/listChatType/:ID', getChatTypeById);

router.post('/createChatType', checkRoleAdmin, addChatType);

router.put('/updateChatType/:ID', checkRoleAdmin, updateChatType);

router.delete('/deleteChatType/:ID', checkRoleAdmin, deleteChatType);

module.exports = {
    routes: router
}