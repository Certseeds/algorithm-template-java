// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int K;
        public final int[] a;

        public TestCase(int n, int K, int[] a) {
            this.n = n;
            this.K = K;
            this.a = a;
        }
    }

    // reader: parse input into TestCase objects
    public static List<TestCase> reader() {
        final FastScanner in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 12));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            final int K = in.nextInt();
            assert ((1 <= n) && (n <= 500000));
            final int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = in.nextInt();
            tests.add(new TestCase(n, K, a));
        }
        return tests;
    }

    // cal: compute K-th biggest for each test case
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>(inputs.size());
        for (final TestCase tc : inputs) {
            out.add(String.valueOf(kthBiggest(tc.n, tc.K, tc.a)));
        }
        return out;
    }

    private static int kthBiggest(final int n, final int K, final int[] a) {
        if (K <= 0 || K > n) {
            throw new IllegalArgumentException("Invalid K");
        }
        final int smallThreshold = 5000;
        final int L = n - K + 1; // L-th smallest is K-th largest
        if (K <= smallThreshold) {
            // use min-heap of size K for K-th largest
            final PriorityQueue<Integer> pq = new PriorityQueue<>(K);
            for (int v : a) {
                if (pq.size() < K) {
                    pq.offer(v);
                } else if (v > pq.peek()) {
                    pq.poll();
                    pq.offer(v);
                }
            }
            return pq.peek();
        } else if (L <= smallThreshold) {
            // find L-th smallest -> use max-heap of size L
            final PriorityQueue<Integer> pq = new PriorityQueue<>((x, y) -> Integer.compare(y, x));
            for (int v : a) {
                if (pq.size() < L) {
                    pq.offer(v);
                } else if (v < pq.peek()) {
                    pq.poll();
                    pq.offer(v);
                }
            }
            return pq.peek();
        } else {
            // general case: sort and pick
            Arrays.sort(a);
            return a[n - K];
        }
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

    public static void main(final String[] args) {
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
            try {
                while (st == null || !st.hasMoreElements()) {
                    final String line = br.readLine();
                    if (line == null) return "";
                    st = new StringTokenizer(line);
                }
                return st.nextToken();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }

}
