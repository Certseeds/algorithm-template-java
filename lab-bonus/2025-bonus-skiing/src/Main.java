// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public final class Main {

    public static final class Input {
        final int n;
        final int m;
        final int[][] h;

        Input(int n, int m, int[][] h) {
            this.n = n;
            this.m = m;
            this.h = h;
        }
    }

    public static Input reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int m = input.nextInt();
        assert ((1 <= n) && (n <= 300));
        assert ((1 <= m) && (m <= 300));

        final var h = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                h[i][j] = input.nextInt();
                assert ((1 <= h[i][j]) && (h[i][j] <= 15));
            }
        }
        return new Input(n, m, h);
    }

    public static String cal(Input input) {
        final int n = input.n;
        final int m = input.m;
        final var h = input.h;
        final int h0 = h[0][0];

        // Dijkstra's algorithm
        // Edge weight from (x,y) to adjacent cell = 2^{h[x][y] - h0}
        // This is because velocity at (x,y) = 2^{h0 - h[x][y]}
        // Time to move = 1/velocity = 2^{h[x][y] - h0}

        final var dist = new double[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Double.MAX_VALUE);
        }
        dist[0][0] = 0;

        // Priority queue: (distance, x, y)
        final var pq = new PriorityQueue<double[]>((a, b) -> Double.compare(a[0], b[0]));
        pq.offer(new double[]{0, 0, 0});

        final int[] dx = {-1, 1, 0, 0};
        final int[] dy = {0, 0, -1, 1};

        while (!pq.isEmpty()) {
            final var curr = pq.poll();
            final double d = curr[0];
            final int x = (int) curr[1];
            final int y = (int) curr[2];

            if (d > dist[x][y]) {
                continue;
            }

            for (int dir = 0; dir < 4; dir++) {
                final int nx = x + dx[dir];
                final int ny = y + dy[dir];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m) {
                    // Time to move from (x,y) to (nx,ny) = 2^{h[x][y] - h0}
                    final double time = Math.pow(2, h[x][y] - h0);
                    final double newDist = d + time;
                    if (newDist < dist[nx][ny]) {
                        dist[nx][ny] = newDist;
                        pq.offer(new double[]{newDist, nx, ny});
                    }
                }
            }
        }

        return String.format("%.2f", dist[n - 1][m - 1]);
    }

    public static void main(String[] args) throws IOException {
        final var input = reader();
        final var result = cal(input);
        output(result);
    }

    public static void output(String result) {
        System.out.print(result);
        System.out.print('\n');
    }


    // refactor from https://github.com/Kattis/kattio/blob/master/Kattio.java
    // url: https://raw.githubusercontent.com/Kattis/kattio/master/Kattio.java
    // license: MIT
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
            st = new StringTokenizer("");
        }

        public boolean hasNext() {
            while (!st.hasMoreTokens()) {
                String nextLine = nextLine();
                if (nextLine == null) {
                    return false;
                }
                st = new StringTokenizer(nextLine);
            }
            return true;
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {return Integer.parseInt(next());}

        long nextLong() {return Long.parseLong(next());}

        double nextDouble() {return Double.parseDouble(next());}

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
