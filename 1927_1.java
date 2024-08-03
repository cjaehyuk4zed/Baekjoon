import java.util.*;
import java.io.*;

public class Main{
    
    static List<Integer> heap;
    
    public static void main(String[] args) throws IOException{
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        int N = Integer.parseInt(br.readLine());
        heap = new ArrayList<Integer>(N);
        
        for(int i = 0; i<N; i++){
            int num = Integer.parseInt(br.readLine());
            int write = 0;
            if(num == 0){
                if(!heap.isEmpty()){
                    write = pollHeap();
                }
                bw.write(write + "\n");
            } else{
                addHeap(num);
            }
        }
        
        bw.flush();
        bw.close();
    }
    
    // Get and remove root element of the heap
    static int pollHeap(){
        int num = heap.get(0);
        heap.set(0, heap.get(heap.size()-1));
        heap.remove(heap.size()-1);
        
        heapifyFirst();
        return num;
    }
    
    // Add element to end of heap
    static void addHeap(int num){
        heap.add(num);
        heapifyLast();
    }
    
    // Heapify after the root element has been modified
    static void heapifyFirst(){
        int left, right;
        int index = 0;
        int smallest;
        
        while(true){
            smallest = index;
            left = 2*index + 1;
            right = 2*index + 2;
            
            if(left < heap.size() && heap.get(left) < heap.get(smallest)){
                smallest = left;
            }
            if(right < heap.size() && heap.get(right) < heap.get(smallest)){
                smallest = right;
            }
            if(smallest!=index){
                int tmp = heap.get(index);
                heap.set(index, heap.get(smallest));
                heap.set(smallest, tmp);
                index = smallest;
            } else{
                break;
            }   
        }
    }
    
    // Heapify after the last element has been added/modified
    static void heapifyLast(){
        int index = heap.size() - 1;
        int parent;
        
        while(index>0){
            parent = (index-1)/2;
            if(heap.get(parent) > heap.get(index)){
                int tmp = heap.get(index);
                heap.set(index, heap.get(parent));
                heap.set(parent, tmp);
            }
            index = parent;
        }
    }
    
}

/**
> Java의 List 클래스로 heap 구조를 구현했다. 4가지 함수를 직접 구현하여, minHeap 구조의 정렬 기능을 구현했다.
> 
> - pollHeap() 메서드는 root 원소를 뽑아낸 후에, heapifyFirst() 함수로 minHeap를 정렬한다.
> - heapifyFirst() 메서드는 마지막 원소를 root 위치로 이동 시킨다. 그리고 root 원소에 변경이 있었기에, root 부터 minHeap 정렬을 실행한다. index를 root위치로 선언 후, left/right위치의 child node 값과 비교한다. child node중 값이 더 작은 것이 있으면 서로 위치를 변경하고, 없다면 정렬을 종료한다.
> - addHeap() 메서드는 heap의 끝에 원소를 추가한 후에, heapifyLast() 함수로 minHeap를 정렬한다.
> - heapifyLast() 메서드는 마지막 원소부터 minHeap 정렬을 실행한다. index를 마지막 원소의 위치로 선언 후, parent인덱스를 (index-1)/2 로 계산하면서 root에 도달할 때까지 정렬을 반복한다.
> 
> 그리고 최종 출력을 위하여 BufferedWriter bw를 선언했다. (처음에는 System.out.println을 활용하여 풀이를 했었는데, 이때 풀이 시간이 812ms가 나왔다. BufferedWriter 풀이의 340ms와 비교하면 차이가 매우 크다).
> 
> - 입력이 ‘0’일 때 : heap에 원소가 없을 경우에는 0을 bw에 추가한다. heap에 원소가 있을 경우에는 pollHeap()로 root 원소를 뽑아내어 bw에 추가하고, minHeap로 정렬한다.
> - 입력이 ‘0’이 아닌 숫자일 때 : addHeap()로 원소를 추가하고 minHeap로 정렬한다.
> 
> 이후 최종 결과 bw를 출력하면 정답이 된다.
>
*/
