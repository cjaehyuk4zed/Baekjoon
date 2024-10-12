import java.io.*;
import java.util.*;

public class Main{

	static int[][] dxdy = new int[][]{{-1,0}, {-1,-1}, {0,-1}, {1,-1}, {1,0}, {1,1}, {0,1}, {-1,1}};

	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


		// Init
		Fish[][] matrix = new Fish[4][4];
		Map<Integer, Coords> map = new TreeMap<>();
		for(int i=0; i<4;i++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0;j<4;j++){
				int fn = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken()) - 1;
				map.put(fn, new Coords(i, j));
				matrix[i][j] = new Fish(fn, dir, false);
			}
		}
		// Insert shark to start(0,0)
		Fish startFish = matrix[0][0];
		int fn = startFish.fn;
		matrix[0][0] = new Fish(-1, startFish.dir, true); // Insert shark
		map.put(-1, new Coords(0, 0));
		map.remove(fn); // Remove starting fish from map
		int sum = calc(map, matrix, fn);

		System.out.println(sum);
	}

	static int calc(Map<Integer, Coords> map, Fish[][] matrix, int sum){
		for(Integer fn : map.keySet()){
			if(fn != -1){
				moveFish(fn, map, matrix);
			}
		}

//		printMatrix(matrix);

		// Move shark
		Coords scoords = map.get(-1);
		int nx = scoords.x;
		int ny = scoords.y;
		int ox = nx;
		int oy = ny;

		Fish shark = new Fish(-1, matrix[nx][ny].dir, true);
		int sdir = shark.dir;
		int nsum = sum; // New sum after getting return val
		nx += dxdy[sdir][0];
		ny += dxdy[sdir][1];

		// Search until shark goes out of bounds
		while((nx>=0) && (nx<4) && (ny>=0) && (ny<4)){
			if(matrix[nx][ny].isEaten || matrix[nx][ny].fn == 0){
				nx += dxdy[sdir][0];
				ny += dxdy[sdir][1];
				continue;
			}
			int fn = matrix[nx][ny].fn;
			int ndir = matrix[nx][ny].dir;

			Map<Integer, Coords> newMap = copyMap(map);
			Fish[][] newMatrix = copyMatrix(matrix);

			newMap.remove(fn);
			newMap.put(-1, new Coords(nx, ny));
			newMatrix[nx][ny] = new Fish(-1, ndir, true);
			newMatrix[ox][oy] = new Fish(0, 0, false);

			nsum = Math.max(nsum, calc(newMap, newMatrix, sum + fn));
			nx += dxdy[sdir][0];
			ny += dxdy[sdir][1];
		}
		return nsum;
	}

	static Fish[][] copyMatrix(Fish[][] matrix){
		Fish[][] newMatrix = new Fish[4][4];
		for(int i=0; i<4;i++){
			for(int j=0; j<4; j++){
				Fish fish = matrix[i][j];
				newMatrix[i][j] = new Fish(fish.fn, fish.dir, fish.isEaten);
			}
		}
		return newMatrix;
	}

	static Map<Integer, Coords> copyMap(Map<Integer, Coords> map){
		Map<Integer, Coords> newMap = new TreeMap<>();
		for(Integer fn : map.keySet()){
			Coords nc = map.get(fn);
			newMap.put(fn, new Coords(nc.x, nc.y));
		}
		return newMap;
	}

	static void moveFish(int fn, Map<Integer, Coords> map, Fish[][] matrix){
		int x = map.get(fn).x;
		int y = map.get(fn).y;
		Fish cur = matrix[x][y];
		int cfn = cur.fn;
		int dir = cur.dir;

		// No swap happens if conditions not met
		for(int i=0; i<8; i++){
			int ndir = (dir + i) % 8; // Check dirs rotating clockwise;
			int nx = x + dxdy[ndir][0];
			int ny = y + dxdy[ndir][1];
			if((nx<0) || (nx>=4) || (ny<0) || (ny>=4)){continue;} // Out of bounds
			if(matrix[nx][ny].isEaten){continue;} // Fish here is eaten or is shark

			if(matrix[nx][ny].fn == 0){ // Empty space
				Fish cFish = new Fish(cfn, ndir, false);
				matrix[x][y] = new Fish(0, 0, false);
				matrix[nx][ny] = cFish;
				map.put(cfn, new Coords(nx, ny));
				break; // Moved to empty space
			}

			int swapFn = matrix[nx][ny].fn;
			int swapDir = matrix[nx][ny].dir;

			Fish cFish = new Fish(cfn, ndir, false);
			Fish swapFish = new Fish(swapFn, swapDir, false);

			matrix[x][y] = swapFish;
			matrix[nx][ny] = cFish;

			map.put(swapFn, new Coords(x, y));
			map.put(cfn, new Coords(nx, ny));
			break; // Swap complete
		}
	}

	static void printMatrix(Fish[][] matrix){
		for(int i=0; i<4;i++){
			for(int j=0;j<4;j++){
				String str = "";
				if(matrix[i][j].isEaten){
					str += "X";
				} else{
					str += "_";
				}
				System.out.print(matrix[i][j].fn + str + "|");
			}
			System.out.println("");
		}
		System.out.println("-----------------");
	}


	static class Coords{
		int x;
		int y;
		public Coords(int x, int y){
			this.x = x;
			this.y = y;
		}
		public Coords(){
			this.x = -1;
			this.y = -1;
		}
	}

	static class Fish{
		int fn;
		int dir;
		boolean isEaten;
		public Fish(int fn, int dir, boolean isEaten){
			this.fn = fn;
			this.dir = dir;
			this.isEaten = isEaten;
		}
		public Fish(){
			this.fn = -1;
			this.dir = -1;
			this.isEaten = true;
		}
	}
}
