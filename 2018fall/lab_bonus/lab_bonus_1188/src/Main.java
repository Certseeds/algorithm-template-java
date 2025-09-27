// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int[] a;

        public TestCase(int n, int[] a) {
            this.n = n;
            this.a = a;
        }
    }

    // reader
    public static List<TestCase> reader() {
        final FastScanner in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            assert ((1 <= n) && (n <= 100000));
            final int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = in.nextInt();
            tests.add(new TestCase(n, a));
        }
        return tests;
    }

    // cal
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>(inputs.size());
        for (final TestCase tc : inputs) {
            out.add(String.valueOf(solveMedianDifference(tc.a)));
        }
        return out;
    }

    // output
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

    // solve kth smallest pairwise absolute difference median
    private static int solveMedianDifference(final int[] a) {
        final int n = a.length;
        Arrays.sort(a);
        long pairs = (long) n * (n - 1) / 2;
        long k = (pairs % 2 == 0) ? (pairs / 2) : ((pairs + 1) / 2);
        int lo = 0;
        int hi = a[n - 1] - a[0];
        int ans = hi;
        while (lo <= hi) {
            int mid = lo + ((hi - lo) >>> 1);
            long cnt = countPairsLE(a, mid);
            if (cnt >= k) {
                ans = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return ans;
    }

    private static long countPairsLE(final int[] a, final int D) {
        final int n = a.length;
        long cnt = 0L;
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (j < i + 1) j = i + 1;
            while (j < n && (long) a[j] - a[i] <= D) j++;
            cnt += (j - i - 1);
        }
        return cnt;
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
