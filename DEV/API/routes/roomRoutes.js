'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin, checkRoleNormal} =
    require("../middleware/rolesAuthorization");
const {getRooms, getRoomById, addRoom, updateRoom, deleteRoom} =
    require("../controllers/roomController");

router.get('/listRooms', checkRoleAdmin, getRooms);
router.get('/listRoom/:ID', checkRoleNormal, getRoomById);

router.post('/createRoom', checkRoleAdmin, addRoom);

router.put('/updateRoom/:ID', checkRoleAdmin, updateRoom);

router.delete('/deleteRoom/:ID', checkRoleAdmin, deleteRoom);

module.exports = {
    routes: router
}