package com.itu.visamanagement.entity;

public class Hello {
    private String hello;

    public String getHello() {
        return hello;
    }
    public void setHello(String hello) {
        this.hello = hello;
    }

    public Hello(String hello) {
        this.hello = hello;
    }

    public  void sayHello() {
        System.out.println(hello);
    }
}
