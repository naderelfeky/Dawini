const mongoose =require("mongoose") ;

const Requests= new mongoose.Schema({

    RequestId :String ,
    customerId : String ,
    providerIds : { type : [{ }] ,
    default : []
                    } ,
    name: String ,
    address : String ,
    number : String ,
    service : String ,
    date : Date
   

     

    
    
}) ;


const req = mongoose.model("Request" , Requests) ;
module.exports = req ;
