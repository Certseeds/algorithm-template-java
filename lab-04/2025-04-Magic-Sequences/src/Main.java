// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public final class Main {

    private Main() {
        /**/}

    private static final class InputData {
        private final int window;
        private final int[] sequence;

        private InputData(int window, int[] sequence) {
            this.window = window;
            this.sequence = sequence;
        }
    }

    public static void main(String[] args) throws IOException {
        final var input = reader();
        final int result = cal(input);
        output(result);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int window = input.nextInt();
        assert ((1 <= window) && (window < 2000000));
        final int[] inputs = new int[2000001];
        int input_num = 0;
        for (; input.hasNext(); input_num += 1) {
            inputs[input_num] += input.nextInt();
        }
        assert (input_num <= 2_000_001);
        input_num -= 1;
        final int[] sequence = Arrays.copyOf(inputs, input_num);
        return new InputData(window, sequence);
    }

    public static int cal(InputData data) {
        final int window = data.window;
        final int[] arr = data.sequence;
        final int length = arr.length;
        assert ((1 <= window) && (window < length));
        final var deque = new int[length];
        var head = 0;
        var tail = 0;
        var result = 0;
        // Maintain indices of candidates for the sliding window maximum in decreasing
        // order.
        for (int i = 0; i < length; i++) {
            while ((head < tail) && (arr[deque[tail - 1]] <= arr[i])) {
                tail--;
            }
            deque[tail] = i;
            tail++;
            if ((head < tail) && (deque[head] <= i - window)) {
                head++;
            }
            if (i >= window - 1) {
                result ^= arr[deque[head]];
            }
        }
        return result;
    }

    public static void output(int number) {
        System.out.print(number);
        System.out.print('\n');
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

        public boolean hasNext() {
            while (!st.hasMoreTokens()) {
                String nextLine = nextLine();
                if (nextLine == null) {
                    return false;
                }
                st = new StringTokenizer(nextLine);
            }
            return true;
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
