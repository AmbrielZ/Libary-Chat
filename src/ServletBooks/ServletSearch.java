package ServletBooks;

import book.book;
import book.bookSearch;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/ServletSearch")
public class ServletSearch extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String referer = request.getHeader("referer");

            String isbn=request.getParameter("ID11");
            String sele=request.getParameter("uconti");
            bookSearch bs=new bookSearch();
            List<book> lisearch=bs.search(isbn,sele);
            if (lisearch.isEmpty()){
                request.getRequestDispatcher("/nofind.jsp").forward(request,response);
            }else {
                request.setAttribute("lisearch",lisearch);
                request.getRequestDispatcher("/searchBooks.jsp").forward(request,response);
            }


    }
}
