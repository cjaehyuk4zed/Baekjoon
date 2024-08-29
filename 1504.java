import java.util.*;
import java.io.*;

public class Main {

    static class Node implements Comparable<Node> {
        int end;
        int weight;

        public Node(int end, int weight){
            this.end = end;
            this.weight = weight;
        }

				// Must override in order to implement the Comparable<> interface
        @Override
        public int compareTo(Node other){
            return this.weight - other.weight;
        }
    }

    // Number of nodes and edges
    static int N, E;
    // The two nodes which must be visited
    static int V1, V2;
    // Graph of nodes
    static List<List<Node>> graph;

    // Note that PriorityQueue for the Dijkstra algorithm
    // Will be used as local variables, for multiple uses of the algorithm

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        // Init graph
        graph = new ArrayList<List<Node>>(N+1);
        for(int i = 0; i<=N; i++){
            graph.add(new ArrayList<Node>(N+1));
        }
        for(int i = 0; i<E; i++){
            StringTokenizer st1 = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st1.nextToken());
            int b = Integer.parseInt(st1.nextToken());
            int c = Integer.parseInt(st1.nextToken());
            graph.get(a).add(new Node(b, c));
            graph.get(b).add(new Node(a, c));
        }

        // The two nodes which must be visited
        StringTokenizer st2 = new StringTokenizer(br.readLine());
        V1 = Integer.parseInt(st2.nextToken());
        V2 = Integer.parseInt(st2.nextToken());

        // Case 1 : Start -> V1 -> V2 -> Fin
        // Case 2 : Start -> V2 -> V1 -> Fin
        // Case 3 : No such path exists
        // Make the Dijkstra algorithm return the distance, and compare
        // Get arrays of results from each starting point, to reduce the number of Dijkstra iterations needed
        int[] start1 = dijkstra(1);
        int[] startV1 = dijkstra(V1);
        int[] startV2 = dijkstra(V2);

        int case1 = start1[1] + startV1[2] + startV2[3];
        int case2 = start1[2] + startV2[1] + startV1[3];


        // Case 3
        if((start1[1] == -1 || startV1[2] == -1 || startV2[3] == -1)
                && (start1[2] == -1 || startV2[1] == -1 || startV1[3] == -1)) {
            System.out.println(-1);
            return;
        }

        // Case 1 and 2
        System.out.println(Math.min(case1, case2));

        return;
    }

    // Dijkstra shortest path algorithm
    static int[] dijkstra(int start){
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        boolean[] visited = new boolean[N+1];
        int[] dist = new int[N+1];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        // Add starting node with weight=0 to start while loop iteration
        pq.add(new Node(start, 0));

        while(!pq.isEmpty()){
            Node node = pq.poll();
            int cur = node.end;
            if(visited[cur]){
                continue;
            } else{
                visited[cur] = true;
            }

            for(Node tmp : graph.get(cur)){
                int next = tmp.end;
                if(dist[next] > dist[cur] + tmp.weight){
                    dist[next] = dist[cur] + tmp.weight;
                }
                // Add new Node to the PriorityQueue
                // with the total distance from the start node to next destination node
                pq.add(new Node(next, dist[next]));
            }
        }
        for(int i = 1; i<=N; i++){
            if(dist[i] == Integer.MAX_VALUE){
                dist[i] = -1;
            }
        }
        return new int[]{dist[1], dist[V1], dist[V2], dist[N]};
    }
}

