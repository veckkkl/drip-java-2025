package homework4.task9;

import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;

public class Filters {

    public static final AbstractFilter regularFile = Files::isRegularFile;
    public static final AbstractFilter readable = Files::isReadable;
    public static final AbstractFilter writable = Files::isWritable;
    public static final AbstractFilter executable = Files::isExecutable;

    public static AbstractFilter largerThan(long size) {
        return path -> {
            try {
                return Files.size(path) > size;
            } catch (IOException e) {
                return false;
            }
        };
    }

    public static AbstractFilter smallerThan(long size) {
        return path -> {
            try {
                return Files.size(path) < size;
            } catch (IOException e) {
                return false;
            }
        };
    }

    public static AbstractFilter extensionMatches(String extension) {
        return path -> path.toString().endsWith("." + extension);
    }

    public static AbstractFilter regexContains(String regex) {
        return path -> Pattern.compile(regex).matcher(path.getFileName().toString()).find();
    }

    public static AbstractFilter magicNumber(int... magicBytes) {
        return path -> {
            try {
                byte[] fileBytes = Files.readAllBytes(path);
                if (fileBytes.length < magicBytes.length) return false;
                for (int i = 0; i < magicBytes.length; i++) {
                    if ((fileBytes[i] & 0xFF) != magicBytes[i]) {
                        return false;
                    }
                }
                return true;
            } catch (IOException e) {
                return false;
            }
        };
    }

    public static AbstractFilter globMatches(String glob) {
        return path -> {
            String fileName = path.getFileName().toString();
            return fileName.matches(glob.replace(".", "\\.").replace("*", ".*").replace("?", "."));
        };
    }
}