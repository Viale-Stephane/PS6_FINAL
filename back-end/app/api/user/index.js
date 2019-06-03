const { Router } = require('express');
const { User } = require('../../models');

const router = new Router();

router.get('/', (req, res) => res.status(200).json(User.get()));

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
