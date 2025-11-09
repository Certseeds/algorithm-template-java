// SPDX-License-Identifier: AGPL-3.0-or-later 
// SPDX-FileCopyrightText: 2018-2025 nanoseeds

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public final class Main {
    private static final class one {
        private final Integer booksNum;
        private final String customes;

        private one(Integer booksNum, String customes) {
            this.booksNum = booksNum;
            this.customes = customes;
        }
    }

    public static List<one> reader() {
        final var input = new Reader();
        final List<one> cases = new ArrayList<>();
        while (input.hasNext()) {
            final int number = input.nextInt();
            assert ((0 <= number) && (number <= 20));
            if (number != 0) {
                final String custom = input.next();
                cases.add(new one(number, custom));
            }
        }
        return cases;
    }


    public static List<Integer> cal(List<one> nums) {
        final List<Integer> results = nums.stream()
            .map(one -> {
                Integer booksNum = one.booksNum;
                final String customes = one.customes;
                final int[] users = new int[26]; // raw for-range can use the same array
                Arrays.fill(users, 0);
                int lines = 0;
                if (booksNum == 0) {
                    return customes.length() / 2;
                }
                final int customesDouble = customes.length();
                for (int j = 0; j < customesDouble; j++) {
                    final int order = customes.charAt(j) - 'A';
                    if (users[order] == 0) {
                        if (booksNum > 0) {
                            booksNum -= 1;
                            users[order] = 1;
                        } else {
                            users[order] = -1;
                        }
                    } else if (users[order] == 1) {
                        users[order] = 0;
                        booksNum += 1;
                    } else {
                        users[order] = 0;
                        lines += 1;
                    }
                }
                return lines;
            })
            .collect(Collectors.toUnmodifiableList());
        return results;
    }

    public static void main(String[] args) throws IOException {
        final var datas = reader();
        final var result = cal(datas);
        output(result);
    }

    public static void output(List<Integer> decides) {
        final StringBuilder builder = new StringBuilder();
        for (var decide : decides) {
            builder.append(decide).append("\n");
        }
        System.out.print(builder);
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
