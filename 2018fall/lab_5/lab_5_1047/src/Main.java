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
        final String text;
        final String pattern;

        public TestCase(String text, String pattern) {
            this.text = text;
            this.pattern = pattern;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int testCases = in.nextInt();
        assert (testCases >= 1) && (testCases <= 10) : "T must be between 1 and 10";
        final List<TestCase> cases = new ArrayList<>(testCases);
        for (int i = 0; i < testCases; i++) {
            final int n = in.nextInt();
            final String s = in.next();
            final int m = in.nextInt();
            final String p = in.next();
            assert s.length() == n : "Text length should be n";
            assert p.length() == m : "Pattern length should be m";
            assert n <= 1000000 : "|S| <= 1000000";
            assert m <= n : "|P| <= |S|";
            cases.add(new TestCase(s, p));
        }
        return cases;
    }

    public static List<Integer> cal(List<TestCase> inputs) {
        final List<Integer> results = new ArrayList<>();
        for (final var tc : inputs) {
            results.add(kmpSearch(tc.text, tc.pattern));
        }
        return results;
    }

    public static int[] computeLPSArray(String pattern) {
        final int m = pattern.length();
        if (m == 0) {
            return new int[0];
        }
        final int[] lps = new int[m];
        for (int length = 0, i = 1; i < m; ) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    // This is the key: Do not increment i here.
                    // We must re-evaluate pattern[i] with the new (shorter) prefix length in the next loop iteration.
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    public static int kmpSearch(String text, String pattern) {
        final int n = text.length();
        final int m = pattern.length();
        if (m == 0) {
            return 0;
        }
        final int[] lps = computeLPSArray(pattern);
        int i = 0; // pointer for text
        int j = 0; // pointer for pattern
        int count = 0;
        while (i < n) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == m) {
                count++;
                j = lps[j - 1];
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return count;
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

    // refactor from https://github.com/Kattis/kattio/blob/master/Kattio.java
    // url: https://raw.githubusercontent.com/Kattis/kattio/master/Kattio.java
    // license: MIT
    public static final class Reader {
        public final BufferedReader br;
        public StringTokenizer st;

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
