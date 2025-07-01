# Project: User Management System



> ### **VERSIONS:**
>
> **Spring Boot version is 3.0.0**
>
> **and**
>
> **Java version 17**
> 
> **using MySQL**


> ### Schema of Flight Booking System



### In this project I integrate Swagger for API documentation.

- ##### To use swagger you can use this link 

  #### http://localhost:8080/swagger-ui/index.html#/ 

  

> ### **If you want to try this project on localhost**



- #### Go to application properties :

  1. ##### put username of your database for example : root

  2. #####  put password of your database for example : 123456

  3. ##### create new schema with name:  `flight_management_system`

  4. ##### this application will run on server 8080 **you can change it

     

### 

### In this project I implement:

1. ###### Security 

2. ###### register

3. ######  login to make login --> user should enter email and this email should be unique 

4. ######  Exception handling 

5. ######  Request Validation 

6. ######  Spring Data JPA Auditing

7. ######  make for some API Unit test

8. ###### create Collection 

### **Design security In this project .**

#####  **When the user make login**

```
The server generates Two Tokens
1- access token: this token is with short time and is sent with every request
and we check that token that is sent in the request is an access token, not a refresh token 
2- refresh token: this token is with a long time and if isn't expired  we use this token to generate a new access token after the access is expired 
```



#####  We store refresh tokens in the database Why?

```
if the hacker takes a refresh token we can revoke this refresh token from the database 
then the hacker can't use this refresh token to generate a new access token
*Note if the hacker takes an access token this token will be expired in a short time 
but the problem is to refresh the token if is stolen  so we store the refresh token in the database          --> but this token is statless for the server
```



##### We make API for logout Why?

```
Users can make logout from the front end by deleting the access token and the refresh token from the storage , session, or cookies
why do we make a specific API for logout?
because when the user wants to make logout we revoke the refresh token from the database 
this is for security to prevent anyone stole the refresh token before deleting it from the storage, session, or cookies to use it
 
```



#### **This is according to the design  

##### Allow for the user to make login in one place how?

```
if the user make login in another place we revoke the refresh token that the user has and generate a new refresh token for the new login then the user will be logout from the first place 

**Note: in this code, we have this design but easily we can change this design 
 
```



​     







