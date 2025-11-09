// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        final String s;

        public TestCase(String s) {
            this.s = s;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int testCases = in.nextInt();
        assert (testCases >= 1) && (testCases <= 100) : "T must be between 1 and 100";
        final List<TestCase> cases = new ArrayList<>(testCases);
        for (int t = 0; t < testCases; t++) {
            final int n = in.nextInt();
            final String s = in.next();
            assert s.length() == n : "String length should be n";
            assert n >= 1 && n <= 100000 : "n must be between 1 and 10^5";
            cases.add(new TestCase(s));
        }
        return cases;
    }

    public static List<Integer> cal(List<TestCase> inputs) {
        final List<Integer> results = new ArrayList<>();
        for (final var tc : inputs) {
            results.add(solve(tc.s));
        }
        return results;
    }

    private static int solve(String s) {
        final int n = s.length();
        if (n < 3) {
            return 0;
        }

        final int[] lps = computeLPSArray(s);
        final int[] z = computeZArray(s);
        final RMQ rmq = new RMQ(z);

        int len = lps[n - 1]; // 从最长边界开始
        while (len > 0) {
            // 中间那次出现的起点 j 范围: [len, n - 2*len]
            final int L = len;
            final int R = n - 2 * len;
            if (L <= R) {
                int maxZ = rmq.queryMax(L, R);
                if (maxZ >= len) {
                    return len; // 找到满足条件的最长答案
                }
            }
            len = lps[len - 1]; // 退到次长边界
        }
        return 0;
    }

    // Z-Algorithm: z[i] = LCP(s[i..], s[0..])
    private static int[] computeZArray(String s) {
        final int n = s.length();
        final int[] z = new int[n];
        z[0] = n;
        int l = 0, r = 0;
        for (int i = 1; i < n; i++) {
            if (i <= r) {
                z[i] = Math.min(r - i + 1, z[i - l]);
            }
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }
        return z;
    }

    // 稀疏表 RMQ (Range Max Query) for Z-array
    private static final class RMQ {
        private final int[][] st;
        private final int[] lg;

        RMQ(int[] a) {
            int n = a.length;
            lg = new int[n + 1];
            for (int i = 2; i <= n; i++) lg[i] = lg[i >> 1] + 1;
            int K = lg[n] + 1;
            st = new int[K][n];
            System.arraycopy(a, 0, st[0], 0, n);
            for (int k = 1; k < K; k++) {
                int len = 1 << k;
                int half = len >> 1;
                for (int i = 0; i + len <= n; i++) {
                    st[k][i] = Math.max(st[k - 1][i], st[k - 1][i + half]);
                }
            }
        }

        int queryMax(int l, int r) {
            if (l > r) return 0;
            int k = lg[r - l + 1];
            return Math.max(st[k][l], st[k][r - (1 << k) + 1]);
        }
    }

    public static int[] computeLPSArray(String pattern) {
        final int m = pattern.length();
        final int[] lps = new int[m];
        if (m == 0) {
            return lps;
        }
        for (int length = 0, i = 1; i < m; ) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    public static void output(List<Integer> decides) {
        for (final var decide : decides) {
            System.out.print(decide);
            System.out.print('\n');
        }
    }

    public static void main(String[] args) {
        output(cal(reader()));
    }

    public static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        public Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
