import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Swea_1949 {
    static StringBuilder sb = new StringBuilder();
    static int N, K, ans;
    static int[][] map;
    static boolean[][] visited;
    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T;
        T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            ans = 0;
            String[] input = br.readLine().split(" ");
            N = Integer.parseInt(input[0]);
            K = Integer.parseInt(input[1]);
            map = new int[N][N];
            visited = new boolean[N][N];

            //map 데이터 입력
            //등산로의 시작점 = 가장 높은 봉우리 높이 max 구하기
            int max = -1;
            for (int i = 0; i < N; i++) {
                input = br.readLine().split(" ");
                for (int j = 0; j < N; j++) {
                    int val = Integer.parseInt(input[j]);
                    if(max < val) {
                        max = val;
                    }
                    map[i][j] = val;
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if(map[i][j] == max) {
                        //높이가 max인 곳부터 탐색 시작
                        dfs(1,i,j,false);
                    }
                }
            }

            sb.append("#").append(test_case).append(" ").append(ans).append('\n');
        }

        System.out.println(sb);
    }
    static void dfs(int cnt, int r, int c, boolean isCut) {
        //cnt = 방문한 땅 수 = 등산로 길이
        ans = Math.max(ans,  cnt);
        //방문 표시
        visited[r][c] = true;
        int cur = map[r][c];
        for (int i = 0; i < 4; i++) {
            //4방 탐색
            int nr = r + dr[i];
            int nc = c + dc[i];
            if(nr>=0 && nr<N && nc>=0 && nc<N) {
                //방문 안한 땅만 탐색
                if(visited[nr][nc]) continue;
                if(map[nr][nc] < cur) {
                    //안 깎아도 되는 곳이면 현재의 cut상태 유지한 채 인자로 넘김
                    dfs(cnt+1,nr,nc,isCut);
                }else {
                    //아직 깎기 찬스를 안씀 + K 이내의 높이 차이 = 깎으면 지나갈 수 있음
                    if(!isCut && map[nr][nc] - cur < K) {
                        int temp = map[nr][nc];
                        //해당 땅 높이 깎기. 현재 높이보다 1만 낮으면 됨
                        map[nr][nc] = cur - 1;
                        dfs(cnt+1,nr,nc,true);
                        //더 탐색해 들어간 후 원래 높이로 복구
                        map[nr][nc] = temp;
                    }
                }
            }
        }
        //dfs 끝날때 방문 표시제거
        visited[r][c] = false;
    }

}
