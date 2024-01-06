'use strict'
const jwt = require('jsonwebtoken');
const userService = require('../services/userService');
const chatService = require('../services/chatService');
const participantService = require('../services/participantService');

const authUser = async (req, res)=> {
    try {
        const { email, password } = req.body;
        const user = await userService.listUserByEmail(email);

        if (user?.password !== password) {
            return res.status(403).json({
                error: "Invalid Login!"
            });
        }

        user.password = undefined
        const token = jwt.sign({user}, process.env.SECRET_TOKEN );

        return res.status(200).json({Authorization: `${token}`});
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const registerUser = async (req, res)=> {
    try {
        const { email, password, confirmPassword } = req.body;
        const userCheckExist = await userService.listUserByEmail(email);

        if (userCheckExist?.email === email) {
            return res.status(409).json({
                error: "Email already in use!",
            });
        }

        if (password !== confirmPassword) {
            return res.status(409).json({
                error: "Password field doesn't match Confirm Password!"
            });
        }

        const newUserdata = req.body;
        const user = await userService.createUser(newUserdata);
        user.password = undefined

        const chatNotif = await chatService.createChat({ChatTypeID: 1});
        await participantService.createParticipant({UserID: user.ID, ChatID: chatNotif.ID})
        const chatPrivateNotif = await chatService.createChat({ChatTypeID: 2});
        await participantService.createParticipant({UserID: user.ID, ChatID: chatPrivateNotif.ID})

        const token = jwt.sign({user}, process.env.SECRET_TOKEN);

        return res.status(200).json({Authorization: `${token}`});
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    authUser,
    registerUser
}