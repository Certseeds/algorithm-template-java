// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int[] a;
        public final int[][] queries; // each query: {x, y}

        public TestCase(int n, int[] a, int[][] queries) {
            this.n = n;
            this.a = a;
            this.queries = queries;
        }
    }

    // reader: parse input into TestCase objects
    public static List<TestCase> reader() throws IOException {
        final FastScanner in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 5));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            assert ((1 <= n) && (n <= 40000));
            final int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
                assert ((1 <= a[i]) && (a[i] <= 1000000000));
            }
            // next n lines: queries
            final int[][] queries = new int[n][2];
            for (int i = 0; i < n; i++) {
                final int x = in.nextInt();
                final int y = in.nextInt();
                queries[i][0] = x;
                queries[i][1] = y;
            }
            tests.add(new TestCase(n, a, queries));
        }
        return tests;
    }

    // cal: compute results per README: M = floor(sqrt(n)), for each query return M-th biggest among a_x, a_{x+y}, ...
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>();

        for (final TestCase tc : inputs) {
            final int n = tc.n;
            final int[] a = tc.a;
            final int[][] queries = tc.queries;
            final int M = (int) Math.sqrt(n);

            // collect distinct small y values (<= M) used by queries
            final Set<Integer> smallYSet = new HashSet<>();
            for (int[] query : queries) {
                final int y = query[1];
                if (y <= M) {
                    smallYSet.add(y);
                }
            }

            // Precompute for each small y: for each remainder r (0..y-1), compute kth-M for each suffix
            final Map<Integer, int[][]> smallMap = new HashMap<>(); // y -> array of kth arrays per r
            for (final int y : smallYSet) {
                final int[][] kthByR = new int[y][]; // for remainder r, array length = ceil((n-r)/Y)
                for (int r = 0; r < y; r++) {
                    // build sequence for this remainder
                    final int len = (n - 1 - r) >= 0 ? ((n - 1 - r) / y + 1) : 0;
                    if (len == 0) {
                        kthByR[r] = new int[0];
                        continue;
                    }
                    final int[] seq = new int[len];
                    for (int pos = r, idx = 0; pos < n; pos += y) {
                        seq[idx++] = a[pos];
                    }
                    final int[] kth = new int[len];
                    // compute M-th largest of each suffix via min-heap of size M
                    if (M <= 0) {
                        // degenerate: M==0 shouldn't happen since n>=1
                        Arrays.fill(kth, Integer.MIN_VALUE);
                    } else {
                        final PriorityQueue<Integer> minHeap = new PriorityQueue<>(M);
                        for (int i = len - 1; i >= 0; i--) {
                            minHeap.offer(seq[i]);
                            if (minHeap.size() > M) minHeap.poll();
                            if (minHeap.size() == M) kth[i] = minHeap.peek();
                            else kth[i] = Integer.MIN_VALUE;
                        }
                    }
                    kthByR[r] = kth;
                }
                smallMap.put(y, kthByR);
            }

            // answer queries
            for (int[] query : queries) {
                final int x1 = query[0];
                final int y = query[1];
                final int x = x1 - 1; // convert to 0-based
                if (y <= 0) {
                    out.add("-1");
                    continue;
                }
                if (y <= M) {
                    final int[][] kthByR = smallMap.get(y);
                    final int r = x % y;
                    final int t = x / y;
                    if (r < kthByR.length) {
                        final int[] kth = kthByR[r];
                        if (t < kth.length && kth[t] != Integer.MIN_VALUE) {
                            out.add(String.valueOf(kth[t]));
                        } else {
                            out.add("-1");
                        }
                    } else {
                        out.add("-1");
                    }
                } else {
                    // large y: sequence length <= n / y < n / (M) ~ M, so do on-the-fly
                    final PriorityQueue<Integer> minHeap = new PriorityQueue<>(M);
                    int cnt = 0;
                    for (int pos = x; pos < n; pos += y) {
                        final int val = a[pos];
                        minHeap.offer(val);
                        if (minHeap.size() > M) {
                            minHeap.poll();
                        }
                        cnt++;
                    }
                    if (cnt < M) {
                        out.add("-1");
                    } else {
                        out.add(String.valueOf(minHeap.peek()));
                    }
                }
            }
        }

        return out;
    }

    // output: print each line with newline
    public static void output(final List<String> lines) {
        final StringBuilder sb = new StringBuilder();
        for (final String line : lines) {
            sb.append(line);
            sb.append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) throws IOException {
        output(cal(reader()));
    }

    // fast scanner
    public static final class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    final String line = br.readLine();
                    if (line == null) return "";
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }

}
