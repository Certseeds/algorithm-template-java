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
    private static final long mod = 1000000007L;

    public static List<Integer> read() {
        final var input = new Scanner(System.in);
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final var cases = new ArrayList<Integer>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            assert ((1 <= n) && (n <= 1000));
            cases.add(n);
        }
        return cases;
    }

    public static List<Integer> reader() throws IOException {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final var cases = new ArrayList<Integer>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            assert ((1 <= n) && (n <= 1000));
            cases.add(n);
        }
        return cases;
    }

    /**
     * 其实可以打表...
     */
    public static List<Long> cal(List<Integer> nums) {
        final List<Long> memory = new ArrayList<>(1001);
        // whatever it is, 8-bytes is enough
        // in most cases no need to use Int for save memory
        memory.add(0L);
        for (int i = 1; i <= 1000; i++) {
            memory.add((memory.get(i - 1) * 3 + 2) % mod);
        }
        final List<Long> results = nums.stream()
            .map(memory::get)
            .collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    /**
     * 输出
     * For each test case, print the minimum number of moves you need to do when there are n disks on the A rod initially.
     */
    public static void output(Iterable<Long> nums) {
        nums.forEach(x -> System.out.print(x + "\n"));
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
