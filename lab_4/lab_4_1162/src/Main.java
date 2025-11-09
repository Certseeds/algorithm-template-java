// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    // Using a static final class for JDK 11 compatibility
    public static final class TestCase {
        public final int n, m;
        public final char[][] maze;
        public final String instructions;
        public final int startX, startY, endX, endY;

        public TestCase(int n, int m, char[][] maze, String instructions, int startX, int startY, int endX, int endY) {
            this.n = n;
            this.m = m;
            this.maze = maze;
            this.instructions = instructions;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }
    }

    public static List<TestCase> reader() {
        final var in = new Reader();
        final int testcases = in.nextInt();
        final List<TestCase> cases = new ArrayList<>(testcases);
        for (int t = 0; t < testcases; t++) {
            final int n = in.nextInt();
            final int m = in.nextInt();
            final char[][] maze = new char[n][m];
            int startX = -1, startY = -1, endX = -1, endY = -1;
            for (int i = 0; i < n; i++) {
                final String line = in.nextLine();
                for (int j = 0; j < m; j++) {
                    maze[i][j] = line.charAt(j);
                    if (maze[i][j] == 'S') {
                        startX = i;
                        startY = j;
                    } else if (maze[i][j] == 'E') {
                        endX = i;
                        endY = j;
                    }
                }
            }
            final String instructions = in.nextLine();
            cases.add(new TestCase(n, m, maze, instructions, startX, startY, endX, endY));
        }
        return cases;
    }

    public static List<Integer> cal(List<TestCase> inputs) {
        final List<Integer> results = new ArrayList<>();

        int caltimes = 0;
        int[][] judge = new int[24][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if (i != j && j != k && k != i) {
                        judge[caltimes][0] = i;
                        judge[caltimes][1] = j;
                        judge[caltimes][2] = k;
                        judge[caltimes][3] = 6 - i - j - k;
                        caltimes++;
                    }
                }
            }
        }
        for (var testCase : inputs) {
            int validMappings = 0;
            for (int i = 0; i < 24; i++) {
                if (simulate(testCase, judge[i])) {
                    validMappings++;
                }
            }
            results.add(validMappings);
        }
        return results;
    }

    private static boolean simulate(TestCase tc, int[] p) {
        int x = tc.startX;
        int y = tc.startY;

        for (char instruction : tc.instructions.toCharArray()) {
            int digit = instruction - '0';

            if (digit == p[0]) {
                x++;
            } else if (digit == p[1]) {
                y++;
            } else if (digit == p[2]) {
                x--;
            } else if (digit == p[3]) {
                y--;
            }

            if (x < 0 || x >= tc.n || y < 0 || y >= tc.m) {
                return false; // Out of bounds
            }
            if (tc.maze[x][y] == '#') {
                return false; // Hit a wall
            }
            if (x == tc.endX && y == tc.endY) {
                return true;
            }
        }
        return false;
    }

    public static void output(List<Integer> decides) {
        for (Integer decide : decides) {
            System.out.print(decide);
            System.out.print('\n');
        }
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
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
