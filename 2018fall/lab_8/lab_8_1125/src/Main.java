// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int m;
        public final int[][] a;

        public TestCase(final int n, final int m, final int[][] a) {
            this.n = n;
            this.m = m;
            this.a = a;
        }
    }

    public static List<TestCase> reader() throws IOException {
        final var in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final var tests = new ArrayList<TestCase>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            final int m = in.nextInt();
            assert ((1 <= n) && (n <= 1000));
            assert ((1 <= m) && (m <= 1000));
            final int[][] a = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    a[i][j] = in.nextInt();
                }
            }
            tests.add(new TestCase(n, m, a));
        }
        return tests;
    }

    public static List<Integer> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<Integer>(inputs.size());
        for (final var tc : inputs) {
            final int n = tc.n;
            final int m = tc.m;
            final int[][] a = tc.a;
            final var visited = new boolean[n * m];
            int groups = 0;
            final var queue = new ArrayDeque<Integer>();
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < m; c++) {
                    final int start = r * m + c;
                    if (visited[start]) continue;
                    groups++;
                    // BFS flood fill
                    queue.add(start);
                    visited[start] = true;
                    final int color = a[r][c];
                    while (!queue.isEmpty()) {
                        final int cur = queue.removeFirst();
                        final int x = cur / m;
                        final int y = cur % m;
                        if (x - 1 >= 0) {// up
                            pushIfSameColor(visited, queue, a, x - 1, y, m, color);
                        }
                        if (x + 1 < n) {// down
                            pushIfSameColor(visited, queue, a, x + 1, y, m, color);
                        }
                        // left (wrap)
                        pushIfSameColor(visited, queue, a, x, (y - 1 + m) % m, m, color);
                        // right (wrap)
                        pushIfSameColor(visited, queue, a, x, (y + 1) % m, m, color);
                    }
                }
            }
            out.add(groups);
        }
        return out;
    }

    private static void pushIfSameColor(final boolean[] visited, final Deque<Integer> queue,
                                        final int[][] a, final int x, final int y, final int m, final int color) {
        final int idx = x * m + y;
        if (!visited[idx] && a[x][y] == color) {
            visited[idx] = true;
            queue.addLast(idx);
        }
    }

    public static void output(final List<Integer> lines) {
        final StringBuilder sb = new StringBuilder();
        for (final var line : lines) {
            sb.append(line).append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) throws IOException {
        output(cal(reader()));
    }

    public static final class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                final String line = br.readLine();
                if (line == null) return "";
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
