// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        final List<String> strings;

        public TestCase(List<String> strings) {
            this.strings = strings;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int testCases = in.nextInt();
        assert (testCases >= 1) && (testCases <= 10) : "T must be between 1 and 10";
        final List<TestCase> cases = new ArrayList<>(testCases);
        for (int i = 0; i < testCases; i++) {
            final int n = in.nextInt();
            assert (n >= 1) && (n <= 1000) : "N must be between 1 and 1000";
            final List<String> stringList = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                String s = in.next();
                assert (s.length() >= 1) && (s.length() <= 200) : "String length must be between 1 and 200";
                stringList.add(s);
            }
            cases.add(new TestCase(stringList));
        }
        return cases;
    }

    public static List<String> cal(List<TestCase> inputs) {
        final List<String> results = new ArrayList<>();
        for (final var tc : inputs) {
            results.add(solve(tc.strings));
        }
        return results;
    }
    private static final String HONG = "Hong";

    private static String solve(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return HONG;
        }
        int minLen = strings.get(0).length();
        for (String s : strings) {
            minLen = Math.min(minLen, s.length());
        }

        int low = 0, high = minLen;
        int maxL = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid == 0) {
                low = mid + 1;
                continue;
            }
            if (check(mid, strings) != null) {
                maxL = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (maxL == 0) {
            return HONG;
        }

        // Find the lexicographically smallest one of length maxL
        List<String> candidates = new ArrayList<>(check(maxL, strings));
        Collections.sort(candidates);
        return candidates.get(0);
    }

    private static Set<String> check(int len, List<String> strings) {
        String firstString = strings.get(0);
        Set<String> commonSubstrings = new HashSet<>();

        for (int i = 0; i <= firstString.length() - len; i++) {
            commonSubstrings.add(firstString.substring(i, i + len));
        }

        for (int i = 1; i < strings.size(); i++) {
            String currentString = strings.get(i);
            Set<String> nextCommonSubstrings = new HashSet<>();
            for (String sub : commonSubstrings) {
                if (currentString.contains(sub)) {
                    nextCommonSubstrings.add(sub);
                }
            }
            commonSubstrings = nextCommonSubstrings;
            if (commonSubstrings.isEmpty()) {
                return null;
            }
        }

        return commonSubstrings.isEmpty() ? null : commonSubstrings;
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
