DROP DATABASE IF EXISTS Library;
CREATE DATABASE Library;
USE Library;

#图书信息
CREATE TABLE book
(
    isbn varchar(255) NOT NULL PRIMARY KEY ,
    bName varchar(255),
    bAuthor varchar(255),
    bPress varchar(255),
    bLocation varchar(255),
    isBorrowed BOOLEAN
);

#学生用户信息
CREATE TABLE student
(
    sName VARCHAR(255),
    sID VARCHAR(255) NOT NULL PRIMARY KEY ,
    borrowed INT,#借阅数量
    Password VARCHAR(255) NOT NULL

);

#管理员用户信息
CREATE TABLE admin
(
    aName VARCHAR(255) ,
    aID VARCHAR(255) NOT NULL PRIMARY KEY ,
    Password VARCHAR(255) NOT NULL
);

#借阅信息
CREATE TABLE borrow
(
    isbn VARCHAR(255) NOT NULL,#借阅图书
    bID VARCHAR(255) NOT NULL ,#借阅人
    bTime DATE NOT NULL,#归还时间
    FOREIGN KEY (isbn) REFERENCES book(isbn),
    FOREIGN KEY (bID) REFERENCES student(sID)

);
#测试用例
INSERT INTO book VALUE('9787508678498','历史的温度','张玮','中信出版社','历史1排1层',0),
    ('9787115293800','算法','Robert Sedgewick/Kevin Wayne','人民邮电出版社','计算机6排8层',1),
    ('10015','Just For Fun','LinusTorvalds','人民邮电出版社','计算机3排5层',0),
    ('12001','Fancifulness','Hans Rosling','文汇出版社','文学2排6层',0),
    ('78912','Java程序设计','刘晓明、周亚辉','东北大学出版社','计算机2排3层',0)
;
INSERT INTO student VALUE('张三','345',1,123456);
INSERT INTO borrow VALUE('9787115293800','345','2024-12-10');
INSERT INTO admin VALUES('李四','123','123');

