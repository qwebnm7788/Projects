/*
JNI 를 이용하여 C언어에서의 System("cls")를 구현한다.
추후 구현예정
 */
package com.company;

/**
 * Created by jaewon on 2017-08-14.
 */
public class ClearScreen {
    static {
        System.loadLibrary("Clear");
    }

    public native static void clearScreen();
}
