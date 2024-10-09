import java.util.*;
import java.io.*;

public class Main{

	static int[][] matrix;
	static int N;
	// Includes diagonals
	static int[][] dxdyall = new int[][]{{0,-1}, {-1,-1}, {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1, -1}};
	static int[][] dxdydiag = new int[][]{{1,1}, {1,-1}, {-1,1}, {-1,-1}};

	static HashMap<Integer, HashSet<Integer>> map;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		matrix = new int[N][N];
		map = new HashMap<>();

		// Init matrix
		for(int i=0; i<N;i++){
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++){
				matrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// Add first clouds
		map.put(N-1, new HashSet<Integer>());
		map.put(N-2, new HashSet<Integer>());
		map.get(N-1).add(0);
		map.get(N-1).add(1);
		map.get(N-2).add(0);
		map.get(N-2).add(1);

		// Run calcs
		for(int i=0; i<M; i++){
			st = new StringTokenizer(br.readLine());
			// -1 because of indexing from 0
			int dir = Integer.parseInt(st.nextToken()) - 1;
			int dist = Integer.parseInt(st.nextToken());

			calc(dir, dist);
		}
//		System.out.println("final map = " + map);

		int sum = 0;
		for(int i=0; i<N; i++){
			for(int j=0; j<N; j++){
				sum += matrix[i][j];
			}
		}

		System.out.println(sum);
	}

	static void calc(int dir, int dist){
		HashMap<Integer, HashSet<Integer>> tmpMap = new HashMap<>();
//		System.out.println("map = " + map);
		int dx = dxdyall[dir][0];
		int dy = dxdyall[dir][1];
		// Calculate moved cloud locations
		for(Integer i : map.keySet()){ // Map keys
			int nx = i + dx*dist;
			while(nx >= N){nx = nx - N;}
			while(nx < 0){nx = N + nx;}
			tmpMap.putIfAbsent(nx, new HashSet<Integer>());
			for(Integer j : map.get(i)){ // HashSet in key
				int ny = j + dy*dist;
				while(ny >= N){ny = ny - N;}
				while(ny < 0){ny = N + ny;}
				tmpMap.get(nx).add(ny);
				matrix[nx][ny]++; // Increment water+1 in matrix
			}
		}
//		System.out.println("tmpMap = " + tmpMap);
		map.clear();
//		System.out.println("map = " + map);
//		System.out.println("--------------------");
		// Increment water using diagonals
		for(Integer i : tmpMap.keySet()){
			for(Integer j : tmpMap.get(i)){
				int count = 0;
				for(int k=0; k<4; k++){
					int nx = i + dxdydiag[k][0];
					int ny = j + dxdydiag[k][1];
					if((nx<0) || (nx>=N) || (ny<0) || (ny>=N)){continue;}
					if(matrix[nx][ny]>=1){count++;}
				}
				matrix[i][j] += count;
			}
		}

		// Traverse matrix and calc new clouds
		// Check for prev cloud location overlapping
		for(int i=0; i<N; i++){
			for(int j=0; j<N;j++){
				if(matrix[i][j]>=2){
					if(tmpMap.containsKey(i)){
						if(tmpMap.get(i).contains(j)){
							continue;
						}
					}
					matrix[i][j] -=2;
					map.putIfAbsent(i, new HashSet<Integer>());
					map.get(i).add(j);
				}
			}
		}
	}

}
