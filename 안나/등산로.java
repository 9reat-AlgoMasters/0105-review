import java.io.*;
import java.util.*;

public class Solution_등산로 {
	static int T;
	static int N;
	static int K; // 최대 공사 가능 깊이
	static int map[][];
	static int len;
	static int high;
	static int[][] dir = {{0,1},{0,-1},{1,0},{-1,0}};
	static boolean[][] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			
			map = new int[N][N];
			len=0;
			visited  = new boolean[N][N];
			high =0;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					high = Math.max(high, map[i][j]);
				}
			}

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if(map[i][j] == high) {
						visited[i][j] = true;
						dfs(i, j, false, 1);
						visited[i][j] = false;
					}
				}
			}
			
			System.out.printf("#%d %d\n", t, len);
			
		}
		
		
		
		
	}
	 static void dfs(int r, int c, boolean used, int tot) {
		 for (int i = 0; i < 4; i++) {
			 int nr = r + dir[i][0];
			 int nc = c + dir[i][1];
			 if(isRange(nr,nc) && !visited[nr][nc]) {
				 if(map[nr][nc] >= map[r][c]) {
					 if(!used && map[nr][nc]-K < map[r][c]) {
						 visited[nr][nc] = true;
						 int temp = map[nr][nc];
						 map[nr][nc] = map[r][c]-1;
						 dfs(nr,nc,true, tot+1);
						 map[nr][nc]= temp;
						 visited[nr][nc] = false;
					 }
				 }else {
					 visited[nr][nc] = true;
					 dfs(nr,nc, used, tot+1);
					 visited[nr][nc] = false;
				 }
			 }
		}
		 
		 len = Math.max(len, tot);
	}
	static boolean isRange(int nr, int nc) {
		return nr>=0 && nc >=0 &&  nr < N && nc < N;
	}

}
