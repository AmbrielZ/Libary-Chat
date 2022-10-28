package ServletBooks;

import book.book;
import book.bookdo;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import user.user;

import java.io.IOException;
import java.util.List;

@WebServlet("/Servletbooks")
public class Servletbooks extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        bookdo bd=new bookdo();
        List<book> li=bd.howbooks();
        request.setAttribute("libooks",li);
        HttpSession session = request.getSession();
        user ans=(user)session.getAttribute("user");
        String referer = request.getHeader("referer");

            if (ans.getUsername().equals("root")){
                request.getRequestDispatcher("/deletebooks.jsp").forward(request,response);
            }else{
                request.getRequestDispatcher("/showBooks.jsp").forward(request,response);
            }


    }
}
