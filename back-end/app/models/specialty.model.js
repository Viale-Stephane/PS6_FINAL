const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

module.exports = new BaseModel('Specialty', {
  title: Joi.string().required(),
  abr: Joi.string().required(),
  description: Joi.string().required(),
});
