const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const PaymentType = sequelize.define('PaymentType', {
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
    description: {
        type: DataTypes.TEXT,
        allowNull: true,
    },
}, {
    tableName: 'PaymentType',
    timestamps: false
});

module.exports = PaymentType;