const express = require ("express") ;
const router = express.Router() ;
const dotenv =require("dotenv") ;
dotenv.config();
const logValidator =require("../middlewares/CustomerMwLoginValid") ;
const User =require("../models/CustomersModel") ;
const bcrypt = require ("bcrypt") ;
const genToken=require("../controllers/Jwt.js") ;
const jwt = require('jsonwebtoken');


router.post("/customers/signin" ,logValidator, async(req,res)=>{

    
         try {

                let usr = await User.findOne( { email : req.body.email }).exec() ; 
                if(!usr) return res.status(400).send("inValid email or password..") ;

                if(usr.provider=='google')
                return res.status(400).send("this account was logged with google , try sign in with your google account..") ;

                const validpswrd = await bcrypt.compare(req.body.password , usr.password) ;
                if(!validpswrd) return res.status(400).send("inValid email or password..") ;

                if(usr.isVerified==false)
                return res.status(400).send(" Email not verified Please , check your gmail and verify it..") ;

                const token = genToken.generateToken(usr) ;
                res.json({token:token});
                

                 
                  
               // res.status(200).send("Loged in sucsessfully..") ;

            
                        
          }
          catch(err){
                console.log(err) ;
          }
    
    
    
}) ;











module.exports=router ;