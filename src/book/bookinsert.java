package book;

import druidUtils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class bookinsert {
    public boolean rootinsertbook(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean flag = false;
        try {
            con = DruidUtils.getMyConnection();
            String sql = "insert into books(ISBN,name,writer,country,location,state,type_1,type_2,type_3) values(?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, s1);
            ps.setString(2, s2);
            ps.setString(3, s3);
            ps.setString(4, s4);
            ps.setString(5, s5);
            ps.setString(6, s6);
            ps.setString(7, s7);
            ps.setString(8, s8);
            ps.setString(9, s9);
            ps.execute();
            flag = true;
        } catch (SQLException e) {
            flag = false;
        } finally {
            DruidUtils.Close(ps, con);
        }
        return flag;
    }
}
