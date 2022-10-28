package borrow;

import com.mysql.cj.xdevapi.DatabaseObject;
import druidUtils.DruidUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import user.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class borrow {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(DruidUtils.getDataSource());

    public int checknum(user root) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int num;
        try {
            String sql = "select count(*) from timestamps where reader_id=?";
            con = DruidUtils.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, root.getId());
            num = ps.executeUpdate();
        } catch (SQLException e) {
            num = 0;
        } finally {
            DruidUtils.Close(ps, con, rs);
        }
        return num;
    }

    public List<bookborrow> checkbooks(user root) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<bookborrow> li = new ArrayList<>();
        try {
            String sql = "select * from timestamps where reader_id=?";
            con = DruidUtils.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, root.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                bookborrow bb = new bookborrow(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                li.add(bb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.Close(ps, con, rs);
        }
        return li;
    }

    public boolean handle(user root,String bookid){
        boolean flag=false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "update books set state=? where id=?";
            String sql1="insert into timestamps(book_id, reader_id) values(?,?)";
            con = DruidUtils.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,"外借");
            ps.setInt(2,Integer.parseInt(bookid));
            ps.execute();
            ps=con.prepareStatement(sql1);
            ps.setString(1,bookid);
            ps.setString(2,String.valueOf(root.getId()));
            ps.execute();
            flag=true;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DruidUtils.Close(ps, con, rs);
        }
        return flag;
    }

    public boolean returnbook(user root,String bookid){
        boolean flag=false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "update books set state=? where id=?";
            String sql1="delete from timestamps where book_id=? and reader_id=?";
            con = DruidUtils.getMyConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,"在馆");
            ps.setInt(2,Integer.parseInt(bookid));
            ps.execute();
            ps=con.prepareStatement(sql1);
            ps.setString(1,bookid);
            ps.setString(2,String.valueOf(root.getId()));
            ps.execute();
            flag=true;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DruidUtils.Close(ps, con, rs);
        }
        return flag;
    }

    public bookborrow handlebookborrow(String read,String bookid){
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            String sql = "select timestamps.book_id,timestamps.reader_id,books.name,timestamps.return_time from books left join timestamps on timestamps.book_id = books.id where timestamps.book_id = ? and timestamps.reader_id=?";
            bookborrow bb = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(bookborrow.class), bookid, read);
            System.out.println(bb);
            con=DruidUtils.getMyConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1,bookid);
            ps.setString(2,read);
            rs=ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString(3));
                bb.setCreate_time(rs.getString(3));
            }
            return bb;
        }catch (DataAccessException e){
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DruidUtils.Close(ps,con,rs);
        }

    }
}
