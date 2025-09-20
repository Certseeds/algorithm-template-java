// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {
    private static final char[][] smallest = new char[][]{
        {' ', ' ', '+', '-', '+'},
        {' ', '/', '.', '/', '|'},
        {'+', '-', '+', '.', '+'},
        {'|', '.', '|', '/', ' '},
        {'+', '-', '+', ' ', ' '},
    };

    private static final class box {
        private final int a;
        private final int b;
        private final int c;

        public box(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public static List<box> read() {
        final var input = new Scanner(System.in);
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final var cases = new ArrayList<box>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int a = input.nextInt(), b = input.nextInt(), c = input.nextInt();
            assert ((1 <= a) && (a <= 30));
            assert ((1 <= b) && (b <= 30));
            assert ((1 <= c) && (c <= 30));
            cases.add(new box(a, b, c));
        }
        return cases;
    }

    public static List<box> reader() {
        final var input = new Reader();
        final int testcases = input.nextInt();
        assert ((1 <= testcases) && (testcases <= 100));
        final var cases = new ArrayList<box>(testcases);
        for (int i = 0; i < testcases; i++) {
            final int a = input.nextInt(), b = input.nextInt(), c = input.nextInt();
            assert ((1 <= a) && (a <= 30));
            assert ((1 <= b) && (b <= 30));
            assert ((1 <= c) && (c <= 30));
            cases.add(new box(a, b, c));
        }
        return cases;
    }

    public static String sides(int a, char b, char c) {
        final StringBuilder side = new StringBuilder();
        side.append(b);
        for (int i = 0; i < a; i++) {
            side.append(c).append(b);
        }
        return side.toString();
    }

    /**
     * 经典不把规则说全, 要人自己来1!5!
     */
    public static List<String> cal(List<box> boxes) {
        final List<String> builders = new ArrayList<>(boxes.size());
        for (var box : boxes) {
            final StringBuilder stores = new StringBuilder();
            final int a = box.a, b = box.b, c = box.c;
            final String Lside = sides(a, '+', '-'),
                Hside = sides(a, '/', '.') + '|',
                Wside = sides(a, '|', '.') + '/';
            assert (Lside.length() == 2 * a + 1);
            assert (Hside.length() == 2 * a + 2);
            assert (Wside.length() == 2 * a + 2);
            for (int i = 0; i < 2 * b; i++) {
                final StringBuilder builder = new StringBuilder();
                builder.append(".".repeat(Math.max(0, 2 * b - i)));
                final String repeat = "..".repeat(Math.max(0, i / 2 - c));
                if (i % 2 == 0) {
                    builder.append(Lside);
                    for (int k = 0; k < c && k < i / 2; k++) {
                        builder.append(".+");
                    }
                } else {
                    builder.append(Hside);
                    for (int k = 0; (k < i / 2) && (k + 1 < c); k++) {
                        builder.append("/|");
                    }
                    if (i > 2 * c) {
                        builder.append("/.");
                    }
                }
                builder.append(repeat);
                stores.append(builder).append('\n');
            }
            final int lineLength = 2 * a + 2 * b;
            for (int i = 0; i < 2 * c + 1; i++) {
                final StringBuilder builder = new StringBuilder();
                if (i % 2 == 0) {
                    builder.append(Lside);
                    for (int j = c, count = 1; j > i / 2 && count < 2 * b; j--, count++) {
                        builder.append(".+");
                    }
                } else {
                    builder.append(Wside);
                    for (int j = c - 1, count = 0; j > i / 2 && count < 2 * b; j--, count++) {
                        builder.append("|/");
                    }
                }
                for (int j = 2 * b - 2 * c + i; j > 0; j--) {
                    builder.append('.');
                    if (builder.length() > lineLength + 1) {
                        break;
                    }
                }
                stores.append(builder.substring(0, lineLength + 1)).append('\n');
            }
            builders.add(stores.toString());
        }
        return builders;
    }

    public static List<String> print(List<box> boxes) {
        final List<String> builders = new ArrayList<>(boxes.size());
        for (var box : boxes) {
            final int a = box.a, b = box.b, c = box.c;
            final int width = 2 * a + 2 * b + 1, height = 2 * b + 2 * c + 1;
            final StringBuilder stores = new StringBuilder(height * width);
            final char[][] matrix = new char[height][width];
            for (int i = 0; i < height; i++) {
                Arrays.fill(matrix[i], '.');
            }
            final int beginx = 2 + 2 * c, beginy = -2 + 2 * b;
            for (int z = 0; z < c; z++) {
                for (int x = 0; x < b; x++) {
                    final int tempx = beginx + 2 * x - 2 * z;
                    for (int y = 0; y < a; y++) {
                        final int tempy = beginy + 2 * y - 2 * x;
                        for (int nums1 = 0; nums1 < 5; nums1++) {
                            for (int nums2 = 0; nums2 < 5; nums2++) {
                                if (smallest[4 - nums1][nums2] != ' ') {
                                    matrix[tempx - nums1][tempy + nums2] = smallest[4 - nums1][nums2];
                                }
                            }
                        }
                    }
                }
            }
            for (var line : matrix) {
                for (char ch : line) {
                    stores.append(ch);
                }
                stores.append('\n');
            }
            builders.add(stores.toString());
        }
        return builders;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = print(datas);
        // final var result = cal(datas);
        output(result);
    }

    public static void output(Iterable<String> strs) {
        for (var str : strs) {
            System.out.print(str);
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
