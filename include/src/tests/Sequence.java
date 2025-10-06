// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2020-2025 nanoseeds
package tests;

import java.util.*;

public final class Sequence {
    private String prefixOfFileName = "";
    private static final String postfixOfTestout = "test.out";
    private static final String postfixOfDatain = "data.in";
    private static final String postfixOfDataout = "data.out";
    private final int begin;
    private final int end;
    private final int maxLength;

    public Sequence(int begin, int end) {
        this(begin, end, 2);
    }

    public Sequence(int begin, int end, int maxLength) {
        this.begin = begin;
        this.end = end;
        this.maxLength = maxLength;
    }

    public List<String> getSequence() {
        final List<String> result = new ArrayList<>();
        for (int i = begin; i <= end; ++i) {
            result.add(Integer.toString(i));
        }
        return result;
    }

    public List<String> getSameLengthSequence() {
        final int maxLen = maxLength == -1 ? getLength(end) : Math.max(maxLength, getLength(end));
        final List<String> result = getSequence();
        for (int i = 0; i < result.size(); ++i) {
            final String item = result.get(i);
            if (item.length() < maxLen) {
                result.set(i, "0".repeat(maxLen - item.length()) + item);
            }
        }
        return result;
    }

    private static int getLength(int value) {
        int len = 0;
        while (value > 0) {
            len++;
            value /= 10;
        }
        return len;
    }

    public List<FileTriple> getFiles(boolean sameLength) {
        final List<String> seq = sameLength ? getSameLengthSequence() : getSequence();
        final List<FileTriple> result = new ArrayList<>();
        for (String item : seq) {
            final String datain = prefixOfFileName + item + "." + postfixOfDatain;
            final String dataout = prefixOfFileName + item + "." + postfixOfDataout;
            final String testout = prefixOfFileName + item + "." + postfixOfTestout;
            result.add(new FileTriple(datain, dataout, testout));
        }
        return result;
    }

    public static class FileTriple {
        public final Triple<String, String, String> data;

        public String datain() {
            return data.getFirst();
        }

        public String dataout() {
            return data.getSecond();
        }

        public String testout() {
            return data.getThird();
        }

        public FileTriple(final String datain, final String dataout, final String testout) {
            data = new Triple<>(datain, dataout, testout);
        }

        @Override
        public String toString() {
            return "FileTriple{" +
                "datain='" + datain() + '\'' +
                ", dataout='" + dataout() + '\'' +
                ", testout='" + testout() + '\'' +
                '}';
        }
    }
}
