import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(100, 3, 5, 120.5);
        GameProgress game2 = new GameProgress(80, 5, 8, 200.2);
        GameProgress game3 = new GameProgress(120, 6, 10, 300.8);

        saveGame("/sdssgag/savegames/save1.dat", game1);
        saveGame("/sdssgag/savegames/save2.dat", game2);
        saveGame("/sdssgag/savegames/save3.dat", game3);

        List<String> filesToZip = new ArrayList<>();
        filesToZip.add("/sdssgag/savegames/save1.dat");
        filesToZip.add("/sdssgag/savegames/save2.dat");
        filesToZip.add("/sdssgag/savegames/save3.dat");

        zipFiles("/sdssgag/savegames/zip.zip", filesToZip);

        for (String file : filesToZip) {
            File f = new File(file);
            if (!f.getPath().equals("/sdssgag/savegames/zip.zip")) {
                f.delete();
            }
        }
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String zipFilePath, List<String> filesToZip) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String filePath : filesToZip) {
                File file = new File(filePath);
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}