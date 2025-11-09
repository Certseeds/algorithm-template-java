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
        assert ((1 <= testCount) && (testCount <= 10));
        final var strings = new String[testCount];
        for (int i = 0; i < testCount; i++) {
            final String value = input.next();
            assert (value != null);
            assert (!value.isEmpty());
            assert (value.length() <= 1000);
            for (int j = 0; j < value.length(); j++) {
                final char ch = value.charAt(j);
                assert (('a' <= ch) && (ch <= 'z'));
            }
            strings[i] = value;
        }
        return new InputData(testCount, strings);
    }

    public static long[] cal(InputData data) {
        final var results = new long[data.testCount];
        for (int i = 0; i < data.testCount; i++) {
            final int length = data.strings[i].length();
            final long size = length;
            results[i] = size * (size + 1L) / 2L;
        }
        return results;
    }

    public static void output(long[] results) {
        final var builder = new StringBuilder();
        for (long result : results) {
            builder.append(result);
            builder.append('\n');
        }
        System.out.print(builder);
    }

    private static final class InputData {
        private final int testCount;
        private final String[] strings;

        private InputData(int testCount, String[] strings) {
            this.testCount = testCount;
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
