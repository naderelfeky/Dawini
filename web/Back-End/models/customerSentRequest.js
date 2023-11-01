const mongoose =require("mongoose") ;

const sentReq= new mongoose.Schema({

   customerId : String ,
   typeofservice : String , // x-ray  ,  تحليل معين 
   date : Date ,
   service : {
      type :[ String] ,
      default : []
     } 
   
     

    
    
}) ;


const sent = mongoose.model("sent Request" , sentReq) ;
module.exports = sent ;
