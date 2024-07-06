/*
문제
철수의 토마토 농장에서는 토마토를 보관하는 큰 창고를 가지고 있다. 토마토는 아래의 그림과 같이 격자 모양 상자의 칸에 하나씩 넣어서 창고에 보관한다.



창고에 보관되는 토마토들 중에는 잘 익은 것도 있지만, 아직 익지 않은 토마토들도 있을 수 있다. 보관 후 하루가 지나면, 익은 토마토들의 인접한 곳에 있는 익지 않은 토마토들은 익은 토마토의 영향을 받아 익게 된다. 하나의 토마토의 인접한 곳은 왼쪽, 오른쪽, 앞, 뒤 네 방향에 있는 토마토를 의미한다. 대각선 방향에 있는 토마토들에게는 영향을 주지 못하며, 토마토가 혼자 저절로 익는 경우는 없다고 가정한다. 철수는 창고에 보관된 토마토들이 며칠이 지나면 다 익게 되는지, 그 최소 일수를 알고 싶어 한다.

토마토를 창고에 보관하는 격자모양의 상자들의 크기와 익은 토마토들과 익지 않은 토마토들의 정보가 주어졌을 때, 며칠이 지나면 토마토들이 모두 익는지, 그 최소 일수를 구하는 프로그램을 작성하라. 단, 상자의 일부 칸에는 토마토가 들어있지 않을 수도 있다.

입력
첫 줄에는 상자의 크기를 나타내는 두 정수 M,N이 주어진다. M은 상자의 가로 칸의 수, N은 상자의 세로 칸의 수를 나타낸다. 단, 2 ≤ M,N ≤ 1,000 이다. 둘째 줄부터는 하나의 상자에 저장된 토마토들의 정보가 주어진다. 즉, 둘째 줄부터 N개의 줄에는 상자에 담긴 토마토의 정보가 주어진다. 하나의 줄에는 상자 가로줄에 들어있는 토마토의 상태가 M개의 정수로 주어진다. 정수 1은 익은 토마토, 정수 0은 익지 않은 토마토, 정수 -1은 토마토가 들어있지 않은 칸을 나타낸다.

토마토가 하나 이상 있는 경우만 입력으로 주어진다.

출력
여러분은 토마토가 모두 익을 때까지의 최소 날짜를 출력해야 한다. 만약, 저장될 때부터 모든 토마토가 익어있는 상태이면 0을 출력해야 하고, 토마토가 모두 익지는 못하는 상황이면 -1을 출력해야 한다.
*/

/*
예시 입력 : 
6 4
1 -1 0 0 0 0
0 -1 0 0 0 0
0 0 0 0 -1 0
0 0 0 0 -1 1

예시 출력 : 
6
*/

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
