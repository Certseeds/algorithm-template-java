// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    private Main() {/**/}

    public static void main(String[] args) throws IOException {
        final var input = reader();
        final var result = cal(input);
        output(result);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final String value = input.next();
        assert (value != null);
        assert ((0 <= value.length()) && (value.length() <= 1_000_000));
        return new InputData(value);
    }

    public static int[] cal(InputData data) {
        return buildPrefixFunction(data.text);
    }

    public static void output(int[] prefix) {
        final var builder = new StringBuilder(prefix.length * 2);
        for (int j : prefix) {
            builder.append(j);
            builder.append('\n');
        }
        System.out.print(builder);
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
        private final String text;

        private InputData(String text) {
            this.text = text;
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

        String next() throws IOException {
            while ((st == null) || !st.hasMoreElements()) {
                final String line = br.readLine();
                if (line == null) {
                    return null;
                }
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
    }
}
