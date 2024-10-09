import java.io.*;
import java.util.*;

public class Main{
    
    static int[][] matrix;
    static int[][] dxdy = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};
    static int nlen;
    static int totalSum;
    static int maxSize;
    static boolean[][] visited;
    
    public static void main(String[] args) throws IOException{
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        nlen = (int) Math.pow(2, N);
        
        // Init matrix
        matrix = new int[nlen][nlen];
        visited = new boolean[nlen][nlen];
        for(int i =0; i<nlen; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<nlen; j++){
               matrix[i][j] = Integer.parseInt(st.nextToken()); 
            }
        }
        
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<Q; i++){
            int L = Integer.parseInt(st.nextToken());
            int tlen = (int) Math.pow(2, L);
            calc(tlen);
        }
        
        totalSum = 0;
        maxSize = 0;
        for(int i=0; i<nlen; i++){
            for(int j=0; j<nlen; j++){
                totalSum += matrix[i][j];
                if(matrix[i][j]!=0 && !visited[i][j]){
                    calcSize(i, j);
                    
                }
            }
        }
        System.out.println(totalSum);
        System.out.println(maxSize);
    }
    
    
    
    
    static void calcSize(int cx, int cy){
        int curSize = 0;
        Queue<Coords> queue = new ArrayDeque<>();
        queue.add(new Coords(cx, cy));
        while(!queue.isEmpty()){
            Coords cur = queue.poll();
            cx = cur.x;
            cy = cur.y;
            if(visited[cx][cy]){continue;} // Continue if already visited
            visited[cx][cy] = true;
            if(matrix[cx][cy] == 0){continue;} // no curSize+1 if not ice
            curSize++;
            for(int i=0; i<4;i++){
                int nx = cx + dxdy[i][0];
                int ny = cy + dxdy[i][1];
                if((nx<0) || (nx>=nlen) || (ny<0) || (ny>=nlen)){continue;}
                queue.add(new Coords(nx, ny));
            }
        }
        maxSize = Math.max(maxSize, curSize);
    }
    
    static void calc(int tlen){
        // Iterate rotateMatrix for each sub-matrix
        for(int i=0; i<nlen; i+=tlen){
            for(int j=0; j<nlen; j+=tlen){
                rotateMatrix(i, j, tlen);
            }
        }
        // Now calculate new numbers for ice in the matrix
        calcIce();
    }
    
    // Rotate each sub-matrix based on the location in the actual matrix
    static void rotateMatrix(int dx, int dy, int tlen){
        int[][] subMatrix = new int[tlen][tlen];
        for(int i=0; i<tlen; i++){
            for(int j=0; j<tlen; j++){
                int cx = (tlen - 1) - j + dx;
                int cy = i + dy;
                subMatrix[i][j] = matrix[cx][cy];
            }
        }
        // Apply results to the original matrix
        for(int i=0; i<tlen; i++){
            for(int j=0; j<tlen; j++){
                matrix[i+dx][j+dy] = subMatrix[i][j];
            }
        }
    }
    
    static void calcIce(){
        // Map to track which nodes need to decrease ice
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        for(int i=0; i<nlen; i++){
            for(int j=0; j<nlen; j++){
                int count = 0; // Count number of adj nodes "without" ice
                int iter = 0;
                while(iter<4 && count<=1){
                    int dx = i + dxdy[iter][0];
                    int dy = j + dxdy[iter][1];
                    iter++;
                    if((dx<0) || (dx>=nlen) || (dy<0) || (dy>=nlen)){
                        count++;
                        continue;
                    }
                    if(matrix[dx][dy] == 0){count++;}
                }
                if(count<=1){continue;}
                else{
                    map.putIfAbsent(i, new HashSet<>());
                    map.get(i).add(j);
                }
            }
        }
        for(Integer x : map.keySet()){
            for(Integer y : map.get(x)){
                matrix[x][y]--;
                if(matrix[x][y] < 0){matrix[x][y] = 0;}
            }
        }
    }
    
    static class Coords{
        int x;
        int y;
        public Coords(int x, int y){
            this.x = x;
            this.y = y;
        }
        public Coords(){
            this.x = -1;
            this.y = -1;
        }
    }
}
