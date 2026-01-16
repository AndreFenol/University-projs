/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.methodoverloading;

/**
 *
 * @author My PC
 */
public class MethodOverloading {

    public static void main(String[] args) {
        //method call
        output("hi");
    }
     //method definition
    public static void output(){
        System.out.println("hello!");
    }
    public static void output(int num){
        System.out.println("Number: " + num);
    }
    public static void output(String text){
        System.out.println("Message: " + text);
    }
}
