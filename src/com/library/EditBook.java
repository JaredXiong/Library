package com.library;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public interface EditBook extends ActionListener {
    default String[][] search(Connection conn) throws Exception {
        String sql = "select * from book ";
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
    default String[][] searchBorrowBook(Connection conn, String sID) throws Exception {
        String sql = "select * from borrow where bID = ? ";
        PreparedStatement ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,sID);
        ResultSet rs = ps.executeQuery();
        rs.last();
        int rsRow = rs.getRow();
        String[][] books = new String[rsRow][2];
        rs.beforeFirst();
        int i = 0;
        while(rs.next()){
            books[i][0] = rs.getString("isbn");
            books[i][1] = rs.getString("bTime");
            i++;
        }
        return books;
    }
    default boolean borrowBook(Connection conn, String sID, String isbn, int Borrowed) throws Exception {
        try {
            String sql1 = "update book set isBorrowed = 1 where isbn = ? ;";
            String sql2 = "INSERT INTO borrow VALUES(?,?,?);";
            String sql3 = "update student set borrowed = ?+1  where sID = ? ;";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, isbn);
            ps1.executeUpdate();
            ps1.close();

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, isbn);
            ps2.setString(2, sID);
            ps2.setString(3, String.valueOf(LocalDate.now()));
            ps2.executeUpdate();
            ps2.close();

            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, Borrowed);
            ps3.setString(2, sID);
            ps3.executeUpdate();
            ps3.close();

            return true;
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    default int returnBook(Connection conn, String sID, String isbn) throws Exception {
        try {
            int a = 0;
            String sql1 = "update book set isBorrowed = 0 where isbn = ? ;";
            String sql2 = "DELETE FROM borrow WHERE isbn = ? ;";
            String sql3 = "update student set borrowed = borrowed-1  where sID = ? ;";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setString(1, isbn);
            a += ps1.executeUpdate();
            ps1.close();
            if(a != 0) {
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setString(1, isbn);
                a += ps2.executeUpdate();
                ps2.close();

                PreparedStatement ps3 = conn.prepareStatement(sql3);
                ps3.setString(1, sID);
                a += ps3.executeUpdate();
                ps3.close();
            }
            return a;
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

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
        //对sql语句进行输出，查看是否正确
        System.out.println(String.valueOf(sql));
        PreparedStatement ps = conn.prepareStatement(String.valueOf(sql), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = ps.executeQuery();
        rs.last();
        int rsRow = rs.getRow();
        books = new String[rsRow][6];
        rs.beforeFirst();
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

    default int addBook(Connection conn, String isbn, String bName, String bAuthor, String bPress, String bLocation) throws Exception {
        try {
            int a = 0;
            String sql1 = "insert into book values(?,?,?,?,?,?); ;";
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

    default int updateBook(Connection conn, String isbn,String bName, String bAuthor, String bPress, String bLocation) throws Exception {
        try {
            int a = 0;
            String sql1 = "update book set bName=?,bAuthor=?,bPress=?,bLocation=? where isbn = ? ;";
            PreparedStatement ps = conn.prepareStatement(sql1);
            ps.setString(1, bName);
            ps.setString(2, bAuthor);
            ps.setString(3, bPress);
            ps.setString(4, bLocation);
            ps.setString(5, isbn);
            a = ps.executeUpdate();
            ps.close();

            return a;
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    default boolean deleteBook(Connection conn,String isbn) throws Exception {
        try {
            String sql1 = "select isBorrowed from book where isbn = ? ;";
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
}
