const express = require ("express") ;
const router = express.Router() ;

const jwt =require('jsonwebtoken');

const passport=require('passport') ;
require('../controllers/Passport')(passport) ;




    



router.get('/customers/google', passport.authenticate('customer', { scope: ['profile', 'email',] }));

router.get(
      "/customers/google/callback",
      passport.authenticate("customer", { session: false }),
      (req, res) => {
        jwt.sign(
          { user: req.user },
         process.env.JWT_SECRET,
          { expiresIn: "100y" },
          (err, token) => {
            if (err) {
              return res.json({
                token: null,
              });
            }
            res.json({
        msg:"logged in success",
              token:token
    
            });
          }
        );
      }
    );









    router.get('/providers/google', passport.authenticate('provider', { scope: ['profile', 'email',] }));

router.get(
      "/providers/google/callback",
      passport.authenticate("provider", { session: false }),
      (req, res) => {
        jwt.sign(
          { user: req.user },
         process.env.JWT_SECRET,
          { expiresIn: "100y" },
          (err, token) => {
            if (err) {
              return res.json({
                token: null,
              });
            }
            res.json({
        msg:"logged in success",
              token:token
    
            });
          }
        );
      }
    );




    module.exports=router ;