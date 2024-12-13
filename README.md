# 项目介绍
使用Java图形用户界面(Swing,AWT)设计的图书馆管理系统，拥有登陆和注册界面，拥有学生和管理员两个操作端口。
<br>
管理员端设置了图书的增删改查功能；学生端设置了图书查询、借阅和归还功能。数据通过JDBC保存在MySQl数据库中。
<br>
大二Java基础课实训项目，约一千行代码，功能简洁，程序入口在 LoginFrame.java 类中。
# 开发环境
开发软件：IntelliJ IDEA Ultimate
<br>
JDK版本：jdk-21
<br>
JDBC版本：mysql-connector-j-8.2.0.jar
<br>
数据库：MySQL
# 目录结构
|      类名      |     简介     |
|:------------:|:----------:|
|  AdminFrame  |  管理员操作界面   |
| LibraryFrame |   学生操作界面   |
|  LoginFrame  |    登陆界面    |
| SingUpFrame  |    注册界面    |
|   EditBook   |   图书操作接口   |
|    Login     |   用户操作接口   |
| Library.sql  | 简易的数据库创建指令 |
|  README.md   |    项目简介    |
# 开源协议
本项目遵循 MIT 开源协议，虽然说应该也没人会下载这个项目吧。。。