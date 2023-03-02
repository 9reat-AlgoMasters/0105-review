import java.io.*;
import java.util.*;

public class Main_2636 {
	static int N;
	static int M;
	static int[][] map;
	static int time;
	static int count;
	static int dir[][] = {{1,0},{-1,0},{0,1},{0,-1}};
	static int[][] copy;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		copy = new int[N][M];
		

		time =0;
		while (true) {
			time++;
			for (int i = 0; i < N; i++) {
				copy[i] = Arrays.copyOf(map[i], map[i].length);
			}
			
			bfs();
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if(copy[i][j]==1) {
						for (int d = 0; d < 4; d++) {
							int nr= i+dir[d][0];
							int nc= j+dir[d][1];
							if(isRange(nr, nc) && copy[nr][nc]==2) {
								map[i][j]=3;
								break;
							}
						}
					}
				}
			}
			int one = 0;
			count = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if(map[i][j] == 1) {
						one++;
					}
					if(map[i][j] == 3) {
						map[i][j]=0;
						count++;
					}
				}
			}
			if(one ==0) {
				break;
			}
			
		}
		
		System.out.println(time);
		System.out.println(count);
	}
	
	static void bfs() {
		Queue<int[]> q = new LinkedList<> ();
		q.add(new int[] {0,0});
		copy[0][0] = 2;
		while (!q.isEmpty()) {
			int n[] = q.poll();
			for (int i = 0; i < 4; i++) {
				int nr = n[0] + dir[i][0];
				int nc = n[1] + dir[i][1];
				if(isRange(nr,nc) && copy[nr][nc]==0) {
					copy[nr][nc] = 2;
					q.add(new int[] {nr, nc});
				}
			}
			
		}
		
	}
	static boolean isRange(int nr, int nc) {
		return nr >=0 && nc >=0 && nr < N && nc <M;
	}
	
	
	static void printmap(int[][] map) {
		for (int i = 0; i <N; i++) {
			for (int j = 0; j < M; j++) {
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("=====================");
	}

}
