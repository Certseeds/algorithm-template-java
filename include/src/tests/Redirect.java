// SPDX-License-Identifier: Apache-2.0

package tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

public final class Redirect implements AutoCloseable {
    private final String data_path;
    private final PrintStream ori_out = System.out;
    private final InputStream in_ori = System.in;

    private Redirect(String data_path) {
        this.data_path = data_path;
    }

    public static Redirect from(String data_path, String input_path, String output_path) throws FileNotFoundException {
        final Redirect redirect = new Redirect(data_path);
        final FileInputStream fis = new FileInputStream(data_path + input_path);
        System.setIn(fis);
        if (!"".equals(output_path)) {
            final PrintStream ps = new PrintStream(new FileOutputStream(data_path + output_path));
            System.setOut(ps);
        }
        return redirect;
    }

    @Override
    public void close() {
        System.setIn(in_ori);
        System.setOut(ori_out);
    }

    public Pair<String, String> compare_double(String expected, String actual) throws IOException {
        final byte[] file1Bytes = Files.readAllBytes(Paths.get(data_path + expected));
        final byte[] file2Bytes = Files.readAllBytes(Paths.get(data_path + actual));
        final String file1 = new String(file1Bytes, StandardCharsets.UTF_8);
        final String file2 = new String(file2Bytes, StandardCharsets.UTF_8);
        return new Pair<>(file1, file2);
    }

    public String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        byte[] buffer = new byte[8192];
        try (FileInputStream in = new FileInputStream(file);) {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            for (int len = 0; (len = in.read(buffer)) != -1; ) {
                digest.update(buffer, 0, len);
            }
            final BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
