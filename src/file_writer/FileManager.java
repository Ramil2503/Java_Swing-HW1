package file_writer;

import java.io.FileWriter;
import java.io.FileReader;

public class FileManager {
    private String path;

    public FileManager(String path) {
        this.path = path;
    }

    public void saveInLog(String text){
        try (FileWriter writer = new FileWriter(path, true)){
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String loadLog(){
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(path);){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
