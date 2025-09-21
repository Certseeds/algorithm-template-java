// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    // cal: determine whether unique minimal sequence exists
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>(inputs.size());
        for (final TestCase tc : inputs) {
            out.add(checkUnique(tc) ? "Y" : "N");
        }
        return out;
    }

    private static boolean checkUnique(final TestCase tc) {
        final int n = tc.n;
        final int[] a = tc.a.clone();
        final Set<Integer> inv = new HashSet<>();
        for (int i = 0; i < n - 1; i++) if (a[i] > a[i + 1]) inv.add(i);

        // Early exit: if no swaps needed, unique
        if (inv.isEmpty()) return true;

        // Simulate until sorted or multiple choices found
        while (!inv.isEmpty()) {
            if (inv.size() > 1) return false; // more than one choice -> not unique
            // exactly one adjacent inversion
            final int i = inv.iterator().next();
            // perform swap at positions i and i+1
            final int tmp = a[i];
            a[i] = a[i + 1];
            a[i + 1] = tmp;
            // update inv for indices i-1, i, i+1
            for (int idx = Math.max(0, i - 1); idx <= Math.min(n - 2, i + 1); idx++) {
                if (a[idx] > a[idx + 1]) inv.add(idx);
                else inv.remove(idx);
            }
        }
        return true;
    }

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
