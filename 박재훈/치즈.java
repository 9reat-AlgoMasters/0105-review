import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_2636 {
    static int N, M, cnt;
    static int[][] map;
    static boolean[][] isAir;
    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};
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
                if(val == 1){
                    //치즈 개수 세기
                    cnt++;
                }
            }
        }
        //며칠째인지 카운트할 변수, 해당 날짜의 녹기전 치즈 개수 담을 변수
        int day = 0, ans = 0;
        //치즈 다 녹을 때까지
        while(cnt > 0){
            //공기인지 확인할 배열
            isAir = new boolean[N][M];
            day++;
            ans = cnt;

            //공기와 치즈 속 구멍을 구별하기 위한 메서드. 0,0은 반드시 공기이므로 여기서 시작
            markAir(0,0);

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if(map[i][j] == 1){
                        melt(i, j);
                    }
                }
            }
        }
        System.out.println(day);
        System.out.println(ans);
    }

    static void markAir(int r, int c){
        //공기는 2로 표시
        map[r][c] = 2;
        //공기임을 표시
        isAir[r][c] = true;

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            //치즈 아니면서 아직 공기 판정 안한 곳 탐색
            if(nr >= 0 && nr < N && nc >= 0 && nc < M
                    && map[nr][nc] != 1 && !isAir[nr][nc]){
                markAir(nr, nc);
            }
        }
    }

    static void melt(int r, int c){
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            //4방 탐새결과 공기있으면 녹음
            if(nr >= 0 && nr < N && nc >=0 && nc < M
                    && map[nr][nc] == 2){
                //바로 공기(2)로 표기하면 안됨. 일단 0으로 해놓고 공기 판정은 markAir 메서드에서만
                map[r][c] = 0;
                cnt--;
                return;
            }
        }
    }
}
