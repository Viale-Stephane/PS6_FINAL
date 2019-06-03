const { Router } = require('express');
const { Appointment } = require('../../models');

const router = new Router();

router.get('/', (req, res) => res.status(200).json(Appointment.get()));

router.post('/', (req, res) => {
  try {
    const appointment = Appointment.create(req.body);
    res.status(201).json(appointment);
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});

router.put('/:appointmentID', (req, res) => {
  try {
    res.status(200).json(Appointment.update(req.params.appointmentID, req.body));
  } catch (err) {
    if (err.name === 'NotFoundError') {
      res.status(404).end();
    } else if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});

router.delete('/:appointmentID', (req, res) => {
  try {
    Appointment.delete(req.params.appointmentID);
    res.status(204).end();
  } catch (err) {
    if (err.name === 'NotFoundError') {
      res.status(404).end();
    } else {
      res.status(500).json(err);
    }
  }
});

module.exports = router;
