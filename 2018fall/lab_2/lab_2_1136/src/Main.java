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

// ref <https://acm.sustech.edu.cn/onlinejudge/problem.php?id=1136>
public final class Main {

    private static final class one {
        private final int length;
        private final List<Integer> positions;

        private final int racer;

        public one(int length, List<Integer> positions, int racer) {
            this.length = length;
            this.positions = positions;
            this.racer = racer;
        }
    }

    public static List<one> read() {
        final var input = new Scanner(System.in);
        final List<one> cases = new ArrayList<>();
        while (input.hasNext()) {
            final int l = input.nextInt();
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= l) && (l <= 1_000_000_000));
            assert ((1 <= n) && (n <= 500_000));
            assert ((1 <= m) && (m <= n + 1));
            final var posies = new ArrayList<Integer>(n + 2);
            posies.add(0);
            for (int i = 0; i < n; i++) {
                final int r = input.nextInt();
                assert ((0 <= r) && (r <= l)); // but do not obey r_i < r_{i+1}
                posies.add(r);
            }
            posies.add(l);
            posies.sort(Integer::compareTo);
            cases.add(new one(l, posies, m));
        }
        return cases;
    }

    public static List<one> reader() {
        final var input = new Reader();
        final List<one> cases = new ArrayList<>();
        while (input.hasNext()) {
            final int l = input.nextInt();
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= l) && (l <= 1_000_000_000));
            assert ((1 <= n) && (n <= 500_000));
            assert ((1 <= m) && (m <= n + 1));
            final var posies = new ArrayList<Integer>(n);
            posies.add(0);
            for (int i = 0; i < n; i++) {
                final int r = input.nextInt();
                assert ((0 <= r) && (r <= l));  // but do not obey r_i < r_{i+1}
                posies.add(r);
            }
            posies.add(l);
            posies.sort(Integer::compareTo);
            cases.add(new one(l, posies, m));
        }
        return cases;
    }

    // O(n)
    public static int cal(one nums, int stepLength) {
        final int n = nums.positions.size();
        final int m = nums.racer;
        int nowPosition = 1;
        for (int i = 0; i < m; i++) {
            for (int length = stepLength; length > 0 && nowPosition < n; ) {
                final int distance = nums.positions.get(nowPosition) - nums.positions.get(nowPosition - 1);
                if (length >= distance) {
                    length -= distance;
                    nowPosition++;
                } else {
                    break;
                }
            }
        }
        if (nowPosition < n) {
            return -1;
        } else if (nowPosition == n) {
            return 1;
        }
        return 0x3f3f; // not reach there
    }

    public static List<Integer> cal(List<one> nums) {
        final List<Integer> results = nums.stream().map(
            one -> {
                int begin = 1, end = one.length;
                while (begin < end) { // 还是bisect
                    final int mid = (end - begin) / 2 + begin;
                    final int result = cal(one, mid);
                    // 可以认为是在[begin,end)间, 只有两个值， 一个-1，一个1, 要寻找的是最左边的一个1
                    if (result == -1) {
                        begin = mid + 1;
                    } else if (result == 1) {
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

    /**
     * Output
     * <p>
     * For each case, output an integer standing for the meters one athlete should at most to run.
     */
    public static void output(Iterable<Integer> decides) {
        for (var decide : decides) {
            System.out.printf("%d\n", decide);
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
