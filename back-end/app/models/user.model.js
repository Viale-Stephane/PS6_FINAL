const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

module.exports = new BaseModel('User', {
  username: Joi.string().required(),
  password: Joi.string().required(),
  professor: Joi.boolean().required(),
  destination: Joi.string(),
  phoneNumber: Joi.string().required(),
});
