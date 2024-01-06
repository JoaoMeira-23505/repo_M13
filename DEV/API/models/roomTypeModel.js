const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const RoomType = sequelize.define('RoomType', {
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
    price: {
        type: DataTypes.REAL,
        allowNull: false,
    },
    description: {
        type: DataTypes.TEXT,
        allowNull: true,
    },
}, {
    tableName: 'RoomType',
    timestamps: false
});

module.exports = RoomType;