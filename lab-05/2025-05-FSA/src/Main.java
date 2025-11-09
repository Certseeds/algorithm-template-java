// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    private Main() {/**/}

    private static final class InputData {
        private final String pattern;

        private InputData(String pattern) {
            this.pattern = pattern;
        }

        private String pattern() {
            return pattern;
        }
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final String pattern = input.next();
        assert (pattern != null);
        assert ((1 <= pattern.length()) && (pattern.length() <= 100000));
        for (int i = 0; i < pattern.length(); i++) {
            final char ch = pattern.charAt(i);
            assert (('a' <= ch) && (ch <= 'z'));
        }
        return new InputData(pattern);
    }

    public static int[][] cal(InputData data) {
        final String s = data.pattern();
        final int length = s.length();
        final int[][] transitions = new int[length][26];
        final int[] prefix = buildPrefixFunction(s);
        for (int state = 0; state < length; state++) {
            for (int idx = 0; idx < 26; idx++) {
                final char ch = (char) ('a' + idx);
                int nextState = state;
                while ((nextState > 0) && (s.charAt(nextState) != ch)) {
                    nextState = prefix[nextState - 1];
                }
                if (s.charAt(nextState) == ch) {
                    nextState++;
                }
                transitions[state][idx] = nextState;
            }
        }
        return transitions;
    }

    private static int[] buildPrefixFunction(String s) {
        final int length = s.length();
        final int[] prefix = new int[length];
        for (int i = 1; i < length; i++) {
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

    public static void main(String[] args) throws IOException {
        final InputData datas = reader();
        final int[][] result = cal(datas);
        output(result);
    }

    public static void output(int[][] transitions) {
        final StringBuilder builder = new StringBuilder();
        for (int[] transition : transitions) {
            for (int j = 0; j < 26; j++) {
                if (j > 0) {
                    builder.append(" ");
                }
                builder.append(transition[j]);
            }
            builder.append("\n");
        }
        System.out.print(builder);
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
                    final String line = br.readLine();
                    if (line == null) {
                        return null;
                    }
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }
    }
}
