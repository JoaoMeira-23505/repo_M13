const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const Device = sequelize.define('Device', {
    ID: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,
    },
    accessCode: {
        type: DataTypes.INTEGER,
        allowNull: false,
    },
    RoomID: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'Room',
            key: 'ID'
        }
    },
    DeviceTypeID: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'DeviceType',
            key: 'ID'
        }
    },
}, {
    tableName: 'Device',
    timestamps: false
});

module.exports = Device;