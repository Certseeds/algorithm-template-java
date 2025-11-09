// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    private Main() {/**/}

    private static final class InputData {
        private final char[] cipher;
        private final String target;

        private InputData(char[] cipher, String target) {
            this.cipher = cipher;
            this.target = target;
        }

        private char[] cipher() {
            return cipher;
        }

        private String target() {
            return target;
        }
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final char[] cipher = new char[26];
        for (int i = 0; i < 26; i++) {
            final String token = input.next();
            assert (token != null);
            assert (token.length() == 1);
            final char mapped = token.charAt(0);
            assert (('a' <= mapped) && (mapped <= 'z'));
            cipher[i] = mapped;
        }
        final String target = input.next();
        assert (target != null);
        assert ((1 <= target.length()) && (target.length() <= 500000));
        for (int i = 0; i < target.length(); i++) {
            final char ch = target.charAt(i);
            assert (('a' <= ch) && (ch <= 'z'));
        }
        return new InputData(cipher, target);
    }

    public static int cal(InputData data) {
        final String s = data.target();
        final int length = s.length();
        final char[] mapped = new char[length];
        final char[] cipher = data.cipher();
        for (int i = 0; i < length; i++) {
            mapped[i] = cipher[s.charAt(i) - 'a'];
        }
        final char[] combined = buildCombined(s, mapped);
        final int[] prefix = buildPrefixFunction(combined);
        final int overlap = Math.min(prefix[combined.length - 1], length / 2);
        // overlap is the visible portion of the second half that still matches
        return length - overlap;
    }

    private static char[] buildCombined(String s, char[] mapped) {
        final int length = s.length();
        final char[] combined = new char[(2 * length) + 1];
        for (int i = 0; i < length; i++) {
            combined[i] = s.charAt(i);
        }
        combined[length] = '#';
        for (int i = 0; i < length; i++) {
            combined[length + 1 + i] = mapped[i];
        }
        return combined;
    }

    private static int[] buildPrefixFunction(char[] input) {
        final int length = input.length;
        final int[] prefix = new int[length];
        for (int i = 1; i < length; i++) {
            int j = prefix[i - 1];
            while ((j > 0) && (input[i] != input[j])) {
                j = prefix[j - 1];
            }
            if (input[i] == input[j]) {
                j++;
            }
            prefix[i] = j;
        }
        return prefix;
    }

    public static void main(String[] args) throws IOException {
        final InputData datas = reader();
        final int result = cal(datas);
        output(result);
    }

    public static void output(int number) {
        System.out.print(number);
        System.out.print('\n');
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
