import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * This class is responsible about scraping menu data from the Cafe Mac website using Jsoup library.
 * It extracts food items from the "Specials" sections of the menu and put them into categorizes based on dietary labels.
 */
public class JsoupScraper {
    private static final String MENU_URL = "https://macalester.cafebonappetit.com/";


    /**
    * scrapes the menu from Cafe Mac's website, and then parses the food items into a Menu object.
    * Only includes items listed under "Specials" sections for each meal becasue food in the other tabs is served on regular bases and assumed to be known by users.
    * Uses label alt text to categorize items (like: Vegan, Halal).
    * 
    * @return A populated Menu object (or empty if scraping fails).
    */
    public static Menu scrapeMenu() {
        Menu menu = new Menu();
        try {
            Document doc = Jsoup.connect(MENU_URL).get();
    
            Elements mealSections = doc.select("section.panel.s-wrapper.site-panel--daypart");
            if (mealSections.isEmpty()) { // handles the case when meal sections are empty. This happens sometimes, and so we don’t want the program to crash
                System.err.println("No meal sections found.");
                return menu;
            }

            for (Element meal : mealSections) {
                String mealTime = meal.attr("data-jump-nav-title").toUpperCase(); // This would get the meal time. Uses defensive programming to deal with the case when the user enters letter of different format from what is on the menu

                Element specialsTab = meal.select("button:contains(" + mealTime + " Specials)").first(); // get the "Specials" tab button (Lunch Specials or Dinner Specials). This is the unique food on a given day. All the other food is repetitive
               
                if (specialsTab != null) {
                    String tabContentId = specialsTab.attr("aria-controls");// the special Items tab has a special id, that when you click on it, it views the content of the tab. The content of the tab is a div, and the id is the one we fetched from the button.
    
                    Elements specialsContent = meal.select("div#" + tabContentId);// fetch the content under the 'Specials' tab. the content is stored in a div, and the id is the one we fetched from the button.
    
                    Elements items = specialsContent.select("div.site-panel__daypart-item");// extract food items in the Specials section
                 
                    for (Element item : items) {
                        Element nameElement = item.selectFirst("button.h4.site-panel__daypart-item-title");
                        String name = nameElement != null ? nameElement.text().trim() : "Unknown Item";
                        
                        List<String> itemLabels = new ArrayList<>();

                        Elements labels = item.select("img[alt]");
                        
                        Set<String> addedCategories = new HashSet<>();
                        Set<String> addedFoodItems = new HashSet<>();
    
                        for (Element label : labels) {
                            String labelText = label.attr("alt").toLowerCase();// the dietary categories are found inside the 'alt' text
                            System.out.println("Label text: " + labelText);

                            String itemCategory = getCategoryFromLabel(labelText);

                            if (itemCategory != null) {
                                itemLabels.add(itemCategory);

                                if (!addedFoodItems.contains(name)) { // fixed the problem that some items were added twice for each meal
                                    FoodItem foodItem = new FoodItem(name, itemLabels);
                                    menu.addFoodItem(mealTime, foodItem);
                                    addedCategories.add(itemCategory);  // this would mark this category as added
                                    addedFoodItems.add(name);  // Track that this item has been added
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menu;
    }

    /**
     * Helper method to convert label alt text into category names that would be provided later for each FoodItem object.
     * 
     * @param labelText The alt attribute from the label image.
     * @return Mapped category string or null if unrecognized.
     */
    private static String getCategoryFromLabel(String labelText) {
        if (labelText.contains("vegetarian")) {
            return "Vegetarian";
        } else if (labelText.contains("vegan")) {
            return "Vegan";
        } else if (labelText.contains("gluten")) {
            return "Gluten-Free";
        }  else if (labelText.contains("halal")) {
            return "Halal";
        } 
        return null;    
    }
}