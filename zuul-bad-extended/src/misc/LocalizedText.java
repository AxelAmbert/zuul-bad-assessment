package misc;

import communication.Controller;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * static Class LocalizedText - an easy way to handle text.
 * <p>
 * The LocalizedText class is useful to handle different kind
 * of text.
 * A single call to a function of this class let your game switch
 * language or update dialogues.
 *
 * @author Axel Ambert
 * @version 1.0
 */

public class LocalizedText
{
  private static HashMap<String, String> allTexts;
  private static String locale = "en";
  private static String textsPath = "";

  /**
   * Call this function to load the localized texts.
   * It will look into the path and look for every
   * text in a corresponding locale.
   *
   * @param path the file path to look for to load the texts
   * @param locale the locale to use to load the texts
   */
  public static void setLocaleTexts(String path, String locale)
  {
    JSONArray array;

    LocalizedText.locale = locale;
    LocalizedText.textsPath = path;
    LocalizedText.allTexts = new HashMap<>();
    array = LocalizedText.getTextsArray(path);
    for (Object object : array) {
      LocalizedText.addTextToMap(object);
    }
  }

  /**
   * Get a JSONArray representing every texts in the file.
   *
   * Look for the file at filePath and create a JSONArray
   * containing every texts in the file.
   *
   * @param path the JSON file to read to update the texts
   * @return the JSONArray of every texts.
   */
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
      Controller.showError("Error while parsing rooms " + exception.toString());
    }
    return (array);
  }

  /**
   * Get a text into the key->text map
   * Use this function in a loop or a forEach to add every text.
   *
   * @param textObject the actual text to add
   */
  private static void addTextToMap(Object textObject)
  {
    JSONObject parsedText = new JSONObject(textObject.toString());
    String textKey = parsedText.getString("key");
    String text = parsedText.getString("text_" + LocalizedText.locale);

    LocalizedText.allTexts.put(textKey, text);
  }

  /**
   * Get a text related to a key and format it.
   * If a key "test" is related to the text "this is %s %s"
   * and the function call is "getText("test", "a", "test");"
   * The result will be : "This is a test"
   *
   * @param key the key related to the text
   * @param args the vaargs to add to format the text
   * @return the formatted text
   */
  public static String getText(String key, Object... args)
  {
    return (String.format(LocalizedText.allTexts.get(key), args));
  }

  /**
   * Update the texts with a new locale.
   * Use this function if you want to switch the language.
   *
   * @param newLocale the new locale for the text.
   */
  public static void changeLocale(String newLocale)
  {
    LocalizedText.locale = newLocale;
    LocalizedText.setLocaleTexts(LocalizedText.textsPath, LocalizedText.locale);
  }
}
