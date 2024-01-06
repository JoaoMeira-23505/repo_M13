const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const Residence = sequelize.define('Residence', {
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
    location: {
        type: DataTypes.STRING(255),
        allowNull: false,
    },
    phoneNumber: {
        type: DataTypes.INTEGER,
        allowNull: false,
    },
}, {
    tableName: 'Residence',
    timestamps: false
});

module.exports = Residence;