'use strict';
const utils = require('../utils/enums');
const Participant = require('../models/participantModel');

//TODO MAYBE ADD ASSOCIATIONS

const listParticipants = async () => {
    try {
        return await Participant.findAll();
        //TODO ALTER SELECT ALL, VIEW TODO LINE 5
    } catch (error) {
        return error.message;
    }
}

const listParticipantsByChatID = async (ChatID) => {
    try {
        return await Participant.findAll({
            where: { ChatID: ChatID }
        });
    } catch (error) {
        return error.message;
    }
}

const listParticipantById = async (Id)=> {
    try {
        return await Participant.findByPk(Id);
        //TODO ALTER SELECT BY PK, VIEW TODO LINE 5
    }
    catch (error) {
        return  error.message;
    }
}

const createParticipant = async (participantData) => {
    try {
        const newParticipant = await Participant.create({
            UserID: participantData.UserID,
            ChatID: participantData.ChatID
        });

        return newParticipant.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updateParticipant = async (ID, participantData) => {
    try {
        const [updatedCount] = await Participant.update(participantData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedParticipant = await Participant.findByPk(ID);
        return updatedParticipant.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deleteParticipant = async (ID) => {
    try {
        const deletedCount = await Participant.destroy({
            where: { ID: ID },
        });

        if (deletedCount === 0)
            return { message: 'Something went wrong! Record not deleted' };

        return { message: 'Record deleted successfully' };
    }
    catch (error) {
        return error.message;
    }
}

module.exports = {
    listParticipants,
    listParticipantsByChatID,
    listParticipantById,
    createParticipant,
    updateParticipant,
    deleteParticipant
}