const mongoose =require("mongoose") ;

const applyReq= new mongoose.Schema({

   providerId : String ,
   customerId : String ,
   RequestId : String ,
   service : String , // x-ray  ,  تحليل معين 
   status :{type :String ,
    
    enum : ["PENDING" , "ACCEPTED" , "REJECTED"]
}  ,
   
   
     

    
    
}) ;


const apply = mongoose.model("applied Request" , applyReq) ;
module.exports = apply ;
