/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.inputandoutput;
import java.util.Scanner;
/**
 *
 * @author My PC
 */
public class InputAndOutput {

    public static void main(String[] args) {
        input();
    }
public static void input(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter message: ");
        String text = scan.nextLine();
        output(text);
    }
    
    public static void output(String text){
        System.out.println("Message: " + text);
    }
}

