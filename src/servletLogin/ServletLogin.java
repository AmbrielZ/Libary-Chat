package servletLogin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import user.user;
import borrow.borrow;
import user.userdo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String account = request.getParameter("userid");
        String password = request.getParameter("password");
        user rt = new user();
        rt.setPassword(password);
        rt.setUsername(account);
        userdo ud = new userdo();
        user ans = ud.login(rt);
        String referer = request.getHeader("referer");

            if (ans != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date d1 = new Date();
                //转换为字符串
                String d1Str = df.format(d1);
                if (ans.getUsername().equals("root")) {
                    HttpSession session = request.getSession();
                    session.setMaxInactiveInterval(3600);
                    session.setAttribute("user", ans);
                    System.out.println("boss");
                    request.getRequestDispatcher("/main.html").forward(request,response);
                } else {
                    borrow br = new borrow();
                    HttpSession session = request.getSession();
                    session.setMaxInactiveInterval(3600);
                    session.removeAttribute("checknum");
                    session.removeAttribute("libookcheck");
                    session.setAttribute("checknum", br.checknum(ans));
                    session.setAttribute("libookcheck", br.checkbooks(ans));
                    session.setAttribute("d1Str",d1Str);
                    session.setAttribute("user", ans);
                    request.setAttribute("user", ans);
                    System.out.println(ans);
                    request.getRequestDispatcher("/person.jsp").forward(request, response);
                }
            }else{
                request.getRequestDispatcher("/nofind.jsp").forward(request,response);
            }


    }
}
