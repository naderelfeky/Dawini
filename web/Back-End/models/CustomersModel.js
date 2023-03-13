const mongoose =require("mongoose") ;

const CustomerRegistSchema = new mongoose.Schema({



  username: {
    type: String,
    required: true,

       } ,

    email :{
        type : String  ,
         unique : true ,
         required :true  ,
      
        
    } ,
      password : {
        type : String ,
        
      
      } ,
      isVerified: {
        type: Boolean,
        default: false,
    },

   

    googleId: {
        type: String,
    },
    provider: {
        type: String,
        required: true,
    } ,



 
    
    sentrequests :{
        type : [{typeofservice : String , date:Date  }] ,
      default : []
    }

  
    
} ,
{
  timestamps: true
}


) ;



const cust = mongoose.model("Customer" ,CustomerRegistSchema) ;
module.exports = cust ;


