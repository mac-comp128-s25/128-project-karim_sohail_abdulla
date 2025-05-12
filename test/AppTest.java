import java.util.*;
import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/*
 * This class contains tests for the core methods of our classes 
 * NOTE:
 * In addition to the unit testing shown below, we manually verified the correctness and accuracy
 * of the scraped menu data and our classes by comparing the UI output with Cafe Mac's official website.
 * This was important for the sake of confirming that real-time scraping and other classes functionalities were
 * working as intended.
 */


public class AppTest {
    Menu menu;
    FoodItem foodItem1;
    FoodItem foodItem2;
    FoodItem foodItem3;
    AutoComplete autoComplete;
    JPanel container;

    /**
 * Setup method to initialize menu, food items, categories, and autocomplete before each test.
 */
@BeforeEach
public void initialSetup(){
    menu = new Menu();
    foodItem1 = new FoodItem("Shawarma", null);
    foodItem2 = new FoodItem("Piza", null);
    foodItem3 = new FoodItem("Salad", null);

    List<String> foodItem1Category = new ArrayList<>();
    List<String> foodItem2Category = new ArrayList<>();
    List<String> foodItem3Category = new ArrayList<>();

    foodItem1Category.add("Halal");
    foodItem1Category.add("Gluten-Free");
    foodItem2Category.add("Vegetarian");
    foodItem3Category.add("Vegetarian");
    foodItem3Category.add("Vegan");

    foodItem1.setCategory(foodItem1Category);
    foodItem2.setCategory(foodItem2Category);
    foodItem3.setCategory(foodItem3Category);

    menu.addFoodItem("Lunch", foodItem1);
    menu.addFoodItem("Lunch", foodItem2);
    menu.addFoodItem("dinner", foodItem3);

    container = new JPanel();
    autoComplete = new AutoComplete();
    autoComplete.addMenuItems(menu);
}

//////////////////////////////////////////////////////////////////////////////////////////
//////////////////// FoodItem Class Tests ////////////////////////////

/**
 * Tests whether the upVote method increases rating correctly.
 */
@Test
public void testUpVoteIncreasesRating() {
    int initialRating = foodItem1.getRating();
    foodItem1.upVote();
    assertEquals(initialRating + 1, foodItem1.getRating());
}

/**
 * Tests whether the downVote method decreases rating correctly.
 */
@Test
public void testDownVoteDecreasesRating() {
    foodItem2.setRating(3);
    foodItem2.downVote();
    assertEquals(2, foodItem2.getRating());
}

//////////////////////////////////////////////////////////////////////////////////////////
//////////////////// Menu Class Tests ////////////////////////////

/**
 * Tests retrieval of items by meal type.
 */
@Test
public void testGetMenuForMeal() {
    List<FoodItem> lunchItems = menu.getMenuForMeal("Lunch");
    assertEquals(2, lunchItems.size());
    assertTrue(lunchItems.contains(foodItem1));
    assertTrue(lunchItems.contains(foodItem2)); 
}

/**
 * Tests retrieval of all items from the menu.
 */
@Test
public void testGetAllFoodItems() {
    List<FoodItem> allItems = menu.getAllFoodItems();
    assertEquals(3, allItems.size());
}

/**
 * Tests filtering items by a specific category (case-insensitive and whitespace-tolerant).
 */
@Test
public void testGetFoodItemsByCategory() {
    List<FoodItem> vegetarianItems = menu.getFoodItemsByCategory(" vegetarian ");
    assertEquals(2, vegetarianItems.size());
    assertTrue(vegetarianItems.contains(foodItem3));
}

/**
 * Tests correct selection of top food item by rating.
 */
@Test
public void testTopFoodItemSelection() {
    foodItem1.setRating(4);
    foodItem2.setRating(3);
    foodItem3.setRating(5);

    menu.updateTopKFoodItems(2);
    assertEquals(foodItem3, menu.getTopFoodItem());
}

/**
 * Tests top food selection logic when items have equal ratings.
 */
@Test
public void testTopFoodItemWithEqualRatings() {
    foodItem1.setRating(5);
    foodItem2.setRating(5);

    menu.updateTopKFoodItems(2);
    FoodItem top = menu.getTopFoodItem();
    assertTrue(top.equals(foodItem1) || top.equals(foodItem2));
}

/**
 * Tests retrieval of items for a non-existent meal type.
 */
@Test
public void testGetMenuForMissingMealType() {
    List<FoodItem> breakfastItems = menu.getMenuForMeal("Breakfast");
    assertTrue(breakfastItems.isEmpty());
}

//////////////////////////////////////////////////////////////////////////////////////////
//////////////////// JsoupScraper Class Tests ////////////////////////////

/**
 * This test is left commented because it depends on a specific date and menu state.
 */
// @Test
// public void testScrapeMenuIncludesExpectedItems() {
//     Menu scrapedMenu = JsoupScraper.scrapeMenu();
//     List<FoodItem> dinnerItems = scrapedMenu.getMenuForMeal("DINNER");
//     assertTrue(dinnerItems.size() > 0, "Scraped lunch menu should not be empty.");
//     // Additional assertions would go here.
// }

//////////////////////////////////////////////////////////////////////////////////////////
//////////////////// AutoComplete Class Tests ////////////////////////////

/**
 * Tests valid item insertion and retrieval by prefix.
 */
@Test
public void testAddMenuItemsValidItems() {
    List<FoodItem> results = autoComplete.searchByPrefix("sha");
    assertEquals(1, results.size());
    assertEquals("Shawarma", results.get(0).getName());

    results = autoComplete.searchByPrefix("pi");
    assertEquals(1, results.size());
    assertEquals("Piza", results.get(0).getName());

    results = autoComplete.searchByPrefix("sal");
    assertEquals(1, results.size());
    assertEquals("Salad", results.get(0).getName());
}

/**
 * Tests behavior when adding null menu to AutoComplete.
 */
@Test
public void testAddMenuItemsWithNullMenu() {
    AutoComplete newAutoComplete = new AutoComplete();
    newAutoComplete.addMenuItems(null);
    List<FoodItem> results = newAutoComplete.searchByPrefix("sha");
    assertTrue(results.isEmpty());
}

/**
 * Tests behavior when null items or names are added.
 */
@Test
public void testAddMenuItemsWithNullItemsOrNames() {
    Menu newMenu = new Menu();
    newMenu.addFoodItem("Lunch", new FoodItem("Veggie Wrap", List.of("Vegetarian")));
    newMenu.addFoodItem("Lunch", new FoodItem(null, List.of("Vegetarian")));
    List<FoodItem> items = newMenu.getMenuForMeal("Lunch");
    items.add(null);

    AutoComplete newAutoComplete = new AutoComplete();
    newAutoComplete.addMenuItems(newMenu);

    List<FoodItem> results = newAutoComplete.searchByPrefix("veg");
    assertEquals(1, results.size());
    assertEquals("Veggie Wrap", results.get(0).getName());
}

/**
 * Tests valid prefix-based searching.
 */
@Test
public void testSearchByAValidPrefix() {
    List<FoodItem> results = autoComplete.searchByPrefix("s");
    assertEquals(2, results.size());
    assertTrue(results.stream().anyMatch(item -> item.getName().equals("Shawarma")));
    assertTrue(results.stream().anyMatch(item -> item.getName().equals("Salad")));
}

/**
 * Tests behavior when an empty string is used as prefix.
 */
@Test
public void testSearchByEmptyPrefix() {
    List<FoodItem> results = autoComplete.searchByPrefix("");
    assertEquals(3, results.size());
    assertTrue(results.contains(foodItem1));
    assertTrue(results.contains(foodItem2));
    assertTrue(results.contains(foodItem3));
}

/**
 * Ensures prefix matching is case-insensitive.
 */
@Test
public void testCaseInsensitiveSearchByPrefix() {
    List<FoodItem> results = autoComplete.searchByPrefix("SHA");
    assertEquals(1, results.size());
    assertEquals("Shawarma", results.get(0).getName());
}

/**
 * Verifies null input returns empty results.
 */
@Test
public void testSearchByPrefixWithNullQuery() {
    List<FoodItem> results = autoComplete.searchByPrefix(null);
    assertTrue(results.isEmpty());
}
}