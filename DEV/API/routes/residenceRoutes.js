'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin} =
    require("../middleware/rolesAuthorization");
const {getResidences, getResidenceById, getResidenceByLocation, addResidence, updateResidence, deleteResidence} =
    require('../controllers/residenceController');

router.get('/listResidences', getResidences);
router.get('/listResidence/:ID', getResidenceById);
router.get('/listResidenceByLocation', getResidenceByLocation);

router.post('/createResidence', checkRoleAdmin, addResidence);

router.put('/updateResidence/:ID', checkRoleAdmin, updateResidence);

router.delete('/deleteResidence/:ID', checkRoleAdmin, deleteResidence);

module.exports = {
    routes: router
}