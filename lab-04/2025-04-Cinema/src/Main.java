// SPDX-License-Identifier: Apache-2.0

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public final class Main {

    private Main() {/**/}

    public static void main(String[] args) throws IOException {
        final var input = reader();
        final var results = cal(input);
        output(results);
    }

    public static InputData reader() throws IOException {
        final var input = new Reader();
        final int groupCount = input.nextInt();
        final int queuePLength = input.nextInt();
        final int queueQLength = input.nextInt();
        assert ((1 <= groupCount) && (groupCount <= 100000));
        assert ((1 <= queuePLength) && (queuePLength <= groupCount));
        assert ((1 <= queueQLength) && (queueQLength <= groupCount));
        final var queueP = new int[queuePLength];
        final var queueQ = new int[queueQLength];
        for (int i = 0; i < queuePLength; i++) {
            final int value = input.nextInt();
            assert ((1 <= value) && (value <= groupCount));
            queueP[i] = value;
        }
        for (int i = 0; i < queueQLength; i++) {
            final int value = input.nextInt();
            assert ((1 <= value) && (value <= groupCount));
            queueQ[i] = value;
        }
        return new InputData(groupCount, queuePLength, queueQLength, queueP, queueQ);
    }

    public static int[] cal(InputData data) {
        final int groupCount = data.groupCount;
        final var waitTimes = new int[groupCount];
        final var finished = new boolean[groupCount];
        var completed = 0;
        var pointerP = 0;
        var pointerQ = 0;
        var minute = 0;
        // Serve both queues minute by minute while removing partners instantly.
        while (completed < groupCount) {
            minute++;
            var groupP = -1;
            while (pointerP < data.queuePLength) {
                final int candidate = data.queueP[pointerP];
                if (finished[candidate - 1]) {
                    pointerP++;
                    continue;
                }
                groupP = candidate;
                break;
            }
            var groupQ = -1;
            while (pointerQ < data.queueQLength) {
                final int candidate = data.queueQ[pointerQ];
                if (finished[candidate - 1]) {
                    pointerQ++;
                    continue;
                }
                groupQ = candidate;
                break;
            }
            assert ((groupP != -1) || (groupQ != -1));
            if (groupP != -1) {
                final int index = groupP - 1;
                if (!finished[index]) {
                    waitTimes[index] = minute;
                    finished[index] = true;
                    completed++;
                }
                pointerP++;
            }
            if (groupQ != -1) {
                final int index = groupQ - 1;
                if (!finished[index]) {
                    waitTimes[index] = minute;
                    finished[index] = true;
                    completed++;
                }
                pointerQ++;
            }
        }
        return waitTimes;
    }

    public static void output(int[] waitTimes) {
        final var builder = new StringBuilder();
        for (int i = 0; i < waitTimes.length; i++) {
            if (i > 0) {
                builder.append(' ');
            }
            builder.append(waitTimes[i]);
        }
        System.out.print(builder.toString());
        System.out.print('\n');
    }

    private static final class InputData {
        private final int groupCount;
        private final int queuePLength;
        private final int queueQLength;
        private final int[] queueP;
        private final int[] queueQ;

        private InputData(int groupCount, int queuePLength, int queueQLength, int[] queueP, int[] queueQ) {
            this.groupCount = groupCount;
            this.queuePLength = queuePLength;
            this.queueQLength = queueQLength;
            this.queueP = queueP;
            this.queueQ = queueQ;
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
