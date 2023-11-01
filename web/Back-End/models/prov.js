const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const UserSchema = new Schema(
    {
        name: String,
        status: {
            type: Boolean,
            default: true
        },
        
            typeofservice :{
                type :String
            } ,
        
        location: {
            type: { 
                type: String, 
                enum: ['Point'] 
            },
            coordinates: { 
                type: [Number] 
            }
        }
    },
    {
        timestamps: true
    }
);

UserSchema.index({ location: "2dsphere" }); 

const User = mongoose.model("User", UserSchema);

module.exports = User;