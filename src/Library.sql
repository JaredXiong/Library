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

INSERT INTO student VALUE('张三','123456',1,123456);

INSERT INTO book VALUE('9787508678498','历史的温度','张玮','中信出版社','历史1-1',0),
    ('9787115293800','算法','Robert Sedgewick/Kevin Wayne','人民邮电出版社','计算机',1)
;
INSERT INTO borrow VALUE('9787115293800','123456','2024-12-10');

select * from book where bName like '%算%' ;