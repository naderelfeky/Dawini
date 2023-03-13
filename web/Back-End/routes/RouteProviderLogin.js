const express = require ("express") ;
const router = express.Router() ;
const dotenv =require("dotenv") ;
dotenv.config();
const logValidator =require("../middlewares/providerMwLoginValid") ;
const User =require("../models/ProvidersModel") ;
const bcrypt = require ("bcrypt") ;
const genToken=require("../controllers/Jwt.js") ;
const jwt = require('jsonwebtoken');


router.post("/providers/signin" ,logValidator, async(req,res)=>{

    
         try {

                let usr = await User.findOne( { email : req.body.email }).exec() ; 
                if(!usr) return res.status(400).send("inValid email or password..") ;

                if(usr.provider=='google')
                return res.status(400).send("this account was logged with google , try sign in with your google account..") ;

                const validpswrd = await bcrypt.compare(req.body.password , usr.password) ;
                if(!validpswrd) return res.status(400).send("inValid email or password..") ;

                if(usr.isVerified==false)
                return res.status(400).send(" Email is not verified Please check your gmail and verify it..") ;

                if(usr.VerifiedByOffice ==false )
                return res.status(400).send("your account is pending , wait for your account activation by office");

                const token = genToken.generateToken(usr) ;
                res.json({msg:"login sucsess" ,
                    token:token});
                

                 
                  
               // res.status(200).send("Loged in sucsessfully..") ;

            
                        
          }
          catch(err){
                console.log(err) ;
          }
    
    
    
}) ;











module.exports=router ;