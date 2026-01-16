package com.mycompany.fenol_linkedlist;
import java.util.*;

public class Fenol_Linkedlist {
    
    
    public static void main(String[] args) {
       LinkedList<Integer> List = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);

        
        List.add(2);
        List.add(4);
        List.add(6);
        List.add(8);
        List.add(10);
        
         System.out.println("1. Linked list: " + List);

        
        System.out.print("Enter values to add at the beginning: ");
        String input = scanner.nextLine();
        String[] valuesToAddAtBeginning = input.split(" ");
        for (int i = valuesToAddAtBeginning.length - 1; i >= 0; i--) {
            int value = Integer.parseInt(valuesToAddAtBeginning[i].trim());
            List.addFirst(value);
        }

        
        System.out.println("After adding the values at the beginning, the list is: " + List);

        
        System.out.print("Enter values to add at the end: ");
        input = scanner.nextLine();
        String[] valuesToAddAtEnd = input.split(" ");
        for (String value : valuesToAddAtEnd) {
            List.addLast(Integer.parseInt(value.trim()));
        }

       
        System.out.println("After adding the values at the end, the list is: " + List);

        
        System.out.print("Enter value to add in between 1 and 2: ");
        int valueToAddInBetween = scanner.nextInt();
        List.add(2, valueToAddInBetween); 

       
        System.out.println("After adding the value, the list is: " + List);
       
    }
}
