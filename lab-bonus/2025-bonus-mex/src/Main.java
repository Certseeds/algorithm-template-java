// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    public static final class Query {
        final long a;
        final long b;

        Query(long a, long b) {
            this.a = a;
            this.b = b;
        }
    }

    public static Query[] reader() throws IOException {
        final var input = new Reader();
        final int t = input.nextInt();
        assert ((1 <= t) && (t <= 100000));

        final var queries = new Query[t];
        for (int i = 0; i < t; i++) {
            final long a = input.nextLong();
            final long b = input.nextLong();
            assert ((0 <= a) && (a <= 1_000_000_000L));
            assert ((0 <= b) && (b <= 1_000_000_000L));
            queries[i] = new Query(a, b);
        }
        return queries;
    }

    public static long[] cal(Query[] queries) {
        final var results = new long[queries.length];
        for (int i = 0; i < queries.length; i++) {
            results[i] = solve(queries[i].a, queries[i].b);
        }
        return results;
    }

    private static long solve(long a, long b) {
        // mex = min x s.t. (a ^ x) > b
        final long[][] memo = new long[32][2];
        for (int i = 0; i < 32; i++) {
            memo[i][0] = -1;
            memo[i][1] = -1;
        }
        return dfs(30, 0, a, b, memo);
    }

    private static long dfs(int bit, int tight, long a, long b, long[][] memo) {
        if (bit < 0) {
            return (tight == 1) ? 0 : Long.MAX_VALUE / 4;
        }
        if (memo[bit][tight] != -1) {
            return memo[bit][tight];
        }
        final long aBit = (a >> bit) & 1;
        final long bBit = (b >> bit) & 1;
        long best = Long.MAX_VALUE / 4;
        for (int pBit = 0; pBit <= 1; pBit++) {
            if ((tight == 0) && (pBit < bBit)) {
                continue;
            }
            final int nextTight;
            if (tight == 1) {
                nextTight = 1;
            } else {
                nextTight = (pBit > bBit) ? 1 : 0;
            }
            final long xBit = aBit ^ pBit;
            final long suffix = dfs(bit - 1, nextTight, a, b, memo);
            if (suffix >= Long.MAX_VALUE / 8) {
                continue;
            }
            final long candidate = (xBit << bit) | suffix;
            if (candidate < best) {
                best = candidate;
            }
        }
        memo[bit][tight] = best;
        return best;
    }

    public static void main(String[] args) throws IOException {
        final var queries = reader();
        final var results = cal(queries);
        output(results);
    }

    public static void output(long[] results) {
        final var sb = new StringBuilder();
        for (final long result : results) {
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
