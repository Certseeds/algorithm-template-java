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
        final String t;

        public TestCase(String s, String t) {
            this.s = s;
            this.t = t;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int testCases = in.nextInt();
        assert (testCases >= 1) && (testCases <= 50) : "T must be between 1 and 50";
        final List<TestCase> cases = new ArrayList<>(testCases);
        for (int i = 0; i < testCases; i++) {
            final int n = in.nextInt();
            final int m = in.nextInt();
            final String s = in.next();
            final String t = in.next();
            assert s.length() == n : "s length should be n";
            assert t.length() == m : "t length should be m";
            assert n >= 1 && n <= 100000 : "n must be between 1 and 10^5";
            assert m >= 1 && m <= 100000 : "m must be between 1 and 10^5";
            cases.add(new TestCase(s, t));
        }
        return cases;
    }

    public static List<String> cal(List<TestCase> inputs) {
        final List<String> results = new ArrayList<>();
        for (final var tc : inputs) {
            results.add(solve(tc.s, tc.t));
        }
        return results;
    }

    private static String solve(String s, String t) {
        final String combined = s + '#' + t;
        final int[] lps = computeLPSArray(combined);
        final int longestLength = lps[lps.length - 1];

        if (longestLength == 0) {
            return "0";
        } else {
            return longestLength + " " + s.substring(0, longestLength);
        }
    }

    private static int[] computeLPSArray(String pattern) {
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

    public static void output(List<String> decides) {
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
