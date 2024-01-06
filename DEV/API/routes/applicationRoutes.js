'use strict'
const express = require('express');
const router = express.Router();
const { checkRoleAdmin, checkRoleNormal} =
    require("../middleware/rolesAuthorization");
const {getApplications, getApplicationById, addApplication, updateApplication, deleteApplication} =
    require("../controllers/applicationController");

router.get('/listApplications', checkRoleAdmin, getApplications);
router.get('/listApplication/:ID', checkRoleAdmin, getApplicationById);

router.post('/createApplication', checkRoleNormal, addApplication);

router.put('/updateApplication/:ID', checkRoleAdmin, updateApplication);

router.delete('/deleteApplication/:ID', checkRoleAdmin, deleteApplication);

module.exports = {
    routes: router
}