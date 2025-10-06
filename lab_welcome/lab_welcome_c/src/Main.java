// SPDX-License-Identifier: AGPL-3.0-or-later

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public final class Main {
    private static final String SUCCESS = "\"PoSSiBLE\"\n";
    // uncolor is the first so it's default when init array.
    private static final String FAIL = "\"lMP0SSlBLE\"\n";

    public static int[][] reader(){
        final var input = new Reader();
        final int node_number = input.nextInt();
        final int[][] will_return = new int[node_number][];
        for (int i = 0; i < node_number; i++) {
            final int connects = input.nextInt();
            will_return[i] = new int[connects];
            for (int j = 0; j < connects; j++) {
                will_return[i][j] = input.nextInt();
            }
        }
        return will_return;
    }

    public static void main(String[] args) {
        final int[][] graph = reader();
        final boolean result = cal(graph);
        output(result);
    }

    static boolean cal(int[][] graph) {
        final int node_number = graph.length;
        final var color_vec = new COLOR[node_number];
        Arrays.fill(color_vec, COLOR.uncolor);
        final Queue<Integer> que = new LinkedList<>();
        for (int i = 0; i < node_number; i++) {
            if (graph[i].length != 0 && color_vec[i] == COLOR.uncolor) {
                color_vec[i] = COLOR.red;
                que.add(i);
                while (!que.isEmpty()) {
                    final int head = que.remove();
                    final COLOR color_head = color_vec[head];
                    final COLOR next_color = (color_head == COLOR.red) ? COLOR.black : COLOR.red;
                    for (int j : graph[head]) {
                        if (color_vec[j] == COLOR.uncolor) {
                            color_vec[j] = next_color;
                            que.add(j);
                        } else if (color_vec[j] == color_head) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    static void output(boolean data) {
        if (data) {
            System.out.print(SUCCESS);
        } else {
            System.out.print(FAIL);
        }
    }

    private enum COLOR {
        uncolor, red, black
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
