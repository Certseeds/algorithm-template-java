// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    private static final class TestCase {
        final int n;
        final int m;
        final int[][] matrix;

        TestCase(int n, int m, int[][] matrix) {
            this.n = n;
            this.m = m;
            this.matrix = matrix;
        }
    }

    public static List<TestCase> reader() throws IOException {
        final var input = new Reader();
        final int T = input.nextInt();
        assert ((1 <= T) && (T <= 20));

        final var testCases = new ArrayList<TestCase>(T);
        for (int t = 0; t < T; t++) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= n) && (n <= 6));
            assert ((1 <= m) && (m <= 6));

            final var matrix = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    matrix[i][j] = input.nextInt();
                    assert (matrix[i][j] >= 0);
                }
            }
            testCases.add(new TestCase(n, m, matrix));
        }
        return testCases;
    }

    public static List<Long> cal(List<TestCase> testCases) {
        final var results = new ArrayList<Long>(testCases.size());
        for (final var tc : testCases) {
            // State compression DP for 8-connected non-adjacency
            // State: bitmask for two consecutive rows (previous row and current row)
            // For 8-connectivity, we need to track which cells are selected in prev and curr rows
            // and ensure no two selected cells are 8-adjacent

            final int n = tc.n;
            final int m = tc.m;

            // For each row, generate all valid single-row states (no horizontally adjacent)
            final var validStates = new ArrayList<Integer>();
            for (int mask = 0; mask < (1 << m); mask++) {
                if ((mask & (mask << 1)) == 0) {
                    validStates.add(mask);
                }
            }

            // dp[prevMask][currMask] = max sum achievable up to current row
            // But we need to handle 8-connectivity, so we check:
            // - prevMask and currMask have no vertically adjacent (same column)
            // - prevMask and currMask have no diagonally adjacent

            // Actually, simpler: check if two masks are "compatible" for 8-connectivity
            // Two masks are incompatible if any bit in mask2 is adjacent (left, same col, right) to any bit in mask1

            // dp[row][mask] = max sum when row's selection state is mask
            var dp = new long[1 << m];
            for (int i = 0; i < (1 << m); i++) {
                dp[i] = Long.MIN_VALUE;
            }

            // Initialize with first row
            for (final int mask : validStates) {
                dp[mask] = rowSum(tc.matrix[0], mask, m);
            }

            // Process remaining rows
            for (int row = 1; row < n; row++) {
                final var newDp = new long[1 << m];
                for (int i = 0; i < (1 << m); i++) {
                    newDp[i] = Long.MIN_VALUE;
                }

                for (final int prevMask : validStates) {
                    if (dp[prevMask] == Long.MIN_VALUE) {
                        continue;
                    }

                    for (final int currMask : validStates) {
                        // Check 8-connectivity between prevMask and currMask
                        // currMask should not have any bit that is:
                        // - same column as prevMask bit (vertical)
                        // - left column of prevMask bit (diagonal)
                        // - right column of prevMask bit (diagonal)
                        if (!compatible8(prevMask, currMask)) {
                            continue;
                        }

                        final long newSum = dp[prevMask] + rowSum(tc.matrix[row], currMask, m);
                        if (newSum > newDp[currMask]) {
                            newDp[currMask] = newSum;
                        }
                    }
                }
                dp = newDp;
            }

            long maxSum = 0;
            for (final int mask : validStates) {
                if (dp[mask] > maxSum) {
                    maxSum = dp[mask];
                }
            }
            results.add(maxSum);
        }
        return results;
    }

    private static boolean compatible8(int prevMask, int currMask) {
        // For 8-connectivity, currMask bits cannot be:
        // - same column (vertical adjacency): prevMask & currMask != 0
        // - left diagonal: (prevMask >> 1) & currMask != 0
        // - right diagonal: (prevMask << 1) & currMask != 0
        if ((prevMask & currMask) != 0) {
            return false;
        }
        if (((prevMask >> 1) & currMask) != 0) {
            return false;
        }
        if (((prevMask << 1) & currMask) != 0) {
            return false;
        }
        return true;
    }

    private static long rowSum(int[] row, int mask, int m) {
        long sum = 0;
        for (int j = 0; j < m; j++) {
            if ((mask & (1 << j)) != 0) {
                sum += row[j];
            }
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        final var testCases = reader();
        final var results = cal(testCases);
        output(results);
    }

    public static void output(List<Long> results) {
        final var sb = new StringBuilder();
        for (final var result : results) {
            sb.append(result);
            sb.append('\n');
        }
        System.out.print(sb);
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
