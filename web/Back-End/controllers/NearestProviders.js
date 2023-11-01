const User = require("../models/ProvidersModel") ;
//const latitude = 30.941733;
   
   // const longitude = 30.680024 ;
module.exports.NearestProviders= async({longitude,latitude,typeofservice,gender}  )=>{
    let valuesToMatch = [] ;
     if(gender=="anyone"){
         valuesToMatch = ["male","female"] ;
    }else if(gender=="male"){
        valuesToMatch = ["male"]
    } else {
        valuesToMatch = ["female"]
    }
    console.log(valuesToMatch);

    const users = await User.aggregate([
        {
            $geoNear: {
                near: {
                    type: 'Point',
                    coordinates: [longitude, latitude]
                },
                query: {
                    typeofservice: typeofservice,
                    gender: { $in: valuesToMatch } || gender
                   
                   
                      
                },
                maxDistance: 30 * 1000, 
                distanceField: 'distance',
                distanceMultiplier: 1 / 1000
            }
        },
        {
            $project: {
                _id: 1,
                distance: {  $round: ["$distance", 2] } ,
                username:1 ,
                typeofservice :1
            }
        },
        {
            $sort: {
                distance: 1
            }
        },
        
    ]);

    return users ;
}