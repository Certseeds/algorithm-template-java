// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public final class Main {

    private Main() {/**/}

    public static void main(String[] args) throws IOException {
        final var input = reader();
        final int result = cal(input);
        output(result);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final String firstName = input.next();
        final String secondName = input.next();
        assert (firstName != null);
        assert (secondName != null);
        assert (!firstName.isEmpty());
        assert (!secondName.isEmpty());
        assert (firstName.length() <= 100000);
        assert (secondName.length() <= 100000);
        for (int i = 0; i < firstName.length(); i++) {
            final char ch = firstName.charAt(i);
            assert ((ch >= 32) && (ch <= 126));
        }
        for (int i = 0; i < secondName.length(); i++) {
            final char ch = secondName.charAt(i);
            assert ((ch >= 32) && (ch <= 126));
        }
        return new InputData(firstName, secondName);
    }

    public static int cal(InputData data) {
        final String first = data.first;
        final String second = data.second;
        final var automaton = new SuffixAutomaton(first.length());
        for (int i = 0; i < first.length(); i++) {
            automaton.add(first.charAt(i));
        }
        int state = 0;
        int currentLength = 0;
        int best = 0;
        for (int i = 0; i < second.length(); i++) {
            final char ch = second.charAt(i);
            final int idx = SuffixAutomaton.indexOf(ch);
            while ((state > 0) && (automaton.next[state][idx] == -1)) {
                state = automaton.link[state];
                currentLength = automaton.length[state];
            }
            if (automaton.next[state][idx] != -1) {
                state = automaton.next[state][idx];
                currentLength++;
            } else {
                currentLength = 0;
            }
            if (currentLength > best) {
                best = currentLength;
            }
        }
        return best;
    }

    public static void output(int result) {
        System.out.print(result);
        System.out.print('\n');
    }

    private static final class InputData {
        private final String first;
        private final String second;

        private InputData(String first, String second) {
            this.first = first;
            this.second = second;
        }
    }

    private static final class SuffixAutomaton {
        private static final int ALPHABET = 95;
        private static final int OFFSET = 32;
        private final int[][] next;
        private final int[] link;
        private final int[] length;
        private int size;
        private int last;

        private SuffixAutomaton(int maxLength) {
            final int capacity = (2 * maxLength) + 1;
            next = new int[capacity][ALPHABET];
            link = new int[capacity];
            length = new int[capacity];
            for (int i = 0; i < capacity; i++) {
                Arrays.fill(next[i], -1);
            }
            link[0] = -1;
            size = 1;
            last = 0;
        }

        private static int indexOf(char ch) {
            return ch - OFFSET;
        }

        private void add(char ch) {
            final int idx = indexOf(ch);
            int current = size++;
            length[current] = length[last] + 1;
            Arrays.fill(next[current], -1);
            int p = last;
            while ((p != -1) && (next[p][idx] == -1)) {
                next[p][idx] = current;
                p = link[p];
            }
            if (p == -1) {
                link[current] = 0;
            } else {
                final int q = next[p][idx];
                if (length[p] + 1 == length[q]) {
                    link[current] = q;
                } else {
                    final int clone = size++;
                    length[clone] = length[p] + 1;
                    System.arraycopy(next[q], 0, next[clone], 0, ALPHABET);
                    link[clone] = link[q];
                    while ((p != -1) && (next[p][idx] == q)) {
                        next[p][idx] = clone;
                        p = link[p];
                    }
                    link[q] = clone;
                    link[current] = clone;
                }
            }
            last = current;
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
