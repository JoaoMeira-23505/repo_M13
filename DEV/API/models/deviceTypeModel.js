const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const DeviceType = sequelize.define('DeviceType', {
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
    tableName: 'DeviceType',
    timestamps: false
});

module.exports = DeviceType;