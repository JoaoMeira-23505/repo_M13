'use strict';
const utils = require('../utils/enums');
const Payment = require('../models/paymentModel');
const User = require('../models/userModel');
const PaymentType = require('../models/paymentTypeModel');
const {Sequelize} = require("sequelize");

Payment.belongsTo(User, { foreignKey: 'UserID' });
Payment.belongsTo(PaymentType, { foreignKey: 'PaymentTypeID' });

const listPayments = async () => {
    try {
        return await Payment.findAll({
            include: [{
                model: User,
                attributes: ['name'],
                required: true,
            },{
                model: PaymentType,
                attributes: ['name'],
                required: true,
            }]
        });
    } catch (error) {
        return error.message;
    }
}

const listPaymentsByUserId = async (UserID)=> {
    try {
        return await Payment.findAll({
            include: [{
                model: User,
                attributes: ['name'],
                required: true,
            },{
                model: PaymentType,
                attributes: ['name'],
                required: true,
            }],
            where: { UserID: UserID },
            order: [['issueDate', 'ASC']],
        });
    }
    catch (error) {
        return  error.message;
    }
}

const listPaymentById = async (Id)=> {
    try {
        return await Payment.findByPk(Id, {
            include: [{
                model: User,
                attributes: ['name'],
                required: true,
            },{
                model: PaymentType,
                attributes: ['name'],
                required: true,
            }]
        });
    }
    catch (error) {
        return  error.message;
    }
}

const createPayment = async (paymentData) => {
    try {
        const newPayment = await Payment.create({
            description: paymentData.description,
            value: paymentData.value,
            issueDate: Sequelize.literal('CURRENT_TIMESTAMP'),
            status: utils.paymentStatus.Pending,
            PaymentTypeID: paymentData.PaymentTypeID,
            UserID: paymentData.UserID
        });

        return newPayment.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updatePayment = async (ID, paymentData) => {
    try {
        const [updatedCount] = await Payment.update(paymentData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedPayment = await Payment.findByPk(ID);
        return updatedPayment.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deletePayment = async (ID) => {
    try {
        const deletedCount = await Payment.destroy({
            where: { ID: ID },
        });

        if (deletedCount === 0)
            return { message: 'Something went wrong! Record not deleted' };

        return { message: 'Record deleted successfully' };
    }
    catch (error) {
        return error.message;
    }
}

module.exports = {
    listPayments,
    listPaymentsByUserId,
    listPaymentById,
    createPayment,
    updatePayment,
    deletePayment
}