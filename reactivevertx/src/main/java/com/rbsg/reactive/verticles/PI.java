package com.rbsg.reactive.verticles;

public class PI {

    static double calculate(int n){
        double pi = 0;
        for (int i = 1; i < n; i++) {
            pi += Math.pow(-1,i+1) / (2*i - 1);
        }
        return 4 * pi;

    }
}
