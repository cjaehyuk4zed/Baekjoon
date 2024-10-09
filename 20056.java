import java.io.*;
import java.util.*;

public class Main{
    
    static HashMap<Integer, HashMap<Integer, List<Fireball>>> map;
    static int N; // for nx and ny range
    static int[][] dxdy = new int[][]{{-1,0}, {-1,1}, {0, 1}, {1,1}, {1,0}, {1,-1}, {0, -1}, {-1,-1}};
    
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        
        map = new HashMap<>();
        
        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int spd = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            map.putIfAbsent(x, new HashMap<Integer, List<Fireball>>());
            map.get(x).putIfAbsent(y, new ArrayList<Fireball>());
            map.get(x).get(y).add(new Fireball(m, spd, dir));
        }
        
        for(int i=0; i<K; i++){
            calc();
        }
        
        int sum = calcSum();
        
        System.out.println(sum);
        
    }
    
    
    static void calc(){
        HashMap<Integer, HashMap<Integer, List<Fireball>>> tmpMap = new HashMap<>();
        
        // Calculate location of new fireballs
        for(Integer x : map.keySet()){
            for(Integer y : map.get(x).keySet()){
                if(map.get(x).get(y).isEmpty()){continue;} // Continue if no fireballs found
                for(Fireball fb : map.get(x).get(y)){
                    int nx = x + dxdy[fb.dir][0]*fb.spd;
                    int ny = y + dxdy[fb.dir][1]*fb.spd;
                    while(nx>N){nx = nx - N;}
                    while(nx<=0){nx = N + nx;}
                    while(ny>N){ny = ny - N;}
                    while(ny<=0){ny = N + ny;}
                    tmpMap.putIfAbsent(nx, new HashMap<>());
                    tmpMap.get(nx).putIfAbsent(ny, new ArrayList<Fireball>());
                    tmpMap.get(nx).get(ny).add(fb);
                }
            }
        }
        
        map.clear();
        // Check for multiple fireballs in one node, and place results
        for(Integer x : tmpMap.keySet()){
            for(Integer y : tmpMap.get(x).keySet()){
                int size = tmpMap.get(x).get(y).size();
                if(size <= 0){continue;}
                if(size == 1){ // Add just 1 fireball to map
                    Fireball fb = tmpMap.get(x).get(y).get(0);
                    map.putIfAbsent(x, new HashMap<>());
                    map.get(x).putIfAbsent(y, new ArrayList<Fireball>());
                    map.get(x).get(y).add(fb);
                    continue;
                }
                // Add multiple fireballs, then place results in the map
                int sumM = 0;
                int sumSpd = 0;
                boolean isEven = false;
                boolean isOdd = false;
                for(Fireball fb : tmpMap.get(x).get(y)){
                    sumM += fb.m;
                    sumSpd += fb.spd;
                    if(fb.dir %2 == 0){isEven = true;}
                    else{isOdd = true;}
                }
                int newM = sumM/5;
                if(newM == 0){continue;}
                int newSpd = sumSpd/size;
                if(isOdd && isEven){ // 홀수 짝수 방향 섞임
                    map.putIfAbsent(x, new HashMap<>());
                    map.get(x).putIfAbsent(y, new ArrayList<Fireball>());
                    map.get(x).get(y).add(new Fireball(newM, newSpd, 1));
                    map.get(x).get(y).add(new Fireball(newM, newSpd, 3));
                    map.get(x).get(y).add(new Fireball(newM, newSpd, 5));
                    map.get(x).get(y).add(new Fireball(newM, newSpd, 7));
                } else{
                    map.putIfAbsent(x, new HashMap<>());
                    map.get(x).putIfAbsent(y, new ArrayList<Fireball>());
                    map.get(x).get(y).add(new Fireball(newM, newSpd, 0));
                    map.get(x).get(y).add(new Fireball(newM, newSpd, 2));
                    map.get(x).get(y).add(new Fireball(newM, newSpd, 4));
                    map.get(x).get(y).add(new Fireball(newM, newSpd, 6));
                }
            }
        }
    }
    
    static int calcSum(){
        int sum = 0;
        for(Integer x : map.keySet()){
            for(Integer y : map.get(x).keySet()){
                for(Fireball fb : map.get(x).get(y)){
                    sum += fb.m;
                }
            }
        }
        
        return sum;
    }
    
    
    static class Fireball{
        int m;
        int spd;
        int dir;
        public Fireball(int m, int spd, int dir){
            this.m = m;
            this.spd = spd;
            this.dir = dir;
        }
        public Fireball(){
            this.m = -1;
            this.spd = -1;
            this.dir = -1;
        }
    }
}
