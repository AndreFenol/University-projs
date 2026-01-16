package com.mycompany.stackinput;
import java.util.*;
public class StackInput{
        static final int MAX = 5;
        int top;
        int a[] = new int [MAX];
        
        boolean isEmpty(){
            return(top < 0);
        }
        StackInput(){
            top= -1;
        }
        boolean push (int x){
            if (top >= (MAX -1)){
                System.out.println("Stack overflow");
                return false;
            }
            else{
                a[++top] = x;
                System.out.println(x + " pushed into stack");
                return true;
            }
        }
        int pop(){
            if (top < 0){
                System.out.println("Stack underflow");
                return 0;
            }
            else{
                int x = a[top--];
                return x;
            }
        }
        int peek(){
            if(top < 0){
                System.out.println("Stack underflow");
                return 0;
            }
            else{
                int x = a[top];
                return x;
            }
        }
        void print(){
            for(int i = top; i>-1;i--){
                System.out.println("" + a[i]);
            }
        }
public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        StackInput s = new StackInput();
        
        
            System.out.println("Initialize a stack:");
            System.out.println("Is the stack empty? " + s.isEmpty());
 
            System.out.println("Input some elements on the stack:");     
            for(int i = 0; i<5; i++){
                int num = input.nextInt();
                s.push(num);
            }
            System.out.println("Stack Elements: " );
            s.print();
            
            System.out.println("Top element of the stack: " + s.peek());
            
            s.pop();
            s.pop();
            System.out.println("Remove two elements from the stack:");
            System.out.println("Stack elements: ");
            s.print();
            
            System.out.println("Top element after popping: " + s.peek());
            System.out.println("Is the stack empty? " + s.isEmpty());
            
 
}
}