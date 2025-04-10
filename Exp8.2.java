Setting Up Database (MySQL)
  code :
CREATE DATABASE EmployeeDB;
USE EmployeeDB;
CREATE TABLE employees (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    department VARCHAR(50),
    salary DOUBLE
);
INSERT INTO employees VALUES 
(101, 'Alice', 'HR', 50000),
(102, 'Bob', 'IT', 60000),
(103, 'Charlie', 'Finance', 55000);

Setting Up Java Project
  Added MySQL JDBC Driver:

dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.33</version>
</dependency>

Database Connection Class (DBConnection.java)
     code :
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/EmployeeDB";
        String user = "Trilok_Rauniyar";
        String pass = "Tri@123#";
        return DriverManager.getConnection(url, user, pass);
    }
}

 EmployeeServlet.java

  code :
   import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Employee Management</title>");
        out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'/>");
        out.println("</head><body class='container'>");
        out.println("<h2 class='mt-3'>Employee Records</h2>");

        out.println("<form class='mb-3' method='get'>");
        out.println("Search by ID: <input type='text' name='id' class='form-control w-25 d-inline'/>");
        out.println("<input type='submit' value='Search' class='btn btn-primary ms-2'/>");
        out.println("</form>");

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt;
            if (idParam != null && !idParam.isEmpty()) {
                stmt = conn.prepareStatement("SELECT * FROM employees WHERE id = ?");
                stmt.setInt(1, Integer.parseInt(idParam));
            } else {
                stmt = conn.prepareStatement("SELECT * FROM employees");
            }

            ResultSet rs = stmt.executeQuery();

            out.println("<table class='table table-bordered'><tr><th>ID</th><th>Name</th><th>Department</th><th>Salary</th></tr>");
            boolean found = false;
            while (rs.next()) {
                found = true;
                out.println("<tr><td>" + rs.getInt("id") + "</td><td>" +
                        rs.getString("name") + "</td><td>" +
                        rs.getString("department") + "</td><td>" +
                        rs.getDouble("salary") + "</td></tr>");
            }
            if (!found) {
                out.println("<tr><td colspan='4' class='text-center'>No records found.</td></tr>");
            }
            out.println("</table>");

        } catch (Exception e) {
            out.println("<p class='text-danger'>Error: " + e.getMessage() + "</p>");
        }

        out.println("</body></html>");
    }
}


Configuring:
<web-app xmlns="http://jakarta.ee/xml/ns/jakartaee" version="5.0">
    <servlet>
        <servlet-name>EmployeeServlet</servlet-name>
        <servlet-class>EmployeeServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>EmployeeServlet</servlet-name>
        <url-pattern>/EmployeeServlet</url-pattern>
    </servlet-mapping>
</web-app>


Deploying and Runing
Placed servlet class and web.xml inside the correct folder structure:

/src → Java classes

/WebContent or /webapp → WEB-INF/web.xml

Deployed to Tomcat via  IDE or manually place .war file in /webapps of Tomcat.
