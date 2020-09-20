# Location API
### GET current location for all users
`GET /users`
### GET current location for specified user
`GET /users/{id}`
### Get current location for all users within a specified area
`GET "/users?minLat=<value>&minLon=<value>&maxLat=<value>&maxLon=<value>"`
### Get location history for a specified user
`GET /locationHistory?userId=<userId>`
### Receive a location update from a user
#### Supply all user attributes
`PUT /users/{id} -H "Content-type:application/json" -d "{\"name\":\"<value>\", \"latitude\":<value>, \"longitude\":<value>}"`
#### Supply only changed attributes
`PATCH /users/{id} -H "Content-type:application/json" -d "{\"latitude\":<value>, \"longitude\":<value>}"`