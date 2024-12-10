package com.library;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LibraryFrame extends JFrame implements Login, ItemListener {
    JMenuBar menuBar = new JMenuBar();
    JMenu book = new JMenu("图书操作");
    JMenu color = new JMenu("主题颜色");
    JMenu my = new JMenu("我的");
    JMenuItem borrowBook = new JMenuItem("借阅图书");
    JMenuItem returnBook = new JMenuItem("归还图书");
    JMenuItem bookBorrowed = new JMenuItem("已借图书");
    JMenuItem information = new JMenuItem("个人信息");
    JMenuItem changePassword = new JMenuItem("修改信息");
    JMenuItem red = new JRadioButtonMenuItem("Red",false);
    JMenuItem green = new JRadioButtonMenuItem("Green",false);
    JMenuItem blue = new JRadioButtonMenuItem("Blue",false);
    JMenuItem white = new JRadioButtonMenuItem("White",true);
    JMenuItem FileExit = new JMenuItem("退出系统");
    JTextField bookName = new JTextField(15);
    JTextField author = new JTextField(15);
    JTextField press = new JTextField(15);
    JTextField ISBN = new JTextField(15);
    JRadioButton bName = new JRadioButton("按书名",true);
    JRadioButton bAuthor = new JRadioButton("按作者",false);
    JRadioButton bPress = new JRadioButton("按出版社",false);
    JRadioButton bISBN = new JRadioButton("按书号",false);
    JButton search = new JButton("查询");
    JButton borrow = new JButton("借阅");
    Font font = new Font("仿宋", Font.BOLD, 20);
    Label l1 = new Label("书名：");
    Label l2 = new Label("作者：");
    Label l3 = new Label("出版社：");
    Label l4 = new Label("书号：");
    Label l5 = new Label("排序方法：");
    JPanel panel = new JPanel();
    JTable table;

    public LibraryFrame() {
        this.setTitle("图书馆管理系统");
        this.setSize(800, 700);
        //又臭又长的设置格式
        book.setFont(font);
        color.setFont(font);
        my.setFont(font);
        borrowBook.setFont(font);
        returnBook.setFont(font);
        bookBorrowed.setFont(font);
        information.setFont(font);
        changePassword.setFont(font);
        red.setFont(font);
        green.setFont(font);
        blue.setFont(font);
        white.setFont(font);
        FileExit.setFont(font);
        bName.setFont(font);
        bAuthor.setFont(font);
        bPress.setFont(font);
        bISBN.setFont(font);
        search.setFont(font);
        borrow.setFont(font);
        l1.setFont(font);
        l2.setFont(font);
        l3.setFont(font);
        l4.setFont(font);
        l5.setFont(font);
        //添加菜单栏
        menuBar.add(book);
        menuBar.add(color);
        menuBar.add(my);
        book.add(borrowBook);
        book.add(returnBook);
        book.add(bookBorrowed);
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
        panel.add(l1).setBounds(5,5,60,35);
        panel.add(bookName).setBounds(70,5,110,35);
        panel.add(l2).setBounds(180,5,60,35);
        panel.add(author).setBounds(245,5,110,35);
        panel.add(l3).setBounds(355,5,80,35);
        panel.add(press).setBounds(440,5,110,35);
        panel.add(l4).setBounds(550,5,60,35);
        panel.add(ISBN).setBounds(615,5,110,35);
        panel.add(l5).setBounds(5,50,100,35);
        panel.add(bName).setBounds(120,50,100,35);
        panel.add(bAuthor).setBounds(220,50,100,35);
        panel.add(bPress).setBounds(320,50,120,35);
        panel.add(bISBN).setBounds(450,50,100,35);
        panel.add(search).setBounds(625,50,100,35);
        ButtonGroup g2 = new ButtonGroup();
        g2.add(bName);
        g2.add(bAuthor);
        g2.add(bPress);
        g2.add(bISBN);

        //设计数据表
        String[] bookInformation = {"书号","书名","作者","出版社","存放位置","借阅状态"};
        String[][] book = {{"11","aa","一","!","L1","已借出"},bookInformation};

        table = new JTable(book,bookInformation);
        JTableHeader head = table.getTableHeader();
        head.setFont(font);

        table.setFont(font);
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        panel.add(head).setBounds(5,100,777,30);
        panel.add(table).setBounds(5,130,777,450);
        panel.add(borrow).setBounds(300,590,200,35);
        //Button事件
        search.addActionListener(this);
        borrow.addActionListener(this);
        //Menu事件
        borrowBook.addActionListener(this);
        returnBook.addActionListener(this);
        bookBorrowed.addActionListener(this);
        information.addActionListener(this);
        changePassword.addActionListener(this);
        FileExit.addActionListener(this);
        //color事件
        red.addItemListener(this);
        blue.addItemListener(this);
        green.addItemListener(this);
        white.addItemListener(this);

        this.add(panel);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new LibraryFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            JOptionPane.showMessageDialog(null, "查询成功！", "查询", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/happy.png"));
        }
        if (e.getSource() == borrow) {
            JOptionPane.showMessageDialog(null, "借阅成功！", "借阅", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/happy.png"));
        }
        if (e.getSource() == borrowBook) {
            
        }
        if (e.getSource() == returnBook) {
            
        }
        if (e.getSource() == bookBorrowed) {

        }
        if (e.getSource() == information) {

        }
        if (e.getSource() == changePassword) {
            //JOptionPane.

        }
        if (e.getSource() == FileExit) {
            int option = JOptionPane.showConfirmDialog(null,"您确认要退出吗？","退出系统",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(option == JOptionPane.OK_OPTION){System.exit(0);}

        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == red) {
            this.setBackground(Color.red);
            panel.setBackground(Color.red);
            table.setBackground(Color.red);
        }
        if (e.getSource() == blue) {
            this.setBackground(Color.blue);
            panel.setBackground(Color.blue);
            table.setBackground(Color.blue);
        }
        if (e.getSource() == green) {
            this.setBackground(Color.green);
            panel.setBackground(Color.green);
            table.setBackground(Color.green);
        }
        if (e.getSource() == white) {
            this.setBackground(Color.white);
            panel.setBackground(Color.white);
            table.setBackground(Color.white);

        }


    }
}
