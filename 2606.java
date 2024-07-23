import java.util.*;
import java.io.*;

public class Main{

    static List<List<Integer>> pcs;
    static Deque<Integer> stack;

    /**
     * Use HashSet instead of Deque or Stack to keep track of visited nodes
     * Deque and Stack are not efficient for contains() operation, compared 
     * Deque has a linear method of searching if the element exist in the Deque, meaning the entire Deque must be searched to find the element
     * Thus, this gives the Deque contains() method a time complexity of O(n)
     *
     * On the other hand
     * HashSet uses a hash method to searching if the element is in the set
     * Thus, HashSet contains method has a time complexity of O(1) (constant time)
     */
    static HashSet<Integer> visited;


    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int num = Integer.parseInt(br.readLine());
        int conns = Integer.parseInt(br.readLine());

        pcs = new ArrayList<List<Integer>>(num+1);
        for(int i = 0; i<=num;i++){
            pcs.add(new ArrayList<Integer>());
        }

        for(int i=0; i<conns;i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            pcs.get(a).add(b);
            pcs.get(b).add(a);
        }

        stack = new ArrayDeque<Integer>(num);
        visited = new HashSet<Integer>(num);

        stack.push(1);
        while(!stack.isEmpty()){
            int current = stack.pop();

            if(visited.contains(current)){
                continue;
            }

            /**
             * Alternative method of checking if the element is in the HashSet using the add() method
             * If the element is not in the HashSet, the add() method will add the element to the HashSet and return true
             * If the element is already in the HashSet, the add() method will not add the element (prevent duplicate) and return false
             *
             * From the official Java Oracle documentation :
             * " boolean add(E e) "
             * " ...adds the specified element e to this set if the set contains no element e2 such that Objects.equals(e, e2) "
             */
//            if(!visited.add(current)){
//                continue;
//            }

            /**
             * If the current node has not been visited, add its connected computers to the stack (deque) for iteration
             */
            for(Integer i : pcs.get(current)){
                stack.push(i);
            }
        }

        System.out.println(visited.size() -1);

        /**
         * How to check the elements in the current HashSet
         * Use an iterator to iterate through the HashSet
         * Iterate until there are no more elements using the hasNext() method
         * The iterator will iterate through the HashSet in the order of the hash
         */
//        Iterator iter = visited.iterator();
//        while(iter.hasNext()){
//            System.out.println("HashSet : " + iter.next());
//        }

    }
}

// Example input values
/*
7
6
1 2
2 3
1 5
5 2
5 6
4 7
 */
