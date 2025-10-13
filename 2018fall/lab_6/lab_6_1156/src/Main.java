// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {

    public static final class TestCase {
        public final int m;
        public final int n;
        public final int[] shops;

        public TestCase(int m, int n, int[] shops) {
            this.m = m;
            this.n = n;
            this.shops = shops;
        }
    }

    // reader: parse input into TestCase objects
    public static List<TestCase> reader() {
        final var in = new Reader();
        final int T = in.nextInt();
        assert ((1 <= T) && (T <= 10));
        final List<TestCase> tests = new ArrayList<>(T);
        for (int tc = 0; tc < T; tc++) {
            final int M = in.nextInt();
            final int N = in.nextInt();
            assert ((0 <= M) && (M <= 50000));
            assert ((1 <= N) && (N <= 100000));
            final int[] shops = new int[N];
            for (int i = 0; i < N; i++) shops[i] = in.nextInt();
            tests.add(new TestCase(M, N, shops));
        }
        return tests;
    }

    // cal: simulate and return outputs
    public static List<String> cal(final List<TestCase> inputs) {
        final List<String> out = new ArrayList<>();
        for (final var tc : inputs) {
            final int M = tc.m;
            final int N = tc.n;
            final int[] shops = tc.shops;
            if (M == 0) {
                // pocket cannot hold anything, each new distinct gift causes a discard if any space is needed
                // but since he cannot buy at all, every time sees a gift not in pocket and pocket full (M==0),
                // he would discard none from pocket because pocket empty—interpretation: pocket size 0 means never store, so discarded count 0.
                // Following problem intent, treat M==0 as never storing so discard count 0.
                out.add("0");
                continue;
            }

            // pocket entry
            final class Entry {
                final int id;
                int L;
                final long time;

                Entry(int id, int L, long time) {
                    this.id = id;
                    this.L = L;
                    this.time = time;
                }
            }

            final Map<Integer, Entry> inPocket = new HashMap<>();
            final TreeSet<Entry> set = new TreeSet<>((a, b) -> {
                if (a.L != b.L) return Integer.compare(b.L, a.L); // larger L first
                if (a.time != b.time) return Long.compare(a.time, b.time); // earlier time first
                return Integer.compare(a.id, b.id);
            });

            long timer = 0L;
            int discarded = 0;

            for (int i = 0; i < N; i++) {
                final int k = shops[i];
                final Entry cur = inPocket.get(k);
                if (cur != null) {
                    // increment L
                    set.remove(cur);
                    cur.L = cur.L + 1;
                    set.add(cur);
                } else {
                    // not in pocket
                    if (inPocket.size() < M) {
                        final Entry e = new Entry(k, 1, timer++);
                        inPocket.put(k, e);
                        set.add(e);
                    } else {
                        // pocket full: evict one by rule
                        final Entry victim = set.pollFirst();
                        if (victim != null) {
                            inPocket.remove(victim.id);
                            discarded++;
                        }
                        // insert new gift
                        final Entry e = new Entry(k, 1, timer++);
                        inPocket.put(k, e);
                        set.add(e);
                    }
                }
            }
            out.add(String.valueOf(discarded));
        }
        return out;
    }

    // output
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
