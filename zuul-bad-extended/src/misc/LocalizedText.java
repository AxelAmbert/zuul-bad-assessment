package misc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class LocalizedText
{
    private static HashMap<String, String> allTexts;
    private static String locale = "en";
    private static String textsPath = "";

    public static void setLocaleTexts(String path, String locale)
    {
        JSONArray array = null;

        LocalizedText.locale = locale;
        LocalizedText.textsPath = path;
        LocalizedText.allTexts = new HashMap<>();
        array = LocalizedText.getTextsArray(path);
        for (Object object : array) {
            LocalizedText.addTextToMap(object);
        }
    }

    private static JSONArray getTextsArray(String path)
    {
        String file = "";
        JSONArray array = null;
        JSONObject object = null;

        try {
            file = Files.readString(Path.of(path));
            object = new JSONObject(file);
            array = object.getJSONArray("texts");
        } catch (IOException exception) {
            System.out.println("Error while parsing rooms " + exception.toString());
        }
        return (array);
    }

    private static void addTextToMap(Object textObject)
    {
        JSONObject parsedText = new JSONObject(textObject.toString());
        String textKey = parsedText.getString("key");
        String text = parsedText.getString("text_" + LocalizedText.locale);

        LocalizedText.allTexts.put(textKey, text);
    }

    public static String getText(String key, Object... args)
    {
        return (String.format(LocalizedText.allTexts.get(key), args));
    }

    public static void changeLocale(String newLocale)
    {
        LocalizedText.locale = newLocale;
        LocalizedText.setLocaleTexts(LocalizedText.textsPath, LocalizedText.locale);
    }
}
