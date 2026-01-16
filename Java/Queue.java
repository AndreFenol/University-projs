package com.mycompany.queue;
import java.util.LinkedList;
import java.util.Queue;
import java.util.*;


public class Queue {

    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < 5; i++){
            System.out.println("Enter Value: ");
            
            int value = input.nextInt();
            queue.add(value);
        }
           System.out.println("Queue: " + queue);    
           
    }
}
