
const dotenv =require("dotenv") ;
dotenv.config();
const nodemailer = require('nodemailer');
const { google } = require('googleapis');
const CLIENT_ID = process.env.GOOGLE_CLIENT_ID ;
const CLEINT_SECRET =  process.env.GOOGLE_CLIENT_SECRET ;
const REFRESH_TOKEN =process.env.REFRESH_TOKEN
const REDIRECT_URI=process.env.REDIRECT_URI
const bcrypt=require('bcrypt');
const verCode=require('../models/verificationCode') ;

const oAuth2Client = new google.auth.OAuth2(
  CLIENT_ID,
  CLEINT_SECRET,
  REDIRECT_URI
);
oAuth2Client.setCredentials({ refresh_token: REFRESH_TOKEN });

module.exports.sendMail=async ({email },res)=> {
  try {
    
    const accessToken = await oAuth2Client.getAccessToken();
    
    const transport = nodemailer.createTransport({
      service: 'gmail',
      auth: {
        type: 'OAuth2',
        user: 'daweneyapp@gmail.com',
        clientId: CLIENT_ID,
        clientSecret: CLEINT_SECRET,
        refreshToken: REFRESH_TOKEN,
        accessToken: accessToken,
      },
    });
   
      const code = `${Math.floor(1000+Math.random()*9000)}` ;
    const mailOptions = {
      from: 'Daweney<daweneyapp@gmail.com> ',
      to: email,
      subject: 'Your Daweney Verification Code      ',
      text: 'Your Daweney Verification Code  ',
      html: `<h1>This is your verification code :  ${code} </h1>`,
    };
    
    const salt=10 ;
    const hashedCode=await bcrypt.hash(code,salt) ;
    const newVrCode = new verCode({
      email:email ,
      code:hashedCode ,
      createdAt:Date.now() 
    
     
    }) ;
    

    await newVrCode.save() ;
    await transport.sendMail(mailOptions);
    console.log("code sent..") ;
    return 1;
    
    
   
  } catch (error) {
    res.status(400).send(`msg not sent ${error}`) ;
    
  }
}

//sendMail()
  //.then((result) => console.log('Email sent...', result))
  //.catch((error) => console.log(error.message)) ;  