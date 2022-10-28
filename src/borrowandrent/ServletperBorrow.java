package borrowandrent;

import borrow.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import user.user;

import java.io.IOException;
import java.util.List;

@WebServlet("/ServletperBorrow")
public class ServletperBorrow extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String referer = request.getHeader("referer");

            HttpSession session = request.getSession();
            user u=(user)session.getAttribute("user");
            String name = request.getParameter("borrowid");

            borrow br=new borrow();
            boolean flag=br.handle(u,name);
            List<bookborrow> li = (List<bookborrow>) session.getAttribute("libookcheck");
            bookborrow ans=new borrow().handlebookborrow(String.valueOf(u.getId()),name);
            li.add(ans);
            session.removeAttribute("libookcheck");
            session.setAttribute("libookcheck", li);
            if (flag){//要改
                Integer i=(Integer) session.getAttribute("checknum");
                session.removeAttribute("checknum");
                session.setAttribute("checknum",i+1);
                request.getRequestDispatcher("/person.jsp").forward(request,response);
            }else {
                request.getRequestDispatcher("/nofind.jsp").forward(request,response);
            }


    }
}
