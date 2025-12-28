// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    public static final class Input {
        final int n;
        final int m;
        final int k;
        final int x;
        final int[][] operations;

        Input(int n, int m, int k, int x, int[][] operations) {
            this.n = n;
            this.m = m;
            this.k = k;
            this.x = x;
            this.operations = operations;
        }
    }

    public static Input reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int m = input.nextInt();
        final int k = input.nextInt();
        final int x = input.nextInt();
        assert ((n * m) < 1_000_000);
        assert ((0 < k) && (k < 200_000));
        assert ((0 <= x) && (x < n * m));

        final var operations = new int[k][2];
        for (int i = 0; i < k; i++) {
            operations[i][0] = input.nextInt();
            operations[i][1] = input.nextInt();
            assert ((0 <= operations[i][0]) && (operations[i][0] < n * m));
            assert ((0 <= operations[i][1]) && (operations[i][1] < n * m));
        }
        return new Input(n, m, k, x, operations);
    }

    public static int[] cal(Input inp) {
        final int total = inp.n * inp.m;

        // Each node has a left and right pointer
        // Using arrays: left[i] = node to the left of i, right[i] = node to the right of i
        // -1 means no neighbor (head or tail)
        final var left = new int[total];
        final var right = new int[total];

        // Initialize n chains
        for (int chain = 0; chain < inp.n; chain++) {
            for (int pos = 0; pos < inp.m; pos++) {
                final int node = chain * inp.m + pos;
                left[node] = (pos == 0) ? -1 : node - 1;
                right[node] = (pos == inp.m - 1) ? -1 : node + 1;
            }
        }

        // Process each operation
        for (final int[] op : inp.operations) {
            final int a = op[0];
            final int b = op[1];

            // Disconnect right of a: a <-> a_right becomes a, a_right
            // Disconnect left of b: b_left <-> b becomes b_left, b

            final int aRight = right[a];
            final int bLeft = left[b];

            // After operation:
            // a connects to b
            // a_right connects to b_left's next (which is the tail of b_left's side)
            // Actually: the remaining two parts join together

            // Before: ... <-> a <-> a_right <-> ...
            //         ... <-> b_left <-> b <-> ...
            // After: ... <-> a <-> b <-> ...
            //        ... <-> a_right <-> ... and ... <-> b_left <-> ... join

            // Disconnect a from a_right
            right[a] = -1;
            if (aRight != -1) {
                left[aRight] = -1;
            }

            // Disconnect b_left from b
            left[b] = -1;
            if (bLeft != -1) {
                right[bLeft] = -1;
            }

            // Connect a to b
            right[a] = b;
            left[b] = a;

            // Connect a_right to b_left (if both exist)
            if (aRight != -1 && bLeft != -1) {
                right[bLeft] = aRight;
                left[aRight] = bLeft;
            }
        }

        // Find head of the chain containing x
        int head = inp.x;
        while (left[head] != -1) {
            head = left[head];
        }

        // Count nodes
        int count = 0;
        int cur = head;
        while (cur != -1) {
            count++;
            cur = right[cur];
        }

        // Collect nodes
        final var result = new int[count];
        cur = head;
        for (int i = 0; i < count; i++) {
            result[i] = cur;
            cur = right[cur];
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        final var input = reader();
        final var result = cal(input);
        output(result);
    }

    public static void output(int[] result) {
        final var sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(result[i]);
        }
        sb.append('\n');
        System.out.print(sb);
    }


    // refactor from https://github.com/Kattis/kattio/blob/master/Kattio.java
    // url: https://raw.githubusercontent.com/Kattis/kattio/master/Kattio.java
    // license: MIT
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
            st = new StringTokenizer("");
        }

        public boolean hasNext() {
            while (!st.hasMoreTokens()) {
                String nextLine = nextLine();
                if (nextLine == null) {
                    return false;
                }
                st = new StringTokenizer(nextLine);
            }
            return true;
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

        int nextInt() {return Integer.parseInt(next());}

        long nextLong() {return Long.parseLong(next());}

        double nextDouble() {return Double.parseDouble(next());}

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
