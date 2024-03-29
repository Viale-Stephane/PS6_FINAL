const { Router } = require('express');
const { User } = require('../../models');

const router = new Router();

router.get('/', (req, res) => res.status(200).json(User.get()));

router.get('/:userID', (req, res) => {
  try {
    const user = User.getById(req.params.userID);
    res.status(200).json(user);
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
    const user = User.create(req.body);
    res.status(201).json(user);
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});

module.exports = router;
