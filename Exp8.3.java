Setting Up MySQL Database
  code:
CREATE DATABASE StudentDB;
USE StudentDB;
CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100),
    date DATE,
    status VARCHAR(10)
);

INSERT INTO attendance (student_name, date, status) 
VALUES 
('Alice', '2025-04-10', 'Present'),
('Bob', '2025-04-10', 'Absent');

Setting Up Java Project
  Added MySQL JDBC Driver:
  <dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.33</version>
</dependency>

  
Database Connection Utility – DBConnection.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/StudentDB";
        String user = "Trilok_Rauniyar";
        String pass = "Tri@123#";
        return DriverManager.getConnection(url, user, pass);
    }
}


 Creating JSP Form – attendance.jsp
   code :
 <%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Attendance</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
</head>
<body class="container mt-5">
    <h2>Submit Attendance</h2>
    <form method="post" action="AttendanceServlet" class="mb-4">
        <div class="mb-3">
            <label>Student Name:</label>
            <input type="text" name="student_name" class="form-control" required />
        </div>
        <div class="mb-3">
            <label>Date:</label>
            <input type="date" name="date" class="form-control" required />
        </div>
        <div class="mb-3">
            <label>Status:</label>
            <select name="status" class="form-control">
                <option>Present</option>
                <option>Absent</option>
            </select>
        </div>
        <input type="submit" value="Submit" class="btn btn-primary" />
    </form>

    <h3>Attendance Records</h3>
    <table class="table table-bordered">
        <tr><th>ID</th><th>Name</th><th>Date</th><th>Status</th></tr>
        <%
            try {
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM attendance");
                while(rs.next()) {
                    out.print("<tr><td>" + rs.getInt("id") + "</td><td>" + 
                              rs.getString("student_name") + "</td><td>" + 
                              rs.getDate("date") + "</td><td>" + 
                              rs.getString("status") + "</td></tr>");
                }
            } catch(Exception e) {
                out.print("<tr><td colspan='4'>Error: " + e.getMessage() + "</td></tr>");
            }
        %>
    </table>
</body>
</html>


   Creating Servlet 
   code :
   import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AttendanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String name = request.getParameter("student_name");
        String date = request.getParameter("date");
        String status = request.getParameter("status");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO attendance (student_name, date, status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, date);
            stmt.setString(3, status);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("attendance.jsp");
    }
}


Configuring web.xml
  <web-app xmlns="http://jakarta.ee/xml/ns/jakartaee" version="5.0">
    <servlet>
        <servlet-name>AttendanceServlet</servlet-name>
        <servlet-class>AttendanceServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>AttendanceServlet</servlet-name>
        <url-pattern>/AttendanceServlet</url-pattern>
    </servlet-mapping>
</web-app>


Deploying & Testing
Placing attendance.jsp in WebContent or webapp folder.
Placing servlet and DBConnection.java in src.
Deployed to Tomcat and run the server.
