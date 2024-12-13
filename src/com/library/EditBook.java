package com.library;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public interface EditBook extends ActionListener {
    //查找所有图书，返回二维数组
    default String[][] search(Connection conn) throws Exception {
        String sql = "SELECT * FROM book ";
        PreparedStatement ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        rs.last();
        int rsRow = rs.getRow();
        String[][] books = new String[rsRow][6];
        rs.beforeFirst();
        int i = 0;
        while(rs.next()){
            books[i][0] = rs.getString("isbn");
            books[i][1] = rs.getString("bName");
            books[i][2] = rs.getString("bAuthor");
            books[i][3] = rs.getString("bPress");
            books[i][4] = rs.getString("bLocation");
            books[i][5] = rs.getString("isBorrowed").equals("1")?"已借出":"未借出";
            i++;
        }
        return books;
    }
    //查找所有借阅信息，返回二维数组
    default String[][] searchBorrowBook(Connection conn, String sID) throws Exception {
        String sql = "SELECT * FROM borrow LEFT JOIN library.book b on b.isbn = borrow.isbn WHERE bID = ? ";

        PreparedStatement ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,sID);
        ResultSet rs = ps.executeQuery();
        rs.last();
        int rsRow = rs.getRow();
        String[][] books = new String[rsRow][6];
        rs.beforeFirst();
        int i = 0;
        while(rs.next()){
            books[i][0] = rs.getString("isbn");
            books[i][1] = rs.getString("bName");
            books[i][2] = rs.getString("bAuthor");
            books[i][3] = rs.getString("bPress");
            books[i][4] = rs.getString("bLocation");
            books[i][5] = rs.getString("bTime");
            i++;
        }
        return books;
    }
    //借阅图书，借阅成功返回true
    default void borrowBook(Connection conn, String sID, String isbn, int Borrowed) {
        try {
            String sql1 = "UPDATE book SET isBorrowed = 1 WHERE isbn = ? ;";
            String sql2 = "INSERT INTO borrow VALUES(?,?,?);";
            String sql3 = "UPDATE student SET borrowed = ?+1  WHERE sID = ? ;";
            //修改借阅状态
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, isbn);
            ps1.executeUpdate();
            ps1.close();
            //增加借阅信息
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, isbn);
            ps2.setString(2, sID);
            ps2.setString(3, String.valueOf(LocalDate.now()));
            ps2.executeUpdate();
            ps2.close();
            //修改学生借阅数
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, Borrowed);
            ps3.setString(2, sID);
            ps3.executeUpdate();
            ps3.close();

        } catch(Exception e) {
            throw new RuntimeException();
        }
    }
    //归还图书，归还成功返回true
    default void returnBook(Connection conn, String sID, String isbn) throws Exception {
        try {
            String sql1 = "UPDATE book SET isBorrowed = 0 WHERE isbn = ? ;";
            String sql2 = "DELETE FROM borrow WHERE isbn = ? ;";
            String sql3 = "UPDATE student SET borrowed = borrowed-1  WHERE sID = ? ;";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, isbn);
            ps1.executeUpdate();
            ps1.close();
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, isbn);
            ps2.executeUpdate();
            ps2.close();
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setString(1, sID);
            ps3.executeUpdate();
            ps3.close();
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    //查找图书，支持多条件模糊查询
    default String[][] queryBook(Connection conn,String isbn, String bName, String bAuthor, String bPress) throws Exception {
        String[][] books;
        StringBuilder sql =new StringBuilder();
        sql.append("select * from book");
        if (isbn != null && !isbn.isEmpty()) {
            if (String.valueOf(sql).equals("select * from book")) sql.append(" where");
            else sql.append(" and");
            sql.append(" isbn like '%").append(isbn).append("%'");
        }
        if (bName != null && !bName.isEmpty()) {
            if (String.valueOf(sql).equals("select * from book")) sql.append(" where");
            else sql.append(" and");
            sql.append(" bName like '%").append(bName).append("%'");
        }
        if (bAuthor != null && !bAuthor.isEmpty()) {
            if (String.valueOf(sql).equals("select * from book")) sql.append(" where");
            else sql.append(" and");
            sql.append(" bAuthor like '%").append(bAuthor).append("%'");
        }
        if (bPress != null && !bPress.isEmpty()) {
            if (String.valueOf(sql).equals("select * from book")) sql.append(" where");
            else sql.append(" and");
            sql.append(" bPress like '%").append(bPress).append("%'");
        }
        PreparedStatement ps = conn.prepareStatement(String.valueOf(sql), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        rs.last();//将光标移植末位，方便统计查询数量
        int rsRow = rs.getRow();
        books = new String[rsRow][6];//为查询到的图书分配存储空间
        rs.beforeFirst();//将光标移回首位
        int i = 0;
        while (rs.next()) {
            books[i][0] = rs.getString("isbn");
            books[i][1] = rs.getString("bName");
            books[i][2] = rs.getString("bAuthor");
            books[i][3] = rs.getString("bPress");
            books[i][4] = rs.getString("bLocation");
            books[i][5] = rs.getString("isBorrowed").equals("1") ? "已借出" : "未借出";
            i++;
        }
        return books;
    }
    //增加图书，返回增加数量
    default int addBook(Connection conn, String isbn, String bName, String bAuthor, String bPress, String bLocation) throws Exception {
        try {
            int a ;
            String sql1 = "INSERT INTO  book VALUES(?,?,?,?,?,?); ;";
            PreparedStatement ps = conn.prepareStatement(sql1);
            ps.setString(1, isbn);
            ps.setString(2, bName);
            ps.setString(3, bAuthor);
            ps.setString(4, bPress);
            ps.setString(5, bLocation);
            ps.setInt(6, 0);
            a = ps.executeUpdate();
            ps.close();
            return a;
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    //修改图书，无返回值
    default void updateBook(Connection conn, String isbn, String field, String newThing) throws Exception {
        try {
            if(field != null && !field.isEmpty()) {
                PreparedStatement ps = conn.prepareStatement("UPDATE book SET ? = ? WHERE isbn = ? ;");
                ps.setString(1, field);
                ps.setString(2, newThing);
                ps.setString(3, isbn);
                ps.executeUpdate();
                ps.close();
            }
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    //删除图书，返回删除状态
    default boolean deleteBook(Connection conn,String isbn) throws Exception {
        try {
            String sql1 = "SELECT isBorrowed FROM book WHERE isbn = ? ;";
            PreparedStatement ps = conn.prepareStatement(sql1);
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int a = rs.getInt("isBorrowed");
            if(a == 0) {
                String sql2 = "DELETE FROM book WHERE isbn = ? ;";
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setString(1, isbn);
                ps2.executeUpdate();
                ps2.close();
                return true;
            }else{
                return false;
            }
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    default boolean isBorrowBook(Connection conn,String isbn) throws Exception {
        String sql = "select * from book WHERE isbn = ? ;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, isbn);
        ps.executeQuery();
        ResultSet rs = ps.executeQuery();
        rs.next();
        int a = rs.getInt("isBorrowed");
        return a != 0;
    }
}
