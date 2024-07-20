# CarDealerships project

## Overview

The **CarDealerships** project is created to demonstrate various data storage methods, showcasing the use of relational databases, non-relational databases, blob storage, and message brokers for managing and integrating data.
The project is designed with a modular architecture, where each module has distinct responsibilities and minimal dependencies on other modules. 
In this manner, each module serves a specific purpose, ensuring a clear and organized structure. 
Modules interact with each other through well-defined interfaces.

## Features

- A REST API for the **Car** resource is implemented using a **relational database**. This API allows for the creation, retrieval, updating, and deletion (CRUD) of car records.
- The **Shop** resource is managed through a REST API that uses a **non-relational database (MongoDB)**. This API enables CRUD operations for shop records. 
- The **Car** and **Shop** resources are linked using a **blob storage system**, which stores and manages binary large objects.
- The project includes a **Create REST API endpoint** for the event of adding a car to a shop. This functionality is built using a **message broker**, enabling asynchronous communication and event handling between services.

<br>
<br>
<br>

- The API documentation for the backend is available through Swagger. Once the backend server is running, you can access the Swagger UI at http://localhost:8080/swagger-ui.html :
  
![image](https://github.com/user-attachments/assets/c050b7ab-24f6-4400-bf28-58b94ed18fac)

<br>

<br>

<br>

- Mongo Express will be available at http://localhost:8081/, providing a web-based MongoDB administration tool with a graphical user interface for managing MongoDB databases
("admin" for username and "pass" for password)

<br>

![image](https://github.com/user-attachments/assets/b6860101-13d8-4ed9-a974-fcb5bcbf1e36)

<br>

<br>

Blob storage system (linking and retrieval of related data between cars and shops) :

<br>

![image](https://github.com/user-attachments/assets/3ee75ad1-ec31-4898-85eb-32106f81599d)
