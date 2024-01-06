'use strict'
const express = require('express');
const router = express.Router();
const {checkRoleAdmin, checkRoleNormal} =
    require("../middleware/rolesAuthorization");
const {getParticipants, getParticipantsByChatId, getParticipantById, addParticipant, updateParticipant, deleteParticipant} =
    require("../controllers/participantController");

router.get('/listParticipants', checkRoleAdmin, getParticipants);
router.get('/listParticipantsByChatID/:ChatID', checkRoleNormal, getParticipantsByChatId);
router.get('/listParticipant/:ID', checkRoleNormal, getParticipantById);

router.post('/createParticipant', checkRoleAdmin, addParticipant);

router.put('/updateParticipant/:ID', checkRoleAdmin, updateParticipant);

router.delete('/deleteParticipant/:ID', checkRoleAdmin, deleteParticipant);

module.exports = {
    routes: router
}