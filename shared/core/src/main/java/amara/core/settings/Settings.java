package amara.core.settings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import amara.core.Util;
import amara.core.files.FileManager;

public class Settings {

    public static final String FILE__PATH = "./settings.ini";
    public static final String FILE__COMMENT_LINE_MARKER = "#";
    public static final String FILE__SEPERATOR_KEY_VALUE = "=";
    public static final String FILE__BOOLEAN_TRUE = "true";
    public static final String FILE__BOOLEAN_FALSE = "false";
    private static HashMap<String, String> values = new HashMap<>();
    private static HashMap<String, List<Consumer<Integer>>> listenersInt = new HashMap<>();
    private static HashMap<String, List<Consumer<Float>>> listenersFloat = new HashMap<>();
    private static HashMap<String, List<Consumer<Boolean>>> listenersBoolean = new HashMap<>();
    private static HashMap<String, List<Consumer<String>>> listenersString = new HashMap<>();
    static {
        set("server_game_host", "destrostudios.com");
        set("server_game_port", 33900);
        DefaultSettings.setDefaults(new IngameSettings());
        reloadFile();
    }

    public static void reloadFile() {
        if (FileManager.existsFile(FILE__PATH)) {
            String[] lines = FileManager.getFileLines(FILE__PATH);
            for (String line : lines) {
                String[] keyValuePair = getKeyValuePair(line);
                if (keyValuePair != null) {
                    set(keyValuePair[0], keyValuePair[1]);
                }
            }
        } else {
            saveFile();
        }
    }

    private static String[] getKeyValuePair(String line) {
        if (!line.startsWith(FILE__COMMENT_LINE_MARKER)) {
            int seperatorIndex = line.indexOf(FILE__SEPERATOR_KEY_VALUE);
            if (seperatorIndex > 0) {
                String key = line.substring(0, seperatorIndex);
                String value = line.substring(seperatorIndex + 1);
                return new String[]{key, value};
            }
        }
        return null;
    }

    public static void set(String key, int value) {
        set(key, toString(value));
    }

    public static void set(String key, float value) {
        set(key, toString(value));
    }

    public static void set(String key, boolean value) {
        set(key, toString(value));
    }

    public static void set(String key, String value) {
        String oldValue = values.put(key, value);
        if (!value.equals(oldValue)) {
            notify(key, value);
        }
    }

    private static void notify(String key, String value) {
        notify(key, value, listenersInt, Settings::toInteger);
        notify(key, value, listenersFloat, Settings::toFloat);
        notify(key, value, listenersBoolean, Settings::toBoolean);
        notify(key, value, listenersString, Function.identity());
    }

    private static <T> void notify(String key, String value, HashMap<String, List<Consumer<T>>> listeners, Function<String, T> convert) {
        List<Consumer<T>> consumers = listeners.get(key);
        if (consumers != null) {
            T convertedValue = convert.apply(value);
            for (Consumer<T> consumer : consumers) {
                consumer.accept(convertedValue);
            }
        }
    }

    public static int getInteger(String key) {
        return toInteger(get(key));
    }

    public static float getFloat(String key) {
        return toFloat(get(key));
    }

    public static boolean getBoolean(String key) {
        return toBoolean(get(key));
    }

    public static String get(String key) {
        return values.get(key);
    }

    public static boolean toBoolean(String value) {
        return FILE__BOOLEAN_TRUE.equals(value);
    }

    public static int toInteger(String value) {
        return Integer.parseInt(value);
    }

    public static float toFloat(String value) {
        return Float.parseFloat(value);
    }

    public static String toString(boolean value) {
        return (value ? FILE__BOOLEAN_TRUE : FILE__BOOLEAN_FALSE);
    }

    public static String toString(int value) {
        return ("" + value);
    }

    public static String toString(float value) {
        int valueInt = (int) value;
        if (valueInt == value) {
            return ("" + valueInt);
        }
        return ("" + Util.round(value, 4));
    }

    public static void saveFile() {
        String fileContent = "#Settings";
        String[] keySet = values.keySet().toArray(new String[values.size()]);
        Arrays.sort(keySet, String.CASE_INSENSITIVE_ORDER);
        for (String key : keySet) {
            String value = get(key);
            fileContent += "\n" + key + FILE__SEPERATOR_KEY_VALUE + value;
        }
        FileManager.putFileContent(FILE__PATH, fileContent);
    }

    public static void subscribeInteger(String key, Consumer<Integer> listener) {
        subscribe(listenersInt, key, listener);
        listener.accept(getInteger(key));
    }

    public static void subscribeFloat(String key, Consumer<Float> listener) {
        subscribe(listenersFloat, key, listener);
        listener.accept(getFloat(key));
    }

    public static void subscribeBoolean(String key, Consumer<Boolean> listener) {
        subscribe(listenersBoolean, key, listener);
        listener.accept(getBoolean(key));
    }

    public static void subscribeString(String key, Consumer<String> listener) {
        subscribe(listenersString, key, listener);
        listener.accept(get(key));
    }

    private static <T> void subscribe(HashMap<String, List<Consumer<T>>> listeners, String key, Consumer<T> listener) {
        listeners.computeIfAbsent(key, k -> new LinkedList<>()).add(listener);
    }

    public static void unsubscribe(String key, Consumer<?> listener) {
        unsubscribe(listenersInt, key, listener);
        unsubscribe(listenersFloat, key, listener);
        unsubscribe(listenersBoolean, key, listener);
        unsubscribe(listenersString, key, listener);
    }

    private static <T> void unsubscribe(HashMap<String, List<Consumer<T>>> listeners, String key, Consumer<?> listener) {
        List<Consumer<T>> consumers = listeners.get(key);
        if (consumers != null) {
            consumers.remove(listener);
        }
    }
}
