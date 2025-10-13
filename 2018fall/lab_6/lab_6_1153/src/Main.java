// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        public final int n;
        public final int[] left;
        public final int[] right;
        public final int root;

        public TestCase(int n, int[] left, int[] right, int root) {
            this.n = n;
            this.left = left;
            this.right = right;
            this.root = root;
        }
    }

    // reader: parse input into TestCase objects
    public static List<TestCase> reader() {
        final var in = new Reader();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            assert ((1 <= n) && (n <= 10000));
            final int[] left = new int[n + 1];
            final int[] right = new int[n + 1];
            final boolean[] isChild = new boolean[n + 1];
            for (int i = 0; i < n - 1; i++) {
                final int u = in.nextInt();
                final int v = in.nextInt();
                assert ((1 <= u) && (u <= n));
                assert ((1 <= v) && (v <= n));
                if (left[u] == 0) {
                    left[u] = v;
                } else {
                    // place in right if left already occupied
                    assert (right[u] == 0) : "a node has more than two children";
                    right[u] = v;
                }
                isChild[v] = true;
            }
            int root = 1;
            for (int i = 1; i <= n; i++) {
                if (!isChild[i]) {
                    root = i;
                    break;
                }
            }
            tests.add(new TestCase(n, left, right, root));
        }
        return tests;
    }

    // cal: perform traversals for each test case and return list of output lines
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> outLines = new ArrayList<>();
        for (final var tc : inputs) {
            final int root = tc.root;
            // preorder
            final List<Integer> preorder = new ArrayList<>();
            final Deque<Integer> stack = new ArrayDeque<>();
            if (root != 0) stack.push(root);
            while (!stack.isEmpty()) {
                final int u = stack.pop();
                preorder.add(u);
                final int r = tc.right[u];
                final int l = tc.left[u];
                if (r != 0) stack.push(r);
                if (l != 0) stack.push(l);
            }

            // inorder (iterative)
            final List<Integer> inorder = new ArrayList<>();
            int cur = root;
            final Deque<Integer> st2 = new ArrayDeque<>();
            while ((cur != 0) || !st2.isEmpty()) {
                while (cur != 0) {
                    st2.push(cur);
                    cur = tc.left[cur];
                }
                if (!st2.isEmpty()) {
                    final int node = st2.pop();
                    inorder.add(node);
                    cur = tc.right[node];
                }
            }

            // postorder using modified preorder (root-right-left) then reverse
            final List<Integer> postTmp = new ArrayList<>();
            final Deque<Integer> st3 = new ArrayDeque<>();
            if (root != 0) st3.push(root);
            while (!st3.isEmpty()) {
                final int u = st3.pop();
                postTmp.add(u);
                final int l = tc.left[u];
                final int r = tc.right[u];
                if (l != 0) st3.push(l);
                if (r != 0) st3.push(r);
            }
            final List<Integer> postorder = new ArrayList<>(postTmp.size());
            for (int i = postTmp.size() - 1; i >= 0; i--) postorder.add(postTmp.get(i));

            // join lines
            outLines.add(joinInts(preorder));
            outLines.add(joinInts(inorder));
            outLines.add(joinInts(postorder));
        }
        return outLines;
    }

    // output: print each line and ensure newline after each
    public static void output(final List<String> lines) {
        final StringBuilder sb = new StringBuilder();
        for (final var line : lines) {
            sb.append(line);
            sb.append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) {
        output(cal(reader()));
    }

    private static String joinInts(final List<Integer> arr) {
        final StringJoiner sj = new StringJoiner(" ");
        for (final var v : arr) {
            sj.add(String.valueOf(v));
        }
        return sj.toString();
    }

    // fast reader
    public static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        public Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    final String line = br.readLine();
                    if (line == null) return "";
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }

}
