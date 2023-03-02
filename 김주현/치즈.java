import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Q2636 {
    static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    static final int EMPTY = 0;
    static final int CHEESE = 1;
    
    static int N, M;
    static int[][] map;
    static int remainingCheese = 0;
    static boolean[][] visited;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == CHEESE) {
                    remainingCheese++;
                }
            }
        }
    
        int time = 0;
        int lastCheese = 0;
        while (remainingCheese > 0) {
            time++;
            int meltedCheese = melt();
            if (remainingCheese == meltedCheese) {
                lastCheese = meltedCheese;
            }
            remainingCheese -= meltedCheese;
        }
    
        sb.append(time).append("\n").append(lastCheese);
    
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    private static int melt() {
        int count = 0;
        Deque<Point> q = new ArrayDeque<>();
        visited = new boolean[N][M];
        q.add(new Point(0, 0));
        visited[0][0] = true;
    
        while (!q.isEmpty()) {
            Point now = q.poll();
    
            for (int[] d : DIR) {
                int nextX = now.x + d[0];
                int nextY = now.y + d[1];
                if (isInRange(nextX, nextY) && !visited[nextX][nextY] && map[nextX][nextY] == EMPTY) {
                    count += meltAdjCheese(nextX, nextY);
                    visited[nextX][nextY] = true;
                    q.add(new Point(nextX, nextY));
                }
            }
        }
        return count;
    }
    
    private static int meltAdjCheese(int x, int y) {
        int count = 0;
        for (int[] d : DIR) {
            int nextX = x + d[0];
            int nextY = y + d[1];
            if (isInRange(nextX, nextY) && map[nextX][nextY] == CHEESE) {
                count++;
                visited[nextX][nextY] = true;
                map[nextX][nextY] = EMPTY;
            }
        }
        return count;
    }
    
    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
    
    static class Point {
        int x, y;
    
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
