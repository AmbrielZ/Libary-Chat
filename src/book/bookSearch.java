package book;

import druidUtils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class bookSearch {
    public List<book> search(String s1, String s2) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<book> li = new ArrayList<>();
        String sql = null;
        try {
            if (s2.equals("0")){
                sql = "select * from books where writer like ?";
            }else if (s2.equals("1")){
                sql = "select * from books where ISBN = ?";
            }else{
                sql = "select * from books where name like ?";
            }
            con = DruidUtils.getMyConnection();
            ps = con.prepareStatement(sql);
            if (s2.equals("0")||s2.equals("2")){
                ps.setString(1, '%' + s1 + '%');
            }else if (s2.equals("1")){
                ps.setString(1, s1);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                book bk = new book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
                li.add(bk);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.Close(ps, con, rs);
        }
        return li;
    }
}
