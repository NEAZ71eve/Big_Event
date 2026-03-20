package com.demo;


import org.junit.Test;

public class JwtTest {
    @Test
    public void testThreadLocalSetAndGet() throws InterruptedException {
        ThreadLocal tl = new ThreadLocal();

        //开启两个线程
        Thread t1 = new Thread(()->{
            tl.set("硝烟");
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
        },"蓝色");
        Thread t2 = new Thread(()->{
            tl.set("药丸");
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
        },"绿色");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
