'use strict'
const paymentTypeService = require('../services/paymentTypeService');

const getPaymentTypes = async (req, res) => {
    try {
        const paymentTypes = await paymentTypeService.listPaymentTypes();
        res.send(paymentTypes);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getPaymentTypeById = async (req, res)=> {
    try {
        const paymentTypeID = req.params.ID;
        const onePaymentType = await paymentTypeService.listPaymentTypeById(paymentTypeID);
        res.send(onePaymentType);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addPaymentType = async (req, res)=> {
    try {
        const data = req.body;
        const created = await paymentTypeService.createPaymentType(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updatePaymentType = async (req, res)=> {
    try {
        const paymentTypeID = req.params.ID;
        const data = req.body;
        const created = await paymentTypeService.updatePaymentType(paymentTypeID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deletePaymentType = async (req, res)=> {
    try {
        const paymentTypeID = req.params.ID;
        const deleted = await paymentTypeService.deletePaymentType(paymentTypeID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getPaymentTypes,
    getPaymentTypeById,
    addPaymentType,
    updatePaymentType,
    deletePaymentType
}