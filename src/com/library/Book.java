package com.library;

public class Book {
    String name;//书名
    String author;//作者
    String press;//出版社
    String ISBN;//书号
    boolean isBorrowed;//是否借出
    //构造方法，借出状态默认为否
    public Book(String name, String author, String press, String ISBN) {
        this.name = name;
        this.author = author;
        this.press = press;
        this.ISBN = ISBN;
        this.isBorrowed = false;
    }
    //构造器和访问器
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    @Override
    public String toString() {
        return "图书信息:\n" +
                "书名：" + name + '\n' +
                ", 作者：" + author + '\n' +
                ", 出版社：" + press + '\n' +
                ", 书号：" + ISBN + '\n' +
                ", 是否借出：" + isBorrowed +
                '。';
    }
}
