const validator=require("../util/providerRegistValidator") ;


module.exports=(req,res,nxt)=>{

        let valid = validator(req.body) ;
        if(valid){
           
            nxt() ;
        }
        else {
            
            res.send(validator.errors);
            
        }
}