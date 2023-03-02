import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	static class CCTV{
		int r, c, type;

		public CCTV(int r, int c, int type) {
			super();
			this.r = r;
			this.c = c;
			this.type = type;
    }
	}
  
	static int N, M, cnt, ans = 65;
	static int[][] map;
	static int[] dr = {0,1,0,-1};
	static int[] dc = {1,0,-1,0};
  //1~5까지 CCTV 종류별로 확인해야할 방향 배열로 설정
	static int[][][] dType = {{{0},{1},{2},{3}}, 
                            {{0,2},{1,3}},
                            {{0,1},{1,2},{2,3},{3,0}},
                            {{0,1,2},{1,2,3},{2,3,0},{3,0,1}},
                            {{0,1,2,3}}};
	
	static ArrayList<CCTV> list = new ArrayList<>();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			input = br.readLine().split(" ");
			for (int j = 0; j < M; j++) {
				int val = Integer.parseInt(input[j]);
				map[i][j] = val;
				if(val > 0 && val < 6) {
          //CCTV를 ArrayList에 저장
					list.add(new CCTV(i, j, val));
				}
        //사각지대 수 카운트
				if(val == 0) {
					cnt++;
				}
			}
		}
		
		dfs(0);
		
		System.out.println(ans);
	}
	
	static void dfs(int depth) {
    //depth = CCTV 인덱스, list에 저장된 CCTV 다 적용했으면 최소인지 판별
		if(depth == list.size()) {
			if(ans > cnt) {
				ans = cnt;
			}
			return;
		}
		
		CCTV c = list.get(depth);
    //depth번째 CCTV의 종류에 따라 방향 결정
		int[][] dirs = dType[c.type-1];
    //dirs.length는 봐야할 총 경우의 수? ex)1번 타입이면 상하좌우 4, 2번이면 위아래 일직선 2
		for (int i = 0; i < dirs.length; i++) {
      //dirs[i].length는 감시할때 한번에 몇면을 보는가 ex)1번 타입이면 1면, 2번은 2면, 5번은 4면...
			for (int j = 0; j < dirs[i].length; j++) {
				int d = dirs[i][j];
				int nr = c.r + dr[d];
				int nc = c.c + dc[d];
        
        //nr, nc가 범위 내에 있는 동안
				while(check(nr,nc)) {
          //카메라가 볼 수 있는 구역을 구별할 수 있게 map 배열 값 바꿔줌
					if(map[nr][nc] == 0) {
						map[nr][nc] = depth+7;
						cnt--;
            //벽 만나면 탈출
					}else if(map[nr][nc] == 6) {
						break;
					}
					nr += dr[d];
					nc += dc[d];
				}
			}
			
      //바뀐 상태 그대로 다음 카메라 감시 경우의 수 완전탐색
			dfs(depth+1);
      
      //탐색이 끝났다면 바꿔준거 원상태로 복귀하고 다음 감시 경우의 수 탐색
			for (int j = 0; j < dirs[i].length; j++) {
				int d = dirs[i][j];
				int nr = c.r + dr[d];
				int nc = c.c + dc[d];
				while(check(nr,nc)) {
					if(map[nr][nc] == depth+7) {
						map[nr][nc] = 0;
						cnt++;
					}else if(map[nr][nc] == 6) {
						break;
					}
					nr += dr[d];
					nc += dc[d];
				}
			}
		}
	}
	
	
	static boolean check(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < M;
	}
}
