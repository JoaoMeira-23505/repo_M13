const { DataTypes } = require('sequelize');
const sequelize = require('../sequelize');

const Application = sequelize.define('Application', {
    ID: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,
    },
    dateSubmitted: {
        type: DataTypes.DATE,
        allowNull: false,
    },
    status: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    sosNumber: {
        type: DataTypes.INTEGER,
        allowNull: true,
    },
    courseName: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    courseYearAttended: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    courseYearStarted: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    lastYearStatus: {
        type: DataTypes.BOOLEAN,
        allowNull: false,
    },
    socialBenefits: {
        type: DataTypes.BOOLEAN,
        allowNull: false,
    },
    observations: {
        type: DataTypes.TEXT,
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
    tableName: 'Application',
    timestamps: false
});

module.exports = Application;