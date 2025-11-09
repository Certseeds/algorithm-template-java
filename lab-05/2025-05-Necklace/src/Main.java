// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    private Main() {/**/}

    public static void main(String[] args) throws IOException {
        final var input = reader();
        final var results = cal(input);
        output(results);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int testCount = input.nextInt();
        assert ((1 <= testCount) && (testCount <= 1000));
        final var strings = new String[testCount];
        for (int i = 0; i < testCount; i++) {
            final String value = input.next();
            assert (value != null);
            assert (!value.isEmpty());
            assert (value.length() <= 200000);
            strings[i] = value;
        }
        return new InputData(testCount, strings);
    }

    public static int[] cal(InputData data) {
        final var results = new int[data.count];
        for (int caseIndex = 0; caseIndex < data.count; caseIndex++) {
            final String s = data.strings[caseIndex];
            results[caseIndex] = computeExtraLength(s);
        }
        return results;
    }

    public static void output(int[] results) {
        final var builder = new StringBuilder();
        for (int result : results) {
            builder.append(result);
            builder.append('\n');
        }
        System.out.print(builder);
    }

    private static int computeExtraLength(String s) {
        final int length = s.length();
        final int[] prefix = buildPrefixFunction(s);
        final int longestBorder = prefix[length - 1];
        final int minimalPeriod = length - longestBorder;
            if (length % minimalPeriod == 0 && length / minimalPeriod >= 2) {
                return 0;
            } else {
                return minimalPeriod - (length % minimalPeriod);
            }
    }

    private static int[] buildPrefixFunction(String s) {
        final int n = s.length();
        final int[] prefix = new int[n];
        for (int i = 1; i < n; i++) {
            int j = prefix[i - 1];
            while ((j > 0) && (s.charAt(i) != s.charAt(j))) {
                j = prefix[j - 1];
            }
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            prefix[i] = j;
        }
        return prefix;
    }

    private static final class InputData {
        private final int count;
        private final String[] strings;

        private InputData(int count, String[] strings) {
            this.count = count;
            this.strings = strings;
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
