const mongoose =require("mongoose") ;

const verificationCode = new mongoose.Schema({
   email: String ,
   code :String ,
   createdAt: { type: Date, default: Date.now, index: { expires: 600 } } ,
   

    
}) ;



const code = mongoose.model("verification code " ,verificationCode) ;
module.exports = code ;


