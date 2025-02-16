🌟 ContactSphere 🌟

ContactSphere is a secure and feature-rich contact management system built using Spring Boot. It provides functionalities like email verification, Google authentication, JWT, and OAuth2 security, ensuring a seamless and safe user experience.

🎯 Features
✅ User Authentication: Supports email verification, JWT authentication, and Google OAuth2 login.
✅ Contact Management: Users can add, update, and delete contacts securely.
✅ Security: Implements robust security mechanisms using Spring Security, JWT, and OAuth2.
✅ Email Notifications: Sends email verification links and other notifications.
✅ Role-Based Access Control (RBAC): Ensures different user roles and permissions.
✅ Two-Factor Authentication (2FA): Provides an extra layer of security.
✅ Password Reset and Recovery: Securely reset passwords via email.
✅ Activity Logging: Tracks user actions for monitoring and security purposes.
✅ Cloud Storage Integration: Supports storing contacts' profile pictures and other attachments in cloud storage.

🛠 Technologies Used
🚀 Backend: Java 21, Spring Boot
🔒 Security: JWT, OAuth2, Spring Security
🗄 Database: MySQL or PostgreSQL
🛠 Build Tool: Maven 3.9.9
☁ Others: Docker, Render for deployment, Cloud Storage APIs - Cloudinary

⚡ Installation & Setup
📌 Prerequisites
Ensure you have the following installed:
	• 🖥 Java 21
	• 🛠 Maven 3.9.9
	• 🗄 MySQL or PostgreSQL database
	• 🔗 Git
📥 Steps to Run the Project
1️⃣ Clone the Repository:
git clone https://github.com/omkarmundhe46/CONTACTSPHERE-THE-CONTACT-MANAGER.git
cd CONTACTSPHERE-main
2️⃣ Configure Database:
	• Create a database in MySQL or PostgreSQL.
	• Update application.properties or application.yml with your database credentials. 3️⃣ Build and Run the Application:
mvn clean install
mvn spring-boot:run
4️⃣ Access the Application:
	• 🌐 API: http://localhost:8081
	• 📜 Swagger UI : http://localhost:8081/swagger-ui.html

🔐 Authentication & Security
🛡 Email Verification: Users must verify their email before accessing certain features.
🛡 JWT Authentication: Used for secure API access.
🛡 OAuth2 Login: Supports Google authentication.
🛡 Two-Factor Authentication (2FA): Enhances account security.
🛡 Password Recovery: Secure email-based password reset.
🛡 Role-Based Access Control (RBAC): Manages user permissions efficiently.
🛡 Activity Logging: Monitors and logs user actions.

📑 API Documentation
Refer to the Swagger documentation for API details:
🔗 Swagger UI

🚀 Deployment
🐳 Docker Setup (Optional)
1️⃣ Build the Docker image:
docker build -t contactsphere .
2️⃣ Run the container:
docker run -p 8081:8081 contactsphere
☁ Deploy on Render
	• Configure environment variables as required.
	• Use PostgreSQL as Render does not support MySQL.

🤝 Contributing
🚀 Feel free to contribute by submitting pull requests.

📜 License
🔖 This project is licensed under the MIT License.

📩 Contact
📧 For any issues, reach out via the GitHub repository.


