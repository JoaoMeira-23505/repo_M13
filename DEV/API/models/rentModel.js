const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const Rent = sequelize.define('Rent', {
    ID: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,
    },
    entryDate: {
        type: DataTypes.DATE,
        allowNull: false,
    },
    leaveDate: {
        type: DataTypes.DATE,
        allowNull: true,
    },
    status: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    doorAccessCode: {
        type: DataTypes.STRING,
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
    RoomID: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'Room',
            key: 'ID'
        }
    },
}, {
    tableName: 'Rent',
    timestamps: false
});

module.exports = Rent;