/*
> Dijkstra 탐색 알고리즘을 활용하여 풀었다. 
메인 함수 이전에 정의된 클래스와 변수들은 다음과 같다 :
> 
> - `class Node` : 문제풀이를 위한 커스텀 클래스. end는 다음 인접 정점의 인덱스 번호이며, weight는 다음 인접 정점까지의 거리이다. Java의 `PriorityQueue` min Heap 구조에서 “weight” 변수를 기준으로 알아서 자동으로 오름차순 정렬을 할 수 있도록 `Comparable<>` 인터페이스를 상속 받고 `compareTo` 메서드를 오버라이딩했다.
> - `List<List<Node>> graph` : 문제에서 주어지는 그래프를 저장할 변수이다.
> 
> Dijkstra 탐색 알고리즘 내에서 정의된 변수들과 구조는 다음과 같다 :
> (매 탐색마다 리셋해야 되는 변수들이므로, 함수 내에서 정의했다)
> 
> - `PriorityQueue<Node> pq` : PriorityQueue는 Java에 내장되어 있는 클래스이며, 저장된 변수들을 오름차순으로 자동 정렬해준다. 여기서는 앞서 정의했던 class Node의 `compareTo` 메서드에 따라 “시작점부터 다음 노드까지의 거리”를 기준으로 정렬한다. 
> PriorityQueue에서 `pq.poll()` 메서드를 호출하면, 다음으로 거리가 가까운 노드를 호출할 수 있으므로 Dijkstra 탐색에 알맞다.
> - `boolean[] visited` :  이미 방문하고 탐색이 완료된 노드 번호를 기록한다. 사실 처음에는 HashSet<Integer>으로 set.contains() 함수를 활용하는 방법도 생각했었다.
> 하지만 Integer 변수의 크기가 boolean 변수보다 크다는 점과, HashSet에서 hash 방식을 위해 소모하는 메모리량을 고려했을 때 boolean 배열을 활용하는게 더 효율적이다.
> - `int[] dist` : 시작점에서 해당 노드까지의 거리를 저장한다.
> - Dijkstra함수 탐색에서는 우선 시작점을 제외한 dist를 전부 `Integer.MAX_VALUE` 로 설정한다. PriorityQueue에 시작 노드를 weight=0으로 추가해준 다음에, 탐색을 시작한다.
> pq.poll()로 현재 탐색할 노드를 꺼내준다. 이미 방문한 노드이면 건너뛰고, 아니라면 탐색을 시작한다. 해당 노드에 연결된 간선들을 차례대로 탐색하며 dist 배열의 거리값을 업데이트한다. 
> 인접한 정점들을 다음 탐색 후보지로 pq에 저장한다. 이때, 노드 번호와 “시작점부터 해당 노드까지의 거리”를 저장하여 pq에 넣는다.
> 모든 탐색이 종료되면, dist중에서 Integer.MAX_VALUE값 (즉, 도달할 수 없는 노드들)은 “-1”로 변경해준다. 그리고 결과값들을 반환한다. 한번의 탐색에 필요한 정보를 모두 얻기 위해 int 배열을 반환한다.
> 
> 이제 main함수부터 차례대로 살펴보자.
> 
> 문제에서 주어지는 입력값을 알맞게 저장한다. graph 구조까지 완성된 다음에, 문제 상황에서 가능한 3가지 case로 나누어서 생각한다.
> 
> - Case1 : Start -> V1 -> V2 -> Fin
> - Case2 : Start -> V2 -> V1 -> Fin
> - Case 3 : No such path exists (길이 존재하지 않음)
> 
> 시작점, V1, V2에서 다른 노드들까지의 거리값이 필요하므로, dijkstra(1), dijkstra(V1), dijkstra(V2)를 호출하고 결과값을 저장한다. 이때, dijkstra함수를 불필요하게 여러번 호출하지 않도록, dijkstra함수의 반환값을 배열로 받은 다음에 필요한 값들을 한번에 받아왔따.
> 
> 각 결과값을 통해서, 길이 존재하지 않는다면 Case3에 해당하는 결과를 출력한다. 길이 존재한다면, `Math.min(case1, case2)`를 통해서 최소 거리를 출력한다. 
> 
> → 메모 : 단순히 `if(case1 > case2){} else{}` 비교문을 사용했을 때보다, `Math.min(case1, case2)`를 사용하는 것이 실행 속도가 더 빨랐다. Math.min()을 바로 사용하면, if else비교를 할 필요가 없기 때문인 것 같다.
>

*/
