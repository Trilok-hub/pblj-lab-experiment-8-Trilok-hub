Steps to Implement
1. Set Up Your Environment
- Install Java Development Kit (JDK)
- Install Apache Tomcat (Servlet Container)
- Set up an IDE (Eclipse, IntelliJ, or VScode)

2. Create an HTML Login Form (login.html)
This form collects the username and password from the user.

3. Create the Java Servlet to Process Login (LoginServlet.java)
This servlet reads username and password from the request.
It checks the credentials.
- If valid, it displays a welcome message.
- If invalid, it redirects back to the login page.

4.  Configure web.xml
5. Deploy and Run
- Place the login.html file inside the WebContent (for VScode) or webapp (for Maven projects).
- Compile and deploy the servlet in Tomcat.
Access the form in your browser:
http://localhost:8080/login.html
