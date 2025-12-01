// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.StringTokenizer;

public final class Main {
    private Main() {/**/}

    private static final class InputData {
        final int n;
        final long[] a;

        InputData(int n, long[] a) {
            this.n = n;
            this.a = a;
        }
    }

    // 手写 MaxHeap 数据结构 (1-indexed, 记录交换次数)
    private static final class MaxHeap {
        private final long[] heap;
        private int size;

        MaxHeap(int capacity) {
            this.heap = new long[capacity + 1]; // 1-indexed
            this.size = 0;
        }

        // 插入元素并返回上浮交换次数
        int insert(long value) {
            size++;
            heap[size] = value;
            return siftUp(size);
        }

        private int siftUp(int idx) {
            int swaps = 0;
            while (idx > 1) {
                final int parent = idx / 2;
                if (heap[idx] > heap[parent]) {
                    swap(idx, parent);
                    swaps++;
                    idx = parent;
                } else {
                    break;
                }
            }
            return swaps;
        }

        private void swap(int i, int j) {
            final long tmp = heap[i];
            heap[i] = heap[j];
            heap[j] = tmp;
        }
    }

    public static InputData reader() throws IOException {
        final var in = new Reader();
        final int n = in.nextInt();
        assert ((1 <= n) && (n <= 300_000));
        final long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextLong();
            assert ((1 <= a[i]) && (a[i] <= 1_000_000_000L));
        }
        return new InputData(n, a);
    }

    // 使用手写 MaxHeap 的实现
    public static int[] cal(InputData data) {
        final int n = data.n;
        final var maxHeap = new MaxHeap(n);
        final int[] swapCounts = new int[n];

        for (int i = 0; i < n; i++) {
            swapCounts[i] = maxHeap.insert(data.a[i]);
        }

        return swapCounts;
    }

    public static void main(String[] args) throws IOException {
        final var data = reader();
        final int[] results = cal(data);
        output(results);
    }

    public static void output(int[] results) {
        final var sb = new StringBuilder();
        for (int i = 0; i < results.length; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(results[i]);
        }
        System.out.print(sb);
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
            st = new StringTokenizer("");
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
