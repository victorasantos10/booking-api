# Booking API
A booking API to create reservations for guests in properties.

## Tools and frameworks used

 - H2 as in-memory volatile DB
 - Spring Boot
 - Lombok
 - JUnit
 - Swagger

## Notable features

 - CRUD for Properties, Guests, Team members, Blocks and Bookings
 - Custom exception handling
 - Consistent response payload across all kinds of scenarios
 - Unit tests with 100% coverage on all controllers, services and repositories
  ![image](https://github.com/victorasantos10/booking-api/assets/8058097/94fd4016-fa85-4015-b9ae-807ae1fcfd71)
  ![image](https://github.com/victorasantos10/booking-api/assets/8058097/fc2c3a94-5b2e-4a38-ac58-2345e64855cd)

 - Detailed description of the API on Swagger

## How to use

The application will start at `http://localhost:8080`. then, please navigate to `http://localhost:8080/swagger-ui/index.html` to view the API docs and start testing

_Note: the API, when started, will initialize the H2 DB and automatically insert a default Property, a default Guest, and 2 default TeamMembers, to simplify testing, but you are also welcome to use the API to create new records. Please refer to `src/main/resources/init-data.sql` to more details._ 
