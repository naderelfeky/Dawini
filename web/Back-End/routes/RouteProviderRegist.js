const express = require ("express") ;
const router = express.Router() ;
const regValidator =require("../middlewares/providerMwRegistValid") ;
const User =require("../models/ProvidersModel") ;
const bcrypt = require ("bcrypt") ;
const send = require('../controllers/Nodemailer') ;
const verCode=require('../models/verificationCode') ;
const dotenv =require("dotenv") ;
dotenv.config();
const genToken=require("../controllers/Jwt.js") ;
const jwt =require('jsonwebtoken');


//regstration by email and password

router.post("/providers/signup" ,regValidator , async(req,res)=> {

    try {
   let usr = await User.findOne( { email : req.body.email }).exec() ;
  if(usr) {
    return res.status(400).send("user already exist..") ;
  } 

      let salt = await bcrypt.genSalt(10) ;
     let hashedPassword = await bcrypt.hash(req.body.password , salt) ;
     usr = new User({

        username : req.body.username ,
        email : req.body.email ,
        password :  hashedPassword  ,
        provider:'gmail' ,
        googleId:null ,
        location: {
            type: 'Point',
            coordinates: [req.body.longitude, req.body.latitude]
        } ,
        typeofservice:req.body.typeofservice

        

    }) ;
   
       // const token = genToken.generateToken(usr) ;
         
      await usr.save() ;
      await send.sendMail({email:req.body.email}) ;

      res.json({
        status:"PENDING" ,
        message:"Verification code sent",
       
      });
       
      

       
   
      

    }
    catch (err){
        console.log(err) ;
        console.log(`error `) ;
    }
}) ;




// Verify code 


router.post( "/providers/verifyusr",async (req, res) => {


    try {

     
      const email =req.body.email ;
        
  const code = await verCode.find({
      email: email
  });

  if (code.length === 0) return res.status(400).send("You use an Expired Code..! ");

  const rightCode = code[code.length - 1];
  const validUser = await bcrypt.compare(req.body.code, rightCode.code);

  if (rightCode.email === email && validUser) {
      await User.updateOne({email:email},{isVerified:true}) ;


      
       await verCode.deleteMany({
          email: rightCode.email
      });
      return res.status(200).send({
          message: "Authenticated..! ",
          
         
      });
  } else {
      return res.status(400).send("Your Code was wrong!")
  
    }
}
    
 

 catch (err){
    res.send(err);
    console.log(err) ;
    console.log(`error `) ;
}

} ) ;


// forget & Reset password 

router.post('/providers/sendcode', async(req,res)=>{
    
    try {

    const {email} = req.body ;
    
    const findUsr = await User.findOne({email:email}) ;
    if(findUsr){

        if (findUsr.provider == 'google') {

            res.send("User exists with Google account. Try resetting your google account password or logging using it") ;

    }
    else {
        
       await send.sendMail({email:email},res) ;

        res.send('verification code was sent , plz Check your Gmail..!') ;
    } 
        } 
            else {
                    res.send('No user exist with this email..')
        }

    }
    catch (err){
        console.log(err) ;
        console.log(`error `) ;
    }
}) ;



router.post('/providers/resetpass' , async(req,res)=>{

    try {

    const email = req.body.email ;
    const newPassword = req.body.newPassword ;
    if(!newPassword){
    res.send("PLease enter new password") ; }
    else if (newPassword.length<8){
        res.send("password should be at least 8 char") ;
    }
    else {
        let salt = await bcrypt.genSalt(10) ;
     let newHashedPassword = await bcrypt.hash(newPassword , salt) ;
        
    await User.findOneAndUpdate({ email: email }, { $set: { password: newHashedPassword } });
  
       
        res.status(200).send("Your password has updated..") ;
    }
    
}
    catch (err){
    console.log(err) ;
    console.log(`error `) ;
    }

});







//regstration by google










module.exports=router ;




