
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_17406 {
	static int N,M,K,ans;
	static int[][] A;
	static int[][] rot;
	static int[] nums;
	static boolean[] visited;
  //시계방향으로 자리교체를 위해
	static int[] dr = {1,0,-1,0};
	static int[] dc = {0,1,0,-1};
	static int[][] copied;
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]);
		M = Integer.parseInt(input[1]);
		K = Integer.parseInt(input[2]);
		
		A = new int[N][M];
		copied = new int[N][M];
		for (int i = 0; i < N; i++) {
			input = br.readLine().split(" ");
			for (int j = 0; j < M; j++) {
				A[i][j] = Integer.parseInt(input[j]);
			}
		}
		nums = new int[K];
		visited = new boolean[K];
		rot = new int[K][3];
		for (int i = 0; i < K; i++) {
			input = br.readLine().split(" ");
			rot[i][0] = Integer.parseInt(input[0]);
			rot[i][1] = Integer.parseInt(input[1]);
			rot[i][2] = Integer.parseInt(input[2]); 
		}
		ans = Integer.MAX_VALUE;
		perm(0);
		System.out.println(ans);
	}
	static void rotate(int r, int c, int s) {
    //맨 바깥 겹부터 
		for (int i = 0; i < s; i++) {
			int cr = r-s-1+i, cc = c-s-1+i;
      //해당 겹 맨 첫자리 값 저장
			int temp = copied[cr][cc];
			int d = 0;
			while(d<4){
        //둘레를 돌며 자리 교체
				int nr = cr + dr[d];
				int nc = cc + dc[d];
				if(nr>=r-s-1+i&&nr<=r+s-1-i&&nc>=c-s-1+i&&nc<=c+s-1-i) {
					copied[cr][cc] = copied[nr][nc];
					cr = nr;
					cc = nc;
				}else {
					d++;
				}
			}
      //아까 저장해둔 첫자리 값을 채워주기
			copied[r-s-1+i][c-s+i] = temp;
		}
	}
  
  //순열로 회전 어떤 순서로 할지 결정
  //해당 순서로 돌리고 행의 합 중 최솟값 저장
	static void perm(int cnt) {
		if(cnt == K) {
			for (int i = 0; i < N; i++) {
				System.arraycopy(A[i], 0, copied[i], 0, M);
			}
			for (int i = 0; i < K; i++) {
				int val = nums[i];
				int r = rot[val][0];
				int c = rot[val][1];
				int s = rot[val][2];
				rotate(r,c,s);
			}
			for (int i = 0; i < N; i++) {
				int sum = 0;
				for (int j = 0; j < M; j++) {
					sum += copied[i][j];
				}
				ans = Math.min(ans, sum);
			}
			return;
		}
		for (int i = 0; i < K; i++) {
			if(visited[i])continue;
			visited[i] = true;
			nums[cnt] = i;
			perm(cnt+1);
			visited[i] = false;
		}
	}
}
