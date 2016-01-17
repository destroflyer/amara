package amara.core;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
/**
 *
 * @author carl
 */
public class Util{
    
    public static ImageIcon getResourceImageIcon(String imageResourcePath, int width, int height){
        return new ImageIcon(getResourceImage(imageResourcePath, width, height));
    }
    
    public static Image getResourceImage(String imageResourcePath, int width, int height){
        return getResourceImage(imageResourcePath).getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
    
    public static BufferedImage getResourceImage(String imageResourcePath){
        try{
            return ImageIO.read(getResourceURL(imageResourcePath));
        }catch(Exception ex){
            System.out.println("Error while reading image file '" + imageResourcePath + "'.");
        }
        return null;
    }
    
    public static boolean existsResource(String resourcePath){
        return (getResourceURL(resourcePath) != null);
    }
    
    public static InputStream getResourceInputStream(String resourcePath){
        return Util.class.getResourceAsStream(resourcePath);
    }
    
    public static URL getResourceURL(String resourcePath){
        return Util.class.getResource(resourcePath);
    }
    
    public static void browseURL(String url){
        try{
            Desktop.getDesktop().browse(new URI(url));
        }catch(Exception ex){
            System.out.println("Error while browsing url '" + url + "'.");
        }
    }
    
    public static Dimension getScreenResolution(){
        return Toolkit.getDefaultToolkit().getScreenSize(); 
    }

    public static File chooseFile(boolean loadOrSave, String directory, FileFilter... filters){
        File file = null;
        JFileChooser fileChooser = new JFileChooser(new File(directory).getAbsolutePath());
        if((filters != null) && (filters.length > 0)){
            for(int i=0;i<filters.length;i++) {
                fileChooser.addChoosableFileFilter(filters[i]);
            }
            fileChooser.setFileFilter(filters[0]);
        }
        int result;
        if(loadOrSave){
            result = fileChooser.showOpenDialog(fileChooser);
        }
        else{
            result = fileChooser.showSaveDialog(fileChooser);
        }
        if(result == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
        }
        return file;
    }
    
    public static String getFormattedDate(long timestamp){
        return getFormattedDate(timestamp, false);
    }
    
    public static String getFormattedDate(long timestamp, boolean onlyDate){
        DateFormat dateFormat = new SimpleDateFormat("d.M.y" + (onlyDate?"":", k:m"));
        return dateFormat.format(new Date(timestamp * 1000));
    }
    
    public static boolean isTimeElapsed(long start, int duration){
        return ((System.currentTimeMillis() - start) >= duration);
    }
    
    public static float getTimeBasedDistance(long lastTimestamp, int intervall){
        return (((float) (System.currentTimeMillis() - lastTimestamp)) / intervall);
    }
    
    public static int getPercentage_Rounded(float value, float percentageValue){
        return (int) Math.round(getPercentage(value, percentageValue));
    }
    
    public static float getPercentage(float value, float percentageValue){
        return ((percentageValue / value) * 100);
    }
    
    public static int getPercentageValue_Rounded(float value, float percentage){
        return (int) Math.round(getPercentageValue(value, percentage));
    }
    
    public static float getPercentageValue(float value, float percentage){
        return ((value * percentage) / 100);
    }
    
    public static <T> Class<? extends T> getClassForName(String className, Class<T> convertionClass){
        try{
            return (Class<? extends T>) Class.forName(className);
        }catch(ClassNotFoundException ex){
            System.out.println("Error while creating class for name '" + className + "'.");
        }
        return null;
    }
    
    public static <T> T createObjectByClass(Class<T> objectClass){
        return createObjectByClassName(objectClass.getName(), objectClass);
    }
    
    public static <T> T createObjectByClassName(String className, Class<T> convertionClass){
        try{
            return (T) Class.forName(className).newInstance();
        }catch(Exception ex){
            System.out.println("Error while creating object of class '" + className + "'.");
        }
        return null;
    }
    
    public static int getUniqueObjectID(Object object){
        return object.hashCode();
    }
    
    public static boolean containsArrayElement(int[] array, int element){
        if(array != null){
            for(int i=0;i<array.length;i++){
                if(array[i] == element){
                    return true;
                }
            }
        }
        return false;
    }
    
    public static <T> boolean containsArrayElement(T[] array, T element){
        for(int i=0;i<array.length;i++){
            if(array[i] == element){
                return true;
            }
        }
        return false;
    }
    
    public static <T> boolean containsListElement(List<T> list, T element){
        for(int i=0;i<list.size();i++){
            if(list.get(i) == element){
                return true;
            }
        }
        return false;
    }
    
    public static int[] convertToArray(Collection<Integer> collection){
        int[] array = new int[collection.size()];
        Iterator<Integer> iterator = collection.iterator();
        for(int i=0;i<array.length;i++){
            array[i] = iterator.next();
        }
        return array;
    }
    
    public static <T> LinkedList<T[]> split(Collection<T> collection, int length, Class objectClass){
        int arraysCount = (int) Math.ceil(((float) collection.size()) / length);
        LinkedList<T[]> arraysList = new LinkedList<T[]>();
        Iterator<T> iterator = collection.iterator();
        for(int i=0;i<arraysCount;i++){
            int arrayLength = length;
            if(i == (arraysCount - 1)){
                arrayLength = (collection.size() - ((arraysCount - 1) * length));
            }
            T[] array = (T[]) Array.newInstance(objectClass, arrayLength);
            for(int r=0;r<array.length;r++){
                array[r] = iterator.next();
            }
            arraysList.add(array);
        }
        return arraysList;
    }
    
    public static int[] cloneArray(int[] array){
        int[] clonedArray = new int[array.length];
        for(int i=0;i<clonedArray.length;i++){
            clonedArray[i] = array[i];
        }
        return clonedArray;
    }
    
    public static <T> T[] cloneArray(T[] array, Class objectClass){
        T[] clonedArray = (T[]) Array.newInstance(objectClass, array.length);
        for(int i=0;i<clonedArray.length;i++){
            clonedArray[i] = array[i];
        }
        return clonedArray;
    }
    
    public static int[] parseToIntArray(String[] array){
        int[] intArray = new int[array.length];
        for(int i=0;i<intArray.length;i++){
            intArray[i] = Integer.parseInt(array[i]);
        }
        return intArray;
    }
    
    public static float[] parseToFloatArray(String[] array){
        float[] floatArray = new float[array.length];
        for(int i=0;i<floatArray.length;i++){
            floatArray[i] = Float.parseFloat(array[i]);
        }
        return floatArray;
    }
    
    public static int getArraySum(int[] array){
        int sum = 0;
        for(int i=0;i<array.length;i++){
            sum += array[i];
        }
        return sum;
    }
    
    public static int[] merge(int[] array, int element){
        int[] mergedArray = new int[array.length + 1];
        for(int i=0;i<array.length;i++){
            mergedArray[i] = array[i];
        }
        mergedArray[array.length] = element;
        return mergedArray;
    }
    
    public static int[] getIncrementingNumbers(int count){
        return getIncrementingNumbers(count, 0);
    }
    
    public static int[] getIncrementingNumbers(int count, int offset){
        int[] numbers = new int[count];
        for(int i=0;i<numbers.length;i++){
            numbers[i] = (offset + i);
        }
        return numbers;
    }
    
    public static <T> T[] toArray(List list, Class objectClass){
        T[] array = (T[]) Array.newInstance(objectClass, list.size());
        for(int i=0;i<array.length;i++){
            array[i] = (T) list.get(i);
        }
        return array;
    }
    
    public static <T, E> E[] castArray(T[] array, Class<E> newClass){
        E[] castedArray = (E[]) Array.newInstance(newClass, array.length);
        for(int i=0;i<castedArray.length;i++){
            castedArray[i] = (E) array[i];
        }
        return castedArray;
    }
    
    public static <T> T[] extractObjects(Object[] array, Class objectClass){
        LinkedList<T> extractedObjectsList = new LinkedList<T>();
        for(int i=0;i<array.length;i++){
            Object object = array[i];
            if(objectClass.isAssignableFrom(object.getClass())){
                extractedObjectsList.add((T) object);
            }
        }
        T[] extractedArray = (T[]) Array.newInstance(objectClass, extractedObjectsList.size());
        extractedObjectsList.toArray(extractedArray);
        return extractedArray;
    }
    
    public static <T> T getRandomArrayElement(T[] array){
        return array[(int) (Math.random() * array.length)];
    }
    
    public static <T, E> T getHashKeyByValue(Map<T, E> map, E value){
        for(Entry<T, E> entry : map.entrySet()){
            if(value.equals(entry.getValue())){
                return entry.getKey();
            }
        }
        return null;
    }
    
    public static int convertUnit(int number, int unit){
        return (int) Math.ceil(((float) number) / unit);
    }
    
    public static int parseInt(String text){
        try{
            return Integer.parseInt(text);
        }catch(Exception ex){
        }
        return -1;
    }
    
    public static float round(double value, int decimals){
        return (float) new BigDecimal(value).setScale(decimals, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static float compensateFloatRoundingErrors(float number){
        final float MAX_FLOAT_ROUNDING_DIFFERENCE = 0.0001f;
        float remainder = (number % 1);
        if ((remainder < MAX_FLOAT_ROUNDING_DIFFERENCE) || (remainder > (1 - MAX_FLOAT_ROUNDING_DIFFERENCE))) {
            number = Math.round(number);
        }
        return number;
    }
    
    public static <T> boolean containsArrayIndex(T[] array, int index){
        if(array.length == 0){
            return false;
        }
        return ((index >= 0) && (index < array.length));
    }
    
    public static <T> boolean containsArrayIndex(T[][] array, int index1, int index2){
        if(array.length == 0){
            return false;
        }
        return ((index1 >= 0) && (index1 < array.length) && (index2 >= 0) && (index2 < array[0].length));
    }
    
    public static <T> ArrayList<T> getUniqueList(ArrayList<T> list){
        ArrayList<T> uniqueList = new ArrayList<T>();
        for(int i=0;i<list.size();i++){
            T element = list.get(i);
            if(!uniqueList.contains(element)){
                uniqueList.add(element);
            }
        }
        return uniqueList;
    }
    
    public static <T> T[] mergeArrays(T[]... arrays){
        Class arrayClass = null;
        LinkedList<T> mergedList = new LinkedList<T>();
        for(int i=0;i<arrays.length;i++){
            for(int r=0;r<arrays[i].length;r++){
                T element = arrays[i][r];
                mergedList.add(element);
                if(arrayClass != null){
                    while(!arrayClass.isAssignableFrom(element.getClass())){
                        arrayClass = arrayClass.getSuperclass();
                    }
                }
                else{
                    arrayClass = element.getClass();
                }
            }
        }
        T[] mergedArray = (T[]) Array.newInstance(arrayClass, mergedList.size());
        Object[] objectsArray = mergedList.toArray();
        for(int i=0;i<objectsArray.length;i++){
            mergedArray[i] = (T) objectsArray[i];
        }
        return mergedArray;
    }
    
    public static byte[][] splitArray(byte[] array, int partsCount){
        int partLength = (int) Math.ceil(((float) array.length) / partsCount);
        byte[][] parts = new byte[partsCount][];
        for(int i=0;i<partsCount;i++){
            int remainingElements = (array.length - (i * partLength));
            byte[] part = new byte[getMinimum(remainingElements, partLength)];
            for(int r=0;r<part.length;r++){
                part[r] = array[(i * partLength) + r];
            }
            parts[i] = part;
        }
        return parts;
    }
    
    public static int getRandomNumberBetween(int minimum, int maximum){
        return (((int) (Math.random() * (maximum - minimum + 1))) + minimum);
    }
    
    public static float getRandomNumberBetween_Decimal(int minimum, int maximum){
        return (((float) (Math.random() * (maximum - minimum + 1))) + minimum);
    }
    
    public static int getMinimum(int... numbers){
        int minimum = Integer.MAX_VALUE;
        for(int i=0;i<numbers.length;i++){
            if(numbers[i] < minimum){
                minimum = numbers[i];
            }
        }
        return minimum;
    }

    public static <T> T getArrayElement(T[][] array, int index1, int index2){
        if((array.length > index1) && (array[index1].length > index2)){
            return array[index1][index2];
        }
        return null;
    }
    
    public static int getNeededBitsCount(int value){
        return (32 - Integer.numberOfLeadingZeros(value));
    }
}
