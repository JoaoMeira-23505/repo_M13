'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin} =
    require("../middleware/rolesAuthorization");
const {getDeviceTypes, getDeviceTypeById, addDeviceType, updateDeviceType, deleteDeviceType} =
    require('../controllers/deviceTypeController');

router.get('/listDeviceTypes', getDeviceTypes);
router.get('/listDeviceType/:ID', getDeviceTypeById);

router.post('/createDeviceType', checkRoleAdmin, addDeviceType);

router.put('/updateDeviceType/:ID', checkRoleAdmin, updateDeviceType);

router.delete('/deleteDeviceType/:ID', checkRoleAdmin, deleteDeviceType);

module.exports = {
    routes: router
}