# Todo-List-Microservices
This project follows a  microservices, client-server architectures, consisting of separate services for authentication and managing to-do items, each exposing RESTful APIs.

<div align="center">
   <h2>Demo</h2>

   [![Demo](assets/website-home.PNG)](https://drive.google.com/file/d/1oQOSAmZKEzCw7c-x5yrKS_8traggIklm/view?usp=sharing)
</div>

# Application Architecture
- **discovery-service** - Eureka server
- **api-gateway** - API gateway that proxies all the micro-services
- **authentication-service** - user authentication microservice
- **task-service** - task management microservice
- **web-portal** - client Application that provides the UI

# Getting Started
- Clone the repository: `git clone https://github.com/amira921/Todo-List-Microservices `
- Navigate to the project directory: `cd ./Todo-List-Microservices`
- Start Apache, MySQL, from Xampp
Install dependencies: mvn install
- Update application.properties files
- Run discovery-service project
- Run api-gateway project
- Run authentication-service project
- Run task-service project
- Run web-portal project

# Microservices Overview
## 1. Authentication Service
### Features
**1. Registration Process**
   - User provides email, password, and username.
   - Verification email is sent to the provided email address.
   - User is required to navigate to the verification link included in the email to activate the account.
   - **Without activating the account through the provided link**, the user cannot log in.

**2. Authentication Process**
- User provides email and password.
- **If the account is not found**, the user cannot access the app and needs to register.
- **If the account is found but not active**, the user cannot access the app and needs to check the inbox for activation instructions.
- **If the account is found, active, and the password is incorrect**, the user cannot access the app and needs to try again or reset the password.
- **If the account is found, active, and the password is correct**, the user can access the app.

**3. Reset Password Process**
- User provides their email address.
- **If the account is not found**, the user cannot access the app and is prompted to register.
- **If the account is found**, a password reset email is sent to the provided email address.
- User receives an email with link to reset their password and clicks on the link provided.
- **Token is expired after 30 minutes, if it expired**, user can't reset the password and need to send another request
- **If token is valid**, user is directed to a page where they can enter their email address and set a new password.
- After submitting the new password, the user's password is updated and can now log in.

### Technologies Used
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- MySQL Connector/J
- Spring Boot Starter Validation
- Spring Boot Starter Security
- JWT API and Jackson Integration
- Spring Boot Starter Mail
- Project Lombok (with Lombok MapStruct Binding)
- MapStruct (with MapStruct Processor)
- Spring Boot Starter Test
- Mockito Core
- Spring Cloud Netflix Eureka Client
- Springdoc OpenAPI Starter WebMVC UI

### Swagger end-points
<p align="center">
<img src="assets/swagger-authentication.PNG" width="400" length="600">
</p>

## 2. Task Service
### Features
**To access all features, user should authenticate first**
- Get all tasks by user's email
- Retrieves **personal tasks** 
- Retrieves **work-related** tasks 
- Retrieves tasks that are scheduled for **today** 
- Retrieves tasks scheduled for the **upcoming week** 
- Retrieves tasks that are currently **in progress** 
- Retrieves tasks that have been **completed** 
- Retrieves tasks that are **overdue** 
- Add tasks
- Update tasks
- Delete tasks

### Technologies Used
- Spring Cloud Netflix Eureka Client
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- MySQL Connector
- Springdoc OpenAPI Starter WebMVC UI
- Spring Boot Starter Validation
- Project Lombok (with Lombok MapStruct Binding)
- MapStruct (with MapStruct Processor)
- Spring Boot Starter Test

### Swagger end-points

<p align="center">
<img src="assets/swagger-task.PNG" width="400" length="600">
</p>

## 3. Web Portal
### Features
- Authentication: Secure login system to access the portal
- Task Microservices: Utilizes task microservices through an API Gateway for seamless integration and functionality

### Technologies Used
- Spring Boot Starter Web
- Spring Boot Starter Thymeleaf
- Springdoc OpenAPI Starter WebMvc UI
- Project Lombok 
- Spring Cloud Starter Netflix Eureka Client
- OAuth2 Client

### Swagger end-points

<p align="center">
<img src="assets/swagger-app-1.PNG" width="400" length="600">
<img src="assets/swagger-app-2.PNG" width="400" length="600">
</p>

## 4. Discovery Service
<p align="center">
<img src="assets/registry-service.PNG" width="600" length="600">
</p>

### Technologies Used
- Spring Cloud Netflix Eureka Server
- Spring Cloud Config

## 5. API Gateway

### Technologies Used
- Spring Boot Starter Webflux
- Spring Cloud Starter Gateway
- Spring Cloud Starter Netflix Eureka Client
- Project Lombok
- JWT API
- JSON Web Token 

## Postman Testing
#### The application handles various types of validation and exceptions. Below are a few test cases:

<details>

**<summary>Registration Process </summary>**
<p align="left"> 

**1. invalid data**
<p align="center"><img src="assets/invalidDataRequest.PNG" width="600" length="600"></p>

**2. valid data**
<p align="center"><img src="assets/registration.PNG" width="600" length="600"></p>

**3. user exists**
<p align="center"><img src="assets/registration_userExists.PNG" width="600" length="600"></p>

**4. verification email to activate account**
<p align="center"><img src="assets/account-verification.PNG" width="600" length="600"></p>

**5. account activation**
<p align="center"><img src="assets/activation.PNG" width="600" length="600"></p>
</p>
</details>


<details>

**<summary>Reset Password Process</summary>**

<p align="left">

**1. user is not found**
<p align="center"><img src="assets/reset-not-found.PNG" width="600" length="600"></p>

**2. user is found**
<p align="center"><img src="assets/rerset-found.PNG" width="600" length="600"></p>

**3. verify email - token is expired**
<p align="center"><img src="assets/reset-verification.PNG" width="600" length="600"></p>

**4. verify email- token is valid**
<p align="center">
<img src="assets/reset-token-expired.PNG" width="300" length="600">

**5. send new password**
<p align="center">
<img src="assets/reset-send-new-password.PNG" width="300" length="600">
<img src="assets/reset-password-changed.PNG" width="300" length="600"></p>
</p>
</details>

<details>

**<summary> Authentication Process</summary>**
<p align="left>

**1. account is inactive**
<p align="center"><img src="assets/login-inactive.PNG" width="600" length="600"></p>

**2. account is not found**
<p align="center"><img src="assets/login-notfound.PNG" width="600" length="600"></p>

**3. password is incorrect**
<p align="center"><img src="assets/login-incorrectPassword.PNG" width="600" length="600"></p>

**4. valid login**
<p align="center"><img src="assets/login-active.PNG" width="600" length="600"></p>
</p>
</details>


<details>

**<summary> Filter Tasks Feature</summary>**
<p align="left>

**1. get all tasks - user didn't authenticate**
<p align="center"><img src="assets/task-authentication.PNG" width="600" length="600"></p>

**2. get all tasks**
<p align="center"><img src="assets/all-tasks.PNG" width="600" length="600"></p>

**3. filter tasks: completed tasks**
<p align="center"><img src="assets/filter-completed.PNG" width="600" length="600"></p>

</p>
</details>

<details>

**<summary>Tasks: CRUD Operations</summary>**
<p align="left>

**1. add task - user is authorized**
<p align="center"><img src="assets/add task - authorized.PNG" width="700" length="600"></p>

**2. update task - user is authorized**
<p align="center"><img src="assets/update task-authorized.PNG" width="700" length="600"></p>

**3. update task - user is unauthorized**
<p align="center"><img src="assets/update task-unauthorized.PNG" width="700" length="600"></p>

**4. update task - user is authorized - data is invalid**
<p align="center"><img src="assets/update task-authorized-validation.PNG" width="600" length="600"></p>

**5. delete task - user authorized**
<p align="center"><img src="assets/delete task - authorized.PNG" width="700" length="600"></p>

</p>
</details>

## Future Work

- Enhance interactivity: Display feedback messages for user actions.
- Implement OAuth2 authentication with Google API.
- Strengthen security with additional layers.
- Introduce a notification system to alert users before tasks expire.
