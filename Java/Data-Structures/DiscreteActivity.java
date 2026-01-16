package com.mycompany.discreteactivity;
import java.util.*;

public class DiscreteActivity {

    public static void main(String[] args) {
        int[][] m = new int[3][3];

    for(int[] r : m)
        Arrays.fill(r, (int) (Math.random()*10));
    
    for (int i = 0; i < m.length; i++) {
        for (int j = 0; j < m.length; j++) {
            m[i][j] = (int) (Math.random()*10);
        }
    }       

    for (int i = 0; i < m.length; i++) {
        for (int j = 0; j < m.length; j++) {
            System.out.print(m[i][j] + " ");
        }
        System.out.println("");
    }
    
    System.out.println();
    
    for(int[] r : m)
        Arrays.fill(r, (int) (Math.random()*10));
    
    for (int i = 0; i < m.length; i++) {
        for (int j = 0; j < m.length; j++) {
            m[i][j] = (int) (Math.random()*10);
        }
    }       


    for (int i = 0; i < m.length; i++) {
        for (int j = 0; j < m.length; j++) {
            System.out.print(m[i][j] + " ");
        }
        System.out.println("");
    }
    
    
}
}
    
