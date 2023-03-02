import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	static int N;
	static int[][] map;
	static int min = Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			String[] input = br.readLine().split(" ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(input[j]);
			}
		}
		
		dfs(0, 0, 0, 0, 0);
		System.out.println(min);
	}
	
	static void dfs(int cnt, int cost, int prev, int start, int flag) {
    //cost가 이미 최소로 기록해놓은 값보다 커지면 탈락
		if(cost >= min) return;
		
    //모든 도시 방문
		if(cnt == N) {
      //시작점으로 돌아올 수 없으면 탈락
      if(map[prev][start] == 0) return;
      //돌아올 수 있다면 최소 비용 업데이트
			min = Math.min(min, cost + map[prev][start]);
			return;
		}
		
		for (int i = 0; i < N; i++) {
			if((flag & 1<<i) != 0) continue;
			
			if(cnt == 0) {
         //맨 처음 시작 도시 방문이면 prev, start에 i값(도시 번호) 넘기기, 도시를 건너간게 아니므로 비용은 0, 비트마스킹으로 i번째 방문했음 표시
				dfs(cnt+1, 0, i, i, flag | 1<<i);
			}else {
        //prev(직전 방문도시) 에서 i번 도시로 못가면 탈락
				if(map[prev][i] != 0) {
					dfs(cnt+1, cost + map[prev][i], i, start, flag | 1<<i);
				}
			}
		}
	}
	
}
