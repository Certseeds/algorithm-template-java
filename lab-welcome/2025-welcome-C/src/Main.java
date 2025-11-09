// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public final class Main {

    // 读入数据
    public static class InputData {
        int T;
        List<List<String>> tiles = new ArrayList<>();
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final InputData data = new InputData();
        data.T = input.nextInt();
        assert ((data.T >= 1) && (data.T <= 200)) : "T out of range";
        for (int i = 0; i < data.T; i++) {
            final String line = input.nextLine();
            final List<String> strs = Arrays.stream(line.split(" ")).collect(Collectors.toList());
            data.tiles.add(strs);
        }
        return data;
    }

    // 处理逻辑
    public static List<String> cal(InputData data) {
        final List<String> result = new ArrayList<>(data.T);
        final List<String> order = new ArrayList<>();
        for (int i = 1; i <= 9; ++i) {
            order.add("W" + i);
        }
        for (int i = 1; i <= 9; ++i) {
            order.add("T" + i);
        }
        for (int i = 1; i <= 9; ++i) {
            order.add("Y" + i);
        }
        order.add("E");
        order.add("S");
        order.add("W");
        order.add("N");
        order.add("B");
        order.add("F");
        order.add("Z");
        for (List<String> tiles : data.tiles) {
            final Map<String, Integer> cnt = new LinkedHashMap<>();
            for (String s : tiles) {
                cnt.put(s, cnt.getOrDefault(s, 0) + 1);
            }
            final List<String> line = new ArrayList<>();
            for (String s : order) {
                int c = cnt.getOrDefault(s, 0);
                for (int i = 0; i < c; ++i) {
                    line.add(s);
                }
            }
            result.add(String.join(" ", line));
        }
        return result;
    }

    // 输出
    public static void output(List<String> result) {
        for (String s : result) {
            System.out.print(s);
            System.out.print('\n');
        }
    }

    public static void main(String[] args) throws IOException {
        final InputData data = reader();
        final List<String> result = cal(data);
        output(result);
    }

    // 快读类
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
