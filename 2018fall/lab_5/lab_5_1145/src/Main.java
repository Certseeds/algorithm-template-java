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
        final char[] lastChars;

        public TestCase(char[] lastChars) {
            this.lastChars = lastChars;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int testCases = in.nextInt();
        assert (testCases >= 1) && (testCases <= 100) : "T must be between 1 and 100";
        final List<TestCase> cases = new ArrayList<>(testCases);
        for (int t = 0; t < testCases; t++) {
            final int n = in.nextInt();
            assert (n >= 1) && (n <= 10000) : "N must be between 1 and 10^4";
            final char[] lastChars = new char[n];
            for (int i = 0; i < n; i++) {
                final String s = in.nextLine();
                assert (s.length() <= 100) : "Sentence length must not exceed 100";
                assert s.matches("[a-z]+") : "Sentence must consist only of lowercase letters";
                lastChars[i] = s.charAt(s.length() - 1);
            }
            cases.add(new TestCase(lastChars));
        }
        return cases;
    }

    public static List<Integer> cal(List<TestCase> inputs) {
        final List<Integer> results = new ArrayList<>();
        for (final var tc : inputs) {
            if (tc.lastChars.length == 0) {
                results.add(0);
                continue;
            }

            int maxStreak = 1;
            int currentStreak = 1;
            for (int i = 1; i < tc.lastChars.length; i++) {
                if (tc.lastChars[i - 1] == tc.lastChars[i]) {
                    currentStreak++;
                } else {
                    maxStreak = Math.max(maxStreak, currentStreak);
                    currentStreak = 1;
                }
            }
            maxStreak = Math.max(maxStreak, currentStreak);
            results.add(maxStreak);
        }
        return results;
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

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return str;
        }
    }
}
