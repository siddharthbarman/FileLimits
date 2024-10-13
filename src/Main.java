import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            help();
            return;
        }

        String dirPath = args[0];
        File dir = new File(dirPath);
        if (!dir.isDirectory()) {
            System.out.println("Specified path does not exist, quitting.");
            return;
        }

        int fileCount;
        try {
            fileCount = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            createFiles(fileCount, dirPath);
            System.out.println("Files created, press <ENTER> to close & delete the files and exit the program.");
            String line = System.console().readLine();
            deleteFiles();
        }
        catch(Exception e) {
            System.out.printf("Something went wrong: %s\n", e.getMessage());
        }
    }

    private static void help() {
        System.out.println("Create specified number of files in a specified folder.");
        System.out.println("Syntax: java FileLimits <path to create files> <number of files>");
        System.out.println("E.g.: java FileLimits ./temp 100");
    }

    private static void createFiles(int fileCount, String dirPath) throws IOException {
        files = new File[fileCount];
        for(int n=0; n < fileCount; n++){
            Path path = Paths.get(dirPath, String.format("temp%d", n));
            File file = path.toFile();
            if (!file.exists()) {
                if (file.createNewFile()) {
                    files[n] = file;
                }
                else {
                    files[n] = null;
                }
            }
        }
    }

    private static void deleteFiles() {
        if (files != null) {
            for (int n = 0; n < files.length; n++) {
                if (files[n] != null) {
                    files[n].delete();
                }
            }
        }
    }

    static File[] files = null;
}