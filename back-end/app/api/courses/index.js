const { Router } = require('express');
const { Course } = require('../../models');

const router = new Router();

function recursiveFindCourses(course, courseList) {
  if (!courseList.includes(course.id)) {
    courseList.push(course.id);
  } else {
    return undefined;
  }

  if (courseList.indexOf(-1) !== -1) {
    courseList.splice(courseList.indexOf(-1), 1);
    courseList.push(course.id);
  }

  for (let i = 0; i < course.courseConc.length; i += 1) {
    if (course.courseConc[i] !== -1) {
      const findedCourse = Course.getById(course.courseConc[i]);
      recursiveFindCourses(findedCourse, courseList);
    }
  }
  return courseList;
}

function attachCourseConc(course, courseList) {
  for (let i = 0; i < courseList.length; i += 1) {
    const currentCourse = Course.getById(courseList[i]);
    if (currentCourse !== course) {
      Course.getById(courseList[i]).optional = '1';
    }
    currentCourse.courseConc = courseList;
  }
}
function deleteCourseConc(courseToDelete) {
  const courseList = Course.get();
  for (let i = 0; i < courseList.length; i += 1) {
    const course = courseList[i];
    if (course.courseConc.indexOf(courseToDelete.id) !== -1) {
      course.courseConc.splice(course.courseConc.indexOf(courseToDelete.id), 1);
    }
  }
}

router.get('/', (req, res) => res.status(200).json(Course.get()));
router.get('/:courseId', (req, res) => {
  try {
    const course = Course.getById(req.params.courseId);
    res.status(200).json(course);
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});
router.delete('/:courseId', (req, res) => {
  try {
    // console.log(req.params.courseId);
    const courseToDelete = Course.getById(req.params.courseId);
    // console.log(courseToDelete);
    deleteCourseConc(courseToDelete);
    Course.delete(req.params.courseId);
    res.status(200).json('Course delete successfully');
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});
router.put('/:courseId', (req, res) => {
  try {
    Course.update(req.params.courseId, req.body);
    res.status(200).json(Course.getById(req.params.courseId));
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
    const course = Course.create(req.body);
    const courseList = [];
    recursiveFindCourses(course, courseList);
    attachCourseConc(course, courseList);
    res.status(201).json(course);
  } catch (err) {
    if (err.name === 'ValidationError') {
      res.status(400).json(err.extra);
    } else {
      res.status(500).json(err);
    }
  }
});

module.exports = router;
