const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const Room = sequelize.define('Room', {
    ID: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,
    },
    doorNumber: {
        type: DataTypes.STRING(255),
        allowNull: false,
    },
    floor: {
        type: DataTypes.STRING(255),
        allowNull: false,
    },
    status: {
        type: DataTypes.STRING(255),
        allowNull: true,
    },
    roomPhoto: {
        type: DataTypes.STRING(255),
        allowNull: true,
    },
    RoomTypeID: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'RoomType',
            key: 'ID'
        }
    },
    ResidenceID: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'Residence',
            key: 'ID'
        }
    },
}, {
    tableName: 'Room',
    timestamps: false
});

module.exports = Room;