// SPDX-License-Identifier: Apache-2.0 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

// ref <https://acm.sustech.edu.cn/onlinejudge/problem.php?id=1134>
public final class Main {

    private static final class one {
        private final List<Double> location;
        private final List<Double> factors;

        public one(List<Double> location, List<Double> factors) {
            this.location = location;
            this.factors = factors;
        }
    }
    public static List<one> reader() {
        final var input = new Reader();
        final List<one> cases = new ArrayList<>();
        final int testcases = input.nextInt();
        assert ((0 <= testcases) && (testcases <= 20));
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            final List<Double> locations = new ArrayList<>(n);
            final List<Double> factors = new ArrayList<>(n);
            assert ((1 <= n) && (n <= 50_000));
            for (int j = 0; j < n; j++) {
                final double x_i = input.nextDouble();
                final double w_i = input.nextDouble();
                assert ((-1_000_000 <= x_i) && (x_i < 1_000_000));
                assert ((0 < w_i) && (w_i < 15));
                locations.add(x_i);
                factors.add(w_i);
            }
            cases.add(new one(locations, factors));
        }
        return cases;
    }

    public static double calculus(double P, List<Double> kids, List<Double> kidsWeight) {
        final int lengthOfKids = kids.size();
        double minimum = 0;
        for (int i = 0; i < lengthOfKids; i++) {
            minimum += Math.pow(Math.abs(kids.get(i) - P), 3) * kidsWeight.get(i);
        }
        return minimum;
    }

    private static final double eps = 0.000_000_000_000_1;

    public static List<Long> cal(List<one> nums) {
        final List<Long> results = nums.stream().map(
            one -> {
                double begin = Collections.min(one.location), end = Collections.max(one.location);
                while (Math.abs(end - begin) > eps) {
                    final double distance = (end - begin) / 3;
                    final double left = begin + distance, right = end - distance;
                    final double leftValue = calculus(left, one.location, one.factors),
                        rightValue = calculus(right, one.location, one.factors);
                    if (leftValue - rightValue > eps) {
                        begin = left+eps;
                    } else {
                        end = right;
                    }
                }
                return (long) (calculus(begin, one.location, one.factors) + 0.5); // what we need is result, not the begin value
                // doing something
            }).collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(List<Long> decides) {
        for (int i = 1; i <= decides.size(); i++) {
            System.out.printf("Case #%d: %d\n", i, decides.get(i - 1));
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
