// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    // 读取方法，快读+assert
    public static Data reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int S = input.nextInt();
        assert ((1 <= n) && (n <= 3000));
        assert ((1 <= S) && (S <= 1000_000_000));
        final int[] arr = new int[n];
        for (int i = 0; i < n; ++i) {
            arr[i] = input.nextInt();
            assert ((1 <= arr[i]) && (arr[i] <= 1000_000_000));
            if (i > 0) {
                assert (arr[i] >= arr[i - 1]); // 非递减
            }
        }
        return new Data(n, S, arr);
    }

    // 处理函数，返回三元组计数（使用双指针，O(n^2)），结果可能很大，需用 long
    public static long cal(final Data data) {
        final int n = data.n;
        final int[] a = data.arr;
        final long targetSum = data.S;
        long count = 0L;
        for (int i = 0; i < n - 2; ++i) {
            int l = i + 1;
            int r = n - 1;
            final long target = targetSum - a[i];
            while (l < r) {
                final long two = a[l] + (long) a[r];
                if (two == target) {
                    if (a[l] == a[r]) {
                        final long len = r - l + 1L;
                        count += len * (len - 1L) / 2L; // 组合数 C(len,2)
                        break; // 本轮 i 完毕
                    } else {
                        int lVal = a[l];
                        int rVal = a[r];
                        long lCnt = 0;
                        long rCnt = 0;
                        while (l < r && a[l] == lVal) {
                            ++l;
                            ++lCnt;
                        }
                        while (r >= l && a[r] == rVal) {
                            --r;
                            ++rCnt;
                        }
                        count += lCnt * rCnt;
                    }
                } else if (two < target) {
                    ++l;
                } else { // two > target
                    --r;
                }
            }
        }
        return count;
    }

    // 输出函数，最后一个也输出换行
    public static void output(final long result) {
        System.out.print(result);
        System.out.print('\n');
    }

    public static void main(String[] args) throws IOException {
        final Data data = reader();
        final long result = cal(data);
        output(result);
    }

    // 数据类，封装所有输入
    private static final class Data {
        final int n;
        final int S;
        final int[] arr;

        Data(int n, int S, int[] arr) {
            this.n = n;
            this.S = S;
            this.arr = arr;
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
