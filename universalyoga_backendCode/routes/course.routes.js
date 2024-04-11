module.exports  = (app)=>{
    const Course = require('../controllers/course.controller')

    app.post('/api/addYogaCourses',Course.addYogaCourses)

    app.get('/api/getCourseData/:allCourse',Course.getCourseData)

    app.put('/api/updateCoursesDetailsByCourseId/:id',Course.updateCoursesDetailsByCourseId)

    app.delete('/api/deleteCoursesData/:id', Course.deleteCoursesData);
}