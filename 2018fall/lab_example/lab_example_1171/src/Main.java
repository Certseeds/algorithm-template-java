// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {
    public static List<String> read() {
        final var input = new Scanner(System.in);
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 10));
        final List<String> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final String str = input.next();
            assert (str.length() <= 10_000);
            // java没有宏,不太方便在循环里assert
            cases.add(str);
        }
        return cases;
    }

    public static List<String> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 10));
        final List<String> cases = new ArrayList<>(testcases);
        for (int i = 0; i < testcases; i++) {
            final String str = input.next();
            assert (str.length() <= 10_000);
            cases.add(str);
        }
        return cases;
    }

    private static final Set<Character> uncons = Set.of('a', 'e', 'i', 'o', 'u', 'w', 'y');

    private static final int[] chars_map = new int[]{
        0x3f3f, // a
        0,
        1,
        2,
        0x3f3f, // e
        3,
        4,
        5,
        0x3f3f, // i
        6,
        7,
        8,
        9,
        10,
        0x3f3f, // o
        11,
        12,
        13,
        14,
        15,
        0x3f3f, //u
        16,
        0x3f3f, // w
        17,
        0x3f3f, // y
        18,
    };

    public static List<Integer> cal(List<String> strings) {
        final List<Integer> builders = new ArrayList<>(strings.size());
        for (var str : strings) {
            final int[][] map = new int[19][19];
            for (var line : map) {
                Arrays.fill(line, 0);
            }
            for (int i = 0; i < str.length() - 1; i++) {
                final char c1 = str.charAt(i), c2 = str.charAt(i + 1);
                if (uncons.contains(c1) || uncons.contains(c2)) {
                    continue;
                }
                final int index1 = chars_map[c1 - 'a'], index2 = chars_map[c2 - 'a'];
                assert ((0 <= index1) && (index1 <= 18));
                assert ((0 <= index2) && (index2 <= 18));
                map[index1][index2] += 1;
            }
            for (int i = 0; i < 19; i++) {
                map[i][i] = 0; // 必须是两个不同的
            }
            // init finish
            // 之后好像也没什么好方法, 暴力枚举一遍呗
            final int x = 524288; // 2^19
            int maxValue = 0;
            for (int i = 0; i < x; i++) {
                int sum = 0;
                for (int j = 0; j < 19; j++) {
                    if ((i & (1 << j)) != 0) {
                        for (int k = 0; k < 19; k++) {
                            sum += map[j][k];
                            sum += map[k][j];
                            map[j][k] *= -1;
                            map[k][j] *= -1;
                        }
                    }
                }
                maxValue = Math.max(maxValue, sum);
                for (int j = 0; j < 19; j++) {
                    for (int k = 0; k < 19; k++) {
                        map[j][k] = Math.abs(map[j][k]);
                    }
                }
            }
            builders.add(maxValue);
        }
        return builders;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    /**
     * For each test case, print the maximum number of adjacent beautiful pairs you can find.
     */
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
