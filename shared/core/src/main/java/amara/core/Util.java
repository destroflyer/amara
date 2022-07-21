package amara.core;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.IntFunction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class Util{

    public static void browseURL(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Dimension getScreenResolution() {
        return Toolkit.getDefaultToolkit().getScreenSize(); 
    }

    public static File chooseFile(boolean loadOrSave, String directory, FileFilter... filters) {
        File file = null;
        JFileChooser fileChooser = new JFileChooser(new File(directory).getAbsolutePath());
        if ((filters != null) && (filters.length > 0)) {
            for(FileFilter filter : filters){
                fileChooser.addChoosableFileFilter(filter);
            }
            fileChooser.setFileFilter(filters[0]);
        }
        int result;
        if (loadOrSave) {
            result = fileChooser.showOpenDialog(fileChooser);
        } else {
            result = fileChooser.showSaveDialog(fileChooser);
        }
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        return file;
    }

    public static <T> T createObjectByClassName(String className) {
        try {
            return (T) Class.forName(className).newInstance();
        } catch (ReflectiveOperationException ex) {
            System.out.println("Error while creating object of class '" + className + "'.");
        }
        return null;
    }

    public static short[] convertToArray_Short(Collection<Short> collection) {
        short[] array = new short[collection.size()];
        Iterator<Short> iterator = collection.iterator();
        for (int i = 0; i < array.length; i++){
            array[i] = iterator.next();
        }
        return array;
    }

    public static int[] convertToArray_Integer(Collection<Integer> collection) {
        int[] array = new int[collection.size()];
        Iterator<Integer> iterator = collection.iterator();
        for (int i = 0; i < array.length; i++){
            array[i] = iterator.next();
        }
        return array;
    }

    public static float[] convertToArray_Float(Collection<Float> collection) {
        float[] array = new float[collection.size()];
        Iterator<Float> iterator = collection.iterator();
        for (int i = 0; i < array.length; i++) {
            array[i] = iterator.next();
        }
        return array;
    }

    public static <T> LinkedList<T[]> split(Collection<T> collection, int length, IntFunction<T[]> arrayConstructor) {
        int arraysCount = (int) Math.ceil(((float) collection.size()) / length);
        LinkedList<T[]> arrays = new LinkedList<>();
        Iterator<T> iterator = collection.iterator();
        for (int i = 0; i < arraysCount; i++) {
            int arrayLength = length;
            if (i == (arraysCount - 1)) {
                arrayLength = (collection.size() - ((arraysCount - 1) * length));
            }
            T[] array = arrayConstructor.apply(arrayLength);
            for (int r = 0; r < array.length; r++) {
                array[r] = iterator.next();
            }
            arrays.add(array);
        }
        return arrays;
    }

    public static int[] cloneArray(int[] array) {
        int[] clonedArray = new int[array.length];
        System.arraycopy(array, 0, clonedArray, 0, clonedArray.length);
        return clonedArray;
    }

    public static float[] parseToFloatArray(String[] array) {
        float[] floatArray = new float[array.length];
        for (int i = 0; i < floatArray.length; i++) {
            floatArray[i] = Float.parseFloat(array[i]);
        }
        return floatArray;
    }

    public static float round(float value, int decimals) {
        return new BigDecimal(value).setScale(decimals, RoundingMode.HALF_UP).floatValue();
    }

    public static String lineBreakText(String text, int lineLength) {
        String result = "";
        String[] words = text.split(" ");
        String line = "";
        for (String word : words) {
            if ((line.length() + 1 + word.length()) > lineLength) {
                if (result.length() > 0) {
                    result += "\n";
                }
                result += line;
                line = "";
            } else if (line.length() > 0) {
                line += " ";
            }
            line += word;
        }
        if (line.length() > 0) {
            if (result.length() > 0) {
                result += "\n";
            }
            result += line;
        }
        return result;
    }

    public static int getNeededBitsCount(int value) {
        return (32 - Integer.numberOfLeadingZeros(value));
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
