# 🧠 API Conciler – Identity Reconciliation Service

This Spring Boot microservice reconciles user identities using email and phone number inputs.  
Think of it as the Sherlock Holmes of your contact database. 🕵️

---

## 🚀 Prerequisites

Before you begin, make sure you have:

- [Docker](https://www.docker.com/)
- Java 17+
- Maven (or use the bundled \`./mvnw\`)

---

## 🐬 Step 1: Start MySQL with Docker

Use Docker Compose to run a MySQL 8 database locally:

`bash
 $ docker-compose up -d
`

> **Note:** This runs MySQL 8 with:
>
> - **Username:** `root`  
> - **Password:** `password`  
> - **Database:** `api_db`  
> - Data is persisted via Docker volumes

<details>
<summary>📄 Full <code>docker-compose.yml</code></summary>

<pre>
yaml
version: '3.8'

services:
  mysql:
    image: mysql:8
    container_name: api_mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: password
      MYSQL_DATABASE: api_db
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
</pre>

</details>

---

## ☕ Step 2: Run the Spring Boot App

Use Maven to start the app:

<pre>
bash
./mvnw spring-boot:run
</pre>

Or build and run the JAR:

<pre>
./mvnw clean package
java -jar target/api-0.0.1-SNAPSHOT.jar
</pre>

Once running, your app will be available at:  
🌐 [http://localhost:8080](http://localhost:8080)

---

## 📮 API Endpoint

### POST /identify

Send a JSON request body like:

<pre>
{
  "email": "alice@example.com",
  "phoneNumber": "1234567890"
}
</pre>

And you’ll get a response like:

<pre>
{
  "primaryContactId": 1,
  "emails": ["alice@example.com"],
  "phoneNumbers": ["1234567890"],
  "secondaryContactIds": []
}
</pre>


---

## 🧪 Step 3: Run Tests

Run the test suite using Maven:

<pre>
bash
./mvnw test
</pre>


You should see:

<pre>
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
</pre>

---

## 🗂️ Project Structure

<pre>
.
├── src
│   ├── main
│   │   └── java/com/example/apiconciler/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── model/
│   │       ├── repository/
│   │       └── service/
│   └── test
│       └── java/com/example/apiconciler/
│           ├── ApiconcilerApplicationTests.java
│           └── IdentityControllerTest.java
├── docker-compose.yml
├── pom.xml
└── README.md
</pre>

---

## 🧼 Cleanup

To stop and remove containers **and volumes**:

<pre>docker-compose down -v</pre>

---

## 🙌 Author

**Sundaram Pandey**  
Built with Spring Boot, Docker, and an unhealthy amount of caffeine. ☕
`
