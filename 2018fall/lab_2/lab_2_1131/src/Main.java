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
    private static final class single {
        private final int m;
        private final List<Integer> deamons;

        public single(int m, List<Integer> deamons) {
            this.m = m;
            this.deamons = deamons;
        }
    }

    public static List<single> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((2 <= testcases) && (testcases <= 1000));
        final var cases = new ArrayList<single>();
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            assert ((1 <= n) && (n <= 1000_000));
            final int m = input.nextInt();// know nothing about range, only know it's int
            final List<Integer> algorithms = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                algorithms.add(input.nextInt());
            }
            cases.add(new single(m, algorithms));
        }
        return cases;
    }

    public static List<Boolean> cal(List<single> nums) {
        final List<Boolean> results = nums.stream()
            .map(one -> {
                for (int begin = 0, end = one.deamons.size(); begin < end; ) { // range should be [first,last)
                    final int mid = (end - begin) / 2 + begin; // no overflow
                    if (one.deamons.get(mid) == one.m) { // match
                        return true;
                    } else if (one.deamons.get(mid) < one.m) {
                        begin = mid + 1;
                    } else {
                        end = mid;
                    }
                }
                return false;
            })
            .collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(Iterable<Boolean> decides) {
        for (final var decide : decides) {
            if (decide) {
                System.out.print("YES\n");
            } else {
                System.out.print("NO\n");
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
