package root;

import book.bookdelete;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/ServletDeletebook")
public class ServletDeletebook extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String referer = request.getHeader("referer");

            String deleteid = request.getParameter("deleteid");
            boolean deletebook = new bookdelete().deletebook(deleteid);
            if (deletebook){
                request.getRequestDispatcher("/Servletbooks").forward(request,response);
            }else {
                response.sendRedirect("/nofind.jsp");
            }


    }
}
