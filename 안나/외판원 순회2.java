import java.io.*;
import java.util.*;;
//MST 이용해서 풀어보고 싶어요...
public class Main_10971 {
	static int N;
	static int[][] matrix;
	static int cost;
	static boolean[] visited;
	static int[] num;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		matrix = new int[N][N];
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		cost = Integer.MAX_VALUE;
		visited = new boolean[N];
		num = new int[N];
		visited[0]=true;
		dfs(0,0,0);
		System.out.println(cost);
		
	}
	static void dfs(int cnt, int now, int tot) {
		if(tot >= cost) {
			return;
		}
		if(cnt == N-1) {
//			System.out.println(Arrays.toString(num));
			if(matrix[now][0]!=0) {
				tot += matrix[now][0];
				cost = Math.min(cost, tot);
			}
			return;
		}
		for (int i = 0; i < N; i++) {
			if(visited[i]) continue;
			if(matrix[now][i]!=0) {
				visited[i] = true;
				num[cnt]= i;
				dfs(cnt+1, i, tot+matrix[now][i]);
				visited[i] = false;
			}
		}
	}

}
