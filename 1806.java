import java.util.*;
import java.io.*;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st0 = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st0.nextToken());
        int S = Integer.parseInt(st0.nextToken());
        int len = Integer.MAX_VALUE;
        
        // Init sum array
        int[] arr = new int[N+1];
        arr[0] = 0;
        StringTokenizer st1 = new StringTokenizer(br.readLine());
        for(int i=1; i<=N;i++){
            arr[i] = arr[i-1] + Integer.parseInt(st1.nextToken());
        }
        
        int i = 0;
        int j = 0;

        while(i<=N){
            int diff = arr[i] - arr[j];
            
            // Case1
            if(diff < S){
                i++;
                continue;
            }
            
            // Case2
            if(diff >= S){
                len = Math.min(len, i-j);
                j++;
            }
        }
        
        if(len == Integer.MAX_VALUE){
            System.out.println(0);
        } else{
            System.out.println(len);
        }
    }
}


/*

입력
첫째 줄에 N (10 ≤ N < 100,000)과 S (0 < S ≤ 100,000,000)가 주어진다. 둘째 줄에는 수열이 주어진다. 수열의 각 원소는 공백으로 구분되어져 있으며, 10,000이하의 자연수이다.

출력
첫째 줄에 구하고자 하는 최소의 길이를 출력한다. 만일 그러한 합을 만드는 것이 불가능하다면 0을 출력하면 된다.

예제 입력 1 
10 15
5 1 3 5 10 7 4 9 2 8

*/
