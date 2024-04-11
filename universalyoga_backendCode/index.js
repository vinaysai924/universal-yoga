const express = require('express')
const cors = require('cors')
const app = express()
const bodyParser = require('body-parser');
const path = require('path')
const multer = require('multer')
const mongoose = require('mongoose')
const db = require('./models/db.connection.on');
const { appendFileSync } = require('fs');
app.use(express.json())
app.use(bodyParser.json());
app.use(cors());
app.use(express.urlencoded({ extended: true }))
app.use('/uploads', express.static('uploads'));


db.mongoose.connect(db.url, {
    // useNewUrlParser:true,
    // useUnifiedTopology:true,
}).then(() => {
    console.log("Mongodb Connected");
})
    .catch((err) => {
        console.log("Failed to Connect", err)
        process.exit();
    })
app.get('/', function (req, res) {
    res.sendFile(path.join(__dirname + '/index.html'));
    //__dirname : It will resolve to your project folder.
});








require('./routes/admin.routes')(app)
require('./routes/user.routes')(app)
require('./routes/course.routes')(app)
require('./routes/classes.routes')(app)

app.listen(80, () => {
    console.log("Server Started")
});

