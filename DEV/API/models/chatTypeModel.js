const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const ChatType = sequelize.define('ChatType', {
    ID: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,
    },
    name: {
        type: DataTypes.STRING(255),
        allowNull: false,
    },
}, {
    tableName: 'ChatType',
    timestamps: false
});

module.exports = ChatType;