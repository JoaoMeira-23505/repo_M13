'use strict'
const cleaningService = require('../services/cleaningService');
const paymentService = require('../services/paymentService');
const {Sequelize} = require("sequelize");
const utils = require("../utils/enums");

const getCleanings = async (req, res) => {
    try {
        const cleanings = await cleaningService.listCleanings();
        res.send(cleanings);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getCleaningByUserId = async (req, res)=> {
    try {
        const userID = req.params.userID;
        const userCleanings = await cleaningService.listCleaningByUserId(userID);
        res.send(userCleanings);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getCleaningById = async (req, res)=> {
    try {
        const cleaningID = req.params.ID;
        const oneCleaning = await cleaningService.listCleaningById(cleaningID);
        res.send(oneCleaning);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addCleaning = async (req, res)=> {
    try {
        const data = req.body;
        const newCleaning = await cleaningService.createCleaning(data);

        if (newCleaning.status === 'Not Paid') {
            const newPaymentData = {
                description: "Payment refering to the cleaning service schedule to " + new Date(data.date).toUTCString(),
                value: 5,
                PaymentTypeID: 2,
                UserID: data.UserID
            }
            await paymentService.createPayment(newPaymentData);
        } else {
            return res.status(403).json({
                error: "Cleaning creation was not successful."
            });
        }

        res.send(newCleaning);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateCleaning = async (req, res)=> {
    try {
        const cleaningID = req.params.ID;
        const data = req.body;
        const created = await cleaningService.updateCleaning(cleaningID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteCleaning = async (req, res)=> {
    try {
        const cleaningID = req.params.ID;
        const deleted = await cleaningService.deleteCleaning(cleaningID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getCleanings,
    getCleaningByUserId,
    getCleaningById,
    addCleaning,
    updateCleaning,
    deleteCleaning
}