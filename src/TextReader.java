import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TextReader {
    public static List<String> readFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        StringTokenizer st;
        ArrayList<String> out = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            st = new StringTokenizer(line);
            while (st.hasMoreElements()) {
                out.add(st.nextToken());
            }
        }
        return out;
    }
}
