const { Router } = require('express');
const { School } = require('../../models');

const router = new Router();

router.get('/', (req, res) => res.status(200).json(School.get()));
router.get('/:id', (req, res) => {
  try {
    const school = School.getById(req.params.id);
    res.status(200).json(school);
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});
router.delete('/:schoolId', (req, res) => {
  try {
    //console.log(req.params.schoolId);
    const schoolToDelete = School.getById(req.params.schoolId);
    //console.log(schoolToDelete);
    deleteSchoolConc(schoolToDelete);
    School.delete(req.params.schoolId);
    res.status(200).json('School delete successfully');
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});
router.put('/:Id', (req, res) => {
  try {
    School.update(req.params.Id, req.body);
    res.status(200).json(School.getById(req.params.Id));
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
    const school = School.create(req.body);
    const schoolList = [];
    recursiveFindSchools(school, schoolList);
    attachSchoolConc(schoolList);
    res.status(201).json(school);
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});

module.exports = router;
