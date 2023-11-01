var passport = require('passport');
const GoogleStrategy = require('passport-google-oauth20').Strategy;
const User = require('../models/CustomersModel');
const ProviderUser=require('../models/ProvidersModel') ;
const dotenv =require("dotenv") ;
dotenv.config();

module.exports = function (passport) {

passport.serializeUser((user, done) => {
  done(null, user.id);
});

passport.deserializeUser((id, done) => {
  User.findById(id).then(user => {
    done(null, user);
  });
});

passport.use('customer',
  new GoogleStrategy(
    {
        clientID: process.env.GOOGLE_CLIENT_ID,
        clientSecret: process.env.GOOGLE_CLIENT_SECRET,
        callbackURL: 'http://localhost:3000/customers/google/callback' 
    },
    async (accessToken, refreshToken, profile, done) => {
      try {

        
       
        let user = await User.findOne({ googleId: profile.id });
        if (!user) {
          user = await new User({
            username: profile.displayName,
            email: profile.emails[0].value,
            googleId: profile.id,
            password: null,
            provider: 'google',
            isVerified: true,
          }).save();
        }
        return done(null, user); 
       
      /*     console.log("++++++");
          let user = await ProviderUser.findOne({ googleId: profile.id });
          if (!user) {

          user = await new ProviderUser({
            username: profile.displayName,
            email: profile.emails[0].value,
            password: null,
            isVerified: true,
            googleId: profile.id,
            provider: 'google',
            typeofservice:null ,
            gender:"male"
          }).save();
        }
        return done(null, user);  */
        }
       catch (error) {
        done(error, null);
      }
    },
  ),
);


passport.use('provider',
  new GoogleStrategy(
    {
        clientID: process.env.GOOGLE_CLIENT_ID,
        clientSecret: process.env.GOOGLE_CLIENT_SECRET,
        callbackURL: 'http://localhost:3000/providers/google/callback' 
    },
    async (accessToken, refreshToken, profile, done) => {
      try {

        
      
          let user = await ProviderUser.findOne({ googleId: profile.id });
          if (!user) {

          user = await new ProviderUser({
            username: profile.displayName,
            email: profile.emails[0].value,
            password: null,
            isVerified: true,
            googleId: profile.id,
            provider: 'google',
            typeofservice:null ,
            gender:"male"
          }).save();
        }
        return done(null, user); 
        }
       catch (error) {
        done(error, null);
      }
    },
  ),
);



  }