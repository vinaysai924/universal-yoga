const Course = require('../models/course.model')


exports.addYogaCourses = async (req, res) => {
    const Course_Name = req.body.Course_Name ? req.body.Course_Name.trim() : '';
    

    try {
        if (!Course_Name) {
            return res.status(400).send({ message: 'Course Name is required', status: 400 });
        }


        const check = await Course.findOne({ Course_Name: Course_Name, deleteFlag: false }).lean();
        if (check) {
            return res.status(400).send({ message: "Course Name already exists", status: 400 });
        }

        const data = await Course.create({
            Course_Name: Course_Name,
        });

        return res.status(200).send({ data: data, message: "Course Name details added successfully", status: 200 });
    } catch (err) {
        return res.status(500).send({ message: err.message, status: 500 });
    }
};


exports.getCourseData = async (req, res) => {
    try {
        const courseCondition = req.params.allCourse;
        let getCourseCondition = {};

        if (courseCondition.startsWith('id_')) {
            getCourseCondition = { _id: courseCondition.split('_')[1] };
        }
        else if (courseCondition !== 'All') {
            return res.status(400).send({ message: 'Please provide a valid condition (id_, or All)', status: 400 });
        }

        const data = await Course.find(getCourseCondition);
        // console.log(data)
        return res.status(200).send({ data: data, message: 'course data fetched successfully', status: 200 });
    } catch (error) {
        return res.status(500).send({ message: error.message, status: 500 });
    }
};




//function to update course name

exports.updateCoursesDetailsByCourseId = async (req, res) => {
    const id = req.params.id;
    const { Course_Name } = req.body;

    try {
        if (!Course_Name) {
            return res.status(400).send({ message: 'Course  Name is required', status: 400 });
        }

        const existingVehicle = await Course.findById(id)
;
        if (!existingVehicle) {
            return res.status(404).send({ message: 'Course_Name not found', status: 404 });
        }

        const checkExisting = await Course.findOne({ 
            Course_Name: Course_Name,
            deleteFlag: false 
        });

        if (checkExisting && checkExisting._id.toString() !== id) {
            return res.status(400).send({ message: 'Course  Name already exists', status: 400 });
        }

        const data = await Course.findByIdAndUpdate(
            id,
            {
                $set: {
                    Course_Name: Course_Name,
                }
            },
            { new: true }
        );

        return res.status(200).send({ data: data, message: 'Course Name details updated successfully', status: 200 });
    } catch (error) {
        return res.status(500).send({ message: error.message, status: 500 });
    }
};


exports.deleteCoursesData = async (req, res) => {
    let id = req.params.id;
    try {
        let data = await Course.findOneAndDelete({ _id: id });
        if (data) {
            return res.status(200).send({ data: data, message: "course  name deleted successfully", status: 200 });
        } else {
            return res.status(404).send({ message: 'No courses found with this ID', status: 404 });
        }
    } catch (error) {
        return res.status(500).send({ message: `Error deleting class profile: ${error.message}`, status: 500 });
    }
};