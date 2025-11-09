// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public final class Main {
    private static final int TILE_KIND = 34;
    private static final int[] SUITE_OFFSETS = {0, 9, 18, 27}; // w, b, s, z
    private static final int[] SUITE_LENGTHS = {9, 9, 9, 7};
    private static final char[] SUITES = {'w', 'b', 's', 'z'};

    public static List<int[]> reader() throws IOException {
        final var input = new Reader();
        final int T = input.nextInt();
        assert ((1 <= T) && (T <= 10000));
        final List<int[]> cases = new ArrayList<>(T);
        for (int t = 0; t < T; ++t) {
            final String s = input.next();
            assert (s.length() == 28);
            final int[] tiles = new int[TILE_KIND];
            for (int i = 0; i < 14; ++i) {
                final char num = s.charAt(2 * i);
                final char suite = s.charAt(2 * i + 1);
                int suiteIdx = -1;
                for (int j = 0; j < SUITES.length; ++j) {
                    if (SUITES[j] == suite) {
                        suiteIdx = j;
                        break;
                    }
                }
                assert (suiteIdx != -1);
                final int rank = num - '0';
                assert ((1 <= rank) && (rank <= SUITE_LENGTHS[suiteIdx]));
                int idx = SUITE_OFFSETS[suiteIdx] + (rank - 1);
                tiles[idx]++;
            }
            cases.add(tiles);
        }
        return cases;
    }

    public static List<String> cal(List<int[]> cases) {
        final List<String> results = new ArrayList<>(cases.size());
        for (final int[] tiles : cases) {
            results.add(isWinning(tiles) ? "Blessing of Heaven" : "Bad luck");
        }
        return results;
    }

    private static boolean isWinning(int[] tiles) {
        for (int i = 0; i < TILE_KIND; ++i) {
            if (tiles[i] >= 2) {
                final int[] copy = Arrays.copyOf(tiles, TILE_KIND);
                copy[i] -= 2;
                if (canDivide(copy, 4)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean canDivide(int[] tiles, int groupLeft) {
        if (groupLeft == 0) {
            for (int v : tiles) {
                if (v != 0) {
                    return false;
                }
            }
            return true;
        }
        for (int i = 0; i < TILE_KIND; ++i) {
            if (tiles[i] >= 3) {
                tiles[i] -= 3;
                if (canDivide(tiles, groupLeft - 1)) {
                    tiles[i] += 3;
                    return true;
                }
                tiles[i] += 3;
            }
            // 顺子（非 z）
            int suiteIdx = -1;
            for (int j = 0; j < SUITES.length; ++j) {
                if (i >= SUITE_OFFSETS[j] && i < SUITE_OFFSETS[j] + SUITE_LENGTHS[j]) {
                    suiteIdx = j;
                    break;
                }
            }
            if (suiteIdx < 3) { // w, b, s
                int base = i - SUITE_OFFSETS[suiteIdx];
                if (base <= SUITE_LENGTHS[suiteIdx] - 3) {
                    int idx1 = i, idx2 = i + 1, idx3 = i + 2;
                    if (tiles[idx1] > 0 && tiles[idx2] > 0 && tiles[idx3] > 0) {
                        tiles[idx1]--;
                        tiles[idx2]--;
                        tiles[idx3]--;
                        if (canDivide(tiles, groupLeft - 1)) {
                            tiles[idx1]++;
                            tiles[idx2]++;
                            tiles[idx3]++;
                            return true;
                        }
                        tiles[idx1]++;
                        tiles[idx2]++;
                        tiles[idx3]++;
                    }
                }
            }
        }
        return false;
    }

    public static void output(List<String> results) {
        for (String s : results) {
            System.out.print(s + '\n');
        }
    }

    // 快读类
    private static final class Reader {
        private final BufferedReader br;
        private StringTokenizer st;

        private Reader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreElements()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws IOException {
        final List<int[]> datas = reader();
        final List<String> results = cal(datas);
        output(results);
    }
}
