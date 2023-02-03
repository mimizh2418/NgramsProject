import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TextReader {
    private static List<String> readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        StringTokenizer st;
        List<String> out = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            st = new StringTokenizer(line);
            while (st.hasMoreElements()) {
                out.add(st.nextToken());
            }
        }
        return out;
    }

    public static List<List<String>> readFiles(File file) throws IOException {
        List<List<String>> out = new ArrayList<>();
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                out.addAll(readFiles(f));
            }
        } else {
            String filename = file.getName();
            if (filename.endsWith("txt")) out.add(readFile(file));
        }
        return out;
    }
}
