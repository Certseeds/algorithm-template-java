// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public final class Main {
    private static final class one {
        private final List<Integer> fst;
        private final List<Integer> snd;

        public one(List<Integer> fst, List<Integer> snd) {
            this.fst = fst;
            this.snd = snd;
        }
    }

    public static List<one> read() {
        final var input = new Scanner(System.in);
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 15));
        final List<one> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= n) && (n <= 100_000));
            assert ((1 <= m) && (m <= 100_000));
            final List<Integer> fst = new ArrayList<>(n); // 还想优化这里可以用裸数组
            final List<Integer> snd = new ArrayList<>(m);
            for (int j = 0; j < n; j++) {
                final int number = input.nextInt();
                assert ((1 <= number) && (number <= 1_000_000_000));
                fst.add(number);
            }
            for (int j = 0; j < m; j++) {
                final int number = input.nextInt();
                assert ((1 <= number) && (number <= 1_000_000_000));
                snd.add(number);
            }
            cases.add(new one(fst, snd));
        }
        return cases;
    }

    public static List<one> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 15));
        final List<one> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= n) && (n <= 100_000));
            assert ((1 <= m) && (m <= 100_000));
            final List<Integer> fst = new ArrayList<>(n);
            final List<Integer> snd = new ArrayList<>(m);
            for (int j = 0; j < n; j++) {
                final int number = input.nextInt();
                assert ((1 <= number) && (number <= 1_000_000_000));
                fst.add(number);
            }
            for (int j = 0; j < m; j++) {
                final int number = input.nextInt();
                assert ((1 <= number) && (number <= 1_000_000_000));
                snd.add(number);
            }
            cases.add(new one(fst, snd));
        }
        return cases;
    }

    public static List<List<Integer>> cal(List<one> nums) {
        final List<List<Integer>> results = nums.stream()
            .map(one -> {
                final var fst = one.fst;
                final var snd = one.snd;
                final int length1 = fst.size(), length2 = snd.size();
                final List<Integer> result = new ArrayList<>(length1 + length2);
                int x = 0, y = 0;
                while (x < length1 && y < length2) {
                    if (fst.get(x) < snd.get(y)) {
                        result.add(fst.get(x));
                        x++;
                    } else if (fst.get(x) > snd.get(y)) {
                        result.add(snd.get(y));
                        y++;
                    } else {
                        result.add(fst.get(x));
                        result.add(snd.get(y));
                        x++;
                        y++;
                    }
                }
                for (; x < length1; x++) {result.add(fst.get(x));}
                for (; y < length2; y++) {result.add(snd.get(y));}
                return result;
            })
            .collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(List<List<Integer>> decides) {
        for (var decide : decides) {
            final StringBuilder builder = new StringBuilder();
            for (int i = 1; i < decide.size(); i++) {
                builder.append(decide.get(i - 1)).append(" ");
            }
            builder.append(decide.get(decide.size() - 1)).append("\n");
            System.out.print(builder);
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
