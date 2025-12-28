// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public final class Main {

    private static final class InputData {
        final int n;
        final int m;
        final int[] values;

        InputData(int n, int m, int[] values) {
            this.n = n;
            this.m = m;
            this.values = values;
        }
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int m = input.nextInt();
        assert ((1 <= n) && (n <= 50000));
        assert ((1 <= m) && (m <= 50000));
        assert ((1 <= (long) n * m) && ((long) n * m <= 50000));

        final int[] values = new int[n * m];
        for (int i = 0; i < n * m; i++) {
            values[i] = input.nextInt();
            assert ((0 <= values[i]) && (values[i] <= 5000));
        }

        return new InputData(n, m, values);
    }

    public static long cal(InputData data) {
        final int n = data.n;
        final int m = data.m;
        final int[] values = data.values;
        final int total = n * m;

        // This is a maximum spanning tree problem on a grid graph
        // Edge weight between adjacent cells (i,j) and (x,y) is C[i][j] * C[x][y]
        // We want to maximize total points = sum of edge weights in spanning tree

        // Use Prim's algorithm for maximum spanning tree
        final boolean[] visited = new boolean[total];
        final long[] maxGain = new long[total];
        Arrays.fill(maxGain, Long.MIN_VALUE);

        // Find the cell with maximum value as starting point
        int startIdx = 0;
        for (int i = 1; i < total; i++) {
            if (values[i] > values[startIdx]) {
                startIdx = i;
            }
        }

        maxGain[startIdx] = 0;
        // Priority queue: [gain, index], max heap
        final PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> -a[0]));
        pq.offer(new long[]{0, startIdx});

        long totalPoints = 0;
        int visitedCount = 0;

        final int[] dx = {-1, 1, 0, 0};
        final int[] dy = {0, 0, -1, 1};

        while (!pq.isEmpty() && visitedCount < total) {
            final long[] curr = pq.poll();
            final long gain = curr[0];
            final int idx = (int) curr[1];

            if (visited[idx]) {
                continue;
            }

            visited[idx] = true;
            visitedCount++;
            totalPoints += gain;

            final int row = idx / m;
            final int col = idx % m;

            for (int d = 0; d < 4; d++) {
                final int newRow = row + dx[d];
                final int newCol = col + dy[d];

                if ((newRow >= 0) && (newRow < n) && (newCol >= 0) && (newCol < m)) {
                    final int newIdx = newRow * m + newCol;
                    if (!visited[newIdx]) {
                        final long newGain = (long) values[idx] * values[newIdx];
                        if (newGain > maxGain[newIdx]) {
                            maxGain[newIdx] = newGain;
                            pq.offer(new long[]{newGain, newIdx});
                        }
                    }
                }
            }
        }

        return totalPoints;
    }

    public static void main(String[] args) throws IOException {
        final InputData data = reader();
        final long result = cal(data);
        output(result);
    }

    public static void output(long number) {
        System.out.print(number);
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
