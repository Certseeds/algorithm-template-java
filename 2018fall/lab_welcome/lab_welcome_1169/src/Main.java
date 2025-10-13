// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

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
     * 利用规律的做法
     */
    public static List<String> cal(List<box> boxes) {
        final List<String> builders = new ArrayList<>(boxes.size());
        for (var box : boxes) {
            final StringBuilder stores = new StringBuilder();
            final int a = box.a, b = box.b, c = box.c;
            final String Lside = sides(a, '+', '-'), // 最顶上的那一行
                Hside = sides(a, '/', '.') + '|', // 上半部分的奇数行
                Wside = sides(a, '|', '.') + '/'; // 下半部分的奇数行
            assert (Lside.length() == 2 * a + 1);
            assert (Hside.length() == 2 * a + 2);
            assert (Wside.length() == 2 * a + 2);
            // 上半部分
            for (int i = 0; i < 2 * b; i++) {
                final StringBuilder builder = new StringBuilder();
                builder.append(".".repeat(Math.max(0, 2 * b - i))); // 补足左半部分的点, 不难发现他们是逐渐减少到0的
                final String repeat = "..".repeat(Math.max(0, i / 2 - c)); // 当 b 远大于 c 时, 不难注意到, Lside 右侧需要填充 `..`
                if (i % 2 == 0) {
                    builder.append(Lside); // 上半部分-中心
                    for (int k = 0; k < c && k < i / 2; k++) {
                        builder.append('.').append('+'); // 上半部分-高度带来的 mid-right字段
                    }
                } else {
                    builder.append(Hside); // 上半部分-中心
                    for (int k = 0; (k < i / 2) && (k + 1 < c); k++) {
                        builder.append('/').append('|');
                    }
                    if (i > 2 * c) {
                        builder.append('/').append('.');
                    }
                }
                builder.append(repeat);
                stores.append(builder).append('\n');
            }
            for (int i = 0; i < 2 * c + 1; i++) {
                final StringBuilder builder = new StringBuilder();
                if (i % 2 == 0) {
                    builder.append(Lside);
                    // 前半部分是逻辑上的最大值, 后半部分是边长的约束
                    for (int j = 0; 2 * j + i < 2 * c && 2 * j + 1 < 2 * b; j++) {
                        builder.append('.').append('+');
                    }
                } else {
                    builder.append(Wside);
                    for (int j = 0; 2 * j + i + 1 < 2 * c; j++) {
                        if (1 + 2 * j < 2 * b) { // 边长约束
                            builder.append('|');
                        }
                        if (2 + 2 * j < 2 * b) { // 边长约束
                            builder.append('/');
                        }
                    }
                }
                for (int j = 0; j < 2 * b + i - 2 * c; j++) {
                    builder.append('.');
                }
                stores.append(builder).append('\n');
            }
            builders.add(stores.toString());
        }
        return builders;
    }

    /**
     * 暴力做法
     */
    public static List<String> print(List<box> boxes) {
        final List<String> outputs = new ArrayList<>(boxes.size());
        for (var spec : boxes) {
            outputs.add(drawByVoxel(spec));
        }
        return outputs;
    }

    private static String drawByVoxel(box spec) {
        final int a = spec.a;
        final int b = spec.b;
        final int c = spec.c;
        final int width = 2 * a + 2 * b + 1;
        final int height = 2 * b + 2 * c + 1;
        final char[][] canvas = createCanvas(height, width);
        final int baseRow = 2 + 2 * c;
        final int baseCol = -2 + 2 * b;
        for (int z = 0; z < c; z++) {
            for (int x = 0; x < b; x++) {
                final int anchorRow = baseRow + 2 * x - 2 * z;
                for (int y = 0; y < a; y++) {
                    final int anchorCol = baseCol + 2 * y - 2 * x;
                    placeUnitCube(canvas, anchorRow, anchorCol);
                }
            }
        }
        return canvasToString(canvas);
    }

    private static char[][] createCanvas(int height, int width) {
        final char[][] canvas = new char[height][width];
        for (int row = 0; row < height; row++) {
            Arrays.fill(canvas[row], '.');
        }
        return canvas;
    }

    private static void placeUnitCube(char[][] canvas, int anchorRow, int anchorCol) {
        for (int dr = 0; dr < 5; dr++) {
            for (int dc = 0; dc < 5; dc++) {
                final char ch = smallest[4 - dr][dc];
                if (ch != ' ') {
                    canvas[anchorRow - dr][anchorCol + dc] = ch;
                }
            }
        }
    }

    private static String canvasToString(char[][] canvas) {
        final int height = canvas.length;
        final int width = canvas[0].length;
        final StringBuilder builder = new StringBuilder(height * (width + 1));
        for (var row : canvas) {
            builder.append(row);
            builder.append('\n');
        }
        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = print(datas);
        // final var result = print(datas);
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

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

}
