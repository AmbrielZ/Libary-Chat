package ServletBooks;

import book.book;
import borrow.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import user.user;

import java.io.IOException;
import java.util.List;

@WebServlet("/ServletReturnbook")
public class ServletReturnbook extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String referer = request.getHeader("referer");

            String returnbookid = request.getParameter("returnbookid");
            HttpSession session = request.getSession();
            user ans = (user) session.getAttribute("user");
            List<bookborrow> li = (List<bookborrow>) session.getAttribute("libookcheck");
            session.removeAttribute("libookcheck");
            for (bookborrow item : li) {
                if (String.valueOf(item.getBook_id()).equals(returnbookid)) {
                    li.remove(item);
                    break;
                }
            }
            session.setAttribute("libookcheck", li);
            borrow br = new borrow();
            boolean flag = br.returnbook(ans, returnbookid);
            Integer i=(Integer) session.getAttribute("checknum");
            session.removeAttribute("checknum");
            if (i!=0){
                session.setAttribute("checknum",i-1);
            }
            session.setAttribute("checknum",0);
            request.getRequestDispatcher("/person.jsp").forward(request, response);


    }
}
