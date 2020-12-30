# Radical Red Pokedex

# Product Description
This application is the backend server for a portal that allows administrative users to
manage a Pokemon database, and allows all users to access the said database. It displays
Pokemon types, abilities, and potential differences with vanilla Pokemon.

# API Description

## POST /login
Define in the json body "username" and "password", currently no signup function so its
predefined logins for admin and user.

## GET /login
Logs you out

## GET /pokemon
Gets all of the Pokemon in the DB, in json format.

If parameter name is defined, it will return the specified Pokemon.
If parameter type is defined, it will return all pokemon with the specified type.


## POST /pokemon - Only for user admin
Creates a Pokemon and adds it to the db. Needs a json body, for example:
```json
{
	"name": "Magmar",
	"type1":"Fire",
	"type2":"Fire",
	"ability_name":"Flame Body"
}
```
Posting a Pokemon that already exists in the database will result in updating that Pokemon.


## DELETE /pokemon - Only for user admin
Deletes a Pokemon given the Pokemon name as part of the json body, for example:
```json
{
	"name": "Magmar"
}
```
## GET /ability
Gets all of the abilities in the DB, in json format.


## POST /ability - Only for user admin
Creates an ability and adds it to the db. Needs a json body, for example:
```json
{
    "name":"Scrappy",
    "description":"The Pok�mon can hit Ghost-type Pok�mon with Normal- and Fighting-type moves."
}
```
Posting an ability that already exists in the db will result in updating that ability

## DELETE /ability - Only for user admin
Deletes an ability given the Pokemon name in the json body, for example:
```json
{
	"name": "Scrappy"
}
```

# POST /signup
Signs up a user given the username and password in the json body, for example:
```json
{ "name":"poop2", "password":"1234"
}
```

# POST /login
Logs in a user given the username and password as the PARAMETERS (not as json body because I'm stupid and decided to be inconsistent), for example:
```
Set key to: username, value to poop2
Set another row key to: password, value to 1234
```

# GET /logout
Logs out the current user.

To set up database, execute the pokedata-setup.sql script in DBeaver (or whatever database you plan on using).

Make sure to set up the following env variables:

- JAVA_HOME
- CATALINA_HOME
- MAVEN_HOME
- M2_HOME
- DB_URL = connection string for postgresql jdbc drier
- DB_USERNAME = user name for db
- DB_PASSWORD = password for user for PG DB
