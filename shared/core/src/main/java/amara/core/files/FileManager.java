/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package amara.core.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.util.LinkedList;

/**
 *
 * @author Carl
 */
public class FileManager{

    public static File getFile(String filePath){
        if(filePath.startsWith("http:")){
            try{
                return new File(new URI(filePath));
            }catch(Exception ex){
            }
        }
        return new File(filePath);
    }

    public static boolean isDirectory(File file){
        return getFileFormat(file).equals("");
    }

    public static String getFilePath(File file){
        String filePath = file.getPath();
        filePath = filePath.replace(File.separator, "/");
        if(filePath.startsWith("http:")){
            filePath = filePath.replace("http:", "http:/");
        }
        if(isDirectory(file)){
            filePath += "/";
        }
        return filePath;
    }

    public static String getFilePath(URL url){
        String filePath = url.toExternalForm();
        filePath = filePath.replace(File.separator, "/");
        return filePath;
    }

    public static String getFileContent(File file){
        return getFileContent(getFilePath(file));
    }

    public static String getFileContent(URL url){
        return getFileContent(getFilePath(url));
    }

    public static String getFileContent(String filePath){
        String text = "";
        String[] lines = getFileLines(filePath);
        for(int i=0;i<lines.length;i++){
            if(i != 0){
                text += "\n";
            }
            text += lines[i];
        }
        return text;
    }

    public static String[] getFileLines(String filePath){
        LinkedList<String> linesList = new LinkedList<>();
        try{
            BufferedReader reader;
            if(filePath.startsWith("http:")){
                reader = new BufferedReader(new InputStreamReader(new URL(filePath).openStream()));
            }
            else{
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"));
            }
            String line;
            while((line = reader.readLine()) != null){
                linesList.add(line);
            }
            reader.close();
        }catch(Exception ex){
            System.out.println("Error while reading file: " + ex.toString());
        }
        String[] lines = new String[linesList.size()];
        linesList.toArray(lines);
        return lines;
    }

    public static void putFileContent(String filePath, String content){
        try{
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF8"));
            String[] lines = content.split("\n");
            for(int i=0;i<lines.length;i++){
                if(i != 0){
                    writer.newLine();
                }
                writer.write(lines[i]);
            }
            writer.close();
        }catch(Exception ex){
            System.out.println("Error while writing file: " + ex.toString());
        }
    }

    public static void downloadFile(URL sourceURL, String destinationFilePath){
        try{
            InputStream inputStream = sourceURL.openStream();
            FileOutputStream fileOutputStream = new FileOutputStream(destinationFilePath);
            byte[] buffer = new byte[2048];
            int readBytes;
            while((readBytes = inputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, readBytes);
            }
            inputStream.close();
            fileOutputStream.close();
        }catch(Exception ex){
            System.out.println("Error while downloading file from '" + sourceURL.toExternalForm() + "' to '" + destinationFilePath + "'.");
        }
    }
    
    public static String getFileChecksum_MD5(File file){
        try{
            FileInputStream fileInputStream =  new FileInputStream(file);
            byte[] buffer = new byte[2048];
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            int readBytes;
            do{
                readBytes = fileInputStream.read(buffer);
                if(readBytes > 0){
                    messageDigest.update(buffer, 0, readBytes);
                }
            }while(readBytes != -1);
            fileInputStream.close();
            String checksum = new BigInteger(1, messageDigest.digest()).toString(16);
            while(checksum.length() < 32){
                checksum = "0" + checksum;
            }
            return checksum;
        }catch(Exception ex){
            System.out.println("Error while calculating the checksum of the file '" + file.getPath() + "'.");
        }
        return null;
    }

    public static String getFileFormat(File file){
        String fileName = file.getName();
        int lastSeperatorIndex = getLastSubstringIndex(fileName, ".");
        if(lastSeperatorIndex != -1){
            return fileName.substring(lastSeperatorIndex + 1);
        }
        return "";
    }

    public static String getFileDirectory(File file){
        String filePath = getFilePath(file);
        int lastSeperatorIndex = getLastSubstringIndex(filePath, "/");
        if(lastSeperatorIndex != -1){
            return filePath.substring(0, lastSeperatorIndex + 1);
        }
        return filePath;
    }

    private static int getLastSubstringIndex(String string, String substring){
        int substringLenth = substring.length();
        int currentSubstringOffset = string.length() - substringLenth;
        while (!string.substring(currentSubstringOffset, currentSubstringOffset + substringLenth).equals(substring)) {
            currentSubstringOffset--;
            if (currentSubstringOffset < 0) {
                return -1;
            }
        }
        return currentSubstringOffset;
    }

    public static String getFileName(File file){
        String filePath = getFilePath(file);
        if(filePath.startsWith("http:")){
            return filePath;
        }
        return file.getName();
    }
    
    public static void createDirectoryIfNotExists(String directoryPath){
        File file = new File(directoryPath);
        if(!file.exists()){
            file.mkdir();
        }
    }

    public static boolean existsFile(String filePath) {
        return new File(filePath).exists();
    }
}
