package com.mycompany.binarytreetest;
import java.util.Scanner;

class TreeNode {
  int value;
  TreeNode left;
  TreeNode right;
  
TreeNode(int value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }
}



public class BinaryTreeTest{
static TreeNode root;  

    public static void printTree(TreeNode node) {
    if (node != null) {
      printTree(node.left);
      System.out.print(node.value + " ");
      printTree(node.right);
    }
  }

      public static void insert(TreeNode node, int value) {
        if (value > node.value) {
            if (node.left == null) {
                System.out.println("Inserted " + value + " to the left of Node " + node.value);
                node.left = new TreeNode(value);
            } else {
                insert(node.left, value);
            }
        } else if (value < node.value) {
            if (node.right == null) {
                System.out.println("Inserted " + value + " to the right of Node " + node.value);
                node.right = new TreeNode(value);
            } else {
                insert(node.right, value);
            }
        }
    }

  public static int calculateHeight(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            int leftHeight = calculateHeight(node.left);
            int rightHeight = calculateHeight(node.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

  
  public static void main(String[] args) {
  Scanner input = new Scanner(System.in);
  BinaryTreeTest tree = new BinaryTreeTest();
  root = null;
  
  
  System.out.print("Enter the value for the root node: ");
    int rootValue = input.nextInt();
    tree.root = new TreeNode(rootValue);
    
    System.out.print("Enter the value for the left child of the root node: ");
    int leftValue = input.nextInt();
    tree.root.left = new TreeNode(leftValue);
    
    System.out.print("Enter the value for the right child of the root node: ");
    int rightValue = input.nextInt();
    tree.root.right = new TreeNode(rightValue);

    String ans;
    do {
      System.out.print("Do you want to add another value for the next node? [Y/N] ");
      ans = input.next();
      if (ans.equalsIgnoreCase("N")) {
        break;
      }

      System.out.print("Enter another node: ");
      int node = input.nextInt();
      tree.insert(tree.root,node);
    } while (ans.equalsIgnoreCase("Y"));

    int height = calculateHeight(tree.root);
        System.out.println("The height of the tree: " + height);
    System.out.print("In-order Traversal of the Tree: ");
    printTree(root);
  }
}




