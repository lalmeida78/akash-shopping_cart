// Steps  to test the application 

Swagger end point

http://localhost:8083/swagger-ui/index.html?

Important note for POST and PUT API-

/user/{userId}/cart

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

/user/{userId}/cart/{cartId}/item

{

"name": "string",
"description": "string",
"price": 0,
"quantity": 0
}

3- PUT  - update the item in cart .

/user/{userId}/cart/{cartId}/item/{itemId}

{
"id": 3,
"name": "string",
"description": "string",
"price": 0,
"quantity": 0
}


Fetch all carts with filter criteria -

API  - /carts

Filter criteria  supported  - 
userid , cartid and createdAfter

So if there would be no filter criteria then all carts will be returned  from database 

Maven  commands  -

mvn clean install for build the project.
Run the OnlineApplication.java class to test this application.