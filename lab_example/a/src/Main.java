// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public final class Main {

    public static int[] reader() throws IOException {
        final int[] will_return = new int[2];
        final var input = new Reader();
        will_return[0] = input.nextInt();
        will_return[1] = input.nextInt();
        return will_return;
    }

    public static int cal(int[] nums) {
        assert (nums.length == 2);
        return nums[0] + nums[1];
    }

    public static void main(String[] args) throws IOException {
        final int[] datas = reader();
        final int result = cal(datas);
        output(result);
    }

    public static void output(int number) {
        System.out.print(number + '\n');
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
