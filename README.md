# Simple GitHub Wrapper

Github Connector App is a Spring Boot application that serves as an API wrapper for Github. This application lets you fetch user repositories and their branches using the Github API. The application is built on Spring Webflux and uses reactive programming principles for handling the HTTP requests.

## Endpoints

    GET /user/repositories/{username}: Fetches all the repositories and their branches for the given GitHub username. The header of the request must be "Accept: application/json".

You can test the application by making a GET request to the /user/repositories/{username} endpoint. Replace {username} with a valid Github username.

For example:

    curl -H "Accept: application/json" http://localhost:8080/user/repositories/{username}


## Building and Running the Application

    Clone the repository.

    Navigate to the project directory and run ./gradlew clean build to build the application.

    Run the application using the command ./gradlew bootRun.

The application should now be running and ready to accept requests on localhost:8080.