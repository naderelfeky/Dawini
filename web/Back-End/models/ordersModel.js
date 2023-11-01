const mongoose =require("mongoose") ;

const oredrsSchema= new mongoose.Schema({


    providerId: {
    type: String,
    

       } ,

    customerId:{
        type : String  ,
        
    } ,

    requestId : {
        type : String
    } ,


    done : Boolean

        
    
     

    
    
}) ;



const ord = mongoose.model("order" , oredrsSchema) ;
module.exports = ord ;
