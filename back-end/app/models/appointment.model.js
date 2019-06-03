const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

module.exports = new BaseModel('Appointment', {
  title: Joi.string().required(),
  desc: Joi.string().required(),
  date: Joi.date().required(),
  startTime: Joi.string().required(),
  endTime: Joi.string().required(),
  places: Joi.number().required(),
  teacherID: Joi.number().required(),
  studentsID: Joi.array().items(Joi.number()),
});
