module.exports = (app)=>{
    const admin  = require('../controllers/admin.controller')

    app.post('/api/createAdmin', admin.createAdmin);

    app.post('/api/adminLogin', admin.adminLogin);


}