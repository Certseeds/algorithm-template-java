// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public final class Main {
    private static final class one {
        private final List<Map.Entry<Integer, Integer>> fst;
        private final List<Map.Entry<Integer, Integer>> snd;

        public one(List<Map.Entry<Integer, Integer>> fst, List<Map.Entry<Integer, Integer>> snd) {
            this.fst = fst;
            this.snd = snd;
        }
    }

    public static List<one> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final List<one> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            assert ((0 <= n) && (n <= 1_000));
            final List<Map.Entry<Integer, Integer>> fst = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                final int coefficient = input.nextInt();
                final int exponent = input.nextInt();
                assert ((-10000 <= coefficient) && (coefficient <= 10000));
                assert ((0 <= exponent) && (exponent <= 1000_000_000));
                fst.add(new AbstractMap.SimpleImmutableEntry<>(coefficient, exponent));
            }
            final int m = input.nextInt();
            assert ((0 <= m) && (m <= 1_000));
            final List<Map.Entry<Integer, Integer>> snd = new ArrayList<>(m);
            for (int j = 0; j < m; j++) {
                final int coefficient = input.nextInt();
                final int exponent = input.nextInt();
                assert ((-10000 <= coefficient) && (coefficient <= 10000));
                assert ((0 <= exponent) && (exponent <= 1000_000_000));
                snd.add(new AbstractMap.SimpleImmutableEntry<>(coefficient, exponent));
            }
            cases.add(new one(fst, snd));
        }
        return cases;
    }

    public static List<List<Map.Entry<Long, Long>>> cal(List<one> nums) {
        final List<List<Map.Entry<Long, Long>>> results = nums.stream()
            .map(one -> {
                final var fst = one.fst;
                final var snd = one.snd;
                final Map<Long, Long> map = new HashMap<>(fst.size() + snd.size());
                for (var entry : fst) {
                    final long coefficient = entry.getKey(); //系数, 2x^3的2
                    final long exponent = entry.getValue();  //指数, 2x^3的3
                    map.merge(exponent, coefficient, Long::sum); // copilot find exponent should be the key...
                }
                for (var entry : snd) {
                    final long coefficient = entry.getKey();
                    final long exponent = entry.getValue();
                    map.merge(exponent, coefficient, Long::sum); // copilot find exponent should be the key...
                }
                // 其实并不需要排序, 排序是为了之后O(n)的归并, 用了map之后可以高效合并, 所以不需要了.
                final List<Map.Entry<Long, Long>> outputList = map.entrySet().stream()
                    .filter(entry -> entry.getValue() != 0) // 显然不需要使出0, 0x,0x^2这种东西
                    .sorted(Comparator.comparingLong(Map.Entry::getKey)) // 按指数排序
                    .collect(Collectors.toUnmodifiableList());
                //
                return outputList;
            })
            .collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    private static void putResultToStr(Map.Entry<Long, Long> entry, StringBuilder builder) {
        final long expon = entry.getKey();
        final long coeff = entry.getValue();  //系数, 2x^3的2
        assert (0 != coeff);
        assert (0 <= expon);
        if (coeff > 0) {
            builder.append("+");
        }
        //采用正确处理措施以及预处理之后,逻辑简化了
        if (expon == 0) { // 特殊情况, 1,2,3 ...
            builder.append(coeff);
        } else if (expon == 1) {
            if (coeff == 1) { // x
                builder.append("x");
            } else if (coeff == -1) { // -x
                builder.append("-x");
            } else { // -2x 2x 3x -5x
                builder.append(coeff).append("x");
            }
        } else {
            if (coeff == 1) { // x^2
                builder.append("x^").append(expon);
            } else if (coeff == -1) { // -x^2
                builder.append("-x^").append(expon);
            } else { // -2x^2 2x^2 3x^2 -5x^2
                builder.append(coeff).append("x^").append(expon);
            }
        }
    }

    public static void output(List<List<Map.Entry<Long, Long>>> decides) {
        for (var decide : decides) {
            if (decide.isEmpty()) {
                System.out.print("0\n");
                continue;
            }
            final StringBuilder builder = new StringBuilder();
            for (var entry : decide) {
                putResultToStr(entry, builder);
            }
            builder.append("\n");
            if (builder.charAt(0) == '+') {
                final String result = builder.substring(1); // 要是有string_view就好了
                System.out.print(result);
            } else {
                System.out.print(builder);
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
    }
}
