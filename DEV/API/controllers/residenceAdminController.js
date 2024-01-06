'use strict'
const residenceAdminService = require('../services/residenceAdminService');

const getResidenceAdmins = async (req, res) => {
    try {
        const residenceAdmins = await residenceAdminService.listResidenceAdmins();
        res.send(residenceAdmins);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getResidenceAdminById = async (req, res)=> {
    try {
        const residenceAdminID = req.params.ID;
        const oneResidenceAdmin = await residenceAdminService.listResidenceAdminById(residenceAdminID);
        res.send(oneResidenceAdmin);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addResidenceAdmin = async (req, res)=> {
    try {
        const data = req.body;
        const created = await residenceAdminService.createResidenceAdmin(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateResidenceAdmin = async (req, res)=> {
    try {
        const residenceAdminID = req.params.ID;
        const data = req.body;
        const created = await residenceAdminService.updateResidenceAdmin(residenceAdminID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteResidenceAdmin = async (req, res)=> {
    try {
        const residenceAdminID = req.params.ID;
        const deleted = await residenceAdminService.deleteResidenceAdmin(residenceAdminID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getResidenceAdmins,
    getResidenceAdminById,
    addResidenceAdmin,
    updateResidenceAdmin,
    deleteResidenceAdmin
}