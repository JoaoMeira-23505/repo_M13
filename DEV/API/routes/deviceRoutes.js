'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin} =
    require("../middleware/rolesAuthorization");
const {getDevices, getDeviceById, addDevice, updateDevice, deleteDevice} =
    require('../controllers/deviceController');

router.get('/listDevices', checkRoleAdmin, getDevices);
router.get('/listDevice/:ID', checkRoleAdmin, getDeviceById);

router.post('/createDevice', checkRoleAdmin, addDevice);

router.put('/updateDevice/:ID', checkRoleAdmin, updateDevice);

router.delete('/deleteDevice/:ID', checkRoleAdmin, deleteDevice);

module.exports = {
    routes: router
}