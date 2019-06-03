const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

module.exports = new BaseModel('School', {
  name: Joi.string().required(),
  peopleInterested: Joi.any().required(),
  barChartData: Joi.any().required(),
  id: Joi.number().required(),
});
