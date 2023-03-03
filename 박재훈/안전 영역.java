import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_2468 {
    static int N, minHeight, maxHeight, rain;
    static int[][] map;
    static boolean[][] visited;
    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        minHeight = 101;
        maxHeight = 0;
        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                int t = Integer.parseInt(input[j]);
                map[i][j] = t;

                //최대 높이, 최소 높이 구하기
                if (minHeight > t) {
                    minHeight = t;
                }
                if (maxHeight < t) {
                    maxHeight = t;
                }
            }
        }

        //초기값 설정 : 가라 앉는 영역이 없는 경우 = 안정영역 1개
        int ans = 1;
        //처음 가라앉기 시작하는 최소 높이부터 다 가라앉아서 안전영역 0개되는 최대높이 전까지 비 양 증가시킴
        for (rain = minHeight; rain < maxHeight; rain++) {
            visited = new boolean[N][N];

            int cnt = 0;
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    //가라앉지 않은 지역 찾기
                    if(!visited[j][k] && map[j][k] > rain) {
                        cnt++;
                        //해당 지역 주변에 가라앉지 않은 곳 탐색
                        dfs(j, k);
                    }
                }
            }
            if(ans < cnt){
                ans = cnt;
            }
        }
        System.out.println(ans);
    }
    static void dfs(int r, int c){
        visited[r][c] = true;
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if(nr < 0 || nr >= N || nc < 0 || nc >= N
                || visited[nr][nc] || map[nr][nc] <= rain) continue;

            dfs(nr, nc);
        }
    }
}
