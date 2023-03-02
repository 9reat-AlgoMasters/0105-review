import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_1247 {
    static StringBuilder sb = new StringBuilder();
    static int N, ans;
    static int[] home, company;
    static int[][] customer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T;
        T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            ans = Integer.MAX_VALUE;
            N = Integer.parseInt(br.readLine());
            String[] input = br.readLine().split(" ");
            company = new int[]{Integer.parseInt(input[0]), Integer.parseInt(input[1])};
            home = new int[]{Integer.parseInt(input[2]), Integer.parseInt(input[3])};
            customer = new int[N][2];
            for (int i = 0; i < N; i++) {
                customer[i][0] = Integer.parseInt(input[4+i*2]);
                customer[i][1] = Integer.parseInt(input[5+i*2]);
            }

            perm(0, 0, company, 0);


            sb.append("#").append(test_case).append(" ").append(ans).append('\n');
        }

        System.out.println(sb);
    }

    static void perm(int cnt, int dist, int[] prev, int flag){
      //거리 합이 이미 ans(최솟값)넘었으면 탈락
        if(dist >= ans) return;
      //N명의 고객 모두 방문
        if(cnt == N){
          //마지막 방문 고객에서 집까지 거리 더해주고 최소면 업데이트후 메서드 종료
            int val = dist + getDistance(prev[0], prev[1], home[0], home[1]);
            if(ans > val){
                ans = val;
            }
            return;
        }

        for (int i = 0; i < N; i++) {
          //방문한 고객은 넘기고
            if((flag & 1 << i) !=0) continue;

          //현재까지 합산한 거리dist에 이전 방문 고객 ~ 지금 선택한 i번 고객까지 거리를 더해서 넘김
          //지금 선택한 i번고객이 다음 재귀의 prev(이전 고객)
          //비트마스킹으로 방문표시
            perm(cnt+1, dist + getDistance(prev[0], prev[1], customer[i][0], customer[i][1]),
                    customer[i], flag | 1<< i );
        }
    }

    static int getDistance(int x1, int y1, int x2, int y2){
        return Math.abs(x1-x2)+Math.abs(y1-y2);
    }
}
