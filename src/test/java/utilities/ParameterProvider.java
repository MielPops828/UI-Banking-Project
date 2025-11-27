package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class ParameterProvider {
    private static final String PARAMETERS_PATH = "configurations/config.properties";
    private static ParameterProvider instance;
    private final Map<String, String> parameters = new HashMap<>();

    private ParameterProvider() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PARAMETERS_PATH)) {
            if (inputStream == null) {
                throw new RuntimeException("Файл конфигурации не найден: " + PARAMETERS_PATH);
            }
            Properties prop = new Properties();
            prop.load(inputStream);
            for (String key : prop.stringPropertyNames()) {
                parameters.put(key, prop.getProperty(key));
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке конфигурации", e);
        }
    }

    public static String get(String key) {
        if (instance == null) {
            instance = new ParameterProvider();
        }
        return instance.parameters.get(key);
    }
}
