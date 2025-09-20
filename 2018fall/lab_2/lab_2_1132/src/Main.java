// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * (You can also choose a light sword twice if the condition is satisfied for the sheik can get another same one from the craftsman in the forest.)
 * this sentence make no sense, because the input `3 6 1 2 3` had a result: 0, which means the sword 3 can not get another 3 to combine into 6
 */
public final class Main {
    private static final class one {
        private final List<Integer> as;
        private final int m;

        public one(List<Integer> as, int m) {
            this.as = as;
            this.m = m;
        }
    }

    public static List<one> read() {
        final var input = new Scanner(System.in);
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final var cases = new ArrayList<one>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= n) && (n <= 5_000));
            assert ((1 <= m) && (m <= 1000_000_00));
            final var numbers = new ArrayList<Integer>(n);
            for (int j = 0; j < n; j++) {
                final int a = input.nextInt();
                assert ((1 <= a) && (a <= 1000_000));
                numbers.add(a);
            }
            cases.add(new one(numbers, m));
        }
        return cases;
    }

    public static List<one> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final var cases = new ArrayList<one>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= n) && (n <= 5_000));
            assert ((1 <= m) && (m <= 1000_000_00));
            final var numbers = new ArrayList<Integer>(n);
            for (int j = 0; j < n; j++) {
                final int a = input.nextInt();
                assert ((1 <= a) && (a <= 1000_000));
                numbers.add(a);
            }
            cases.add(new one(numbers, m));
        }
        return cases;
    }

    public static List<Integer> cal2(List<one> nums) {
        final List<Integer> results = nums.stream()
            .map(one -> {
                final Set<Integer> set = new HashSet<>(one.as);
                int x = 0;
                for (int a : one.as) {
                    if (set.contains(one.m - a)) {
                        x += 1;
                    }
                }
                return x;
            })
            .collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static List<Integer> cal(List<one> nums) {
        final List<Integer> results = nums.stream()
            .map(one -> {
                int x = 0;
                for (int a : one.as) {
                    for (int begin = 0, end = one.as.size(); begin < end; ) { // 大家都是先写while,改写成for
                        final int another = one.m - a;
                        final int mid = (end - begin) / 2 + begin;
                        final int value = one.as.get(mid);
                        // refer from bisect_left in python stdlib
                        if (value > another) {
                            end = mid;
                        } else if (value < another) {
                            begin = mid + 1;
                        } else {
                            x += 1;
                            break;
                        }
                    }
                }
                return x;
            })
            .collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(Iterable<Integer> decides) {
        for (int decide : decides) {
            System.out.print(decide / 2 + "\n");
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
