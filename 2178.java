// What do I need
// I start from the corner, so no need to keep track of distance per node like Dijkstra
// Just Keep list of visited nodes
// I need coords, and probably use a list, list?

import java.util.*;
import java.io.*;

public class Main{

    static class Coords{
        int x;
        int y;
        public Coords(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    static class Node{
        int dist;
        boolean visited;
        public Node(int dist, char c){
            this.dist = dist;
            if(c == '1'){this.visited = false;}
            else{this.visited = true;}
        }
    }

  // Array for traversing left, right, bottom, top
    static int[][] dir = {{-1, 0},{1,0},{0,-1},{0, 1}};

    static List<List<Node>> map;

    static Deque<Coords> queue;

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // Initialize map
        map = new ArrayList<List<Node>>(n+1);
        for(int i = 0; i < n; i++){
            map.add(new ArrayList<Node>(m+1));
            String line = br.readLine();
            for(int j = 0; j<m;j++){
                map.get(i).add(new Node(1, line.charAt(j)));
            }
        }

        // Initialize Queue
        queue = new ArrayDeque<Coords>();
        queue.add(new Coords(0,0));

        // BFS Algorithm
        while(!queue.isEmpty()){
            Coords current = queue.poll();

            int current_dist = map.get(current.x).get(current.y).dist;

            for(int i = 0; i <4; i++){
                int x = current.x + dir[i][0];
                int y = current.y + dir[i][1];

                if(!(x >= 0 && x < n && y >= 0 && y < m)){
                    continue;
                }

                if(!map.get(x).get(y).visited){
                    map.get(x).get(y).dist = current_dist + 1;
                    map.get(x).get(y).visited = true;
                    queue.add(new Coords(x, y));
                }

            }
        }

        System.out.println(map.get(n-1).get(m-1).dist);


    }
}
