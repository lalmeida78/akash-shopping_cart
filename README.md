// Steps  to test the application 

Swagger end point

http://localhost:8083/swagger-ui/index.html?

Important note for POST and PUT API-

/user/U001/cart

Mandatory  attributes which needs to be passed in request model 
{
"address": "lucknow",
"items": [
{
      "name": "test ",
      "description": "test process",
      "price": 10.11,
      "quantity": 1
    }
]
}

2 - POST to create the item with existing cart .

/user/U001/cart/C0042/item

{

"name": "string",
"description": "string",
"price": 10.52,
"quantity": 2
}

3- PUT  - update the item in cart .

/user/U001/cart/C001/item/3

{
"id": 3,
"name": "test",
"description": "test",
"price": 10.52,
"quantity": 2
}


Fetch all carts with filter criteria -

API  - /carts

Filter criteria  supported  - 
userid , cartid and createdAfter

So if there would be no filter criteria then all carts will be returned  from database 

Cart Price and cart status not stored into database  and caluclated value will be returned from the system based on the number of items has been added and the expiary time defined in the constant file.

Database scripts location-
celfocus-backend-challenge\src\main\resources\script

Swagger Yaml Location -

celfocus-backend-challenge\src\main\resources\api

Maven  commands  -

mvn clean install for build the project.
Run the OnlineApplication.java class to test this application.
