'use strict';

const userRoles = {
    Normal: 'Normal',
    CleaningStaff: 'Cleaning Staff',
    Admin: 'Admin'
}

const userStatus = {
    Active: 'Active',
    WaitList: 'Wait List',
    Inactive: 'Inactive'
}

const roomStatus = {
    Occupied: 'Occupied',
    SemiOccupied: 'SemiOccupied',
    Unoccupied: 'Unoccupied'
}

const rentStatus = {
    Active: 'Active',
    Inactive: 'Inactive'
}

const paymentStatus = {
    Pending: 'Pending',
    Fine: 'Fine',
    Paid: 'Paid'
}

const cleaningStatus = {
    NotPaid: 'Not Paid',
    NotClean: 'Not Clean',
    Clean: 'Clean',
    Canceled: 'Canceled'
}

const chatStatus = {
    Active: 'Active',
    Inactive: 'Inactive'
}

const applicationStatus = {
    Accepted: 'Accepted',
    Refused: 'Refused',
    Waiting: 'Waiting',
    AcceptedButWaiting: 'Accepted but Waiting'
}

module.exports = {
    userRoles,
    userStatus,
    roomStatus,
    rentStatus,
    paymentStatus,
    cleaningStatus,
    chatStatus,
    applicationStatus
}