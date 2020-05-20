package com.way.javassistdemo;

import com.way.javassistdemo.annotation.AnnoOpera;
import com.way.javassistdemo.annotation.TestAnno;
import com.way.javassistdemo.annotation.TestAnno2;

public class Base {

    @TestAnno2
    @TestAnno
    @AnnoOpera
    public void process(String name){
        System.out.println("process");
    }

}
