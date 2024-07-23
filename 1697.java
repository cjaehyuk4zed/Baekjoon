import java.util.*;
import java.io.*;


public class Main {
    
    // Index ArrayList?
    // init ArrayList<>(100001)
    // Cannot use ArrayList, as max capacity needs to be ensured
    // while list.get(fin).equals(null) -> cont
    
    static int[] list;
    static Deque<Integer> queue;
    
    public static void main(String[] args) throws IOException{
        int start;
        int fin;
        
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        
        start = Integer.parseInt(st.nextToken());
        fin = Integer.parseInt(st.nextToken());
        
        // Init as max index + 1
        list = new int[100001];
        list[start] = 0;
        
        queue = new ArrayDeque<Integer>();
        queue.add(start);
        
        // x-1, x+1, x*2
        while(!queue.isEmpty()){
            
            int x = queue.poll();
            
            // 이 break 코드가 있어야만 정답이 된다. 왜 그럴까?
            // 이론상으론 이거 없이도, 중복 제거가 되어야 한다
            if(x == fin){
                break;
            }
            
            if(x-1 >=0){
                if(list[x-1]==0){
                    list[x-1] = list[x]+1;
                    queue.add(x-1);
                }
            }
            if(x+1 <= 100000){
                if(list[x+1]==0){
                    list[x+1] = list[x]+1;
                    queue.add(x+1);
                }
            }
            if(x*2 <= 100000){
                if(list[x*2]==0){
                    list[x*2] = list[x]+1;
                    queue.add(x*2);
                }
            }
        }

        System.out.println(list[fin]);
        
    }
    
}

// Example input values
/*
5 17
*/
