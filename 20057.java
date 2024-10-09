import java.io.*;
import java.util.*;

public class Main{
    
    static int[][] matrix;
    static int N;
    static int cx, cy;
    static int sum; // Amount of sand which goes outside of matrix
    
    public static void main(String[] args) throws IOException{
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        cx = N/2;
        cy = N/2;
        sum = 0;
        
        // Init matrix
        matrix = new int[N][N];
        for(int i=0; i<N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        // From iter=1 until iter=N-1
        int iter = 1;
        while(iter < N){
            if(iter%2==1){
                for(int i=0; i<iter; i++){cy--; calcLeft();}
                for(int i=0; i<iter; i++){cx++; calcDown();}
            } else if(iter%2==0){
                for(int i=0; i<iter; i++){cy++; calcRight();}
                for(int i=0; i<iter; i++){cx--; calcUp();}
            }
            iter++;
        }
        // Final case of iter=N
        for(int i=0; i<iter-1; i++){cy--; calcLeft();}
        
        System.out.println(sum);
    }
    
    static void calcRight(){
        int csand = matrix[cx][cy];
        int rem = matrix[cx][cy]; // square 'a'
        matrix[cx][cy] = 0; // This square has no sand now
        int p1 = csand/100; // 1% sand
        if(p1>0){
            calcSand(cx-1, cy-1, p1);
            calcSand(cx+1, cy-1, p1);
            rem -= p1*2;
        }
        int p2 = csand*2/100; // 2% sand
        if(p2>0){
            calcSand(cx-2, cy, p2);
            calcSand(cx+2, cy, p2);
            rem -= p2*2;
        }
        int p5 = csand*5/100; // 5% sand
        if(p5>0){
            calcSand(cx, cy+2, p5);
            rem -= p5;
        }
        int p7 = csand*7/100;
        if(p7>0){
            calcSand(cx-1, cy, p7);
            calcSand(cx+1, cy, p7);
            rem -= p7*2;
        }
        int p10 = csand/10;
        if(p10>0){
            calcSand(cx-1, cy+1, p10);
            calcSand(cx+1, cy+1, p10);
            rem -= p10*2;
        }
        calcSand(cx, cy+1, rem);
    }
    
    static void calcLeft(){
        int csand = matrix[cx][cy];
        int rem = matrix[cx][cy]; // square 'a'
        matrix[cx][cy] = 0; // This square has no sand now
        int p1 = csand/100; // 1% sand
        if(p1>0){
            calcSand(cx-1, cy+1, p1);
            calcSand(cx+1, cy+1, p1);
            rem -= p1*2;
        }
        int p2 = csand*2/100; // 2% sand
        if(p2>0){
            calcSand(cx-2, cy, p2);
            calcSand(cx+2, cy, p2);
            rem -= p2*2;
        }
        int p5 = csand*5/100; // 5% sand
        if(p5>0){
            calcSand(cx, cy-2, p5);
            rem -= p5;
        }
        int p7 = csand*7/100;
        if(p7>0){
            calcSand(cx-1, cy, p7);
            calcSand(cx+1, cy, p7);
            rem -= p7*2;
        }
        int p10 = csand/10;
        if(p10>0){
            calcSand(cx-1, cy-1, p10);
            calcSand(cx+1, cy-1, p10);
            rem -= p10*2;
        }
        calcSand(cx, cy-1, rem);
    }
    
    
    static void calcDown(){
        int csand = matrix[cx][cy];
        int rem = matrix[cx][cy]; // square 'a'
        matrix[cx][cy] = 0; // This square has no sand now
        int p1 = csand/100; // 1% sand
        if(p1>0){
            calcSand(cx-1, cy-1, p1);
            calcSand(cx-1, cy+1, p1);
            rem -= p1*2;
        }
        int p2 = csand*2/100; // 2% sand
        if(p2>0){
            calcSand(cx, cy-2, p2);
            calcSand(cx, cy+2, p2);
            rem -= p2*2;
        }
        int p5 = csand*5/100; // 5% sand
        if(p5>0){
            calcSand(cx+2, cy, p5);
            rem -= p5;
        }
        int p7 = csand*7/100;
        if(p7>0){
            calcSand(cx, cy-1, p7);
            calcSand(cx, cy+1, p7);
            rem -= p7*2;
        }
        int p10 = csand/10;
        if(p10>0){
            calcSand(cx+1, cy-1, p10);
            calcSand(cx+1, cy+1, p10);
            rem -= p10*2;
        }
        calcSand(cx+1, cy, rem);
    }
    

    static void calcUp(){
        int csand = matrix[cx][cy];
        int rem = matrix[cx][cy]; // square 'a'
        matrix[cx][cy] = 0; // This square has no sand now
        int p1 = csand/100; // 1% sand
        if(p1>0){
            calcSand(cx+1, cy-1, p1);
            calcSand(cx+1, cy+1, p1);
            rem -= p1*2;
        }
        int p2 = csand*2/100; // 2% sand
        if(p2>0){
            calcSand(cx, cy-2, p2);
            calcSand(cx, cy+2, p2);
            rem -= p2*2;
        }
        int p5 = csand*5/100; // 5% sand
        if(p5>0){
            calcSand(cx-2, cy, p5);
            rem -= p5;
        }
        int p7 = csand*7/100;
        if(p7>0){
            calcSand(cx, cy-1, p7);
            calcSand(cx, cy+1, p7);
            rem -= p7*2;
        }
        int p10 = csand/10;
        if(p10>0){
            calcSand(cx-1, cy-1, p10);
            calcSand(cx-1, cy+1, p10);
            rem -= p10*2;
        }
        calcSand(cx-1, cy, rem);
    }
    
    static void calcSand(int x, int y, int nsand){
        if((x>=0) && (x<N) && (y>=0) && (y<N)){
            matrix[x][y] += nsand;
        } else{
            sum += nsand;
        }
    }
}
