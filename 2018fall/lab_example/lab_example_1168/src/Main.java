// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

// thanks <https://www.zhihu.com/column/p/30377770>
// thanks <https://www.cnblogs.com/lfri/p/9899014.html>
// this problem refactor from `Chomp Game`
// this problem do not have a easy analytical-solution, but have a simple answer
// PS: 取而不告, 是为?
public final class Main {
    // java不能有多返回值, 确实很烦
    public static List<Map.Entry<Integer, Integer>> read() {
        final var input = new Scanner(System.in);
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final var cases = new ArrayList<Map.Entry<Integer, Integer>>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= n) && (n <= 1000));
            assert ((1 <= m) && (m <= 1000));
            cases.add(new AbstractMap.SimpleImmutableEntry<>(n, m));
        }
        return cases;
    }

    public static List<Map.Entry<Integer, Integer>> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final var cases = new ArrayList<Map.Entry<Integer, Integer>>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            final int m = input.nextInt();
            assert ((1 <= n) && (n <= 1000));
            assert ((1 <= m) && (m <= 1000));
            cases.add(new AbstractMap.SimpleImmutableEntry<>(n, m));
        }
        return cases;
    }

    public static List<Boolean> cal(List<Map.Entry<Integer, Integer>> nums) {
        return nums.stream().map(entry -> entry.getKey() + entry.getValue())
            .map(x -> x == 2)
            .collect(Collectors.toUnmodifiableList());
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(Iterable<Boolean> nums) {
        for (var isBob : nums) {
            if (isBob) {
                System.out.print("Bob\n");
            } else {
                System.out.print("Alice\n");
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
