// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public final class Main {

    public static final class TestCase {
        final int n;
        final List<List<Integer>> adj;

        public TestCase(int n, List<List<Integer>> adj) {
            this.n = n;
            this.adj = adj;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int testCases = in.nextInt();
        assert (testCases >= 1) && (testCases <= 10) : "T must be between 1 and 10";
        final List<TestCase> cases = new ArrayList<>(testCases);
        for (int i = 0; i < testCases; i++) {
            final int n = in.nextInt();
            assert (n >= 2) && (n <= 10000) : "N must be between 2 and 10^4";
            final List<List<Integer>> adj = new ArrayList<>(n + 1);
            for (int j = 0; j <= n; j++) {
                adj.add(new ArrayList<>());
            }
            for (int j = 0; j < n - 1; j++) {
                final int u = in.nextInt();
                final int v = in.nextInt();
                assert (u >= 1) && (u <= n) : "u must be between 1 and N";
                assert (v >= 1) && (v <= n) : "v must be between 1 and N";
                adj.get(u).add(v);
                adj.get(v).add(u);
            }
            cases.add(new TestCase(n, adj));
        }
        return cases;
    }

    public static List<List<Integer>> cal(List<TestCase> inputs) {
        final List<List<Integer>> results = new ArrayList<>();
        for (final var tc : inputs) {
            final List<Integer> leaves = new ArrayList<>();
            if (tc.n == 2) {
                // For N=2, the tree is just 1-2. Node 1 is the root, so node 2 is the only leaf.
                leaves.add(2);
            } else {
                // A leaf is a non-root node with degree 1.
                // We iterate from 2 to N to exclude the root.
                for (int i = 2; i <= tc.n; i++) {
                    if (tc.adj.get(i).size() == 1) {
                        leaves.add(i);
                    }
                }
            }
            results.add(leaves);
        }
        return results;
    }

    public static void output(List<List<Integer>> allDecides) {
        final StringBuilder sb = new StringBuilder();
        for (final var decides : allDecides) {
            if (!decides.isEmpty()) {
                sb.append(
                    decides.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" "))
                );
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }

    public static void main(String[] args) {
        output(cal(reader()));
    }

    // refactor from https://github.com/Kattis/kattio/blob/master/Kattio.java
    // url: https://raw.githubusercontent.com/Kattis/kattio/master/Kattio.java
    // license: MIT
    public static final class Reader {
        public final BufferedReader br;
        public StringTokenizer st;

        public Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
