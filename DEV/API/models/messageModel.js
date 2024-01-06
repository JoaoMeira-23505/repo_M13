const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const Message = sequelize.define('Message', {
    ID: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,
    },
    text: {
        type: DataTypes.TEXT,
        allowNull: false,
    },
    dateSended: {
        type: DataTypes.DATE,
        allowNull: false,
    },
    ChatID: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'Chat',
            key: 'ID'
        }
    },
    UserID: {
        type: DataTypes.INTEGER,
        allowNull: true,
        references: {
            model: 'User',
            key: 'ID'
        }
    },
}, {
    tableName: 'Message',
    timestamps: false
});

module.exports = Message;