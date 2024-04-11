const mongoose = require('mongoose');
var Schema = mongoose.Schema;
var ObjectId = Schema.ObjectId;

const bookingSchema = mongoose.Schema({

    userId:{
        type: mongoose.Schema.Types.ObjectId,
        ref:"User"
    },
    classId:{
        type: mongoose.Schema.Types.ObjectId,
        ref:"Classes"
    },
    userName :{
        type: String
       },
    userEmail:{
        type:String
    }, Day: {
        type: String,
    },
    Timing: {
        type: String,
    },
    Capacity: {
        type: String,
    },
    Duration:{
        type:Number,
    },
    Course_Name:{
        type:String
    },
    Types_of_Class:{
        type:String

    },
    price:{
        type:String,
    },
    Class:{
        type:String,
    },
    Description:{
        type:String,
    },
    date:{
        type:String,
 
    },
    teachers_name:{
        type:String,
    },
    comments:{
        type:String
    }



     
   
},{timestamps : true})

module.exports = mongoose.model("book_yoga", bookingSchema)