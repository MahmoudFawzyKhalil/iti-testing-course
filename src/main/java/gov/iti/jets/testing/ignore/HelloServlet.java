package gov.iti.jets.testing.ignore;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
//        writer.println(req.getServletContext().getAttribute(ServletContextKeys.ENTITY_MANAGER_FACTORY.name()));
        req.setAttribute("betengan", 10);
        writer.println("Hello from Servlet :)");
        req.getRequestDispatcher("/WEB-INF/betengan.jsp").forward(req, resp);
        writer.close();
    }
}
