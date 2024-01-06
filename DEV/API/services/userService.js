'use strict';
const utils = require('../utils/enums');
const User = require('../models/userModel');

const config = require('../config');
const sql = require('mssql');

/**
 * Retrieves a list of users.
 * @async
 * @returns {Promise<Array<User> | string>} - A promise that resolves with an array of user objects
 * (with 'password' excluded) or an error message.
 */
const listUsers = async () => {
    try {
        return await User.findAll({
            attributes: { exclude: ['password'] },
        });
    } catch (error) {
        return error.message;
    }
}

/**
 * Finds a user by ID.
 * @async
 * @param {number} Id - ID of the user to search for.
 * @returns {Promise<User | string>} - A promise that resolves with the found user object or an error message.
 */
const listUserById = async (Id)=> {
    try {
        return await User.findByPk(Id, {
            attributes: { exclude: ['password'] },
        });
    }
    catch (error) {
        return  error.message;
    }
}

/**
 * Finds a user by email.
 * @async
 * @param {string} Email - Email address of the user to search for.
 * @returns {Promise<User | string>} - A promise that resolves with the found user object or an error message.
 */
const listUserByEmail = async (Email)=> {
    try {
        return await User.findOne(
            { where: { email: Email } }
        );
    }
    catch (error) {
        return  error.message;
    }
}

/**
 * Creates a new user with the provided user data.
 * @async
 * @param {User} userData - Information required to create a new user.
 * @returns {Promise<User | string>} - A promise that resolves with the newly created user object or an error message.
 */
const createUser = async (userData) => {
    try {
        const newUser = await User.create({
            name: userData.name,
            surname: userData.surname,
            email: userData.email,
            password: userData.password,
            gender: userData.gender,
            nationality: userData.nationality,
            nif: userData.nif,
            phoneNumber: userData.phoneNumber,
            status: utils.userStatus.Inactive,
            role: utils.userRoles.Normal,
            phoneToken: userData.phoneToken,
            userPhoto: userData.userPhoto
        });

        return newUser.toJSON();
    }
    catch (error) {
        return  error.message;
    }
}

/**
 * Updates an existing user's information based on the provided ID and userData.
 * @async
 * @param {number} ID - The ID of the user to be updated.
 * @param {User} userData - New information to update for the user.
 * @returns {Promise<User | { message: string } | string>} - A promise that resolves with the updated user object
 * (with 'password' excluded), a message indicating no update occurred, or an error message.
 */
const updateUser = async (ID, userData) => {
    try {
        const [updatedCount] = await User.update(userData, {
            where: { ID: ID },
        });

        if (updatedCount === 0)
            return { message: 'Something went wrong! Record not updated' };

        const updatedUser = await User.findByPk(ID, {
            attributes: { exclude: ['password'] },
        });
        return updatedUser.toJSON();
    }
    catch (error) {
        return error.message;
    }
}


/**
 * Updates the status of a user in the database.
 *
 * @async
 * @param {number} ID - The ID of the user to be updated.
 * @param {User} userData - New information to update for the user.
 * @returns {Promise<any>} - A Promise containing the result of the update operation.
 */
const updateUserStatus = async (ID, userData) => {
    try {
        let pool = await sql.connect(config.sql);
        let query = 'UPDATE [dbo].[User] SET status = @status ' +
            'WHERE [ID]=@ID';

        const update = await pool.request()
            .input('ID', sql.Int, ID)
            .input('status', sql.VarChar(255), userData.status)
            .query(query);

        return update.json;
    }
    catch (error) {
        return error.message;
    }
}

/**
 * Deletes an existing user based on the provided ID.
 * @async
 * @param {number} ID - The ID of the user to be deleted.
 * @returns {Promise<{ message: string }> | string} - A promise that resolves with a message indicating successful deletion,
 *  or a message indicating no deletion occurred due to an error.
 */
const deleteUser = async (ID) => {
    try {
        const deletedCount = await User.destroy({
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
    listUsers,
    listUserById,
    listUserByEmail,
    createUser,
    updateUser,
    updateUserStatus,
    deleteUser
}