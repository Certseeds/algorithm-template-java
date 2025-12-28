// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public final class Main {

    public static final class Input {
        final int n;
        final int k;
        final String[] qrCodes;

        Input(int n, int k, String[] qrCodes) {
            this.n = n;
            this.k = k;
            this.qrCodes = qrCodes;
        }
    }

    public static Input reader() throws IOException {
        final var input = new Reader();
        final int n = input.nextInt();
        final int k = input.nextInt();
        assert ((1 < n) && (n <= 1000));
        assert ((1 <= k) && (k <= n));

        final var qrCodes = new String[n];
        for (int i = 0; i < n; i++) {
            final var sb = new StringBuilder();
            for (int row = 0; row < 16; row++) {
                for (int col = 0; col < 16; col++) {
                    sb.append(input.nextInt());
                }
            }
            qrCodes[i] = sb.toString();
        }
        return new Input(n, k, qrCodes);
    }

    public static String[] cal(Input input) {
        final int n = input.n;
        final int k = input.k;
        final var qrCodes = input.qrCodes;

        // LRU Cache using LinkedHashMap with access order
        final var cache = new LinkedHashMap<String, Boolean>(k, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Boolean> eldest) {
                return size() > k;
            }
        };

        final var results = new String[n];
        for (int i = 0; i < n; i++) {
            final var code = qrCodes[i];
            if (cache.containsKey(code)) {
                results[i] = "hit";
                cache.get(code); // update access order
            } else {
                results[i] = "miss";
                cache.put(code, true);
            }
        }
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var input = reader();
        final var results = cal(input);
        output(results);
    }

    public static void output(String[] results) {
        for (final var result : results) {
            System.out.print(result);
            System.out.print('\n');
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
