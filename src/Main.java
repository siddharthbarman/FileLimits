import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
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
            System.out.printf("Files created, %d streams opened.\nPress <ENTER> to close & delete the files and exit the program.\n", streamsOpenedCount);
            String line = System.console().readLine();


        }
        catch(Exception e) {
            System.out.printf("Something went wrong: %s\nNumber of open files: %d.\n", e.getMessage(), streamsOpenedCount);
        }
        finally {
            int closedCount = closeFiles();
            int deletedCount = deleteFiles();
            System.out.printf("%d files closed, %d files deleted.\n", closedCount, deletedCount);
        }
    }

    private static void help() {
        System.out.println("Create specified number of files in a specified folder.");
        System.out.println("Syntax: java FileLimits <path to create files> <number of files>");
        System.out.println("E.g.: java FileLimits ./temp 100");
    }

    private static void createFiles(int fileCount, String dirPath) throws IOException {
        files = new File[fileCount];
        fis = new FileInputStream[fileCount];

        for(int n=0; n < fileCount; n++){
            Path path = Paths.get(dirPath, String.format("temp%d", n));
            File file = path.toFile();
            if (!file.exists()) {
                if (file.createNewFile()) {
                    files[n] = file;
                    fis[n] = new FileInputStream(file);
                    streamsOpenedCount++;
                }
                else {
                    files[n] = null;
                    fis[n] = null;
                }
            }
        }
    }

    private static int closeFiles() throws IOException {
        int filesClosedCount = 0;
        if (files != null) {
            for (int n = 0; n < fis.length; n++) {
                if (fis[n] != null) {
                    fis[n].close();
                    filesClosedCount++;
                }
            }
        }
        return filesClosedCount;
    }

    private static int deleteFiles() {
        int deletedCount = 0;
        if (files != null) {
            for (int n = 0; n < files.length; n++) {
                if (files[n] != null) {
                    files[n].delete();
                    deletedCount++;
                }
            }
        }
        return deletedCount;
    }

    static File[] files = null;
    static FileInputStream[] fis = null;
    static int streamsOpenedCount = 0;
}