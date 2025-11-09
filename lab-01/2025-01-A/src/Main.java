// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public final class Main {

    public static InputData reader() throws IOException {
        final var in = new Reader();

        final int n = in.nextInt();
        assert ((1 <= n) && (n <= 100000));
        final int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            final int ai = in.nextInt();
            assert ((0 <= ai) && (ai <= 1000000000));
            a[i] = ai;
        }

        final int t = in.nextInt();
        assert ((1 <= t) && (t <= 100000));
        final int[] x = new int[t];
        for (int i = 0; i < t; i++) {
            final int xi = in.nextInt();
            assert ((1 <= xi) && (xi <= 1000000000));
            x[i] = xi;
        }
        return new InputData(n, a, t, x);
    }

    public static String[] cal(final InputData data) {
        final var set = new HashSet<Integer>(Math.max(16, data.n * 2));
        for (final int v : data.a) {
            set.add(v);
        }
        final String[] ans = new String[data.t];
        for (int i = 0; i < data.t; i++) {
            ans[i] = set.contains(data.x[i]) ? "YES" : "NO";
        }
        return ans;
    }

    public static void main(final String[] args) throws IOException {
        final InputData input = reader();
        final String[] results = cal(input);
        output(results);
    }

    public static void output(final String[] results) {
        final StringBuilder sb = new StringBuilder(results.length * 4);
        for (int i = 0; i < results.length; i++) {
            sb.append(results[i]).append('\n');
        }
        System.out.print(sb);
    }

    private static final class InputData {
        final int n;
        final int[] a;
        final int t;
        final int[] x;

        private InputData(int n, int[] a, int t, int[] x) {
            this.n = n;
            this.a = a;
            this.t = t;
            this.x = x;
        }
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
    }
}
