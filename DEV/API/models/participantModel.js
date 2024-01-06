const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const Participant = sequelize.define('Participant', {
    ID: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,
    },
    UserID: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'User',
            key: 'ID'
        }
    },
    ChatID: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'Chat',
            key: 'ID'
        }
    },
}, {
    tableName: 'Participant',
    timestamps: false
});

module.exports = Participant;