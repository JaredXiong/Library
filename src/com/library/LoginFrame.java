package com.library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;

public class LoginFrame extends JFrame implements Login{
    JLabel label = new JLabel();
    JTextField ID = new JTextField(20);
    JTextField Password = new JTextField(20);
    JRadioButton Admin = new JRadioButton("管理员", false);
    JRadioButton Student = new JRadioButton("学生", true);
    JButton Login = new JButton("登陆");
    JButton SingUp = new JButton("注册");
    Font font = new Font("仿宋", Font.BOLD, 20);
    Label l1 = new Label("账号：");
    Label l2 = new Label("密码：");
    Label l3 = new Label("身份：");

    public LoginFrame() {
        this.setTitle("登陆");
        this.setSize(330,500);
        JPanel panel = new JPanel();
        l1.setFont(font);
        l2.setFont(font);
        l3.setFont(font);
        label.setIcon(new ImageIcon("src/LibraryLoading.jpg"));
        label.setBounds(1, 1, 320 , 160);
        panel.setLayout(null);
        panel.add(label);
        panel.add(l1).setBounds(20,170,60,35);
        panel.add(ID).setBounds(80,170,200,35);
        panel.add(l2).setBounds(20,210,60,35);
        panel.add(Password).setBounds(80,210,200,35);
        panel.add(l3).setBounds(35,250,50,35);
        ButtonGroup group = new ButtonGroup();//选择控件互斥
        group.add(Admin);
        group.add(Student);
        panel.add(Admin).setBounds(115,250,70,35);
        panel.add(Student).setBounds(185,250,70,35);
        panel.add(Login).setBounds(80,300,60,40);
        panel.add(SingUp).setBounds(160,300,60,40);
        Login.addActionListener(this);
        SingUp.addActionListener(this);

        this.add(panel);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    //项目唯一入口
    public static void main(String[] args) {
        new LoginFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/library";
            String user = "root";
            String password = "123456";
            Connection conn = DriverManager.getConnection(url, user, password);
            if (e.getSource() == Login) {
                String lid = ID.getText();
                String lpassword = Password.getText();
                if (Admin.isSelected() && isAdminLogin(conn,lid,lpassword)) {
                    this.dispose();
                    new AdminFrame(lid);
                } else if (Student.isSelected() && isStudentLogin(conn,lid,lpassword)) {
                    int borrowNo = searchBorrowNo(conn, lid);
                    this.dispose();
                    new LibraryFrame(lid,borrowNo);
                } else {
                    JOptionPane.showMessageDialog(null, "账号或密码错误！","错误",JOptionPane.ERROR_MESSAGE);
                }
                conn.close();
            } else {
                this.dispose();
                new SignupFrame().setVisible(true);
            }
            conn.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
