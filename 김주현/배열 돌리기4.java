import java.io.*;
import java.util.StringTokenizer;

public class Q17406 {
    static int N, M, K;
    static int[][] map;
    static Rotate[] rotates;
    static int[] rotateOrder;
    static boolean[] visited;
    static int minValue = Integer.MAX_VALUE;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
    
        map = new int[N][M];
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    
        rotates = new Rotate[K];
        for (int i=0; i<K; i++) {
            st = new StringTokenizer(br.readLine());
            rotates[i] = new Rotate(Integer.parseInt(st.nextToken())-1,
                    Integer.parseInt(st.nextToken())-1,
                    Integer.parseInt(st.nextToken()));
        }
    
        rotateOrder = new int[K];
        visited = new boolean[K];
    
        perm(0);
        sb.append(minValue);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    public static void perm(int depth) {
        if (depth == K) {
            int value = calculate();
            minValue = Math.min(minValue, value);
            return;
        }
        
        for (int i=0; i<K; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            rotateOrder[depth] = i;
            perm(depth + 1);
            visited[i] = false;
        }
    }
    
    private static int calculate() {
        int[][] copiedMap = copyMap();
        for (int i=0; i<K; i++) {
            rotate(copiedMap, rotates[rotateOrder[i]]);
        }
    
        int min = Integer.MAX_VALUE;
        for (int i=0; i<N; i++) {
            min = Math.min(min, calculateRowValue(copiedMap, i));
        }
        return min;
    }
    
    private static int calculateRowValue(int[][] copiedMap, int r) {
        int total = 0;
        for (int i=0; i<M; i++) {
            total += copiedMap[r][i];
        }
        return total;
    }
    
    private static int[][] copyMap() {
        int[][] copiedMap = new int[N][M];
        for (int i = 0; i < N; i++) {
            System.arraycopy(map[i], 0, copiedMap[i], 0, M);
        }
        return copiedMap;
    }
    
    public static void rotate(int[][] map, Rotate rotate) {
        for (int i = 1; i <= rotate.s; i++) {
            rotateBorder(map, rotate.r, rotate.c, i);
        }
    }
    
    private static void rotateBorder(int[][] map, int r, int c, int s) {
        int len = s*2;
        
        int temp1 = map[r-s][c-s];
        int temp2;
        
        // 위
        for (int i = 0; i < len; i++) {
            temp2 = map[r - s][c - s + i + 1];
            map[r - s][c - s + i + 1] = temp1;
            temp1 = temp2;
        }
    
        // 오른쪽
        for (int i = 0; i < len; i++) {
            temp2 = map[r - s + i + 1][c + s];
            map[r - s + i + 1][c + s] = temp1;
            temp1 = temp2;
        }
    
        // 아래
        for (int i = 0; i < len; i++) {
            temp2 = map[r + s][c + s - i -1];
            map[r + s][c + s - i -1] = temp1;
            temp1 = temp2;
        }
    
        // 왼쪽
        for (int i = 0; i < len; i++) {
            temp2 = map[r + s - i - 1][c - s];
            map[r + s - i - 1][c - s] = temp1;
            temp1 = temp2;
        }
    }
    
    public static void printMap(int[][] map) {
        for (int i=0; i<N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
    }
    
    static class Rotate {
        int r, c, s;
    
        public Rotate(int r, int c, int s) {
            this.r = r;
            this.c = c;
            this.s = s;
        }
    }
}
