'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin} =
    require("../middleware/rolesAuthorization");
const {getPaymentTypes, getPaymentTypeById, addPaymentType, updatePaymentType, deletePaymentType} =
    require('../controllers/paymentTypeController');

router.get('/listPaymentTypes', getPaymentTypes);
router.get('/listPaymentType/:ID', getPaymentTypeById);

router.post('/createPaymentType', checkRoleAdmin, addPaymentType);

router.put('/updatePaymentType/:ID', checkRoleAdmin, updatePaymentType);

router.delete('/deletePaymentType/:ID', checkRoleAdmin, deletePaymentType);

module.exports = {
    routes: router
}