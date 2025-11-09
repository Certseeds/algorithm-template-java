// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class Main {

    private static final int POP_SENTINEL = 0;

    private static final int PUSH = -1;
    private static final int POP = -2;

    /**
     * Reads all test cases and their operations into a lightweight primitive array format.
     * 'push x' is stored as x.
     * 'pop' is stored as POP_SENTINEL (0).
     * This avoids creating millions of Integer objects, which is a major performance bottleneck.
     */
    public static List<int[]> reader() throws Exception {
        try (final var in = new FastReader();) {
            final int testcases = in.nextInt();
            assert (testcases >= 1 && testcases <= 5) : "T must be between 1 and 5";
            final List<int[]> allCases = new ArrayList<>(testcases);

            for (int t = 0; t < testcases; t++) {
                final int n = in.nextInt();
                assert (n >= 1 && n <= 400000) : "n must be between 1 and 400000";
                final int[] operations = new int[n];
                for (int i = 0; i < n; i++) {
                    if (PUSH == in.nextOperation()) {
                        final int x = in.nextInt();
                        assert (x >= 1 && x <= 100000000) : "x must be between 1 and 100,000,000";
                        operations[i] = x;
                    } else {
                        operations[i] = POP_SENTINEL;
                    }
                }
                allCases.add(operations);
            }
            return allCases;
        }
    }

    /**
     * Processes all test cases and returns the results for each "pop" operation.
     * The algorithm is already O(1) per operation, which is optimal.
     */
    public static List<List<Integer>> cal(final List<int[]> allCases) {
        final List<List<Integer>> allResults = new ArrayList<>();

        for (final var operations : allCases) {
            final Deque<Integer> values = new ArrayDeque<>();
            final Deque<Integer> minStack = new ArrayDeque<>();
            final Deque<Integer> maxStack = new ArrayDeque<>();
            final List<Integer> caseResults = new ArrayList<>();

            for (final int op : operations) {
                if (op != POP_SENTINEL) { // Push operation
                    values.push(op);
                    if (minStack.isEmpty()) {
                        minStack.push(op);
                        maxStack.push(op);
                    } else {
                        minStack.push(Math.min(op, minStack.peek()));
                        maxStack.push(Math.max(op, maxStack.peek()));
                    }
                } else { // Pop operation
                    if (values.isEmpty()) {
                        caseResults.add(0);
                    } else {
                        values.pop();
                        minStack.pop();
                        maxStack.pop();

                        if (values.isEmpty()) {
                            caseResults.add(0);
                        } else {
                            caseResults.add(maxStack.peek() - minStack.peek());
                        }
                    }
                }
            }
            allResults.add(caseResults);
        }
        return allResults;
    }

    /**
     * Prints the results to standard output using a StringBuilder for performance.
     */
    public static void output(final List<List<Integer>> allResults) {
        final var sb = new StringBuilder();
        for (final var caseResults : allResults) {
            for (final var result : caseResults) {
                sb.append(result).append('\n');
            }
        }
        System.out.print(sb);
    }

    public static void main(String[] args) throws Exception {
        output(cal(reader()));
    }

    // High-performance buffered reader, optimized for this problem
    private static final class FastReader implements AutoCloseable {
        private final DataInputStream din;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastReader() {
            din = new DataInputStream(System.in);
            buffer = new byte[1 << 16]; // 64KB buffer
            bufferPointer = bytesRead = 0;
        }

        public int nextOperation() throws IOException {
            byte c;
            while ((c = read()) <= ' ') ; // Skip whitespace

            // First char must be 'p'
            byte secondChar = read();
            if (secondChar == 'o') {
                read(); // consume 'p'
                return POP;
            } else { // must be 'u'
                read(); // consume 's'
                read(); // consume 'h'
                return PUSH;
            }
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg) {
                c = read();
            }
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            return neg ? -ret : ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, buffer.length);
            if (bytesRead == -1) {
                buffer[0] = -1;
            }
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) {
                fillBuffer();
            }
            return buffer[bufferPointer++];
        }

        @Override
        public void close() throws IOException {
            din.close();
        }
    }
}
