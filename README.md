## Crypto Wallet Manager app launch instructions

---

### Build & Run with Docker & Docker Compose

1. To launch the application, Maven, Docker and Docker Compose must be installed on the system and corresponding system environment variables must be set.
2. Use **start-docker.bat** to launch the application on Windows or **start-docker.sh** to launch it on Linux.
3. Once the application is deployed, the Swagger API can be accessed using the following link: http://localhost:8765/webjars/swagger-ui/index.html. Open the link to view and test all available API methods and their descriptions.

> The environment where the application was launched successfully:
> 
> Windows:
> - Maven (3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f))
> - Docker (20.10.11, build dea9396)
> - Docker Compose (1.29.2, build 5becea4c)
> 
> Linux:
> - Maven (3.8.6 (84538c9988a25aec085021c365c560670ad80f63))
> - Docker (23.0.1, build a5ee5b1)
> - Docker Compose (v2.16.0)

---

### Alternative Build & Run with Java & Maven

1. To launch the application, Java 17 and Maven must be installed on the system and corresponding system environment variables must be set.
2. Install PostgreSQL on your system.
3. Create a database with the name **wallet-view**, and create a database schema with name **wallet_management**.
4. Create a file named **hidden.yml** in wallet-management-module: **wallet-management-view/src/main/resources**, and include your PostgreSQL username and password in the following format:
```
psql:
  username: {your_username}
  password: {your_password}
```
5. Use **start-local.bat** to launch the application on Windows or **start-local.sh** if you have Git Bash installed (script won't work on Linux because here's .zip file of AxonServer)
6. Once the application is deployed, the Swagger API can be accessed using the following link: http://localhost:8765/webjars/swagger-ui/index.html. Open the link to view and test all available API methods and their descriptions.

Note:
- Axon Server will be automatically installed when you run the script
- If you want to check the second profile with name **ddlgen**, you can add the '-Dspring.profiles.active=ddlgen' parameter to -ArgumentList for **wallet-management-view** inside 'start-local.ps1' file or into Java command for **wallet-management-view** in 'start-local.sh' file. The **ddlgen** profile uses Hibernate auto-ddl to generate database tables, whereas the main profile uses Liquibase.

> The environment where the application was launched successfully:
>
> Windows:
> - Java (17.0.3.1 2022-04-22 LTS)
> - Maven (3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f))
> - PostgreSQL (12.4)
> - AxonServer (4.6.11)

---

### Tests

1. To run the tests, Java 17 and Maven must be installed on the system and corresponding system environment variables must be set.
2. Use **start-tests.bat** to launch the tests on Windows or start-tests.sh if you have Git Bash installed (script won't work on Linux because here's .zip file of AxonServer)

---
