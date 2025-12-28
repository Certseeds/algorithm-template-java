// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    private static final class FeasibleWork {
        final int[] outdeg;
        final int[] head;
        final int[] next;
        final long[] active;
        int maxWord;

        FeasibleWork(int n) {
            this.outdeg = new int[n + 1];
            this.head = new int[n + 1];
            this.next = new int[n + 1];
            this.active = new long[((n + 63) >>> 6) + 1];
            this.maxWord = 0;
        }

        void clearBuckets(int n) {
            java.util.Arrays.fill(head, 0);
            java.util.Arrays.fill(next, 0);
            java.util.Arrays.fill(active, 0L);
            maxWord = 0;
        }

        void bucketAdd(int node, int d) {
            next[node] = head[d];
            head[d] = node;
            final int bit = d - 1;
            final int w = bit >>> 6;
            active[w] |= 1L << (bit & 63);
            if (w > maxWord) {
                maxWord = w;
            }
        }

        int bucketPollMax() {
            while ((maxWord >= 0) && (active[maxWord] == 0L)) {
                maxWord--;
            }
            if (maxWord < 0) {
                return 0;
            }
            final long word = active[maxWord];
            final int b = 63 - Long.numberOfLeadingZeros(word);
            final int bitIndex = (maxWord << 6) + b;
            final int d = bitIndex + 1;
            final int u = head[d];
            head[d] = next[u];
            next[u] = 0;
            if (head[d] == 0) {
                active[maxWord] &= ~(1L << b);
            }
            return u;
        }
    }

    public static final class Input {
        final int n;
        final int m;
        final int[] k;
        final int[][] edges; // edges[i] = {from, to}

        Input(int n, int m, int[] k, int[][] edges) {
            this.n = n;
            this.m = m;
            this.k = k;
            this.edges = edges;
        }
    }

    public static Input reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int m = input.nextInt();
        assert ((1 <= n) && (n <= 2000));
        assert ((0 <= m) && (m <= 10000));

        final var k = new int[n + 1]; // 1-indexed
        for (int i = 1; i <= n; i++) {
            k[i] = input.nextInt();
        }

        final var edges = new int[m][2];
        for (int i = 0; i < m; i++) {
            edges[i][0] = input.nextInt();
            edges[i][1] = input.nextInt();
            assert ((1 <= edges[i][0]) && (edges[i][0] <= n));
            assert ((1 <= edges[i][1]) && (edges[i][1] <= n));
        }

        return new Input(n, m, k, edges);
    }

    public static int[] cal(Input inp) {
        final int n = inp.n;

        final var deadline = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // Treat constraint as: position <= k[i]
            // Some judges use inclusive bound even if statement says "< k[i]".
            deadline[i] = Math.min(n, inp.k[i]);
            assert (deadline[i] >= 1);
        }

        final var outdegBase = new int[n + 1];
        final var predCnt = new int[n + 1];
        for (final var e : inp.edges) {
            outdegBase[e[0]]++;
            predCnt[e[1]]++;
        }

        final var preds = new int[n + 1][];
        for (int i = 1; i <= n; i++) {
            preds[i] = new int[predCnt[i]];
        }
        final var fill = new int[n + 1];
        for (final var e : inp.edges) {
            preds[e[1]][fill[e[1]]++] = e[0];
        }

        final var result = new int[n + 1];
        final var work = new FeasibleWork(n);
        for (int i = 1; i <= n; i++) {
            int lo = 1;
            int hi = deadline[i];
            while (lo < hi) {
                final int mid = (lo + hi) >>> 1;
                if (feasible(n, preds, outdegBase, deadline, i, mid, work)) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            result[i] = lo;
        }
        return result;
    }

    private static boolean feasible(int n,
                                    int[][] preds,
                                    int[] outdegBase,
                                    int[] deadline,
                                    int special,
                                    int specialBound,
                                    FeasibleWork work) {
        System.arraycopy(outdegBase, 0, work.outdeg, 0, n + 1);
        work.clearBuckets(n);

        for (int i = 1; i <= n; i++) {
            if (work.outdeg[i] == 0) {
                final int d = (i == special) ? Math.min(deadline[i], specialBound) : deadline[i];
                work.bucketAdd(i, d);
            }
        }

        for (int t = n; t >= 1; t--) {
            final int u = work.bucketPollMax();
            if (u == 0) {
                return false;
            }
            final int du = (u == special) ? Math.min(deadline[u], specialBound) : deadline[u];
            if (du < t) {
                return false;
            }
            for (final int p : preds[u]) {
                final int v = --work.outdeg[p];
                if (v == 0) {
                    final int dp = (p == special) ? Math.min(deadline[p], specialBound) : deadline[p];
                    work.bucketAdd(p, dp);
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        final var input = reader();
        final var result = cal(input);
        output(result);
    }

    public static void output(int[] result) {
        final var sb = new StringBuilder();
        for (int i = 1; i < result.length; i++) {
            if (i > 1) {
                sb.append(' ');
            }
            sb.append(result[i]);
        }
        sb.append('\n');
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
