"# UsefulPlants-API" 

This is a REST API made using Spring Boot. I imagine it as a sketch for a phone app for people with an interest in foraging. 

USER STORIES:

User is on a hike in some part of Arizona with my app on her phone.
-As a user I want to be able to look up the common name of a plant so that I can find the latin name and other information. Or add a common name if I know one for a plant in the database.

-As a user I want to be able to find all plants in the ecosystem I'm exploring so I know what to be on the lookout for, and get general information about the ecosystem.

-If I see a plant that's not listed under the ecosystem I'm in, I want to be able to add it. 
-Also, if I know of a use (benefit) for a plant that isn't listed, I want to be able to add that, including benefits that aren't in the database yet.

-I want to look up a benefit and get a list of the plants that provide it.

-User can also add and delete plants, but I would imagine those would be restricted on a real app! Also looking various things up by their IDs wouldn't be very important to a user, but maybe the front end would want it.


ENDPOINTS:

C -add a plant
R -get all plants with given benefit (on benefit)
R -search plant by id (on plant)
R -search plant by common name (on commonName)
R -all ecosystems (on ecosystem)
R -search ecosystem by id (on ecosystem)
R -all plants in an ecosystem (on ecosystem)  
U -add plant to ecosystem (and vice versa) (on ecosystem)
U -add benefit to plant (and vice versa) (on plant)
U -add common name to plant
U -add picture to plant
D -delete plant

(=12 endpoints)





