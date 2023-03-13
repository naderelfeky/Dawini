const express =require("express") ;
const app = express() ;
const dotenv =require("dotenv") ;
dotenv.config();

const CustomerRegistRouter=require("./routes/RouteCustomerRegist") ;
const CustomerLoginRouter=require("./routes/RouteCustomerLogin.js");
const Requests= require("./routes/RouteRequest");
const ProviderRegist =require("./routes/RouteProviderRegist") ;
const ProviderLogin = require("./routes/RouteProviderLogin");
const googleAuth=require("./routes/GoogleAuth") ;
const mongoose = require ("mongoose") ;
const bodyParser=require("body-parser") ;



app.use(express.json()) ;
app.use(express.urlencoded({extended:true})) ;



app.use(bodyParser.urlencoded({extended:false})) ;
app.use(bodyParser.json()) ;

const passport = require("passport");


//const expressSession = require('express-session');
//const MemoryStore = require('memorystore')(expressSession)

/* app.use(expressSession({
  secret: "random",
  resave: true,
  saveUninitialized: true,
 // setting the max age to longer duration
  maxAge: 24 * 60 * 60 * 1000,
  store: new MemoryStore(),
})); */


//app.use(passport.session());

app.use(passport.initialize());


app.use("",googleAuth);
app.use("",CustomerRegistRouter) ;
app.use("",CustomerLoginRouter) ;
app.use("",Requests) ;
app.use("",ProviderLogin) ;
app.use("",ProviderRegist) ;





mongoose.connect(process.env.Database_url ,{
    useNewUrlParser:true ,
  useUnifiedTopology:true ,
  useCreateIndex :true ,
  useFindAndModify:false  
}  


).then(()=>{
    console.log("Db Connect :)") ;
}).catch((err)=>{

    console.log("Error :(",err) ;
})



const port=process.env.PORT||3000 ;

app.listen(port , ()=>{
  console.log(`server is listening at port ${port}`)  ;
}) ;