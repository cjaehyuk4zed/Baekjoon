import java.util.*;
import java.io.*;

public class Main {

    static class House {
        int r, g, b;

        public House(int r, int g, int b){
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    static List<House> houses;
    static int[][] dist;

    public static void main(String[] args) throws IOException{                System.out.println(pq.poll());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        houses = new ArrayList<House>(N);
        dist = new int[N][3];

        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            houses.add(new House(r, g, b));
        }

        dist[0][0] = houses.get(0).r;
        dist[0][1] = houses.get(0).g;
        dist[0][2] = houses.get(0).b;

        System.out.println(Math.min(calc(N-1, 0), Math.min(calc(N-1, 1), calc(N-1, 2))));
    }

    static int calc(int N, int color){
        int num = color;

        if(dist[N][num] == 0 && (N-1) >= 0){
            if(color == 0){
            // memoization 전에는, dist[N][num]에 저장하는 것 없이
            // return Math.min(calc(N-1, 1), calc(N-1, 2)) + houses.get(N).r;
                return dist[N][num] = Math.min(calc(N-1, 1), calc(N-1, 2)) + houses.get(N).r;
            }
            if(color == 1){
                return dist[N][num] = Math.min(calc(N-1, 0), calc(N-1, 2)) + houses.get(N).g;
            }
            if(color == 2){
                return dist[N][num] = Math.min(calc(N-1, 0), calc(N-1, 1)) + houses.get(N).b;
            }
        }
        return dist[N][num];
    }

}

/**
> 재귀 함수 방식으로 풀이를 했다.
> 
> 
> 아래 풀이 설명에서, 거리 (distance)와 거리 (road) 용어가 했갈리므로 이 용어들은 영어로 사용하겠다.
> 
> 문제에서 road의 각 위치에서 집이 R, G, B로 3가지 색깔과 그에 해당하는 distance값을 가지므로, 이에 맞추어 class House 를 생성했다. 그리고 House 클래스의 배열인 List<House> houses를 선언했다.
> 
> 그리고 road의 각 위치까지의 색깔과 distance 합산 값을 저장할 매트릭스 int[][] dist 를 선언했다.
> 
> 시작 위치인 dist[0]에 초기 거리 값을 넣어줬다. 그리고는 calc(int N, int color) 함수를 정의하여, 목적지인 dist[N] 부터 시작하여 dist[0]까지 거슬러 올라가는 재귀 함수를 실행한다.
> 
> 각 위치에서의 최소값은  Math.min() 메서드를 통해서 계산하였고, 이를 dist[N]에 저장하면서 다음 재귀문으로 반환하는 memoization을 통해 코드의 실행 시간을 줄였다 (…사실은 memoization을 넣기 전에는 계속 `시간 초과`가 되었었다…)
> 
> calc 재귀함수를 “road의 마지막 위치에 집의 색깔이 R/G/B”인 3가지 경우에 대해 각각 실행하고, 이 중에서 최소값을 출력함으로써 정답을 도출했다.
>
*/
