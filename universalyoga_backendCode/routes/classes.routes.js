module.exports = (app)=>{

    const Classes = require('../controllers/classes.controller')

    app.post('/api/addClassesByAdmin',Classes.addClassesByAdmin)

    app.get('/api/getClassDataByAdmin/:allModel',Classes.getClassDataByAdmin)

    app.put('/api/getClasses/:id/:courseId',Classes.getClasses)

    app.put('/api/editClassesDataByAdmin/:id',Classes.editClassesDataByAdmin)

    app.delete('/api/deleteClassDataById/:id',Classes.deleteClassDataById)
}