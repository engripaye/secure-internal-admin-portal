# 🛡️ Secure Internal Admin Portal

**A secure and role-based internal portal for managing sensitive company tools, built with Java 21 and Spring Boot 3.5.**
This portal ensures only **authorized internal staff** can log in and access features, with **granular permission control** for different admin roles.

<img width="1024" height="1024" alt="Secure Admin Portal Interface Design" src="https://github.com/user-attachments/assets/6d9d67fe-63b3-486f-82d4-36f7302f69a2" />

---

## 🚀 Features

* **OAuth2 Login** with Google Workspace (internal staff only)
* **Role-Based Access Control (RBAC)** for granular permissions (`VIEW`, `EDIT`, `DELETE`)
* **Secure Session Management** with Spring Security
* **Dashboard UI** for internal operations
* **Audit Logging** for admin actions
* **API Protection** with Bearer Tokens
* **CSRF & XSS Protection** enabled by default

---

## 📂 Project Structure

```
secure-internal-admin-portal/
│
├── src/main/java/com/company/adminportal/
│   ├── config/         # Security and OAuth2 configurations
│   ├── controller/     # MVC Controllers for UI and APIs
│   ├── service/        # Business logic and permission checks
│   ├── repository/     # Data access layer
│   ├── model/          # Entities and DTOs
│   └── AdminPortalApplication.java
│
├── src/main/resources/
│   ├── templates/      # Thymeleaf HTML pages (dashboard, login, error)
│   ├── static/         # CSS, JS, Images
│   └── application.yml # Configurations
│
├── pom.xml
└── README.md
```

---

## 🔐 Security Flow

1. **Login** → User logs in via **Google OAuth2** (restricted to company domain)
2. **Token Exchange** → OAuth2 provider sends authorization code → backend exchanges for ID Token & Access Token
3. **Domain Validation** → Backend validates email domain (e.g., `@company.com`)
4. **RBAC Enforcement** → Only allowed roles can access certain endpoints or UI sections
5. **Audit Logging** → Every admin action is logged

---

## 🛠️ Tech Stack

| Layer      | Technology                     |
| ---------- | ------------------------------ |
| Backend    | Java 21, Spring Boot 3.5       |
| Security   | Spring Security, OAuth2 Client |
| View       | Thymeleaf, Bootstrap           |
| Database   | MySQL / PostgreSQL             |
| Build Tool | Maven / Gradle                 |
| Deployment | Docker, Kubernetes (optional)  |

---

## ⚙️ Setup & Installation

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/secure-internal-admin-portal.git
cd secure-internal-admin-portal
```

### 2️⃣ Configure Environment Variables

Create a `.env` file or set environment variables:

```properties
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
APP_BASE_URL=http://localhost:8080
ALLOWED_DOMAIN=company.com
```

### 3️⃣ Run the Application

```bash
./mvnw spring-boot:run
```

Access the portal at: **[http://localhost:8080](http://localhost:8080)**

---

## 🔑 Role-Based Access Example

| Role           | Permissions                |
| -------------- | -------------------------- |
| `ROLE_ADMIN`   | View, Edit, Delete, Create |
| `ROLE_MANAGER` | View, Edit                 |
| `ROLE_VIEWER`  | View Only                  |

---

## 📸 Screenshots

**🔐 Login Page**

> ![Login Page](docs/images/login.png)

**📊 Dashboard**

> ![Dashboard](docs/images/dashboard.png)

---

## 🧪 Running Tests

```bash
./mvnw test
```

---

## 📜 License

This project is licensed under the **MIT License**.

---

## 🌟 Real-World Impact

✅ Prevents unauthorized access to sensitive internal tools
✅ Reduces risk of data breaches
✅ Provides an audit trail for compliance

---

If you want, I can also **add code snippets** inside the README so that new developers can quickly understand the **OAuth2 configuration and role-based access control** setup. That would make it even more **developer-friendly**.
