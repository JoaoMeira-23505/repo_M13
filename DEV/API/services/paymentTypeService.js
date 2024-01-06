'use strict';
const utils = require('../utils/enums');
const PaymentType = require('../models/paymentTypeModel');

const listPaymentTypes = async () => {
    try {
        return await PaymentType.findAll();
    } catch (error) {
        return error.message;
    }
}

const listPaymentTypeById = async (Id)=> {
    try {
        return await PaymentType.findByPk(Id);
    }
    catch (error) {
        return  error.message;
    }
}


const createPaymentType = async (paymentTypeData) => {
    try {
        const newPaymentType = await PaymentType.create({
            name: paymentTypeData.name,
            description: paymentTypeData.description
        });

        return newPaymentType.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

const updatePaymentType = async (ID, paymentTypeData) => {
    try {
        const [updatedCount] = await PaymentType.update(paymentTypeData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedPaymentType = await PaymentType.findByPk(ID);
        return updatedPaymentType.toJSON();
    }
    catch (error) {
        return error.message;
    }
}

const deletePaymentType = async (ID) => {
    try {
        const deletedCount = await PaymentType.destroy({
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
    listPaymentTypes,
    listPaymentTypeById,
    createPaymentType,
    updatePaymentType,
    deletePaymentType
}