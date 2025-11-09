// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    // Using a static final class for JDK 11 compatibility
    public static final class TestCase {
        final int n;
        final int m;
        final String s;
        final String t;

        public TestCase(int n, int m, String s, String t) {
            this.n = n;
            this.m = m;
            this.s = s;
            this.t = t;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int testCases = in.nextInt();
        assert (testCases >= 1) && (testCases <= 10) : "T must be between 1 and 10";
        final List<TestCase> cases = new ArrayList<>(testCases);
        for (int i = 0; i < testCases; i++) {
            final int n = in.nextInt();
            final int m = in.nextInt();
            assert (n >= 1) && (n <= 200000) : "n must be between 1 and 2*10^5";
            assert (m >= 1) && (m <= 200000) : "m must be between 1 and 2*10^5";
            final String s = in.next();
            final String t = in.next();
            assert s.length() == n : "s length should be n";
            assert t.length() == m : "t length should be m";
            cases.add(new TestCase(n, m, s, t));
        }
        return cases;
    }
    private static final String TRUE = "YES";
    private static final String FALSE = "NO";

    public static List<String> cal(List<TestCase> inputs) {
        final List<String> results = new ArrayList<>();
        for (final var tc : inputs) {
            final int starIndex = tc.s.indexOf('*');

            if (starIndex == -1) {
                // Case 1: No wildcard
                if (tc.s.equals(tc.t)) {
                    results.add(TRUE);
                } else {
                    results.add(FALSE);
                }
            } else {
                // Case 2: Wildcard exists
                if (tc.n - 1 > tc.m) {
                    results.add(FALSE);
                    continue;
                }

                final String prefix = tc.s.substring(0, starIndex);
                final String suffix = tc.s.substring(starIndex + 1);

                if (tc.t.startsWith(prefix) && tc.t.endsWith(suffix)) {
                    // Ensure prefix and suffix don't overlap in the target string
                    if (prefix.length() + suffix.length() <= tc.m) {
                        results.add(TRUE);
                    } else {
                        results.add(FALSE);
                    }
                } else {
                    results.add(FALSE);
                }
            }
        }
        return results;
    }

    public static void output(List<String> decides) {
        for (final var decide : decides) {
            System.out.print(decide);
            System.out.print('\n');
        }
    }

    public static void main(String[] args) {
        output(cal(reader()));
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
