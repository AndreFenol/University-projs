/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.assignment1methods;
import java.util.Scanner;
/**
 *
 * @author My PC
 */

public class Assignment1Methods {

   public static void main(String args[]){
          input();
   }
    public static void input(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter text: ");
        String input = scan.nextLine();
        System.out.print("Count letter: ") ;
        char search = scan.nextLine().charAt(0);
         
  int count=0;
  for(int i=0; i<input.length(); i++)
  {
      if(input.charAt(i) == search)
      count++;
  }
  
  System.out.println(  search  + " occurs "+ count +" times.");
  }
}



