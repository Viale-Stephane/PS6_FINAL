const { Router } = require('express');
const { Specialty } = require('../../models');

const router = new Router();
router.get('/', (req, res) => res.status(200).json(Specialty.get()));
router.get('/:specialtyId', (req, res) => {
  try {
    const specialty = Specialty.getById(req.params.specialtyId);
    res.status(200).json(specialty);
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});
router.delete('/:specialtyId', (req, res) => {
  try {
    Specialty.delete(req.params.specialtyId);
    res.status(200).json('specialty delete successfully');
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});
router.put('/:specialtyId', (req, res) => {
  try {
    Specialty.update(req.params.specialtyId, req.body);
    res.status(200).json(Specialty.getById(req.params.specialtyId));
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});
router.post('/', (req, res) => {
  try {
    const specialty = Specialty.create(req.body);
    res.status(201).json(specialty);
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});

module.exports = router;
