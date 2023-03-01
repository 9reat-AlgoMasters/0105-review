import java.io.*;
import java.util.StringTokenizer;

public class Q10971 {
    private static final int IMPOSSIBLE = 0;
    static int N;
    static int[][] dist;
    static boolean[] visited;
    static int start = 0;
    static int minDist = Integer.MAX_VALUE;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        dist = new int[N][N];
        visited = new boolean[N];
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<N; j++) {
                dist[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        visited[start] = true;
        dfs(1, start, 0);
        sb.append(minDist);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    public static void dfs(int depth, int city, int distance) {
        if (depth == N) {
            if (dist[city][start] != IMPOSSIBLE)
            minDist = Math.min(minDist, distance + dist[city][start]);
            return;
        }
        
        for (int i=0; i<N; i++) {
            if (i == city || dist[city][i] == IMPOSSIBLE || visited[i]) {
                continue;
            }
            visited[i] = true;
            dfs(depth+1, i, distance + dist[city][i]);
            visited[i] = false;
        }
    }
}
