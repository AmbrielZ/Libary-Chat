package book;

import druidUtils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class bookdelete {
    public boolean deletebook(String id){
        Connection con = null;
        PreparedStatement ps = null;
        boolean flag = false;
        try {
            con= DruidUtils.getMyConnection();
            String sql="delete from books where id=?";
            ps=con.prepareStatement(sql);
            ps.setString(1,id);
            ps.execute();
            flag=true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DruidUtils.Close(ps,con);
        }
        return flag;
    }
}
