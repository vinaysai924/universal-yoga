const { Mongoose } = require('mongoose');
const ObjectId = require('mongodb').ObjectID;
const book = require("../models/book.model")
const user = require("../models/user.model")
const className = require("../models/classes.model");

exports.getClassByDayAndCourseNameForUser = async(req,res)=>{
  let courseId = req.params.courseId;
  let Day = req.params.Day;
  let Timing = req.params.Timing;

  try {
    let data = await className.findOne({courseId:courseId,Day:Day,Timing:Timing},{
      price:1
    })
       if(!data){
        return res.status(404).send({message:"No class found",status:404})

       }
    return res.status(200).send({data:data,message:"Success",status:200})
  } catch (error) {
    return res.status(500).send({message:error.message,status:500})

  }
}

exports.bookClassesByUser = async(req,res)=>{
    let userId = req.params.userId;
    let classId = req.params.classId;

    try {
        let classData = await className.findOne({_id:classId})
        let userData = await user.findOne({_id:userId}) 
        let data = await book.create({
            userId:userId,
            classId:classId,
            userName:userData.user_Name,
            userEmail:userData.user_Email,
            Day:classData.Day, 
            Timing:classData.Timing, 
            Capacity:classData.Capacity, 
            Duration: classData.Duration,
            price:classData.price, 
            Class:classData.Class, 
            Description:classData.Description,
            date:classData.Date,
            teachers_name:classData.Teacher,
            comments:classData.comments,
            Course_Name:classData.Course_Name,
            Types_of_Class:classData.Types_of_Class,



        })

        return res.status(200).send({data:data,message:"Success",status:200})
    } catch (error) {
        return res.status(500).send({message:error.message,status:500})
    }

}

exports.getBookingListData = async(req,res)=>{
    try {

        
        let userAccording = req.params.allActive;
        console.log(userAccording);
        let userTypeSplit = userAccording.split("_");
        let getUserCondition = '';
        switch (userTypeSplit[0]) {
          case 'All':
            getUserCondition = {}; 
              break;
              case 'userId':
                getUserCondition = { userId:userTypeSplit[1]};
                  break;
          case 'id':
            getUserCondition = { _id:userTypeSplit[1]};
              break;
      
          default:
            getUserCondition = { message:"Please provide valid type"};
              break;
        }
        let data = await book.find(getUserCondition)
        if(!data){
            return res.status(404).send({data:data,message:"No data fount",status: 404});
        }else{
        return res.status(200).send({data:data,message:"Success",status: 200});
        }
    } catch (error) {
        return res.status(500).send({ message: error.message, "status": 500 })

    }
}