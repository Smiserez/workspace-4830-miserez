import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearch")
public class SimpleFormSearch extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearch() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      String keyword2 = request.getParameter("keyword2");
   

      System.out.println(keyword + " " + keyword2); 
      search(keyword, keyword2,  response);
   }

   void search(String keyword, String keyword2,  HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;

         if (keyword.isEmpty() && keyword2.isEmpty()) {
            String selectSQL = "SELECT * FROM todolist";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else if(!keyword.isEmpty() && keyword2.isEmpty()) {
            String selectSQL = "SELECT * FROM todolist WHERE name like ?";
            String theName =  "%" + keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theName);
         }
         else if(keyword.isEmpty() && !keyword2.isEmpty()) {
        	 String selectSQL = "SELECT * FROM todolist WHERE priority like ?";
             String priority =  "%" + keyword2 + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, priority);
         }
         else if(!keyword.isEmpty() && !keyword2.isEmpty()) {
        	 String selectSQL = "SELECT * FROM todolist WHERE name like ? AND priority like ?";
             String theName =  "%" + keyword + "%";
             String priority = "%" + keyword2 + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, theName);
             preparedStatement.setString(2, priority);
         }
         
         
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String userName = rs.getString("name").trim();
            String priority = rs.getString("priority").trim();
            String status = rs.getString("status").trim();
            String date = rs.getString("date").trim();
            System.out.println(priority + "contains the keyword: " + priority.contains(keyword)); 

            if (keyword.isEmpty() || userName.contains(keyword) || priority.contains(keyword)) {
               out.println("ID: " + id + ", ");
               out.println("Project Name: " + userName + ", ");
               out.println("priority: " + priority + ", ");
               out.println("status: " + status + " ,");
               out.println("date: " + date + "<br>");
            }
         }
         out.println("<a href=/webproject/simpleFormSearch.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
