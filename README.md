# 🍔 MOS Burgers — Backend API

Transform your burger shop operations with this modern, robust backend solution built for speed, reliability, and scalability.

## 🛠️ Tech Stack
- ☕ **Java 17** - Modern LTS version with enhanced performance
- 🍃 **Spring Boot 3.4.x** - Enterprise-grade framework
  - 🌐 Spring Web - RESTful API endpoints
  - 🗃️ Spring Data JPA - Elegant database operations
- 🐬 **MySQL 8+** - Reliable relational database
- 📦 **Maven** - Dependency management & build automation
- 🔧 **Lombok** - Boilerplate code reduction
- 🗺️ **ModelMapper** - Object mapping made simple

## ✨ Features
- 📋 **Order Management** - Create, update, track customer orders
- 👥 **Customer Database** - Maintain customer profiles and history
- 🍟 **Inventory Control** - Manage menu items and categories
- 💰 **Payment Processing** - Multiple payment method support
- 📊 **Reporting System** - Business insights and analytics
- 🔄 **CORS Enabled** - Seamless frontend integration
- 🛡️ **Error Handling** - Robust exception management


## ⚙️ Configuration

### 🗃️ Database Settings
Update your database credentials in configuration file:

 `src/main/resources/application.yml`
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mosburgers
    username: root
    password: 1234
```

### 🌐 Server Configuration
- **Default Port:** `8080` (Spring Boot default)

## 🗃️ Database Setup

### 1️⃣ Import Database

Import the `mos.mwb` file using MySQL Workbench or a relevant tool.

## 🚀 Build and Run

### 🏃‍♂️ Quick Start (Development)
```bash
# Clone and navigate to the project
cd MOS-Burgers-Back-End

# Install dependencies and run
mvn spring-boot:run
```

## 📄 License
🎓 **Educational/Demo Use** - Perfect for learning, coursework, and portfolio projects!

## 🔗 Links
- 🌐 **Frontend Repository:** https://github.com/Nugi29/Simple-POS-System-FrontEnd
- 📖 **Spring Boot Docs:** https://docs.spring.io/spring-boot/
- 🐬 **MySQL Documentation:** https://dev.mysql.com/doc/

---

