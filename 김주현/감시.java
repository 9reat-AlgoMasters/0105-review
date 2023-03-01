import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Q15683 {
    static final int[][] DIR = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    static final int[] rotate = {0, 4, 2, 4, 4, 1};
    static final int[] possibleDirs = {0, 1, 2, 2, 3, 4};
    static final int[] rotateUnit = {0, 1, 2, 1, 1, 1};
    static final int EMPTY = 0;
    static final int WALL = 6;
    
    static int N, M;
    static int[][] map;
    static List<CCTV> cctvs;
    static int numOfCctvs;
    static int emptySpace;
    static int minBlindingSpots;
    
    public static void setInputFile(String path, String fileName) throws FileNotFoundException {
        String curPath = System.getProperty("user.dir");
        System.setIn(new FileInputStream(curPath + path + fileName));
    }
    
    public static void main(String[] args) throws IOException {
        String path = "\\solve\\tc\\";
        String fileName = "Q15683.txt";
        setInputFile(path, fileName);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        cctvs = new ArrayList<>();
        
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] != EMPTY && map[i][j] != WALL) {
                    cctvs.add(new CCTV(i, j, map[i][j]));
                } else if (map[i][j] == EMPTY) {
                    emptySpace++;
                }
            }
        }
        minBlindingSpots = N*M;
        numOfCctvs = cctvs.size();
    
        dfs(0, emptySpace);
    
        sb.append(minBlindingSpots);
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }
    
    public static void dfs(int depth, int blindingSpots) {
        if (depth == numOfCctvs) {
            minBlindingSpots = Math.min(minBlindingSpots, blindingSpots);
//            System.out.printf("blindingSpots : %d\n", blindingSpots);
//            printMap();
//            System.out.println();
            return;
        }
        
        // depth 번째 cctv의 방향을 정하고 맵에 표시
        int cctvNum = cctvs.get(depth).cctvNum;
        int d = 0;
        for (int dir=0; dir<rotate[cctvNum]; dir++) {
            int markedSpace = turnOnCCTV(depth, d);
            dfs(depth + 1, blindingSpots - markedSpace);
            turnOffCCTV(depth, d);
            d++;
        }
    }
    
    public static void printMap() {
        for (int i=0; i<N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();
        }
    }
    
    
    
    private static int turnOnCCTV(int cctvIdx, int d) {
        int markedSpace = 0;
        int cctvNum = cctvs.get(cctvIdx).cctvNum;
        for (int i=0; i<possibleDirs[cctvNum]; i++) {
            markedSpace += mark(cctvIdx, (d + rotateUnit[cctvNum]*i)%4);
        }
        return markedSpace;
    }
    
    private static int mark(int cctvIdx, int d) {
        CCTV cctv = cctvs.get(cctvIdx);
        int nextX = cctv.x + DIR[d][0];
        int nextY = cctv.y + DIR[d][1];
        int markedSpace = 0;
        
        while (isInRange(nextX, nextY) && map[nextX][nextY] != WALL) {
            if (map[nextX][nextY] == EMPTY) {
                markedSpace++;
                map[nextX][nextY] = cctvIdx + 7;
            }
            nextX += DIR[d][0];
            nextY += DIR[d][1];
        }
        return markedSpace;
    }
    
    private static void turnOffCCTV(int cctvIdx, int d) {
        int cctvNum = cctvs.get(cctvIdx).cctvNum;
        for (int i=0; i<possibleDirs[cctvNum]; i++) {
            unMark(cctvIdx, (d + rotateUnit[cctvNum]*i)%4);
        }
    }
    
    private static void unMark(int cctvIdx, int d) {
        CCTV cctv = cctvs.get(cctvIdx);
        int nextX = cctv.x + DIR[d][0];
        int nextY = cctv.y + DIR[d][1];
    
        while (isInRange(nextX, nextY) && map[nextX][nextY] != WALL) {
            if (map[nextX][nextY] == cctvIdx + 7) {
                map[nextX][nextY] = EMPTY;
            }
            nextX += DIR[d][0];
            nextY += DIR[d][1];
        }
    }
    
    
    
    private static boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
    
    static class CCTV {
        int x, y, cctvNum;
        
        public CCTV(int x, int y, int cctvNum) {
            this.x = x;
            this.y = y;
            this.cctvNum = cctvNum;
        }
    }
}
