'use strict'
const rentService = require('../services/rentService');

const getRents = async (req, res) => {
    try {
        const rents = await rentService.listRents();
        res.send(rents);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getRentById = async (req, res)=> {
    try {
        const rentID = req.params.ID;
        const oneRent = await rentService.listRentById(rentID);
        res.send(oneRent);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addRent = async (req, res)=> {
    try {
        const data = req.body;
        const created = await rentService.createRent(data);
        //TODO ADD DEPENDENT TABLES
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateRent = async (req, res)=> {
    try {
        const rentID = req.params.ID;
        const data = req.body;
        const created = await rentService.updateRent(rentID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteRent = async (req, res)=> {
    try {
        const rentID = req.params.ID;
        const deleted = await rentService.deleteRent(rentID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getRents,
    getRentById,
    addRent,
    updateRent,
    deleteRent
}