const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const raySchema = new Schema(
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


const ray = mongoose.model("Ray Service", raySchema);

module.exports = ray;