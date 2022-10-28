package root;

import book.bookinsert;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ServletInsert")
public class ServletInsert extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String referer = request.getHeader("referer");

            String isbn = request.getParameter("isbn");
            String name = request.getParameter("name");
            String writer = request.getParameter("writer");
            String country = request.getParameter("country");
            String location = request.getParameter("location");
            String state = request.getParameter("state");
            String type1 = request.getParameter("type1");
            String type2 = request.getParameter("type2");
            String type3 = request.getParameter("type3");
            boolean f = new bookinsert().rootinsertbook(isbn, name, writer, country, location, state, type1, type2, type3);
            if (!f) {
                response.sendRedirect("/nofind.jsp");
            } else {
                request.getRequestDispatcher("/root.jsp").forward(request, response);
            }


    }
}
