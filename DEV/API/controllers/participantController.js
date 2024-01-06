'use strict'
const participantService = require('../services/participantService');

const getParticipants = async (req, res) => {
    try {
        const participants = await participantService.listParticipants();
        res.send(participants);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getParticipantsByChatId = async (req, res) => {
    try {
        const chatID = req.params.ChatID;
        const participants = await participantService.listParticipantsByChatID(chatID);
        res.send(participants);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const getParticipantById = async (req, res)=> {
    try {
        const participantID = req.params.ID;
        const oneParticipant = await participantService.listParticipantById(participantID);
        res.send(oneParticipant);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addParticipant = async (req, res)=> {
    try {
        const data = req.body;
        const created = await participantService.createParticipant(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateParticipant = async (req, res)=> {
    try {
        const participantID = req.params.ID;
        const data = req.body;
        const created = await participantService.updateParticipant(participantID, data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteParticipant = async (req, res)=> {
    try {
        const participantID = req.params.ID;
        const deleted = await participantService.deleteParticipant(participantID);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getParticipants,
    getParticipantsByChatId,
    getParticipantById,
    addParticipant,
    updateParticipant,
    deleteParticipant
}