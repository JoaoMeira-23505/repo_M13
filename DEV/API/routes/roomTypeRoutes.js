'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin} =
    require("../middleware/rolesAuthorization");
const {getRoomTypes, getRoomTypeById, addRoomType, updateRoomType, deleteRoomType} =
    require('../controllers/roomTypeController');

router.get('/listRoomTypes', getRoomTypes);
router.get('/listRoomType/:ID', getRoomTypeById);

router.post('/createRoomType', checkRoleAdmin, addRoomType);

router.put('/updateRoomType/:ID', checkRoleAdmin, updateRoomType);

router.delete('/deleteRoomType/:ID', checkRoleAdmin, deleteRoomType);

module.exports = {
    routes: router
}