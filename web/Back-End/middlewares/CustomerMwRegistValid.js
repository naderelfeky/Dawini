const validator=require("../util/CustomerRegistValidator") ;


module.exports=(req,res,nxt)=>{

        let valid = validator(req.body) ;
        if(valid){
           
            nxt() ;
        }
        else {
            
            res.send(validator.errors);
            
        }
}