const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const Payment = sequelize.define('Payment', {
    ID: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,
    },
    description: {
        type: DataTypes.TEXT,
        allowNull: false,
    },
    value: {
        type: DataTypes.REAL,
        allowNull: false,
    },
    issueDate: {
        type: DataTypes.DATE,
        allowNull: false,
    },
    paymentDate: {
        type: DataTypes.DATE,
        allowNull: true,
    },
    status: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    PaymentTypeID: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'PaymentType',
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
    tableName: 'Payment',
    timestamps: false
});

module.exports = Payment;