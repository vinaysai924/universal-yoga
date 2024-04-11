module.exports = (app)=>{
    const user = require('../controllers/user.controller')
    const book = require("../controllers/bookClasses.controller")
    

    app.post('/api/userSignupApi',user.userSignupApi)

    app.post('/api/userLoginApi',user.userLoginApi)

    app.put('/api/changePassword/:usersRegId', user.changePassword);

    app.get('/api/getUserDataById/:id',user.getUserDataById)

    app.put('/api/updateUserData/:id',user.updateUserData)

    app.delete('/api/deleteUserById/:usersRegId',user.deleteUserById)

    app.post("/api/bookClassesByUser/:userId/:classId",book.bookClassesByUser)

    
    app.get("/api/getBookingListData/:allActive",book.getBookingListData)

    app.get("/api/getClassByDayAndCourseNameForUser/:courseId/:Day/:Timing",book.getClassByDayAndCourseNameForUser)


}
