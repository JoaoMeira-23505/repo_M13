'use strict'
const residenceService = require('../services/residenceService');

const getResidences = async (req, res) => {
    try {
        const residences = await residenceService.listResidences();
        res.send(residences);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getResidenceById = async (req, res)=> {
    try {
        const residenceID = req.params.ID;
        const oneResidence = await residenceService.listResidenceById(residenceID);
        res.send(oneResidence);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getResidenceByLocation = async (req, res)=> {
    try {
        const residenceLocation = req.body.location;
        const oneResidence = await residenceService.listResidenceByLocation(residenceLocation);
        res.send(oneResidence);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addResidence = async (req, res)=> {
    try {
        const data = req.body;
        const created = await residenceService.createResidence(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateResidence = async (req, res)=> {
    try {
        const residenceID = req.params.ID;
        const data = req.body;
        const created = await residenceService.updateResidence(residenceID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteResidence = async (req, res)=> {
    try {
        const residenceID = req.params.ID;
        const deleted = await residenceService.deleteResidence(residenceID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getResidences,
    getResidenceById,
    getResidenceByLocation,
    addResidence,
    updateResidence,
    deleteResidence
}