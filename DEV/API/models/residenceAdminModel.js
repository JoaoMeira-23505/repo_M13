const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const ResidenceAdmin = sequelize.define('ResidenceAdmin', {
    ID: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,
    },
    startDate: {
        type: DataTypes.DATE,
        allowNull: false,
    },
    endDate: {
        type: DataTypes.DATE,
        allowNull: true,
    },
    UserID: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: 'User',
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
    tableName: 'ResidenceAdmin',
    timestamps: false
});

module.exports = ResidenceAdmin;