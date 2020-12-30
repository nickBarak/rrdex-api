# Radical Red Pokedex

## Product Description
This application is a portal that allows administrative users to
manage a Pokemon database, and allows all users to access the said database. It displays
Pokemon types, abilities, and potential differences with vanilla Pokemon.


## Technologies
- Java
- Servlets
- Apache Tomcat
- PostgreSQL
- Maven
- React
- Redux
- TypeScript
- AWS EC2/RDS


## Application Requirements
You will need the following installed to run the application:
- jdk8 (Java)
- Maven
- Apache Tomcat
- PostgreSQL
- Node.js

You will need to ensure the following variables are set in your system environment.
- JAVA_HOME
- CATALINA_HOME
- MAVEN_HOME
- M2_HOME
- DB_USERNAME
- DB_PASSWORD
- DB_URL

## Getting Started
Clone the repository at "https://github.com/nickBarak/rrdex-ui" to acquire the front-end of the project.  
Clone the repository at "https://github.com/nickBarak/rrdex-api" to acquire the back-end of the project.

## Deployment
When you are ready to deploy your server, make sure to execute the following command from the project directory of the API:
        ```mvn clean package```

You may need to delete your "/target" folder for the above command to execute properly.

You must then locate the ".war" file in the "/target" folder and copy it to the "/webapps" folder of your Apache Tomcat directory.

To start the server, navigate to the "/bin" sibling folder of the "/webapps" folder and execute the "startup" script.

To launch the client, navigate to the front-end project directory and run
	```npm start```

You can view the application by navigating to the URL "http://localhost:3000". The API will be located at "http://localhost:8080/RRDex".


## UI Route Descriptions

### /
Home page

### /signup
Register an account

### /login
Authenticate and establish a global user

### /pokemon
View all pokemon or those satisfying criteria name or type

### /abilities
View all abilities or search by name

### /admin
Manage pokemon and types in the database.


## API Route Descriptions

### POST /login
Logs in a user given a json body with attributes "username" and "password"

### GET /logout
Logs the user out

### GET /pokemon
Gets all of the Pokemon in the database, in json format.

If parameter name is defined, it will return the specified Pokemon.
If parameter type is defined, it will return all pokemon with the specified type.

### POST /pokemon - Only for user admin
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

### DELETE /pokemon - Only for user admin
Deletes a Pokemon given the Pokemon name as part of the json body

### GET /ability
Gets all of the abilities in the database, in json format.

### POST /ability - Only for user admin
Creates an ability and adds it to the database. Needs a json body, for example:
```json
{
    "name":"Scrappy",
    "description":"The Pokemon can hit Ghost-type Pokemon with Normal- and Fighting-type moves."
}
```
Posting an ability that already exists in the database will result in updating that ability

### DELETE /ability - Only for user admin
Deletes an ability given the ability name in the json body

### POST /signup
Signs up a user given the username and password in the json body

### POST /login
Logs in a user given the username and password in the json body
