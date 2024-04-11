const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");
const config = require("../config/auth.config");
const { Mongoose } = require('mongoose');
const ObjectId = require('mongodb').ObjectID;
const user = require('../models/user.model')

function generateToken(userid) {
    return jwt.sign({ id: userid }, config.secret, { expiresIn: 15552000 });
}

exports.userSignupApi = async (req, res) => {
    try {
        const { user_Name, user_Email,  password } = req.body;

        // Validating email, full name, mobile number, password, and confirm password
        const emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s)/;
        const nameRegex = /^[a-zA-Z ]+$/;

        if (!user_Email) {
            return res.status(400).send({ message: "Email is required", status: 400 });
        } else if (!user_Email.match(emailRegex)) {
            return res.status(400).send({ message: "Please provide a valid Email address", status: 400 });
        }

        if (!user_Name) {
            return res.status(400).send({ message: "Full name is required", status: 400 });
        } else if (!nameRegex.test(user_Name)) {
            return res.status(400).send({ message: "Name should contain alphabets only", status: 400 });
        }

        

        if (!password) {
            return res.status(400).send({ message: "Password is required", status: 400 });
        } else if (!password.match(passwordRegex)) {
            return res.status(400).send({ message: "Password must be a combination of 8 characters long ( including at least one uppercase and one lowercase letter,a number, and a symbol)", status: 400 });
        }


        const checkEmail = await user.findOne({ user_Email }).lean();
        if (checkEmail) {
            return res.status(409).send({ message: 'Email already exists', status: 409 });
        }


        const data = await user.create({
            user_Name:user_Name.toLowerCase(),
            user_Email: user_Email.toLowerCase(),
            password: bcrypt.hashSync(password, 8)
        });

        return res.status(200).send({ data, message: "Congratulations! Your account has been created successfully!", status: 200 });

    } catch (error) {
        return res.status(500).send({ message: error.message || 'Some error occurred while creating an account', status: 500 });
    }
};

exports.userLoginApi = async (req, res) => {
    try {
        const user_Email = (req.body.user_Email || '').toLowerCase();
        const password = req.body.password || '';

        // Validation
        if (!user_Email || !password) {
            return res.status(400).send({ message: 'Please provide both email and password.', status: 400 });
        }

        const userData = await user.findOne({ "user_Email": user_Email, deleteFlag: false });

        if (!userData) {
            return res.status(404).send({ message: 'Your email is not registered with us.', status: 404 });
        }

        const passwordIsValid = bcrypt.compareSync(password, userData.password);
        if (!passwordIsValid) {
            return res.status(401).send({ message: 'Please enter a valid password.', status: 401 });
        }

        const token = generateToken(userData._id);
        return res.status(200).send({ accessToken: token, data: userData, message: 'Login successful!', status: 200 });
    } catch (error) {
        return res.status(500).send({ message: 'Internal server error.', status: 500 });
    }
};


exports.changePassword = async (req, res) => {
    try {
        const usersRegId = req.params.usersRegId;
        const oldPassword = req.body.oldPassword || '';
        const newPassword = req.body.newPassword || '';
        const confirmPassword = req.body.confirmPassword || '';
        const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s)/;


        // Validate request data
        if (!oldPassword || !newPassword || !confirmPassword) {
            return res.status(400).send({ message: 'All password fields must be provided', status: 400 });
        } else {
            if (!newPassword.match(passwordRegex)) {
                return res.status(400).send({ message: "Password must be a combination of 8 characters long ( including at least one uppercase and one lowercase letter,a number, and a symbol)", status: 400 });
            }
        }

        if (newPassword !== confirmPassword) {
            return res.status(401).send({ message: 'New password and confirm password do not match', status: 401 });
        }

        const existingUser = await user.findOne({ _id: usersRegId }).lean();
        if (!existingUser) {
            return res.status(404).send({ message: 'User not found', status: 404 });
        }

        const passwordIsValid = bcrypt.compareSync(oldPassword, existingUser.password);
        if (!passwordIsValid) {
            return res.status(401).send({ message: 'Incorrect old password', status: 401 });
        }

        await user.findOneAndUpdate({ _id: usersRegId }, { $set: { password: bcrypt.hashSync(newPassword, 8) } });

        return res.status(200).send({ message: 'Password changed successfully', status: 200 });
    } catch (err) {
        return res.status(500).send({ message: err.message || 'Error changing password', status: 500 });
    }
};


exports.getUserDataById = async (req, res) => {
    try {
        let id = req.params.id;
        let foundUser = await user.findById({ _id: id });

        if (!foundUser) {
            return res.status(404).send({ message: "User not found", status: 404 });
        }

        return res.status(200).send({ data: foundUser, message: "Success", status: 200 });
    } catch (error) {
        return res.status(500).send({ message: error.message, status: 500 });
    }
};


exports.updateUserData = async (req, res) => {
    try {
        const id = req.params.id;
        const user_Name = req.body.user_Name ? req.body.user_Name : '';
        const user_Email = req.body.user_Email ? req.body.user_Email : ''
        const mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

        // Validation: Check for empty/null email and correct format
        if (!user_Email || !user_Email.match(mailformat)) {
            return res.status(400).send({ message: "Email is incorrect or empty", status: 400 });
        }

        const existingUser = await user.findOne({ _id: id });

        // Check if email or mobile number is being updated and if they already exist
        const checkExisting = await user.findOne({
            $or: [{ user_Email }], 
            _id: { $ne: id } // Exclude current user's ID from the search
        });

        if (checkExisting) {
            if (checkExisting.user_Email === user_Email) {
                return res.status(409).send({ message: "Email already exists", status: 409 });
            } 
        }

        const updatedData = await user.findOneAndUpdate(
            { _id: id },
            {
                $set: {
                    user_Name: user_Name.toLowerCase(),
                    user_Email: user_Email.toLowerCase(),
                }
            },
            { new: true }
        );

        return res.status(200).send({ data: updatedData, message: "Successfully updated user data", status: 200 });

    } catch (error) {
        return res.status(500).send({ message: error.message, status: 500 });
    }
};

exports.deleteUserById = async (req, res) => {
    let id = req.params.usersRegId;
    try {
        let data = await user.findOneAndDelete({ _id: id });
        if (data) {
            return res.status(200).send({ data: data, message: "User profile deleted successfully", status: 200 });
        } else {
            return res.status(404).send({ message: 'No user found with this ID', status: 404 });
        }
    } catch (error) {
        return res.status(500).send({ message: `Error deleting user profile: ${error.message}`, status: 500 });
    }
};
