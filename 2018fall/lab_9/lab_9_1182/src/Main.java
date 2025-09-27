// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        final int n;
        final int[] x;               // target (0-based)
        final boolean[] claimVillager; // true if "villager", false if "werewolf"
        public TestCase(final int n, final int[] x, final boolean[] claimVillager) {
            this.n = n;
            this.x = x;
            this.claimVillager = claimVillager;
        }
    }

    // reader: parse input into test cases
    public static List<TestCase> reader() throws IOException {
        final var in = new FastScanner();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final var tests = new ArrayList<TestCase>(T);
        for (int tc = 0; tc < T; tc++) {
            final int n = in.nextInt();
            assert (1 <= n && n <= 100000);
            final int[] x = new int[n];
            final boolean[] c = new boolean[n];
            for (int i = 0; i < n; i++) {
                final int xi = in.nextInt();
                final String s = in.next();
                x[i] = xi - 1;
                c[i] = "villager".equals(s);
            }
            tests.add(new TestCase(n, x, c));
        }
        return tests;
    }

    // cal: compute count of "always werewolf" for each test case
    public static List<Integer> cal(final List<TestCase> inputs) {
        final var out = new ArrayList<Integer>(inputs.size());
        for (final TestCase tc : inputs) {
            final int n = tc.n;
            final int[] next = tc.x;
            final boolean[] isV = tc.claimVillager;

            final byte[] state = new byte[n]; // 0=unvisited, 1=visiting, 2=processed
            final boolean[] can = new boolean[n]; // can be villager?
            final int[] terminal = new int[n]; // -1 if leads to 1-cycle, else index of first b=0 node

            final int[] stack = new int[n];
            int top = 0;

            for (int i = 0; i < n; i++) {
                if (state[i] != 0) continue;
                int v = i;
                while (true) {
                    stack[top++] = v;
                    state[v] = 1;
                    if (isV[v]) {
                        final int nx = next[v];
                        if (state[nx] == 0) {
                            v = nx;
                            continue;
                        } else if (state[nx] == 1) {
                            // found a cycle via only "villager" edges -> all in stack lead to cycle, all can be villager
                            while (top > 0) {
                                final int w = stack[--top];
                                can[w] = true;
                                terminal[w] = -1;
                                state[w] = 2;
                            }
                            break;
                        } else { // state[nx] == 2, next already processed
                            final int u = terminal[nx];
                            if (u == -1) {
                                // next leads to 1-cycle -> all in current stack can be villager
                                while (top > 0) {
                                    final int w = stack[--top];
                                    can[w] = true;
                                    terminal[w] = -1;
                                    state[w] = 2;
                                }
                                break;
                            } else {
                                final int X = next[u]; // x[u]
                                final boolean tailHasX = !can[nx]; // if next cannot be villager, X is in its tail
                                boolean prefixHasX = false;
                                while (top > 0) {
                                    final int w = stack[--top];
                                    if (w == X) prefixHasX = true;
                                    final boolean forcedWerewolf = tailHasX || prefixHasX;
                                    can[w] = !forcedWerewolf;
                                    terminal[w] = u;
                                    state[w] = 2;
                                }
                                break;
                            }
                        }
                    } else {
                        // current node says "werewolf" -> terminal reached
                        final int u = v;
                        final int X = next[u];
                        boolean seenX = false;
                        while (top > 0) {
                            final int w = stack[--top];
                            if (w == X) seenX = true;
                            can[w] = !seenX;   // if suffix (w..u) contains X, starting at w causes conflict
                            terminal[w] = u;
                            state[w] = 2;
                        }
                        break;
                    }
                }
            }

            int cntAlwaysWerewolf = 0;
            for (int i = 0; i < n; i++) {
                if (!can[i]) cntAlwaysWerewolf++;
            }
            out.add(cntAlwaysWerewolf);
        }
        return out;
    }

    // output: print results (accept integers)
    public static void output(final Iterable<Integer> lines) {
        final var sb = new StringBuilder();
        for (final Integer v : lines) {
            sb.append(v).append('\n');
        }
        System.out.print(sb);
    }

    public static void main(final String[] args) throws IOException {
        output(cal(reader()));
    }

    // fast scanner
    private final static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                final String line = br.readLine();
                if (line == null) return "";
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
