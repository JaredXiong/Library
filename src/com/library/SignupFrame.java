package com.library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLIntegrityConstraintViolationException;

public class SignupFrame extends JFrame implements Login {
    JLabel label = new JLabel();
    JTextField name = new JTextField();
    JTextField ID = new JTextField(20);
    JTextField Password = new JTextField(20);
    JRadioButton Admin = new JRadioButton("管理员", false);
    JRadioButton Student = new JRadioButton("学生", true);
    JButton SingUp = new JButton("注册");
    JButton Return = new JButton("返回");
    Font font = new Font("仿宋", Font.BOLD, 20);
    Label l1 = new Label("账号：");
    Label l2 = new Label("密码：");
    Label l3 = new Label("身份：");
    Label l4 = new Label("注册身份信息");
    Label l5 = new Label("姓名：");

    public SignupFrame() {
        this.setTitle("注册");
        this.setSize(330,500);
        JPanel panel = new JPanel();
        l1.setFont(font);
        l2.setFont(font);
        l3.setFont(font);
        l4.setFont(font);
        l5.setFont(font);
        label.setIcon(new ImageIcon("src/LibraryLoading.jpg"));
        label.setBounds(1, 1, 320 , 160);
        panel.setLayout(null);
        panel.add(label);
        panel.add(l4).setBounds(100,160,200,35);
        panel.add(l5).setBounds(20,205,60,35);
        panel.add(name).setBounds(80,205,200,35);
        panel.add(l1).setBounds(20,245,60,35);
        panel.add(ID).setBounds(80,245,200,35);
        panel.add(l2).setBounds(20,285,60,35);
        panel.add(Password).setBounds(80,285,200,35);
        panel.add(l3).setBounds(35,325,50,35);
        ButtonGroup group = new ButtonGroup();//选择控件互斥
        group.add(Admin);
        group.add(Student);
        panel.add(Admin).setBounds(115,325,70,35);
        panel.add(Student).setBounds(185,325,70,35);
        panel.add(SingUp).setBounds(80,385,60,35);
        panel.add(Return).setBounds(160,385,60,35);
        SingUp.addActionListener(this);
        Return.addActionListener(this);

        this.add(panel);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SingUp) {
            if(ID.getText().isEmpty() || Password.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "账号或密码不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
            }else{
                try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/library";
                String user = "root";
                String password = "123456";
                Connection conn = DriverManager.getConnection(url, user, password);
                if(Admin.isSelected()) {
                    adminSingUp(conn,name.getText(),ID.getText(),Password.getText());
                    conn.close();
                }else if(Student.isSelected()) {
                    studentSingUp(conn,name.getText(),ID.getText(), Password.getText());
                    conn.close();
                }
                ID.setText("");
                Password.setText("");
                name.setText("");
                    JOptionPane.showMessageDialog(null, "注册成功！", "欢迎", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("src/happy.png"));
                } catch (SQLIntegrityConstraintViolationException se) {
                    JOptionPane.showMessageDialog(null, "账号已被注册！","警告",JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }

        }else{
            this.dispose();
            new LoginFrame().setVisible(true);
        }

    }
}
