const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

module.exports = new BaseModel('Course', {
  title: Joi.string().required(),
  description: Joi.string().required(),
  ects: Joi.string().required(),
  hour: Joi.string().required(),
  year: Joi.string().required(),
  courseConc: Joi.array().items(Joi.number()),
  optional: Joi.string(),
  spe: Joi.string(),
});
