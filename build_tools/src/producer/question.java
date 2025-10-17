// SPDX-License-Identifier: AGPL-3.0-or-later
// SPDX-FileCopyrightText: 2020-2025 nanoseeds

package producer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

public class question {
    private static final String Main =
        constant.FILE_HEADER_STR + file_producer.read_file("template/Main.java");
    private static final String MainTest =
        constant.FILE_HEADER_STR + file_producer.read_file("template/MainTest.java");
    private final String lab_number, ques_number;
    private final String sub_sub_pom;

    public question(String lab_number, String ques_number) {
        this.lab_number = lab_number;
        this.ques_number = ques_number;
        sub_sub_pom = file_producer.read_file("template/subsubpom.xml");
    }

    public String subPom() {
        return String.format("        <module>%1$s-%2$s</module>\n", lab_number, ques_number);
    }

    public void create_dir() {
        file_producer.ensureAndCreate.accept(String.format(constant.LAB_QUESTION_PATH, lab_number, ques_number));
        file_producer.ensureAndCreate.accept(String.format(constant.LAB_QUESTION_SOURCE_PATH, lab_number, ques_number));
        file_producer.ensureAndCreate.accept(String.format(constant.LAB_QUESTION_TEST_PATH, lab_number, ques_number));
        file_producer.ensureAndCreate.accept(
            String.format(constant.LAB_QUESTION_TEST_DATA_PATH, lab_number, ques_number));
    }

    public void try_make_data_dir() {
        final Consumer<List<String>> lambda = (var strs) -> {
            try {
                final String input_file_name =
                    String.format(constant.LAB_QUESTION_TEST_DATA_PATH, lab_number, ques_number)
                        + String.format(strs.get(0), strs.get(1));
                final Path of = Path.of(input_file_name);
                if (!Files.exists(of)) {
                    Files.createFile(of);
                }
                Files.writeString(of, String.format("%s\n", strs.get(2)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        for (String num : file_producer.test_datas) {
            lambda.accept(List.of("/%s.data.in", num, "114 514"));
            lambda.accept(List.of("/%s.data.out", num, "628"));
        }
    }

    public void create_pom() throws IOException {
        final Path of = Path.of(String.format(constant.LAB_QUESTION_POM_PATH, lab_number, ques_number));
        if (!Files.exists(of)) {
            Files.createFile(of);
        }
        Files.writeString(of, String.format(sub_sub_pom, lab_number, ques_number));
    }

    public void writeFile() throws IOException {
        Files.writeString(Path.of(String.format(constant.SOURCE_FILE, lab_number, ques_number)), Main);
        Files.writeString(Path.of(String.format(constant.TEST_FILE, lab_number, ques_number)), MainTest);
    }
}
