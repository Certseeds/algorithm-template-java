// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2020-2025 nanoseeds
package producer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class file_producer {

    public static final List<String> labs = List.of("01", "02", "03", "04", "05", "06", "07", "08", "09", "bonus");
    public static final List<String> problem_order = List.of("A", "B", "C", "D", "E");
    public static final List<String> test_datas = List.of("01");

    public static void main(String[] args) throws IOException {
        final List<lab> LabList = labs.stream()
            .map(x -> new lab(x, problem_order))
            .collect(Collectors.toList());
        final base root = new base(LabList);
        root.writeFile();
        for (lab labElement : LabList) {
            labElement.create_dir();
            labElement.create_pom();
            for (question ques : labElement.getQues_order()) {
                ques.create_dir();
                ques.try_make_data_dir();
                ques.create_pom();
                ques.writeFile();
            }
        }
        System.out.println("produce files finish");
        System.out.printf("%d files is produces", labs.size() * problem_order.size() * 2);
    }

    static final Consumer<String> ensureAndCreate = (String str) -> {
        System.out.printf("creating %s%n", str);
        final File lab = new File(str);
        if (!lab.exists()) {
            lab.mkdir();
            System.out.printf("creating %s success %n", str);
        }
        System.out.printf("created %s %n", str);
    };

    public static String read_file(String file_name) {
        try (final InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(file_name)) {
            assert (stream != null);
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
}
