const mongoose =require("mongoose") ;

const providerRequest= new mongoose.Schema({


    providerId : String ,
    RequestId :String ,
   customerId : String ,
   name: String ,
   address : String ,
   number : String ,
   service : {
    type :[ String] ,
    default : []
   } , // x-ray  ,  تحليل معين 
   timeOfService:String
   
   
     

    
    
}) ;


const PR = mongoose.model("Provider Request" , providerRequest) ;
module.exports = PR ;
