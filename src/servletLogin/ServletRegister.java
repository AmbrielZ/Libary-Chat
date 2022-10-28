package servletLogin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import user.*;
import java.io.IOException;

@WebServlet("/ServletRegister")
public class ServletRegister extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String account = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword=request.getParameter("repassword");
        user u=new userdo().checklogin(account);
        String referer = request.getHeader("referer");

            if (u==null){
                if (new userdo().register(account,password)){
                    request.getRequestDispatcher("/login.jsp").forward(request,response);
                }else{
                    response.sendRedirect("/register.jsp");
                }
            }else {
                response.sendRedirect("/register.jsp");
            }


    }
}
