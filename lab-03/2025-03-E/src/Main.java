// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public final class Main {

    public static void main(String[] args) throws IOException {
        final InputData data = reader();
        final ResultData result = cal(data);
        output(result);
    }

    // read -> process -> output
    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        assert ((2 <= n) && (n <= 2_000_000));
        final int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            final int v = input.nextInt();
            assert ((1 <= v) && (v <= 1_000_000_000));
            values[i] = v;
        }
        return new InputData(values);
    }

    public static ResultData cal(InputData data) {
        final int[] a = data.values;
        final int n = a.length;
        final int[] ans = new int[Math.max(0, n - 1)];
        if (n <= 1) { // safety, though constraints ensure n >= 2
            return new ResultData(ans);
        }

        // Build nodes, each with (value, index)
        final Node[] nodes = new Node[n];
        final Node[] nodeByIndex = new Node[n];
        for (int i = 0; i < n; i++) {
            final Node nd = new Node(a[i], i);
            nodes[i] = nd;
            nodeByIndex[i] = nd;
        }
        // Sort references by value, tie by index to keep determinism
        Arrays.sort(nodes, (x, y) -> (x.value != y.value) ? Integer.compare(x.value, y.value) : Integer.compare(x.index, y.index));
        // Link as a doubly linked list in value order
        for (int p = 0; p < n; p++) {
            final Node cur = nodes[p];
            cur.prev = (p > 0) ? nodes[p - 1] : null;
            cur.next = (p + 1 < n) ? nodes[p + 1] : null;
        }

        // Iterate original indices from 0..n-2; active set at step i is {i..n-1}
        for (int i = 0; i < n - 1; i++) {
            final Node cur = nodeByIndex[i];
            int best = Integer.MAX_VALUE;
            final Node l = cur.prev;
            if (l != null) {
                final int diff = cur.value - l.value; // non-negative in value order
                if (diff < best) { best = diff; }
            }
            final Node r = cur.next;
            if (r != null) {
                final int diff = r.value - cur.value; // non-negative in value order
                if (diff < best) { best = diff; }
            }
            ans[i] = best;
            // unlink cur from value-order list
            if (l != null) { l.next = r; }
            if (r != null) { r.prev = l; }
            // help GC
            cur.prev = null;
            cur.next = null;
        }
        return new ResultData(ans);
    }

    public static void output(ResultData result) {
        final int[] arr = result.differences;
        final int m = arr.length;
        final var builder = new StringBuilder((m == 0) ? 1 : Math.min(1_000_000_000, m * 3));
        for (int i = 0; i < m; i++) {
            builder.append(arr[i]);
            if (i + 1 < m) { builder.append(' '); }
        }
        System.out.print(builder.toString());
        System.out.print('\n');
    }

    // Doubly linked list node in value order
    private static final class Node {
        final int value;
        final int index;
        Node prev;
        Node next;
        Node(int value, int index) { this.value = value; this.index = index; }
    }

    public static final class InputData {
        private final int[] values;
        private InputData(int[] values) { this.values = values; }
    }

    public static final class ResultData {
        private final int[] differences;
        private ResultData(int[] differences) { this.differences = differences; }
    }

    // refactor from https://github.com/Kattis/kattio/blob/master/Kattio.java
    // url: https://raw.githubusercontent.com/Kattis/kattio/master/Kattio.java
    // license: MIT
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() { br = new BufferedReader(new InputStreamReader(System.in)); }

        String next() {
            while ((st == null) || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() { return Integer.parseInt(next()); }
    }
}
