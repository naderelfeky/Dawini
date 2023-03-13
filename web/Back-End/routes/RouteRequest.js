const express = require ("express") ;
const router = express.Router() ;
const dotenv = require('dotenv');
dotenv.config();
const verify = require("../controllers/Jwt.js") ;
var jwt = require('jsonwebtoken');
const User=require("../models/ProvidersModel");
const near = require("../controllers/NearestProviders");
const SentReq=require("../models/customerSentRequest") ;
const ProvReq =require("../models/ProviderRequest") ;
const AppliedReq = require("../models/applyRequest") ;

router.post("/addreq" , async(req,res)=>{


    const token = req.header("Authorization") ;
    let data =jwt.verify(token,process.env.JWT_SECRET) ;
    if(data){
        

        const typeofservice = req.body.typeofservice ;
        const realservice = req.body.realservice ;
        const address = req.body.address ;
        const number = req.body.number ;
        const latitude=req.body.latitude ;  //           بتوع ال    
        const longitude=req.body.longitude ; //     customer 
        const name=data.name ;
        //const customerProfile=


                const users= await near.NearestProviders({longitude:longitude,latitude:latitude,typeofservice:typeofservice}) ;
        
        // loop and send request 
        // save request in "sent requests"

         

    }
    else{
        res.send("invalid token");
    }

   

});


router.post("/cancelrequest" , (req,res)=>{



});
















router.post("/customers/sendreq" , async(req,res)=>{

    try {
    

    //const token = req.header("Authorization") ;
    //let data =jwt.verify(token,process.env.JWT_SECRET) ;
    //if(data){
    const customerId="640c997a9e34ed16fa467a8d";
    const latitude = 30.941733;
    const longitude = 30.680024 ;
    const typeofservice="nurse";
    const service= ["تركيب محاليل","حقنة","تركيب كالونا"] ;
   
    const name="ahmed elhosary" ;
    const number="01210615654" ;
    const address ="zawya" ;
    let gender = "anyone";
    let timeOfService="3:00"

    const Sent= new SentReq ({

        customerId : customerId ,
        date:Date.now(),
        typeofservice:typeofservice,
        service:service



    }) ;

    
 
    await Sent.save() ;

    const users= await near.NearestProviders({
        longitude:longitude,latitude:latitude,typeofservice:typeofservice ,gender:gender}) ;
 
    for(let i=0; i<users.length ;i++){
        
        const provReq= new ProvReq({

        providerId:users[i]._id,    
        RequestId: Sent._id, 
        customerId:customerId ,
        typeofservice:typeofservice,
        service:service ,
        name:name ,
        address : address ,
        number:number ,
        timeOfService:timeOfService
        
        }) ;

        await provReq.save() ;
      


    }

    
    res.send(users);
    //res.status(200).json({ msg : "Request Sent..",RequestId:Sent._id});   
        
}
   
//else{
  //  res.send("invalid token");
//}

 catch (error) {
      res.status(400).send(error)  ;
}
}) ;










router.post("/customers/cancelreq" ,async (req,res)=>{

    try {

    const reqId= req.body.reqId 


   await SentReq.deleteOne({_id:reqId}) ;
   await ProvReq.deleteMany({RequestId :reqId}) ;

   res.status(200).send(`Request canceled`)
    }

    catch (error) {
        res.status(400).send(error)  ;
  }

}) ;






router.get("/customers/getreq" ,async(req,res)=>{


    //check auth 


        try {

        const customerId= "64074e41d3581b0035c8dc86" ;
        const requests = await SentReq.find({customerId:customerId}) ;
        res.status(200).send(requests) ;

        }
    
        catch (error) {
            res.status(400).send(error)  ;
      }
}) ;


router.get("/providers/getreq" ,async(req,res)=>{


    //check auth 


        try {

        const providerId= "6403a0a6c1225b6b679b7708" ;
        const requests = await ProvReq.find({providerId:providerId}) ;
        res.status(200).send(requests) ;

        }
    
        catch (error) {
            res.status(400).send(error)  ;
      }
}) ;




router.post("/providers/applyreq" , async(req,res)=>{

    // Token Plz

    try {

        const reqId= "6408f10066d35a29ab1f8a5e" ;  // req.body.reqId ;
        const customerId ="64074e41d3581b0035c8dc86" //req.body.customerId ;
        const providerId ="6403a0a6c1225b6b679b7707" //req.body.providerId ;
        let typeofservice="nurse"                    //req.body.typeofservice  ;
        const service = "تركيب محاليل"  ; //req.body.service  ;
        let priceOfService=req.body.priceOfService // + سعرها الاصلي

        //const profile = 

       let apply = new AppliedReq({

        providerId :providerId ,
        customerId : customerId ,
        RequestId : reqId ,
        service :  service ,

        status : "PENDING" 


        }) ;

        await ProvReq.deleteOne({RequestId : reqId}) ;
        await apply.save() ;

        res.status(200).json({
            msg : "Your application sent , wait for customer accept " ,
            appData:apply
        }) ;


        
        
     
        }
    
        catch (error) {
            res.status(400).send(error)  ;
      }

}) ;



// get provider application 

router.get("/customers/getapp" , async(req,res)=>{

    // Token Plz

    try {

        const customerId = "64074e41d3581b0035c8dc86" ;
        
        const getApp = await AppliedReq.find({customerId:customerId},{customerId:false}) ;
        res.status(200).send(getApp) ;
        
     
        }
    
        catch (error) {
            res.status(400).send(error)  ;
      }

}) ;




router.post("/customers/acceptapp" , (req,res)=>{

        
})


router.post("/customers/rejectapp" , (req,res)=>{

        
})









router.post("/providers/insert" , async(req,res)=>{


    await User.insertMany([
        {

        username : "batanon" ,
        email : "aelhosary@gmail.com" ,
        password :  "hashedPassword"  ,
        provider:'gmail' ,
        googleId:null ,
        location: {
            type: 'Point',
            coordinates: [30.610238 ,30.987055 ]
        } ,
        typeofservice:"nurse" ,
        isVerified:true ,
        VerifiedByOffice:true ,
        request:[] ,

    } ,

    {

        username : "zawya" ,
        email : "hemeida@gmail.com" ,
        password :  "hashedPassword"  ,
        provider:'gmail' ,
        googleId:null ,
        location: {
            type: 'Point',
            coordinates: [30.713015 ,30.940498 ]
        } ,
        typeofservice:"nurse" ,
        isVerified:true ,
        VerifiedByOffice:true ,
        request:[] ,

    } ,

    {

        username : "qashtokh" ,
        email : "aelhosaryaa@gmail.com" ,
        password :  "hashedPassword"  ,
        provider:'gmail' ,
        googleId:null ,
        location: {
            type: 'Point',
            coordinates: [30.746525,30.931908 ]
        } ,
        typeofservice:"nurse" ,
        isVerified:true ,
        VerifiedByOffice:true ,
        request:[] ,

    } ,

    {

        username : "bandarya" ,
        email : "ahmed@gmail.com" ,
        password :  "hashedPassword"  ,
        provider:'gmail' ,
        googleId:null ,
        location: {
            type: 'Point',
            coordinates: [30.725592 ,30.937666 ]
        } ,
        typeofservice:"ray" ,
        isVerified:true ,
        VerifiedByOffice:true ,
        request:[] ,

    } ,


       
    ]);

}) ;







module.exports=router ;