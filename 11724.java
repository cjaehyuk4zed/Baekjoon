import java.util.*;
import java.io.*;

public class Main {

    /**
     * Can use a boolean[] array for the visited array
     * But int array seems to be more efficient...?
     */
    static int[] visited;
    static List<Deque<Integer>> edges;

    // recursion on each element of list
    // check visited during recursion iteration

    public static void main(String[] args) throws IOException{

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());

        final int N = Integer.parseInt(st.nextToken());
        final int M = Integer.parseInt(st.nextToken());
        int count = 0;

        //track visited nodes
        visited = new int[N+1];
        visited[0] = 1; // since 0 node does not exist

        //init edges arraylist
        edges = new ArrayList<Deque<Integer>>(N+1); // 0 node does not exist
        for(int i = 0; i<=N; i++){
            Deque<Integer> list = new ArrayDeque<Integer>();
            edges.add(list);
        }

        //add edge nodes
        for(int i = 0; i<M; i++){
            StringTokenizer st1 = new StringTokenizer(bf.readLine());
            int a = Integer.parseInt(st1.nextToken());
            int b = Integer.parseInt(st1.nextToken());
            edges.get(a).add(b);
            edges.get(b).add(a);
        }

        for(int i = 0; i<=N; i++){
            if(visited[i] == 0){
                iter(i);
                count++;
            }
        }

        System.out.println(count);
    }

    /**
     * This code is the most efficient code
     * Skip iter() if visited[a] == 1
     * Read without modifying the deque
     */
    static void iter(int cur){
        visited[cur] = 1;
        for(int a : edges.get(cur)){
            if(visited[a] == 0){
                iter(a);
            }
        }
    }

    /**
     * This code is more efficient than the below code
     * Skip iter() if visited[a] == 1
     */
    static void iterByPop(int cur){
        visited[cur] = 1;
        while(!edges.get(cur).isEmpty()){
            int a = edges.get(cur).pop();
            if(visited[a] == 0){
                iter(a);
            }
        }
    }

    /**
     * This code was reason for the inefficiency.
     * Must iter until every element in the list's deques have been popped
     */
    static void iterByPopOnly(int cur){
        visited[cur] = 1;
        while(!edges.get(cur).isEmpty()){
            int a = edges.get(cur).pop();
            visited[a] = 1;

            iter(a);
        }
    }

}

// Example input values 
/*
6 8
1 2
2 5
5 1
3 4
4 6
5 4
2 4
2 3
*/
