'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin} =
    require("../middleware/rolesAuthorization");
const {getResidenceAdmins, getResidenceAdminById, addResidenceAdmin, updateResidenceAdmin, deleteResidenceAdmin} =
    require("../controllers/residenceAdminController");

router.get('/listResidenceAdmins', checkRoleAdmin, getResidenceAdmins);
router.get('/listResidenceAdmin/:ID', checkRoleAdmin, getResidenceAdminById);

router.post('/createResidenceAdmin', checkRoleAdmin, addResidenceAdmin);

router.put('/updateResidenceAdmin/:ID', checkRoleAdmin, updateResidenceAdmin);

router.delete('/deleteResidenceAdmin/:ID', checkRoleAdmin, deleteResidenceAdmin);

module.exports = {
    routes: router
}