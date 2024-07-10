# springboot-gradle-rest-api
Project to demonstrate the implementation of a REST API with Gradle and an IBM DB2 Database. 

This projects follows the principles and best practices for designing REST APIs commented in:
- https://learn.microsoft.com/en-us/azure/architecture/best-practices/api-design?source=post_page-----57b797eca69c--------------------------------
- https://github.com/microsoft/api-guidelines/blob/vNext/azure/Guidelines.md

## API REST

The project publish several REST API with the same operations but with different types of versioning:
- URI versioning: http://localhost:8080/v1/users
- Query string versioning: http://localhost:8080/users?api-version=1
- Header versioning: http://localhost:8080/users_h
- Media type versioning: http://localhost:8080/users_m

## DEMO

To test the application use one of the REST API commented before. Please, use the Swagger Endpoint http://localhost:8080/swagger-ui/index.html for easy testing. Although, some of the APIs can't be tested this way because with swagger is not possible to add headers in the requests. So, for this cases you can use Postman or a similar application/client.
