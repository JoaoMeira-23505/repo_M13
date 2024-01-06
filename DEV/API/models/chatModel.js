const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const Chat = sequelize.define('Chat', {
    ID: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,
    },
    status: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    ChatTypeID: {
        type: DataTypes.INTEGER,
        allowNull: true,
        references: {
            model: 'ChatType',
            key: 'ID'
        }
    },
}, {
    tableName: 'Chat',
    timestamps: false
});

module.exports = Chat;