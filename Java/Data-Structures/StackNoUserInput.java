package com.mycompany.stacknouserinput;
import java.util.*;

public class StackNoUserInput {

private int[] arr;
  private int top;

  public StackNoUserInput(int size) {
    arr = new int[size];
    top = -1;
  }

  public void push(int num) {
    if (top == arr.length - 1) {
      System.out.println("Stack is full");
    } else {
      top++;
      arr[top] = num;
    }
  }

  public int pop() {
    if (top == -1) {
      System.out.println("Stack Underflow");
      return -1;
    } else {
      int poppedElement = arr[top];
      top--;
      return poppedElement;
    }
  }

  public int peek() {
    if (top == -1) {
      System.out.println("Stack is empty");
      return -1;
    } else {
      return arr[top];
    }
  }

  public boolean isEmpty() {
    return top == -1;
  }


  public void display() {
    if (top == -1) {
      System.out.println("Stack is empty");
    } else {
      System.out.print("Stack elements: ");
      for (int i = top; i >= 0; i--) {
        System.out.print(arr[i] + " ");
      }
      System.out.println();
    }
  }


  public static void main(String[] args) {
    System.out.println("Initialize a stack:");
    
    StackNoUserInput stack = new StackNoUserInput(5);
    System.out.println("Is the stack empty? " + stack.isEmpty());
    System.out.println("\nInput some elements on the stack:");
    
    stack.push(1);
    stack.push(2);
    stack.push(3);
    stack.push(4);
    stack.push(5);
    stack.display();
    
    
    System.out.println("\nTop element of the stack: " + stack.peek());
    System.out.println("\nRemove two element from the stack:");
    stack.pop();
    stack.pop();
    
    System.out.println("\nTop element of the stack after popping: " + stack.peek());
    System.out.println("\nIs the stack empty? " + stack.isEmpty());
  }
}