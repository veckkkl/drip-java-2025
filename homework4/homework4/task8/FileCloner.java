package homework4.task8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileCloner {

    public static void cloneFile(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("Такого файла не существует: " + path);
        }

        String fileName = path.getFileName().toString();
        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        String extension = fileName.substring(fileName.lastIndexOf('.'));

        Path parentDir = path.getParent();
        int copyNumber = 1;

        String newFileName;
        Path newFilePath;
        do {
            if (copyNumber == 1) {
                newFileName = baseName + " — копия" + extension;
            } else {
                newFileName = baseName + " — копия (" + copyNumber + ")" + extension;
            }
            newFilePath = parentDir.resolve(newFileName);
            copyNumber++;
        } while (Files.exists(newFilePath));

        Files.copy(path, newFilePath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Создана копия: " + newFilePath);
    }

    public static void main(String[] args) {
        try {
            Path filePath = Paths.get("C:/path/to/Tinkoff Bank Biggest Secret.txt");
            cloneFile(filePath);
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}