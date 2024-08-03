
import java.util.*;
import java.io.*;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        
        int N = Integer.parseInt(br.readLine());
        
        for(int i = 0; i < N; i++){
            int num = Integer.parseInt(br.readLine());
            int write = 0;
            if(num == 0){
                if(!pq.isEmpty()){
                    write = pq.poll();
                }
                bw.write(write + "\n");
            }
            else {
                pq.add(num);
            }
        }
        bw.flush();
    }
}

/**
> Java에서는 heap구조를 자동으로 생성해주는 PriorityQueue라는 클래스가 존재한다.
> 
> 
> 이를 활용하기 위해 우선 PriorityQueue pq를 선언했다.
> 
> 그리고 최종 출력을 위하여 BufferedWriter bw를 선언했다. (처음에는 System.out.println을 활용하여 풀이를 했었는데, 이때 풀이 시간이 812ms가 나왔다. BufferedWriter 풀이의 340ms와 비교하면 차이가 매우 크다).
> 
> - 입력이 ‘0’일 때 : pq에 숫자들을 삽입하면 자동으로 minHeap 순서로 정렬이 된다. 따라서 pq에 원소가 없을 경우에는 0을 bw에 추가하고, 있을 경우 단순히 root 원소를 뽑아내어 bw에 추가했다.
> - 입력이 ‘0’이 아닌 숫자일 때 : pq에 숫자들을 삽입하면 자동으로 minHeap 순서로 정렬이 된다. 따라서 단순히 pq의 끝에 숫자를 추가하면, 알아서 정렬이 된다.
> 
> 이후 최종 결과 bw를 출력하면 정답이 된다.
> 
> PriorityQueue클래스가 Java에 내재되어있는 클래스로써 최적화가 잘 되어있는지, 혹은 위에서 직접 구현했는 heap 구조에서 비효율적인 부분이 있었던 것인지는 모르겠으나, 현제 결과론적으로는 PriorityQueue를 활용한 풀이가 메모리와 시간 둘 다 효율적이었다.
>
*/

