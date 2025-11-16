// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {
    private static final int ALPHABET = 26;
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 500_000;

    private Main() {/**/}

    public static void main(String[] args) throws IOException {
        final var inputData = reader();
        final var answer = cal(inputData);
        output(answer);
    }

    public static InputData reader() throws IOException {
        final var fastReader = new Reader();
        final var cipher = new char[ALPHABET];
        for (int i = 0; i < ALPHABET; ++i) {
            final var token = fastReader.next();
            assert (token != null);
            assert ((token.length() == 1));
            final char mapped = token.charAt(0);
            assert ((mapped >= 'a') && (mapped <= 'z'));
            cipher[i] = mapped;
        }
        final var text = fastReader.next();
        assert (text != null);
        final int length = text.length();
        assert ((MIN_LENGTH <= length) && (length <= MAX_LENGTH));
        for (int i = 0; i < length; ++i) {
            final char ch = text.charAt(i);
            assert ((ch >= 'a') && (ch <= 'z'));
        }
        return new InputData(cipher, text);
    }

    public static int cal(InputData data) {
        final var s = data.text;
        final int n = s.length();
        final var mapped = new char[n];
        for (int i = 0; i < n; ++i) {
            mapped[i] = data.cipher[s.charAt(i) - 'a'];
        }
        final var prefix = new int[n];
        for (int i = 1; i < n; ++i) {
            int j = prefix[i - 1];
            while ((j > 0) && (s.charAt(i) != s.charAt(j))) {
                j = prefix[j - 1];
            }
            if (s.charAt(i) == s.charAt(j)) {
                ++j;
            }
            prefix[i] = j;
        }
        int j = 0;
        for (int i = 0; i < n; ++i) {
            while ((j > 0) && (mapped[i] != s.charAt(j))) {
                j = prefix[j - 1];
            }
            if (mapped[i] == s.charAt(j)) {
                ++j;
            }
            if (j == n) {
                j = prefix[j - 1];
            }
        }
        final int maxPrefix = Math.min(j, n / 2);
        return n - maxPrefix;
    }

    public static void output(int number) {
        System.out.print(number);
        System.out.print('\n');
    }

    private static final class InputData {
        private final char[] cipher;
        private final String text;

        private InputData(char[] cipher, String text) {
            this.cipher = cipher;
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
    }
}
