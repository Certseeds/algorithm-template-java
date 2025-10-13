// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    public static List<List<Integer>> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 10));
        final List<List<Integer>> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int n = input.nextInt();
            assert ((2 <= n) && (n <= 20_0000));
            final List<Integer> nes = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                final int num = input.nextInt();
                assert ((1 <= num) && (num <= 10_0000));
                nes.add(num);
            }
            cases.add(nes);
        }
        return cases;
    }

    public static List<Integer> cal(List<List<Integer>> Integeres) {
        final List<Integer> builders = new ArrayList<>(Integeres.size());
        for (var list : Integeres) {
            final int nums1 = list.get(0), nums2 = list.get(1);
            int diff = nums1 - nums2;
            for (int i = 2, maxValue = Math.max(nums1, nums2); i < list.size(); i++) {
                final int value = list.get(i);
                diff = Math.max(diff, maxValue - value);
                if (value > maxValue) {
                    maxValue = value;
                }
            }
            builders.add(diff);
        }
        return builders;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(Iterable<Integer> nums) {
        for (var num : nums) {
            System.out.printf("%d\n", num);
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
