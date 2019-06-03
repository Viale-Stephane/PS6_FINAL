const { Router } = require('express');
const CourseRouter = require('./courses');
const UserRouter = require('./user');
const SpecialtyRouter = require('./specialties');
const AppointmentRouter = require('./appointments');
const SchoolRouter = require('./school');

const router = new Router();
router.get('/status', (req, res) => res.status(200).json('ok'));
router.use('/courses', CourseRouter);
router.use('/login', UserRouter);
router.use('/register', UserRouter);
router.use('/specialties', SpecialtyRouter);
router.use('/appointments', AppointmentRouter);
router.use('/school', SchoolRouter);

module.exports = router;
