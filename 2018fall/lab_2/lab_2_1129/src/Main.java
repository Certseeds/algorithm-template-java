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
    // f'(0) = -2y, 说明肯定是先减
    // f'(100) = 5*10^14 + ... - 200y(<= 2*10^12) > 0,到最后递增
    // find where f'(x) equals to 0
    private static final class funcer {
        public funcer(double y) {
            this.y = y;
        }

        private final double y;

        public void setPosi(double posi) {
            this.posi = posi;
        }

        private double posi = 0;

        public double level0() {
            final double x = posi;
            assert ((0 <= x) && (x <= 100));
            return ((((5 * x + 6) * x * x * x + 3) * x + 4) * x - 2 * y) * x;
        }

        public double level1(double x) {
            assert ((0 <= x) && (x <= 100));
            return (((35 * x + 36) * x * x * x + 9) * x + 8) * x - 2 * y;
        }

    }

    public static List<funcer> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final var cases = new ArrayList<funcer>(testcases);
        for (int i = 0; i < testcases; i++) {
            final double number = input.nextDouble();
            assert ((0 < number) && (number < Math.pow(10, 10)));
            cases.add(new funcer(number));
        }
        return cases;
    }

    private static final double minidiff = 0.0000_0000_0000_0000_1d; // step should smaller enough
    private static final double fivezero = 0.000_01d; // but just need five or six

    public static List<funcer> cal(List<funcer> nums) {
        final List<funcer> results = nums.stream()
            .map(one -> {
                for (double begin = 0, end = 100; Math.abs(begin - end) > minidiff; ) {
                    final double mid = (end - begin) / 2 + begin;
                    final double value = one.level1(mid);
                    if (Math.abs(value) < fivezero) {
                        one.setPosi(mid);
                        return one;
                    } else if (value > 0) {
                        end = mid - minidiff; // mid is too big
                    } else {
                        begin = mid + minidiff; // mid is too small
                    }
                }
                return one;
            })
            .collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(List<funcer> decides) {
        for (int i = 1; i <= decides.size(); i++) {
            System.out.printf("Case %d: %.4f\n", i, decides.get(i - 1).level0());
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

        double nextDouble() {return Double.parseDouble(next());}
    }
}
