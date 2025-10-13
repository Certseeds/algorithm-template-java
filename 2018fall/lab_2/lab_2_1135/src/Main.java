// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ref <https://acm.sustech.edu.cn/onlinejudge/problem.php?id=1134>

public final class Main {

    private static final class one {
        private final List<Map.Entry<Integer, Integer>> classes;
        private final int giveup;
        private final int classNum; // cache

        public one(List<Integer> credits, List<Integer> scores, int giveup) {
            classNum = scores.size();
            assert (scores.size() == credits.size());
            classes = Stream.iterate(0, x -> x + 1)
                .limit(classNum)
                .map(x -> new AbstractMap.SimpleImmutableEntry<>(credits.get(x), scores.get(x)))
                // .sorted(Map.Entry.<Integer, Integer>comparingByValue(Comparator.reverseOrder()).thenComparing(Map.Entry.comparingByKey()))
                // 不能预先排序的问题在于, 大于score的一边需要按comparingByKey从大到小排,小于的另外一边需要从小到大排序.
                .collect(Collectors.toUnmodifiableList());
            this.giveup = giveup;
        }
    }

    public static List<one> reader() {
        final var input = new Reader();
        final List<one> cases = new ArrayList<>();
        while (input.hasNext()) {
            final int n = input.nextInt();
            final int k = input.nextInt();
            assert ((0 <= n) && (n <= 500_000));
            assert ((0 <= k) && (k <= n));
            final List<Integer> credits = new ArrayList<>(n);
            final List<Integer> scores = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                final int si = input.nextInt();// s[i] is 学分
                assert ((1 <= si) && (si <= 1_000));
                credits.add(si);
            }
            for (int i = 0; i < n; i++) {
                final int ci = input.nextInt(); // ci is成绩
                assert ((1 <= ci) && (ci <= 1_000));
                scores.add(ci);
            }
            cases.add(new one(credits, scores, k));
        }
        return cases;
    }


    private static boolean judgement(one oneCase, double credits) {
        final double sums = oneCase.classes.stream()
            .map(x -> (x.getValue() - credits) * x.getKey())
            .sorted(Comparator.reverseOrder())
            .mapToDouble(x -> x)
            .limit(oneCase.classNum - oneCase.giveup)
            .sum();
        return sums > 0;
    }

    private static final double eps = 0.0001; // 10^-4 is enough, ^-12 also not tle

    public static List<Double> cal(List<one> nums) {
        final List<Double> results = nums.stream().map(
            one -> {
                assert (!one.classes.isEmpty());
                double begin = one.classes.stream()
                    .mapToDouble(x -> x.getKey() * x.getValue())
                    .sum() / one.classes.stream()
                    .mapToDouble(Map.Entry::getKey)
                    .sum(); // should not smaller than do not delete
                for (double end = one.classes.stream().mapToDouble(Map.Entry::getValue).max().getAsDouble();
                     Math.abs(end - begin) > eps; ) {
                    final double mid = (end - begin) / 2 + begin;
                    if (judgement(one, mid)) {
                        begin = mid;
                    } else {
                        end = mid;
                    }
                }
                return begin;
            }).collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(Iterable<Double> decides) {
        for (double decide : decides) {
            System.out.printf("%.3f\n", decide);
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
