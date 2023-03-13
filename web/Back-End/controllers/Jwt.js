var jwt = require('jsonwebtoken');
const JwtStrategy = require("passport-jwt").Strategy;
const { ExtractJwt } = require("passport-jwt");
const dotenv = require('dotenv');
dotenv.config();

exports.generateToken = (user )=>{
    var token = jwt.sign({

        email: user.email , id: user._id ,
       
    } , process.env.JWT_SECRET ,{ expiresIn: '100y' } )
    return token;
} ;


exports.validateToken =(token)=>{




   // router.post("/vtoken",(req,res)=>{

       
        
  
        let data =jwt.verify(token,process.env.JWT_SECRET) ;
        return data ;
  
  
  

}





exports.validateGoogleToken = (passport) => {
    passport.use(new GoogleStrategy() );
    passport.use(
      new JwtStrategy(
        {
          jwtFromRequest: ExtractJwt.fromHeader("authorization"),
          secretOrKey: process.env.JWT_SECRET,
        },
        async (jwtPayload, done) => {
          try {
            
            const user = jwtPayload.user;
            done(null, user); 
          } catch (error) {
            done(error, false);
          }
        }
      )
    );
}