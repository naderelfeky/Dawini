const mongoose =require("mongoose") ;
const config = require ("config") ;
const jwt = require("jsonwebtoken") ;
const providerSchema = new mongoose.Schema({

  
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

      photo: {
        type: String,
        default: 'default.jpg'
      } ,


  gender :{ type : String ,
    enum:["male","female"] ,


} ,

      isVerified: {
        type: Boolean,
        default: false,
    },

    VerifiedByOffice: {
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

    location: {
      type: { 
          type: String, 
          enum: ['Point'] 
      },
      coordinates: { 
          type: [Number] ,
          default:[]
       ,
      } ,

      


  } ,

  typeofservice :{

    type : String ,
   
    
    


  } ,
    
    requests :{
        type : [{name : String , id:String , realservice : String , address : String , number:String }] ,
      default : []
    }

  
    
} ,
{
  timestamps: true
}


) ;


providerSchema.index({ location: "2dsphere" }); 

const prov = mongoose.model("Provider" , providerSchema) ;


module.exports = prov ;
