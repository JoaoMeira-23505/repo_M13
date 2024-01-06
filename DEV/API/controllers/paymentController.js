'use strict'
const paymentService = require('../services/paymentService');

const getPayments = async (req, res) => {
    try {
        const payments = await paymentService.listPayments();
        res.send(payments);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getPaymentsByUserId = async (req, res)=> {
    try {
        const userID = req.params.UserID;
        const onePayment = await paymentService.listPaymentsByUserId(userID);
        res.send(onePayment);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getPaymentById = async (req, res)=> {
    try {
        const paymentID = req.params.ID;
        const onePayment = await paymentService.listPaymentById(paymentID);
        res.send(onePayment);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addPayment = async (req, res)=> {
    try {
        const data = req.body;
        const created = await paymentService.createPayment(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updatePayment = async (req, res)=> {
    try {
        const paymentID = req.params.ID;
        const data = req.body;
        const created = await paymentService.updatePayment(paymentID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deletePayment = async (req, res)=> {
    try {
        const paymentID = req.params.ID;
        const deleted = await paymentService.deletePayment(paymentID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getPayments,
    getPaymentsByUserId,
    getPaymentById,
    addPayment,
    updatePayment,
    deletePayment
}