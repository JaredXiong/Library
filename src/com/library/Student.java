package com.library;

public class Student extends User{
    static int MaxBorrow;//最大借阅数量，由管理员控制
    private int Borrower;//已借阅图书，登陆后通过访问数据库获取

    public void setMaxBorrow(int maxBorrow) {
        MaxBorrow = maxBorrow;
    }

    public int getMaxBorrow() {
        return MaxBorrow;
    }

    @Override
    public String getID() {
        return super.getID();
    }

    @Override
    public void setID(String ID) {
        super.setID(ID);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }


}
