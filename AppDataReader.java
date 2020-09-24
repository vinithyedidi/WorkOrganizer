package workOrganizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppDataReader {

    public static final String FILE_PATH = AppDataReader.class.getResource("AppData.csv").getPath();
    
    private List<String> notes;

    public AppDataReader() {
        notes = new ArrayList<>();
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(FILE_PATH));
            String line = "";
            while ((line = bReader.readLine()) != null) {
                notes.add(line);
            }
            bReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getNotes() {
        return notes;
    }

    public void writeToFile(List<String> newNotes) {
        try {
            FileWriter eraseFile = new FileWriter(FILE_PATH, false);
            eraseFile.close();

            BufferedWriter bWriter = new BufferedWriter(new FileWriter(FILE_PATH));
            for (String line : newNotes) {
                if (line.isBlank()) {
                } else if (line != null) {
                    bWriter.write(line);
                    bWriter.newLine();
                }
            }
            bWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
