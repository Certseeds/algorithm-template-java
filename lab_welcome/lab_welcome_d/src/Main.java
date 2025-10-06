// SPDX-License-Identifier: AGPL-3.0-or-later

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {
    private static final List<Map.Entry<Integer, String>> spis = List.of(
        new AbstractMap.SimpleImmutableEntry<>(2, "+---+")
        , new AbstractMap.SimpleImmutableEntry<>(1, "/   /|")
        , new AbstractMap.SimpleImmutableEntry<>(0, "+---+ |")
        , new AbstractMap.SimpleImmutableEntry<>(0, "|   | +")
        , new AbstractMap.SimpleImmutableEntry<>(0, "|   |/")
        , new AbstractMap.SimpleImmutableEntry<>(0, "+---+")
    );

    public static int[][] reader() {
        final var input = new Reader();
        final int m = input.nextInt();
        final int n = input.nextInt();
        int[][] will_return = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                will_return[i][j] = input.nextInt();
            }
        }
        return will_return;
    }

    public static void print(char[][] map, int a, int b) {
        for (int i = 0; i < spis.size(); i++) {
            for (int j = 0; j < spis.get(i).getValue().length(); j++) {
                map[a - 1 + i][b + j + spis.get(i).getKey()] = spis.get(i).getValue().charAt(j);
            }
        }
    }

    public static void cal(int[][] hi) {
        final char[][] out = new char[302][301];
        Arrays.stream(out).forEach(a -> Arrays.fill(a, '.'));
        final int m = hi.length - 1;
        final int n = hi[0].length - 1;
        final int wide = 4 * n + 2 * m + 1;
        int h = -0x3f3f;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                h = Math.max(h, hi[i][j] * 3 + 2 * (m - i + 1) + 1);
            }
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 0; k < hi[i][j]; k++) {
                    final int x = h - 3 * (k + 2) - 2 * (m - i) + 1;
                    final int y = 4 * j + 2 * (m - i - 1) - 1;
                    print(out, x, y);
                }
            }
        }
        output(out, h, wide);
    }

    public static void main(String[] args) {
        final var input = reader();
        cal(input);
    }

    public static void output(char[][] map, int high, int wide) {
        for (int i = 0; i < high; i++) {
            for (int j = 1; j <= wide; j++) {
                System.out.print(map[i][j]);
            }
            System.out.print("\n");
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

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

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
