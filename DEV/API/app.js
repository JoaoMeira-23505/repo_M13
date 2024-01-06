'use strict'
const express = require('express');
const cookieParser = require('cookie-parser');
const cors = require('cors');
const bodyParser = require('body-parser');
const app = express();
const expressWs = require('express-ws')(app);

app.use(cors());
app.use(bodyParser.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParser());

const baseUrl = '/unihome/api'
app.use(baseUrl, require('./routes/_authRoutes').routes);
app.use(baseUrl, require('./routes/userRoutes').routes);
app.use(baseUrl, require('./routes/paymentRoutes').routes);
app.use(baseUrl, require('./routes/paymentTypeRoutes').routes);
app.use(baseUrl, require('./routes/deviceRoutes').routes);
app.use(baseUrl, require('./routes/deviceTypeRoutes').routes);
app.use(baseUrl, require('./routes/residenceRoutes').routes);
app.use(baseUrl, require('./routes/roomRoutes').routes);
app.use(baseUrl, require('./routes/roomTypeRoutes').routes);
app.use(baseUrl, require('./routes/chatTypeRoutes').routes);
app.use(baseUrl, require('./routes/chatRoutes').routes);
app.use(baseUrl, require('./routes/cleaningRoutes').routes);
app.use(baseUrl, require('./routes/participantRoutes').routes);
app.use(baseUrl, require('./routes/messageRoutes').routes);
app.use(baseUrl, require('./routes/rentRoutes').routes);
app.use(baseUrl, require('./routes/applicationRoutes').routes);
app.use(baseUrl, require('./routes/residenceAdminRoutes').routes);

const chatRooms = {};
const messageService = require("./services/messageService");
const participantService = require("./services/participantService");
const userService = require("./services/userService");
const {getAccessToken} = require("./utils/getPushNotifToken");
const axios = require("axios");
app.ws('/unihome/ws/:chatID', function(ws, req) {
    const chatID = req.params.chatID;

    if (!chatRooms[chatID]) {
        chatRooms[chatID] = new Set();
    }

    const chatUsers = chatRooms[chatID];
    chatUsers.add(ws);

    ws.on('message', async function (msg) {
        try {
            const messageData = JSON.parse(msg);
            const newMessage = await messageService.createMessage(messageData);
            const participants = await participantService.listParticipantsByChatID(messageData.ChatID);

            for (const participant of participants){
                if (participant.UserID !== messageData.UserID) {
                    try {
                        const token = await getAccessToken()
                        const senderUser = await userService.listUserById(messageData.UserID);
                        const receivingUser = await userService.listUserById(participant.UserID);

                        if (receivingUser.phoneToken != null) {
                            const config = {
                                headers: { Authorization: `Bearer ${token}` }
                            };

                            const bodyParameters = {
                                message: {
                                    token: receivingUser.phoneToken,
                                    notification: {
                                        body: messageData.text,
                                        title: senderUser.name
                                    }
                                }
                            };

                            axios.post(
                                'https://fcm.googleapis.com/v1/projects/unihome-32de5/messages:send',
                                bodyParameters,
                                config
                            )
                            .then(function (response) {
                                console.log('Successful response:', response.data);
                            })
                            .catch(function (error) {
                                console.error('Error:', error.message);
                            })
                        }
                    } catch (error) {
                        console.log('Error fetching user data:', error);
                    }
                }
            }

            // Broadcast the newly created message to all clients
            chatUsers.forEach(client => {
                if (client.readyState === client.OPEN) {
                    client.send(JSON.stringify(newMessage));
                }
            });
        } catch (error) {
            console.error("Error while processing message:", error);
        }
    });

    ws.on('close', function () {
        // Remove disconnected client from the set
        chatUsers.delete(ws);

        // Delete the chat room if there are no more users in it
        if (chatUsers.size === 0) {
            delete chatRooms[chatID];
        }
    });
});

module.exports = app;