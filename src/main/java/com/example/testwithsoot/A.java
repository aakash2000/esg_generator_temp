package com.example.testwithsoot;

public class A {
    int x;
    int y;
    int z;
    public A() {
        x = 1;
        y = 2;
    }

    public static void main(String[] args) {
        A a = new A();
        a.doStuff();
    }
    void doStuff() {
        doStuff3();
    }
    void doStuff2() {
        z = x + y;
        System.out.println(z);
    }

    void doStuff3() {
        doStuff2();
    }
}
