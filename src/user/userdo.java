package user;

import druidUtils.DruidUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userdo {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());

    public user login(user root) {
        try {
            String sql = "select * from libuser where username = ? and password = ?";
            user user1 = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(user.class), root.getUsername(), root.getPassword());
            return user1;
        } catch (DataAccessException e) {
            return null;
        }
    }

    public user checklogin(String s1) {
        try {
            String sql = "select * from libuser where username = ?";
            user user1 = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(user.class), s1);
            return user1;
        } catch (DataAccessException e) {
            return null;
        }
    }

    public boolean register(String s1, String s2) {
        Connection con = null;
        PreparedStatement ps = null;
        int flag=0;
        try {
            String sql = "insert into libuser(username,password) values (?,?)";
            con = DruidUtils.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,s1);
            ps.setString(2,s2);
            ps.execute();
            flag=1;
        } catch (DataAccessException e) {
            flag=0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.Close(ps, con);
        }
        return flag==1;
    }
}
