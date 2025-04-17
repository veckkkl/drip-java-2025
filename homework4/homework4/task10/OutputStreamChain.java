package homework4.task10;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

public class OutputStreamChain {
    public static void main(String[] args) {
        Path filePath = Paths.get("output.txt");

        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile());

                CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStream, new CRC32());

                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, "UTF-8");

                PrintWriter printWriter = new PrintWriter(outputStreamWriter)
        ) {
            printWriter.println("Programming is learned by writing programs. ― Brian Kernighan");

            System.out.println("Контрольная сумма: " + checkedOutputStream.getChecksum().getValue());
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}