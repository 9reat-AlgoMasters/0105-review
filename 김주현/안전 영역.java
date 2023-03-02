import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Q2468 {
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static int N;
    static int[][] map;
    static boolean[][] visited;
    static int min = Integer.MAX_VALUE;
    static int max = Integer.MIN_VALUE;
    static int maxSafe = -1;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                min = Math.min(map[i][j], min);
                max = Math.max(map[i][j], max);
            }
        }
    
        for (int i = min; i <= max; i++) {
            int safeArea = countSafeArea(i);
            maxSafe = Math.max(maxSafe, safeArea);
        }
    
        sb.append(maxSafe);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    // h가 물에 잠기지 않는 첫 높이 일때 안전영역의 수 반환
    private static int countSafeArea(int h) {
        visited = new boolean[N][N];
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j] && map[i][j] >= h) {
                    count++;
                    bfs(i, j, h);
                }
            }
        }
        return count;
    }
    
    private static void bfs(int x, int y, int h) {
        Deque<Point> q = new ArrayDeque<>();
        q.add(new Point(x, y));
        visited[x][y] = true;
    
        while (!q.isEmpty()) {
            Point now = q.poll();
    
            for (int[] d : DIR) {
                int nextX = now.x + d[0];
                int nextY = now.y + d[1];
                if (isInRange(nextX, nextY) && !visited[nextX][nextY] && map[nextX][nextY] >= h) {
                    visited[nextX][nextY] = true;
                    q.add(new Point(nextX, nextY));
                }
            }
        }
    }
    
    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N;
    }
    
    static class Point {
        int x, y;
    
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
}
