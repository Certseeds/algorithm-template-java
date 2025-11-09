// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {
    private static final class one {
        private final int a, b, c;
        private final int n, m;

        public one(int a, int b, int c, int n, int m) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.n = n;
            this.m = m;
        }
    }

    public static List<one> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final List<one> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int a = input.nextInt(), b = input.nextInt(), c = input.nextInt();
            final int n = input.nextInt(), m = input.nextInt();
            assert ((1 <= a) && (a <= 100_000_000));
            assert ((1 <= b) && (b <= 100_000_000));
            assert ((1 <= c) && (c <= 100_000_000));
            assert ((1 <= n) && (n <= 100_000_000));
            assert ((1 <= m) && (m <= 100_000_000));
            // java没有宏,不太方便在循环里assert
            cases.add(new one(a, b, c, n, m));
        }
        return cases;
    }

    public static Boolean test(int length1, int length2, int real1, int real2) {
        if (length1 >= real1 && length2 >= real2) {
            return true;
        } else return length2 >= real1 && length1 >= real2;
    }

    public static List<Boolean> cal(List<one> cases) {
        final List<Boolean> builders = new ArrayList<>(cases.size());
        for (var one : cases) {
            final int a = one.a, b = one.b, c = one.c;
            final int n = one.n, m = one.m;
            final int a1 = 2 * a + 2 * b,
                a2 = 2 * b + 2 * c,
                a3 = 2 * c + 2 * a,
                b1 = a + 2 * b,
                b2 = b + 2 * c,
                b3 = c + 2 * a,
                b4 = a + 2 * c,
                b5 = b + 2 * a,
                b6 = c + 2 * b,
                c1 = a + b + c,
                d1 = c1 + a,
                d2 = c1 + b,
                d3 = c1 + c,
                e1 = a + b,
                e2 = b + c,
                e3 = c + a,
                f1 = e1 + 3 * c,
                f2 = e2 + 3 * a,
                f3 = e3 + 3 * b;
            final List<Map.Entry<Integer, Integer>> items = List.of(Map.entry(a1, b3)
                , Map.entry(a1, b6)
                , Map.entry(a2, b1)
                , Map.entry(a2, b4)
                , Map.entry(a3, b2)
                , Map.entry(a3, b5)
                , Map.entry(a1, c1)
                , Map.entry(a2, c1)
                , Map.entry(a3, c1)
                , Map.entry(d1, c1)
                , Map.entry(d1, b2)
                , Map.entry(d1, b6)
                , Map.entry(d2, c1)
                , Map.entry(d2, b3)
                , Map.entry(d2, b4)
                , Map.entry(d3, c1)
                , Map.entry(d3, b1)
                , Map.entry(d3, b5)
                , Map.entry(e1, f1)
                , Map.entry(e2, f2)
                , Map.entry(e3, f3)
            );
            builders.add(items.stream().anyMatch(entry -> test(n, m, entry.getKey(), entry.getValue())));
        }
        return builders;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    /**
     * For each test case, print the maximum number of adjacent beautiful pairs you can find.
     */
    public static void output(Iterable<Boolean> nums) {
        for (boolean state : nums) {
            if (state) {
                System.out.print("Yes\n");
            } else {
                System.out.print("No\n");
            }
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
