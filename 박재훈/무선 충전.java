import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class Solution {
    //배터리 차저
    static class BC{
        int x, y, c, p;
 
        public BC(int x, int y, int c, int p) {
            this.x = x;
            this.y = y;
            this.c = c;
            this.p = p;
        }
    }
    static StringBuilder sb = new StringBuilder();
    static int M, A, sumA, sumB, ans;
    static int[] moveA, moveB;
    static int[] dy = {0,-1,0,1,0};
    static int[] dx = {0,0,1,0,-1};
    static BC[] bc;
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T;
        T = Integer.parseInt(br.readLine());
 
        for(int test_case = 1; test_case <= T; test_case++)
        {
            ans = 0;
            sumA = 0;
            sumB = 0;
            String[] input = br.readLine().split(" ");
            M = Integer.parseInt(input[0]);
            A = Integer.parseInt(input[1]);
          //A, B의 이동 궤적 저장
            moveA = new int[M];
            input = br.readLine().split(" ");
            for (int i = 0; i < M; i++) {
                moveA[i] = Integer.parseInt(input[i]);
            }
            moveB = new int[M];
            input = br.readLine().split(" ");
            for (int i = 0; i < M; i++) {
                moveB[i] = Integer.parseInt(input[i]);
            }
          //BC 정보 저장
            bc = new BC[A];
            for (int i = 0; i < A; i++) {
                input = br.readLine().split(" ");
                int x = Integer.parseInt(input[0]);
                int y = Integer.parseInt(input[1]);
                int c = Integer.parseInt(input[2]);
                int p = Integer.parseInt(input[3]);
                bc[i] = new BC(x,y,c,p);
            }
 
            int ax = 1;
            int ay = 1;
            int bx = 10;
            int by = 10;
          //0초일때 계산
            solve(ax,ay,bx,by);
          //이동 궤적따라 계산 
            for (int i = 0; i < M; i++) {
                int da = moveA[i];
                ax += dx[da];
                ay += dy[da];
                int db = moveB[i];
                bx += dx[db];
                by += dy[db];
                solve(ax,ay,bx,by);
            }
 
            ans = sumA + sumB;
            sb.append("#").append(test_case).append(" ").append(ans).append('\n');
        }
 
        System.out.println(sb);
    }
 
    static void solve(int ax, int ay, int bx, int by){
        int maxSum = 0, maxA = 0, maxB = 0;
      //이중 for문으로 가능한 조합 다 해보기
        for (int i = 0; i < A; i++) {
            BC bcA = bc[i];
            int chargeA = calc(ax,ay,bcA);
 
            for (int j = 0; j < A; j++) {
                BC bcB = bc[j];
                int chargeATemp = chargeA;
                int chargeB = calc(bx, by, bcB);
 
               //같은 BC로부터 충전 가능하면 절반씩 나눠 가져감
                if (i == j && chargeATemp != 0 && chargeB != 0) {
                    chargeATemp /= 2;
                    chargeB /= 2;
                }
                int sum = chargeATemp + chargeB;
              //충전량 합이 최대일 때 A,B가 받는 양을 기록해놓음
                if (maxSum < sum) {
                    maxSum = sum;
                    maxA = chargeATemp;
                    maxB = chargeB;
                }
            }
        }
        //기록해둔 충전양 더하기
        sumA += maxA;
        sumB += maxB;
    }
    static int calc(int x, int y, BC bc) {
      //x, y좌표상 범위 내에 있으면 충전, 아니면 충전량 0
        if(Math.abs(x-bc.x) + Math.abs(y-bc.y) <= bc.c) {
            return bc.p;
        }
        return 0;
    }
 
}
