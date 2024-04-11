const Classes = require('../models/classes.model')
const course = require('../models/course.model')
const ObjectId = require('mongoose').ObjectId;

exports.addClassesByAdmin = async (req, res) => {
    try {

        const { Day, Timing, Capacity, Duration, price, Types_of_Class, Description } = req.body;

        if (!Day) {
            return res.status(400).send({ message: "please enter number of day is required", status: 400 });
        }

        if (!Timing) {
            return res.status(400).send({ message: "please enter time of course", status: 400 });
        }

        if (!Capacity) {
            return res.status(400).send({ message: "please enter Capacity", status: 400 });
        }

        if (!Duration) {
            return res.status(400).send({ message: "please enter duration of time like 1 hour , 2 hour", status: 400 });
        }

        if (!price) {
            return res.status(400).send({ message: "please enter price per class ", status: 400 });
        }

        if (!Types_of_Class) {
            return res.status(400).send({ message: "please enter type of class", status: 400 });
        }

        if (!Description) {
            return res.status(400).send({ message: "please enter description", status: 400 });
        }


        const data = await Classes.create({
            Day: Day,
            Timing: Timing,
            Capacity: Capacity,
            Duration: Duration,
            price: price,
            Types_of_Class: Types_of_Class,
            Description: Description,
        });

        return res.status(200).send({ data, message: "Congratulations! Your class data has been saved successfully!", status: 200 });

    } catch (error) {
        return res.status(500).send({ message: error.message || 'Some error occurred while adding  an classes', status: 500 });
    }
};

exports.getClassDataByAdmin = async (req, res) => {
    try {
        const modelCondition = req.params.allModel;
        let getModelCondition = {};

        if (modelCondition.startsWith('courseId_')) {
            getModelCondition = { courseId: modelCondition.split('_')[1] };
        } else if (modelCondition.startsWith('id_')) {
            getModelCondition = { _id: modelCondition.split('_')[1] };
        } else if (modelCondition !== 'All') {
            return res.status(400).send({ message: 'Please provide a valid condition (courseId_, id_, or All)', status: 400 });
        }

        const data = await Classes.find(getModelCondition);
        // console.log(data)
        return res.status(200).send({ data: data, message: 'Class data fetched successfully', status: 200 });
    } catch (error) {
        return res.status(500).send({ message: error.message, status: 500 });
    }
};


exports.getClasses = async (req, res) => {
    try {
        let id = req.params.id;
        let data = await Classes.findById({ _id: id });

        if (!data) {
            return res.status(404).send({ message: "class data  not found", status: 404 });
        }

        return res.status(200).send({ data: data, message: "Success", status: 200 });
    } catch (error) {
        return res.status(500).send({ message: error.message, status: 500 });
    }
};



exports.updateClassesByAdmin = async (req, res) => {

    const id = req.params.id;
    const courseId = req.params.courseId;
    try {
        const { Date, Teacher, Comments } = req.body;

        if (!Date) {
            return res.status(400).send({ message: "please enter Date", status: 400 });
        }

        if (!Teacher) {
            return res.status(400).send({ message: "please enter Teacher name", status: 400 });
        }

        if (!Comments) {
            return res.status(400).send({ message: "please enter Comments", status: 400 });
        }

        const getdata = await Classes.findOne({ _id: id })
        // console.log(getdata)

        const coursedata = await course.findOne({ _id: courseId })

        const coursename = coursedata.Course_Name;
        // console.log(coursename)


        const data = await Classes.findByIdAndUpdate({ _id: id }, {
            $set:
            {
                courseId: courseId,
                Course_Name: coursename,
                Date: Date,
                Teacher: Teacher,
                Comments: Comments


            }
        },
            { new: true });

        return res.status(200).send({ data, message: "Congratulations! In class data course data and teacher data  has been updated successfully!", status: 200 });

    } catch (error) {
        return res.status(500).send({ message: error.message || 'Some error occurred while updating an classes', status: 500 });
    }
};

exports.editClassesDataByAdmin = async (req, res) => {

    const id = req.params.id;
    // const courseId = req.params.courseId;
    try {
        const { Day, Timing, Capacity, Duration, price, Types_of_Class, Description, Date, Teacher, Comments } = req.body;

        if (!Day) {
            return res.status(400).send({ message: "please enter number of day is required", status: 400 });
        }

        if (!Timing) {
            return res.status(400).send({ message: "please enter time of course", status: 400 });
        }

        if (!Capacity) {
            return res.status(400).send({ message: "please enter Capacity", status: 400 });
        }

        if (!Duration) {
            return res.status(400).send({ message: "please enter duration of time like 1 hour , 2 hour", status: 400 });
        }

        if (!price) {
            return res.status(400).send({ message: "please enter price per class ", status: 400 });
        }

        if (!Types_of_Class) {
            return res.status(400).send({ message: "please enter type of class", status: 400 });
        }

        if (!Description) {
            return res.status(400).send({ message: "please enter description", status: 400 });
        }

        if (!Date) {
            return res.status(400).send({ message: "please enter Date", status: 400 });
        }

        if (!Teacher) {
            return res.status(400).send({ message: "please enter Teacher name", status: 400 });
        }

        if (!Comments) {
            return res.status(400).send({ message: "please enter Comments", status: 400 });
        }

        const getdata = await Classes.findOne({ _id: id })
        // console.log(getdata)

        // const coursedata = await course.findOne({ _id: courseId })

        // const coursename = coursedata.Course_Name;
        // console.log(coursename)


        const data = await Classes.findByIdAndUpdate({ _id: id }, {
            $set:
            {
                Day: Day,
                Timing: Timing,
                Capacity: Capacity,
                Duration: Duration,
                price: price,
                Types_of_Class: Types_of_Class,
                Description: Description,
                Date: Date,
                Teacher: Teacher,
                Comments: Comments


            }
        },
            { new: true });

        return res.status(200).send({ data, message: "Congratulations! Your class data has been updated successfully!", status: 200 });

    } catch (error) {
        return res.status(500).send({ message: error.message || 'Some error occurred while updating an classes', status: 500 });
    }
};


exports.deleteClassDataById = async (req, res) => {
    let id = req.params.id;
    try {
        let data = await Classes.findOneAndDelete({ _id: id });
        if (data) {
            return res.status(200).send({ data: data, message: "class profile deleted successfully", status: 200 });
        } else {
            return res.status(404).send({ message: 'No class found with this ID', status: 404 });
        }
    } catch (error) {
        return res.status(500).send({ message: `Error deleting class profile: ${error.message}`, status: 500 });
    }
};