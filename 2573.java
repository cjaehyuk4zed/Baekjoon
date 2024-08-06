import java.util.*;
import java.io.*;

public class Main{

    static class Iceberg{
        // Height of iceberg
        int h;
        // Number of adjacent ocean nodes
        int adj;

        public Iceberg(int h, int adj){
            this.h = h;
            this.adj = adj;
        }
    }

    // Use static int, in order to use values in external functions
    static int M;
    static int N;

    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static List<List<Iceberg>> icebergs;

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        icebergs = new ArrayList<List<Iceberg>>();


        // init icebergs matrix, with values of adj=0
        for(int i = 0; i< N; i++){
            StringTokenizer st1 = new StringTokenizer(br.readLine());
            icebergs.add(new ArrayList<Iceberg>());

            for(int j = 0; j< M; j++){
                int h = Integer.parseInt(st1.nextToken());
                Iceberg tmp = new Iceberg(h, 0);
                icebergs.get(i).add(tmp);
            }
        }
        int year = 0;
        while(true){
            year++;
            // Calculate adj values of each iceberg
            calcAdj();

            // Calculate height of icebergs the next year
            calcHeight();

            // Check if iceberg has been split into multiple pieces
            int count = findCycle();
            if(count == 0){
                System.out.println(0);
                break;
            }
            if(count >= 2){
                System.out.println(year);
                break;
            }

        }

    }

    // Function to calculate the adj values of each iceberg
    static void calcAdj(){
        for(int i = 0; i< N; i++){
            for(int j = 0; j< M; j++){

                for(int k=0; k<4; k++){
                    int x = i+dir[k][0];
                    int y = j+dir[k][1];

                    if(x>=0 && x< N && y>=0 && y< M){
                        if(icebergs.get(x).get(y).h == 0){
                            icebergs.get(i).get(j).adj++;
                        }
                    }
                }
            }
        }
    }

    // Function to calculate height of icebergs the next year
    // Also, reset adj values to 0
    static void calcHeight(){
        for(int i = 0; i< N; i++){
            for(int j = 0; j< M; j++){

                int num = icebergs.get(i).get(j).h;
                num -= icebergs.get(i).get(j).adj;

                if(num <0){num = 0;}
                icebergs.get(i).get(j).h = num;
                icebergs.get(i).get(j).adj = 0;
            }
        }
    }

    // Function to check if iceberg has been split into multiple pieces
    static int findCycle(){
        int[][] visited = new int[N][M];
        int count = 0;

        for(int i = 0; i< N; i++){
            for(int j = 0; j< M; j++){
                if(icebergs.get(i).get(j).h != 0 && visited[i][j] == 0){
                    count++;
                    bfs(i, j, visited);
                }
            }
        }
        if(count == 0){
            return 0;
        }
        if(count >= 2) {
            return count;
        }
        return 1;
    }

    static void bfs(int i, int j, int[][] visited){
        visited[i][j] = 1;
        for(int k=0; k<4; k++){
            int x = i+dir[k][0];
            int y = j+dir[k][1];

            if(x>=0 && x< N && y>=0 && y< M){
                if(icebergs.get(x).get(y).h != 0 && visited[x][y] == 0){
                    bfs(x, y, visited);
                }
            }
        }
    }
}


/**
메모리 사용량과 시간이 되게 큰 것 같아서 백준에 Java 다른 풀이들을 찾아보니까 시간은 조금 느린데 메모리 사용량은 제 풀이가 가장 적습니다…놀라운 사실…ㅇㅅㅇ…

문제에서 ‘빙산이 인접한 바다 칸의 개수만큼 매년 녹는다’라고 한다. 

이때 빙산이 매년 녹는 것을 계산하기 위해서, 순간 캡처처럼 해당 년도에서 각 빙산에 인접한 바다 칸 개수를 저장해야 한다. 실시간으로 계산을 하게 되면 앞선 빙산이 먼저 녹은 것이 다음 빙산의 녹는 계산에 영향을 주기 때문이다.

따라서, 빙산은 높이(h)와 해당 년도의 인접한 바다 칸의 개수(adj)를 저장하는 Iceberg 클래스를 정의했다.

Iceberg 클래스 객체로 매트릭스를 생성하며, 처음에는 높이(h) 값만 입력받고, (adj)는 0으로 설정했다. 

그리고 다음 함수들을 정의하여 필요한 연산들을 진행했다 :

- calcAdj() 함수 : 인접한 바다 칸의 개수를 계산하고, 각 Iceberg 개체에 저장한다.
- calcHeight() 함수 : 인접한 바다 칸의 개수에 따라서 각 Iceberg의 다음 년도의 높이(h)를 계산한다 (음수가 되면 0으로 설정). 또한, 다음 년도의 계산을 위해서 (adj) 값을 0으로 초기화 한다.
- findCycle() 함수 : 빙산의 덩어리를 찾기 위한 함수. 서로 연결되어 있는 cycle을 이루는 빙산들을 찾으며, 하나의 cycle을 찾을 때마다 count를 증가한다.
    - count==1이 문제에서 주어지는 초기 상태이다
    - count==0 인 경우에는, 모든 빙산이 녹았다는 뜻이기에 0을 반환한다.
    - count≥2인 경우에는 문제의 조건을 달생했다.
- bfs() 함수 : findCycle() 함수에서, 하나의 cycle에 속한 칸들을 탐색하기 위한 알고리즘

다시 main 함수로 돌아와보자. 

main함수의 while(true) 문에서, calcAdj()와 calcHeight()를 순서대로 계산하여, 해당 년도에서 인접한 바다 칸 개수(adj)를 계산하고 이에 따라 다음 년도의 빙산 높이(h)를 계산한다. 그 다음에 findCycle() 함수를 통해서 문제의 조건이 충족 되었는지 검사한다. 이 과정을 문제의 조건이 충족되거나 (count≥2), 모든 빙산이 녹아서 더 이상 진행이 되지 않을 때 (count==0)까지 while반복문을 실행하여 계산한다.

그리고 count를 출력하면 정답이 된다.
*/
