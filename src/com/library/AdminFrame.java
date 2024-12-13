package com.library;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class AdminFrame extends JFrame implements Login, EditBook, ItemListener {
    //定义成员属性
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
    JButton updateBook = new JButton("修改图书");
    JButton deleteBook = new JButton("删除图书");
    Font font = new Font("仿宋", Font.BOLD, 20);
    Label l1 = new Label("图书信息");
    String aID ;
    JPanel panel = new JPanel();
    JTable table;
    String[][] books;
    String[] bookInformation = {"书号","书名","作者","出版社","存放位置","借阅状态"};
    String[] bookDB = {"isbn","bName","bAuthor","bPress","bLocation"};
    int row = -1;
    int column = -1;
    //内部类，通过button点击事件弹出弹窗
    class dialog extends JDialog {
        Label l1 = new Label("书名：");
        Label l2 = new Label("作者：");
        Label l3 = new Label("出版社：");
        Label l4 = new Label("书号：");
        Label l5 = new Label("位置：");
        Label l6 = new Label("请输入要添加图书的信息");
        JTextField bookName = new JTextField(15);
        JTextField author = new JTextField(15);
        JTextField press = new JTextField(15);
        JTextField ISBN = new JTextField(15);
        JTextField location = new JTextField(15);
        JButton ok = new JButton("添加");
        JButton cancel = new JButton("取消");
        Font font = new Font("仿宋", Font.BOLD, 20);
        //内部类构造方法，在弹窗中输入要增加的图书信息
        public dialog(JFrame frame) {
            super(frame, "添加图书", true);//重写JDialog方法，继承JFrame父类，弹窗打开时锁定父类状态
            l1.setFont(font);
            l2.setFont(font);
            l3.setFont(font);
            l4.setFont(font);
            l5.setFont(font);
            l6.setFont(font);
            bookName.setFont(font);
            author.setFont(font);
            press.setFont(font);
            ISBN.setFont(font);
            location.setFont(font);
            setSize(300,400);
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.add(l1);
            panel.setLayout(null);
            panel.add(l6).setBounds(30,10,250,35);
            panel.add(l1).setBounds(10,60,70,35);
            panel.add(bookName).setBounds(90,60,180,35);
            panel.add(l2).setBounds(10,100,70,35);
            panel.add(author).setBounds(90,100,180,35);
            panel.add(l3).setBounds(10,140,70,35);
            panel.add(press).setBounds(90,140,180,35);
            panel.add(l4).setBounds(10,180,70,35);
            panel.add(ISBN).setBounds(90,180,180,35);
            panel.add(l5).setBounds(10,220,70,35);
            panel.add(location).setBounds(90,220,180,35);
            panel.add(ok).setBounds(30,260,100,35);
            panel.add(cancel).setBounds(150,260,100,35);
            //button点击事件，使用lambda表达式
            ok.addActionListener(e -> {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/library";
                    String user = "root";
                    String password = "123456";
                    Connection conn = DriverManager.getConnection(url, user, password);
                    if (e.getSource() == ok) {
                        if(ISBN.getText().isEmpty()){
                            JOptionPane.showMessageDialog(null, "书号不可为空！", "添加图书", JOptionPane.ERROR_MESSAGE);
                        }else {
                            int a = addBook(conn, ISBN.getText(), bookName.getText(), author.getText(), press.getText(), location.getText());
                            JOptionPane.showMessageDialog(null, "成功添加" + a + "本图书！", "添加图书", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/happy.png"));
                            dispose();
                        }
                    }
                } catch(java.lang.Exception ex){
                    JOptionPane.showMessageDialog(null, "书号不可重复！", "添加图书", JOptionPane.ERROR_MESSAGE);
                }
            });
            cancel.addActionListener(e -> dispose());
            add(panel);
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            setLocationRelativeTo(null);
        }
    }
    //管理员窗口构造方法
    public AdminFrame(String ID) throws Exception {
        aID = ID;
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
        books = search(conn);
        table = new JTable(books,bookInformation);
        JTableHeader head = table.getTableHeader();
        head.setFont(font);
        //数据表格式
        table.setFont(font);
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //将表格和表头加入panel容器
        panel.add(head).setBounds(5,50,777,30);
        panel.add(table).setBounds(5,80,777,500);
        panel.add(addBook).setBounds(150,590,100,35);
        panel.add(updateBook).setBounds(300,590,100,35);
        panel.add(deleteBook).setBounds(450,590,100,35);
        //color事件
        red.addItemListener(this);
        blue.addItemListener(this);
        green.addItemListener(this);
        white.addItemListener(this);
        //table点击事件
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                row = table.getSelectedRow();
                column = table.getSelectedColumn();

            }
        });
        //button点击事件
        addBook.addActionListener(this);
        updateBook.addActionListener(this);
        deleteBook.addActionListener(this);
        information.addActionListener(this);
        changePassword.addActionListener(this);
        FileExit.addActionListener(this);
        //设置窗体状态
        this.add(panel);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        conn.close();
    }

    @Override
    public String[][] search(Connection conn) throws Exception {
        return EditBook.super.search(conn);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/library";
            String user = "root";
            String password = "123456";
            Connection conn = DriverManager.getConnection(url, user, password);
            if (e.getSource() == addBook) {
                new dialog(this);//弹出内部类弹窗，添加图书
                this.dispose();
                new AdminFrame(aID);//重启当前窗体
            }
            if (e.getSource() == updateBook) {
                if(row != -1 && column != -1){
                    if(column == 0 || column == 5){
                        JOptionPane.showMessageDialog(null,"书号和借阅状态不可修改！","修改图书",JOptionPane.ERROR_MESSAGE);
                    }else{
                        String newThing = JOptionPane.showInputDialog(null, "新信息：", "修改图书", JOptionPane.WARNING_MESSAGE);
                        if(newThing != null && !newThing.isEmpty()){
                            updateBook(conn,books[row][0],bookDB[column],newThing);
                            this.dispose();
                            new AdminFrame(aID);
                        }else{
                            if(newThing != null ){
                                JOptionPane.showMessageDialog(null,"输入为空！","警告",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "请选择要操作的图书！", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
            if (e.getSource() == deleteBook) {
                if(row != -1){
                    int option = JOptionPane.showConfirmDialog(null,"您确认要删除图书吗？","删除图书",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(option == JOptionPane.OK_OPTION){
                        if(deleteBook(conn,books[row][0])){
                            JOptionPane.showMessageDialog(null,"删除成功！","删除图书",JOptionPane.INFORMATION_MESSAGE);
                            this.dispose();
                            new AdminFrame(aID);
                        }else{
                            JOptionPane.showMessageDialog(null,"图书不存在或已借出！","删除图书",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    row = -1;
                }else{
                    JOptionPane.showMessageDialog(null, "请选择要操作的图书！", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
            if (e.getSource() == information) {
                searchInformation(conn, aID, false);//获取用户信息
            }
            if (e.getSource() == changePassword) {
                String oldPassword = JOptionPane.showInputDialog(null,"原密码：","输入",JOptionPane.WARNING_MESSAGE);
                if (oldPassword != null) {
                    if(isAdminLogin(conn,aID,oldPassword)) {
                        String newPassword = JOptionPane.showInputDialog(null, "新密码：", "输入", JOptionPane.WARNING_MESSAGE);
                        if(newPassword != null) {
                            adminChange(conn,aID,newPassword);
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
            conn.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
