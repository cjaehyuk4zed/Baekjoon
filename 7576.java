import java.util.*;
import java.io.*;

public class Main{
    // Use a queue to keep track of newly ripe tomatoes
    // So that the newly ripe tomatoes don't affect the current iteration
    // 1. If all tomatoes are already ripe = 0
    // 2. If complete, and not all tomatoes are ripe = -1
    // 3. If complete and all tomatoes are ripe = count
    
    static class Node {
        int x;
        int y;
        
        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    
    static List<List<Integer>> farm;
    static Deque<Node> queue;
    
    static int N;
    static int M;
    static int count;
    
    static final int dx[] = {-1, 1, 0, 0};
    static final int dy[] = {0, 0, -1, 1};
    
    public static void main(String[] args) throws IOException{
        
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        
        M = Integer.parseInt(st.nextToken()); // 가로 x
        N = Integer.parseInt(st.nextToken()); // 새로 y
        count = 0;
        
        farm = new ArrayList<List<Integer>>(N);
        queue = new ArrayDeque<Node>(N*M);
        
        for(int i = 0; i<N; i++){
            List<Integer> row = new ArrayList<Integer>(M);
            StringTokenizer st1 = new StringTokenizer(bf.readLine());
            for(int j = 0; j<M; j++){
                int num = Integer.parseInt(st1.nextToken());
                row.add(num);
                if(num == 1){
                    Node node = new Node(j, i);
                    queue.add(node);
                }
            }
            farm.add(row);
        }
        
        // Case 1
        if(!hasZero()){
            System.out.println(0);
            return;
        }
        
        while(!queue.isEmpty()){
            int size = queue.size();
            
            while(size > 0){
                size--;
                Node node = queue.poll();
                
                int x = node.x;
                int y = node.y;
                
                for (int i = 0; i < 4; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];
    
                    if(nx < 0 || nx >= M || ny < 0 || ny >= N ) continue;
                    
                    int num = farm.get(ny).get(nx);
                    if (num == 0) {
                        farm.get(ny).set(nx, 1);
                        queue.add(new Node(nx, ny));
                    }
                }
                
// It seems to be faster to use the above method of dx[], dy[]
//                if(x-1 >= 0){
//                    int num = farm.get(y).get(x-1);
//                    if(num == 0){
//                        farm.get(y).set(x-1, 1);
//                        queue.add(new Node(x-1,y));
//                    }
//                }
//                if(x+1 < M){
//                    int num = farm.get(y).get(x+1);
//                    if(num == 0){
//                        farm.get(y).set(x+1, 1);
//                        queue.add(new Node(x+1,y));
//                    }
//                }
//                if(y-1 >= 0){
//                    int num = farm.get(y-1).get(x);
//                    if(num == 0){
//                        farm.get(y-1).set(x, 1);
//                        queue.add(new Node(x,y-1));
//                    }
//                }
//                if(y+1 < N){
//                    int num = farm.get(y+1).get(x);
//                    if(num == 0){
//                        farm.get(y+1).set(x, 1);
//                        queue.add(new Node(x,y+1));
//                    }
//                }
                
            }
            
            count++;
            if(!hasZero()){
                System.out.println(count);
                return;
            }
        }
        
        // Case 2
        if(hasZero()){
            System.out.println(-1);
            return;
        }
        
        // Case 3
        System.out.println(count);
        return;
        
    }
    
    public static boolean hasZero(){
        for(int i = 0; i<N; i++){
            for(int j = 0; j<M; j++){
                int num = farm.get(i).get(j);
                if(num == 0){
                    return true;
                }
            }
        }
        return false;
    }
    
}
