// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public final class Main {
    private static final class one {
        private final List<Integer> moneys;

        public one(List<Integer> moneys) {
            this.moneys = moneys;
        }
    }

    public static List<one> read() {
        final var input = new Scanner(System.in);
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 10));
        final List<one> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            assert ((1 <= n) && (n <= 300_000));
            final List<Integer> moneys = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                final int money = input.nextInt();
                assert ((0 <= money) & (money <= 300_000));
                moneys.add(money);
            }
            cases.add(new one(moneys));
        }
        return cases;
    }

    public static List<one> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 10));
        final List<one> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            assert ((1 <= n) && (n <= 300_000));
            final List<Integer> moneys = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                final int money = input.nextInt();
                assert ((0 <= money) & (money <= 300_000));
                moneys.add(money);
            }
            cases.add(new one(moneys));
        }
        return cases;
    }


    public static List<List<Integer>> cal(List<one> nums) {
        final List<List<Integer>> results = nums.stream()
            .map(one -> {
                final int n = one.moneys.size();
                final List<Integer> vals = new ArrayList<>((n + 1) / 2);
                final PriorityQueue<Integer> min = new PriorityQueue<>(n);
                final PriorityQueue<Integer> max = new PriorityQueue<>(n, Comparator.reverseOrder());
                min.add(one.moneys.get(0));
                vals.add(one.moneys.get(0));
                for (int i = 1; i < n; i++) {
                    final int money = one.moneys.get(i);
                    final int minSize = min.size();
                    final int maxSize = max.size();
                    if (money > min.peek()) {
                        min.add(money);
                        if (minSize > maxSize) {
                            final int value = min.poll();
                            max.add(value);
                        }
                    } else {
                        max.add(money);
                        if (minSize == maxSize) {
                            final int value = max.poll();
                            min.add(value);
                        }
                    }
                    if (i % 2 == 0) {
                        vals.add(min.peek());
                    }
                }
                return vals;
            })
            .collect(Collectors.toList());
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
            for (int x : decide) {
                builder.append(x).append(" ");
            }
            builder.setCharAt(builder.length() - 1, '\n');
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
