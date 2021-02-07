package amara.core.settings;

import java.util.function.Consumer;

public interface SettingsSubscriptions {

    void subscribeInteger(String key, Consumer<Integer> listener);

    void subscribeFloat(String key, Consumer<Float> listener);

    void subscribeBoolean(String key, Consumer<Boolean> listener);

    void subscribeString(String key, Consumer<String> listener);
}
