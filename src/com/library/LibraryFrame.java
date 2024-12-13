package com.library;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class LibraryFrame extends JFrame implements Login, EditBook, ItemListener {
    //定义成员属性
    JMenuBar menuBar = new JMenuBar();
    JMenu book = new JMenu("图书操作");
    JMenu color = new JMenu("主题颜色");
    JMenu my = new JMenu("我的");
    JMenuItem borrowBook = new JMenuItem("借阅图书");
    JMenuItem returnBook = new JMenuItem("归还图书");
    JMenuItem bookBorrowed = new JMenuItem("已借图书");
    JMenuItem information = new JMenuItem("个人信息");
    JMenuItem changePassword = new JMenuItem("修改密码");
    JMenuItem red = new JRadioButtonMenuItem("Red",false);
    JMenuItem green = new JRadioButtonMenuItem("Green",false);
    JMenuItem blue = new JRadioButtonMenuItem("Blue",false);
    JMenuItem white = new JRadioButtonMenuItem("White",true);
    JMenuItem FileExit = new JMenuItem("注销");
    JTextField bookName = new JTextField(15);
    JTextField author = new JTextField(15);
    JTextField press = new JTextField(15);
    JTextField ISBN = new JTextField(15);
    JRadioButton bName = new JRadioButton("按书名",false);
    JRadioButton bAuthor = new JRadioButton("按作者",false);
    JRadioButton bPress = new JRadioButton("按出版社",false);
    JRadioButton bISBN = new JRadioButton("按书号",true);
    JButton search = new JButton("查询");
    JButton borrow = new JButton("借阅");
    JButton rBook = new JButton("归还");
    Font font = new Font("仿宋", Font.BOLD, 20);
    Label l1 = new Label("书名：");
    Label l2 = new Label("作者：");
    Label l3 = new Label("出版社：");
    Label l4 = new Label("书号：");
    Label l5 = new Label("排序方法：");
    JPanel panel = new JPanel();
    JTable table;
    String sID;
    int borrowedNo;
    JPanel panel2 = new JPanel();
    Label l6 = new Label("已借图书");
    JTable table2;
    String[][] books;
    int row = -1;
    int row2 = -1;

    public LibraryFrame(String ID, int borrowed) throws Exception {
        sID = ID;
        borrowedNo = borrowed;
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
        rBook.setFont(font);
        l1.setFont(font);
        l2.setFont(font);
        l3.setFont(font);
        l4.setFont(font);
        l5.setFont(font);
        l6.setFont(font);
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
        this.setJMenuBar(menuBar);//添加菜单栏
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
        //获取数据
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "123456";
        Connection conn = DriverManager.getConnection(url, user, password);
        //设计数据表
        String[] bookInformation = {"书号","书名","作者","出版社","存放位置","借阅状态"};
        books = search(conn);
        table = new JTable(books,bookInformation)
        {public boolean isCellEditable(int row, int column) {
            return false;
        }};
        JTableHeader head = table.getTableHeader();
        head.setFont(font);
        table2 = new JTable(searchBorrowBook(conn,sID), new String[]{"书号","书名","作者","出版社","存放位置","借阅时间"})
        {public boolean isCellEditable(int row, int column) {
            return false;
        }};//表格禁止编辑
        JTableHeader head2 = table2.getTableHeader();
        head2.setFont(font);
        table2.setFont(font);
        table2.setRowHeight(25);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFont(font);
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        panel2.setLayout(null);
        panel2.add(l6).setBounds(350,5,100,35);
        panel2.add(head2).setBounds(5,50,777,35);
        panel2.add(table2).setBounds(5,80,777,500);
        panel2.add(rBook).setBounds(300,590,200,35);
        panel.add(head).setBounds(5,100,777,30);
        panel.add(table).setBounds(5,130,777,450);
        panel.add(borrow).setBounds(300,590,200,35);
        //Button事件
        search.addActionListener(this);
        borrow.addActionListener(this);
        rBook.addActionListener(this);
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
        //table事件
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                row = table.getSelectedRow();
            }
        });
        table2.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                row2 = table2.getSelectedRow();
            }
        });
        this.add(panel);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/library";
            String user = "root";
            String password = "123456";
            Connection conn = DriverManager.getConnection(url, user, password);
            //查询图书信息，支持多条件模糊查询
            if (e.getSource() == search) {
                panel.remove(table);//移除原有表格
                books = queryBook(conn,ISBN.getText(), bookName.getText(), author.getText(), press.getText());
                String[] bookInformation = {"书号","书名","作者","出版社","存放位置","借阅状态"};
                JTable table3 = new JTable(books,bookInformation)//将查询信息写入新表格
                {public boolean isCellEditable(int row, int column) {
                    return false;
                }};
                table3.updateUI();
                table3.setFont(font);
                table3.setRowHeight(25);
                table3.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                panel.add(table3).setBounds(5,130,777,450);//将新表格加入窗体
                table3.revalidate();
                table3.repaint();//刷新窗体
            }
            if (e.getSource() == borrow) {
                if (row != -1) {
                    if(isBorrowBook(conn,books[row][0])) {
                        JOptionPane.showMessageDialog(null, "图书已被借出！", "错误", JOptionPane.ERROR_MESSAGE);
                        row = -1;
                    }else{
                        borrowBook(conn,sID,books[row][0],borrowedNo);
                        JOptionPane.showMessageDialog(null, "借阅成功！", "借阅", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/happy.png"));
                        row = -1;
                        borrowedNo++;
                        this.dispose();
                        new LibraryFrame(sID,borrowedNo);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "请选择要借阅的图书！", "借阅", JOptionPane.WARNING_MESSAGE);
                }
            }
            if (e.getSource() == borrowBook) {
                this.remove(panel2);
                this.revalidate();
                this.repaint();
                this.add(panel);
                this.revalidate();
                this.repaint();
            }
            if (e.getSource() == returnBook) {
                if(borrowedNo > 0) {
                    String returnBook = JOptionPane.showInputDialog(null,"请输入要归还的图书编号：","输入",JOptionPane.WARNING_MESSAGE);
                    if(returnBook != null && !returnBook.isEmpty()) {
                        if (isBorrowBook(conn,books[row2][0])) {
                            returnBook(conn, sID, returnBook);
                            JOptionPane.showMessageDialog(null, "归还成功！", "还书", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/happy.png"));
                            borrowedNo--;
                            this.dispose();
                            new LibraryFrame(sID, borrowedNo);
                        }else{
                            JOptionPane.showMessageDialog(null, "请输入正确的图书编号！", "还书", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "暂无未归还图书！", "还书", JOptionPane.WARNING_MESSAGE);
                }
            }
            if(e.getSource() == rBook) {
                if (row2 != -1) {
                    if(isBorrowBook(conn,books[row2][0])) {
                        returnBook(conn,sID,books[row2][0]);
                        JOptionPane.showMessageDialog(null, "归还成功！", "还书", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/happy.png"));
                        row2 = -1;
                        borrowedNo--;
                        this.dispose();
                        new LibraryFrame(sID, borrowedNo);
                    }else{
                        JOptionPane.showMessageDialog(null, "归还失败！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "请选择要归还的图书！", "还书", JOptionPane.WARNING_MESSAGE);
                }
            }
            if (e.getSource() == bookBorrowed) {
                this.remove(panel);
                this.revalidate();
                this.repaint();
                this.add(panel2);
                this.revalidate();
                this.repaint();
            }
            if (e.getSource() == information) {
                searchInformation(conn,sID,true);
            }
            if (e.getSource() == changePassword) {
                String oldPassword = JOptionPane.showInputDialog(null,"原密码：","输入",JOptionPane.WARNING_MESSAGE);
                if (oldPassword != null) {
                    if(isStudentLogin(conn,sID,oldPassword)) {
                        String newPassword = JOptionPane.showInputDialog(null, "新密码：", "输入", JOptionPane.WARNING_MESSAGE);
                        if(newPassword != null) {
                            studentChange(conn,sID,newPassword);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"密码错误","警告",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
            if (e.getSource() == FileExit) {
                int option = JOptionPane.showConfirmDialog(null,"您确认要注销吗？","退出系统",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(option == JOptionPane.OK_OPTION){
                    this.dispose();
                    new LoginFrame().setVisible(true);
                }
            }
        } catch(java.lang.ArrayIndexOutOfBoundsException ae){
            JOptionPane.showMessageDialog(null,"查询为空！","警告",JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
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
