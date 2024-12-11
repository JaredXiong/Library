package com.library;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdminFrame extends JFrame implements Login, EditBook, ItemListener {
    JMenuBar menuBar = new JMenuBar();

    JMenu color = new JMenu("主题颜色");
    JMenu my = new JMenu("我的");

    JMenuItem information = new JMenuItem("个人信息");
    JMenuItem changePassword = new JMenuItem("修改密码");
    JMenuItem red = new JRadioButtonMenuItem("Red",false);
    JMenuItem green = new JRadioButtonMenuItem("Green",false);
    JMenuItem blue = new JRadioButtonMenuItem("Blue",false);
    JMenuItem white = new JRadioButtonMenuItem("White",true);
    JMenuItem FileExit = new JMenuItem("退出系统");

    JButton addBook = new JButton("添加图书");
    JButton updataBook = new JButton("修改图书");
    JButton deleteBook = new JButton("删除图书");
    Font font = new Font("仿宋", Font.BOLD, 20);
    Label l1 = new Label("图书信息");

    JPanel panel = new JPanel();
    JTable table;

    public AdminFrame() throws Exception {
        this.setTitle("图书馆管理系统");
        this.setSize(800, 700);
        //又臭又长的设置格式
        color.setFont(font);
        my.setFont(font);
        information.setFont(font);
        changePassword.setFont(font);
        red.setFont(font);
        green.setFont(font);
        blue.setFont(font);
        white.setFont(font);
        FileExit.setFont(font);
        l1.setFont(font);
        //添加菜单栏
        menuBar.add(color);
        menuBar.add(my);
        my.add(information);
        my.add(changePassword);
        my.add(FileExit);
        ButtonGroup g1 = new ButtonGroup();
        color.add(red);
        color.add(green);
        color.add(blue);
        color.add(white);
        g1.add(red);
        g1.add(green);
        g1.add(blue);
        g1.add(white);

        this.setJMenuBar(menuBar);

        panel.setLayout(null);
        panel.add(l1).setBounds(350,5,100,35);
        //获取数据
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "123456";
        Connection conn = DriverManager.getConnection(url, user, password);

        //设计数据表
        String[] bookInformation = {"书号","书名","作者","出版社","存放位置","借阅状态"};

        table = new JTable(search(conn),bookInformation);
        JTableHeader head = table.getTableHeader();
        head.setFont(font);

        table.setFont(font);
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        panel.add(head).setBounds(5,50,777,30);
        panel.add(table).setBounds(5,80,777,500);
        panel.add(addBook).setBounds(150,590,100,35);
        panel.add(updataBook).setBounds(300,590,100,35);
        panel.add(deleteBook).setBounds(450,590,100,35);


        this.add(panel);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
        new AdminFrame();
    }

    @Override
    public String[][] search(Connection conn) throws Exception {
        return EditBook.super.search(conn);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
