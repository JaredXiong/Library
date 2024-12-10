package com.library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Login extends ActionListener {
    //注册账号
    default void adminSingUp(Connection conn,String aName, String aID, String Password) throws Exception {
        String sql = "insert into admin (aName, aID, Password) values (?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, aName);
        ps.setString(2, aID);
        ps.setString(3, Password);
        ps.executeUpdate();
        ps.close();
    }

    default void studentSingUp(Connection conn, String sName, String sID, String Password) throws Exception {
        String sql = "insert into student (sName, sID, Password) values (?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, sName);
        ps.setString(2, sID);
        ps.setString(3, Password);
        ps.executeUpdate();
        ps.close();

    }

    //登陆账号
    default boolean isAdminLogin(Connection conn, String ID, String Password) throws Exception {
        String sql = "select * from admin where aID = ? and Password = ? ";
        return isLogin(conn, ID, Password, sql);
    }

    default boolean isStudentLogin(Connection conn, String ID, String Password) throws Exception {
        String sql = "select * from student where sID = ? and Password = ? ";
        return isLogin(conn, ID, Password, sql);
    }

    private boolean isLogin(Connection conn, String ID, String Password, String sql) throws Exception {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, ID);
        ps.setString(2, Password);
        ResultSet rs = ps.executeQuery();
        boolean ans;
        ans = rs.next();
        ps.close();
        rs.close();
        conn.close();
        return ans;
    }

    //修改账号
    default void adminChange(Connection conn, String ID, String Password) throws Exception {
        String sql = "update admin set Password = ? where aID = ?";
        changePassword(conn, ID, Password, sql);
    }

    default void studentChange(Connection conn, String ID, String Password) throws Exception {
        String sql = "update student set Password = ? where sID = ?";
        changePassword(conn, ID, Password, sql);
    }

    private void changePassword(Connection conn, String ID, String Password, String sql) throws Exception {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, Password);
        ps.setString(2, ID);
        ps.executeUpdate();
        ps.close();
        conn.close();
        JOptionPane.showMessageDialog(null, "修改成功！");
    }

}
