// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static final class TestCase {
        public final long m;
        public final long[] A;

        public TestCase(long m, long[] A) {
            this.m = m;
            this.A = A;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int testcases = in.nextInt();
        assert (testcases <= 5) : "T must be <= 5";
        final List<TestCase> cases = new ArrayList<>(testcases);
        for (int t = 0; t < testcases; t++) {
            final int n = in.nextInt();
            assert ((n >= 1) && (n <= 100000)) : "n must be between 1 and 100000";
            final long m = in.nextLong();
            assert ((m >= 1) && (m <= 1000000000)) : "m must be between 1 and 10^9";
            final long[] A = new long[n];
            for (int i = 0; i < n; i++) {
                A[i] = in.nextInt();
                assert (Math.abs(A[i]) <= 1000000000) : "abs(A[i]) must be <= 10^9";
                if (i > 0) {
                    assert (A[i] >= A[i - 1]) : "A must be an increasing sequence";
                }
            }
            cases.add(new TestCase(m, A));
        }
        return cases;
    }

    public static List<BigInteger> cal(List<TestCase> inputs) {
        final List<BigInteger> results = new ArrayList<>();
        for (var testCase : inputs) {
            final long m = testCase.m;
            final long[] A = testCase.A;
            final int n = A.length;
            var count = BigInteger.ZERO;
            int right = 0;
            // O(n) 双指针/滑动窗口算法
            for (int i = 0; i < n; i++) {
                // 对于每个 i, 找到满足条件的最远 right
                while (right < n && A[right] - A[i] <= m) {
                    right++;
                }
                // 从 i+1 到 right-1 中选择两个数作为 j 和 k
                long len = right - i;
                if (len >= 3) {
                    // 计算组合数 C(len-1, 2)
                    BigInteger temp = BigInteger.valueOf(len - 1).multiply(BigInteger.valueOf(len - 2)).divide(BigInteger.TWO);
                    count = count.add(temp);
                }
            }
            results.add(count);
        }
        return results;
    }

    public static void output(List<BigInteger> decides) {
        for (var decide : decides) {
            System.out.print(decide);
            System.out.print('\n');
        }
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    // refactor from https://github.com/Kattis/kattio/blob/master/Kattio.java
    // url: https://raw.githubusercontent.com/Kattis/kattio/master/Kattio.java
    // license: MIT
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
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

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

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
