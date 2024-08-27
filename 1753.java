import java.util.*;
import java.io.*;

public class Main {

    // Implement Comparable<> interface to use PriorityQueue sorting
    static class Node implements Comparable<Node>{
        int end, weight;

        public Node(int end, int weight){
            this.end = end;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }

    static int V, E, K;

    static List<List<Node>> nodes;
    static int[] dist;
    static PriorityQueue<Node> queue;


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // Get init params
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(br.readLine());

        // Init nodes matrix
        nodes = new ArrayList<List<Node>>(V+1);
        for(int i = 0; i <=V; i++){
            nodes.add(new ArrayList<Node>());
        }
        for(int i = 0; i <E; i++){
            StringTokenizer st1 = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st1.nextToken());
            int v = Integer.parseInt(st1.nextToken());
            int w = Integer.parseInt(st1.nextToken());
            nodes.get(u).add(new Node(v, w));
        }

        // Init dist array
        dist = new int[V+1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // Init priority queue
        queue = new PriorityQueue<Node>();

        // Run Dijkstra
        dijkstra(K);

        // Add results to BufferedWriter
        for(int i = 1; i<=V;i++){
            if(dist[i] == Integer.MAX_VALUE){
                bw.write("INF\n");
            } else{
                bw.write(dist[i] + "\n");
            }
        }

        // Flush and Close BufferedWriter
        bw.flush();
        bw.close();
    }

    static void dijkstra(int start){
        // Add starting node with weight=0 to start while loop iteration
        queue.add(new Node(start, 0));
        // Distance from start to start = 0
        dist[start] = 0;
        // HashSet to keep track of visited nodes
        HashSet<Integer> set = new HashSet<Integer>();

        while(!queue.isEmpty()){
            // PriorityQueue will poll the next "shortest distance" node
            Node node = queue.poll();
            int cur = node.end;

            // Skip already visited nodes
            if(set.contains(cur)){
                continue;
            } else{
                set.add(cur);
            }

            for(Node tmp : nodes.get(cur)){
                // Next destination node (temporary)
                int next = tmp.end;
                if(dist[next] > dist[cur] + tmp.weight){
                    dist[next] = dist[cur] + tmp.weight;
                }
                // Add new Node to the PriorityQueue
                // with the total distance from the start node to next destination node
                queue.add(new Node(next, dist[next]));
            }
        }
    }
}


/*
> 우선 각 Node에서, 다음 Node까지 이어지는 간선을 표현하기 위해 `class Node` 를 정의해서 사용했다. `(end = 다음 노드 번호, weight = 간선 길이)`
> 
> - `static List<List<Node>> nodes` : 매트릭스 구조 (directed weighted matrix)에 djikstra 탐색 알고리즘을 적용했다.
> - `static int[] dist` : 통해서 시작점부터 각 정점까지의 최단 경로를 전역변수로 관리했다.
> - `static PriorityQueue<Node> queue` : Java에 내장되어 있는 클래스다. min Heap 구조를 구현할 수 있는 “우선순위 큐”를 활용함으로써, dijkstra 탐색에서 “다음으로 가까운 목적지”를 순차적으로 탐색하는 방식을 구현하는데 사용했다.
> 
> `dijkstra()`함수로 한번의 탐색으로 모든 최단 경로를 계산하고, 이를 알맞게 출력한다. 탐색 과정에서, 탐색이 완료된 노드는 `HashSet<Integer> set`에 저장하여 다시 방문하지 않도록 한다.
> 
> 현재 노드에 연결되어 있는 간선들을 dist 배열에 저장된 거리값과 비교하여 더 작은 값 (최소 거리)를 저장한다. 비교 후에 queue에 다음 노드와 “**시작점부터 다음 노드까지의 거리**”를 저장한다.
> 
> 노드에 연결된 모든 간선을 탐색하면, 해당 노드는 set에 탐색 완료로 표시한다. 그리고 `queue.poll()` 을 통해서 다음 가장 가까운 목적지부터 탐색을 시작한다. 이는 PriorityQueue에서, 각 노드들을 오름차순으로 자동 정렬하기 때문에 가능하다.
> 
> 모든 노드가 탐색이 종료되면, dist 배열에 저장된 값에 따라 순차적으로 결과를 출력하면 정답이 된다.
>
*/
