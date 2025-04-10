1. Setting Up Environment
  
Installing JDK: Downloaded and installed the Java Development Kit.

Installing Apache Tomcat: Downloaded from tomcat.apache.org and extract it.

Choosing an IDE: Used VS Code, Eclipse, or IntelliJ for development.


  Creating login.html
  code :
<!DOCTYPE html>
<html>
<head><title>Login</title></head>
<body>
  <form method="post" action="login">
    Username: <input type="text" name="Trilok_Rauniyar" required /><br/>
    Password: <input type="password" name="Tri@123#" required /><br/>
    <input type="submit" value="Login" />
  </form>
</body>
</html>


  Creating LoginServlet.java 
  code :
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String user = request.getParameter("Trilok_Rauniyar");
    String pass = request.getParameter("Tri@123#");

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    if ("admin".equals(user) && "1234".equals(pass)) {
      out.println("<h2>Welcome, " + user + "!</h2>");
    } else {
      response.sendRedirect("login.html");
    }
  }
}



Configuring web.xml
  code:
  <servlet>
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>LoginServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>LoginServlet</servlet-name>
    <url-pattern>/login</url-pattern>
  </servlet-mapping>
</web-app>


5. Deploying and Runing
  
Place login.html inside:
WebContent 
src/main/webapp 
Compiled and builded the project.
Copied the .war file 
Started Tomcat server.
