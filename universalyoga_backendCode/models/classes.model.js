const mongoose = require('mongoose')
const Schema = mongoose.Schema;
const ObjectId = Schema.ObjectId;


const classesSchema = new Schema({
    Day: {
        type: String,
    },
    Timing: {
        type: String,
    },
    Capacity: {
        type: String,
    },
    Duration:{
        type:String,
    },
    price:{
        type:String,
    },
    Types_of_Class:{
        type:String,
    },
    Description:{
        type:String,
    },
    courseId: {
        type: Schema.Types.ObjectId,
        ref: 'Course',
        // required: true
    },
    Course_Name:{
        type:String,
    },
    Date:{
        type:String,
    },
    Teacher:{
        type:String,
    },
    Comments:{
        type:String,
    },
    deleteFlag: {
        type: Boolean,
        default: false
    }
}, {
    timestamps: true
})
module.exports = mongoose.model("Classes", classesSchema)