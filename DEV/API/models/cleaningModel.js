const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const Cleaning = sequelize.define('Cleaning', {
    ID: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,
    },
    date: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    status: {
        type: DataTypes.STRING,
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
    UserID: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'User',
            key: 'ID'
        }
    },
}, {
    tableName: 'Cleaning',
    timestamps: false
});

module.exports = Cleaning;