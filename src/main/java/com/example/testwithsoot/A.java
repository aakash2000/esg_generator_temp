package com.example.testwithsoot;

import java.util.concurrent.ThreadLocalRandom;

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
        x = ThreadLocalRandom.current().nextInt(0, 10);
        doStuff3();
    }

    void doStuff3() {
        if (x > 5) {
            y = 5;
        } else {
            y = 7;
        }
        doStuff2();
    }
    void doStuff2() {
        z = x + y;
        System.out.println(z);
    }
}
