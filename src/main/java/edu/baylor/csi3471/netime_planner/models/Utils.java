package edu.baylor.csi3471.netime_planner.models;

public class Utils {
    static int getLCM(int i1, int i2){
        return i1 * (i2 / getGCD(i1, i2));
    }

    static int getGCD(int i1, int i2){
        while(i2 > 0){
            int temp = i2;
            i2 = i1 % i2;
            i1 = temp;
        }
        return i1;
    }
}
