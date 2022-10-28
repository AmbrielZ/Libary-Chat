package book;

import druidUtils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class bookdo {
    public List<book> howbooks(){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<book> li=new ArrayList<>();
        try {
            String sql="select * from books";
            con=DruidUtils.getMyConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                book bk=new book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11));
                li.add(bk);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DruidUtils.Close(ps,con,rs);
        }
        return li;
    }

    public static void main(String[] args) {
        List<book> li=new bookdo().howbooks();
        System.out.println(li);
    }
}
