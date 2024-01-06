'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin, checkRoleCleaningStaff, checkRoleNormal} =
    require("../middleware/rolesAuthorization");
const {getCleanings, getCleaningByUserId, getCleaningById, addCleaning, updateCleaning, deleteCleaning} =
    require("../controllers/cleaningController");

router.get('/listCleanings', checkRoleCleaningStaff, getCleanings);
router.get('/listCleaningsByUserId/:userID', checkRoleNormal, getCleaningByUserId);
router.get('/listCleaning/:ID', checkRoleCleaningStaff, getCleaningById);

router.post('/createCleaning', checkRoleNormal, addCleaning);

router.put('/updateCleaning/:ID', checkRoleCleaningStaff, updateCleaning);

router.delete('/deleteCleaning/:ID', checkRoleAdmin, deleteCleaning);

module.exports = {
    routes: router
}