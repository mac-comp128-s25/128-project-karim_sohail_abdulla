import java.awt.Font;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu {
    private Map<String, List<FoodItem>> menuItems;
    private Comparator<FoodItem> foodComparator;
    private List<FoodItem> topItems;

    /**
     * Initializes the Menu data structure with all the necessary variabl and top item tracker.
     */
    public Menu() {
        menuItems = new LinkedHashMap<>();
        foodComparator = new FoodComparator();
        topItems = new ArrayList<>();
    }

    /**
     * Adds a food item under a given meal type. Creates list if absent.
     * @param mealType Meal category like Breakfast or Dinner.
     * @param foodItem The food item to be added.
     */
    public void addFoodItem(String mealType, FoodItem foodItem) {
        menuItems.putIfAbsent(mealType.toUpperCase(), new ArrayList<>());
        menuItems.get(mealType.toUpperCase()).add(foodItem);
    }

    /**
     * Retrieves the food items associated with a meal type.
     * @param mealType The meal type to query.
     * @return List of food items.
     */
    public List<FoodItem> getMenuForMeal(String mealType) {
        return menuItems.getOrDefault(mealType.toUpperCase(), new ArrayList<>());
    }

    /**
     * Gathers all food items across all meals into one list.
     * @return Flattened list of all food items.
     */
    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> allFoodItems = new ArrayList<>();
        for (List<FoodItem> items : menuItems.values()) {
            allFoodItems.addAll(items);
        }
        return allFoodItems;
    }

    /**
     * Updates the list of top K food items based on rating.
     * @param k Number of top items to include.
     */
    public void updateTopKFoodItems(int k) {
        PriorityQueue<FoodItem> maxHeap = new PriorityQueue<>(foodComparator);
        for (List<FoodItem> items : menuItems.values()) {
            maxHeap.addAll(items);
        }
        topItems.clear();
        for (int i = 0; i < k && !maxHeap.isEmpty(); i++) {
            topItems.add(maxHeap.poll());
        }
    }

    /**
     * Returns the current top K food items.
     * @return List of top items.
     */
    public List<FoodItem> getTopKFoodItems() {
        return new ArrayList<>(topItems);
    }

    /**
     * Finds the top-rated item within a specific category.
     * @param category Category to search within.
     * @return Highest-rated item or null.
     */
    public FoodItem getTopItemInCategory(String category) {
        List<FoodItem> items = getFoodItemsByCategory(category);
        if (items.isEmpty()) {
            return null;
        }
        PriorityQueue<FoodItem> maxHeap = new PriorityQueue<>(foodComparator);
        maxHeap.addAll(items);
        return maxHeap.poll();
    }

    /**
     * Renders the top K food items into a GUI panel.
     * @param panel Panel to populate with top items.
     */
    public void displayTopK(JPanel panel) {
        for (int i = 0; i < topItems.size(); i++) {
            JLabel label = new JLabel((i + 1) + ". " + topItems.get(i).getName());
            label.setFont(new Font("Poppins", Font.PLAIN, 14));
            panel.add(label);
        }
    }

    /**
     * Gets the top rated item across all meals.
     * @return Top FoodItem or null.
     */
    public FoodItem getTopFoodItem() {
        return topItems.isEmpty() ? null : topItems.get(0);
    }

    /**
     * Gives access to the entire meal-to-items mapping.
     * @return Menu map.
     */
    public Map<String, List<FoodItem>> getMenue() {
        return menuItems;
    }

    /**
     * Prints all menu items to console.
     */
    public void displayMenu() {
        for (String meal : menuItems.keySet()) {
            System.out.println("=== " + meal + " ===");
            for (FoodItem item : menuItems.get(meal)) {
                System.out.println("• " + item.getName());
            }
            System.out.println();
        }
    }

    /**
     * Finds all food items labeled with a specific category.
     * @param category Category name.
     * @return Filtered list of food items.
     */
    public List<FoodItem> getFoodItemsByCategory(String category) {
        List<FoodItem> result = new ArrayList<>();
        if (category == null) return result;

        String normalized = normalize(category);
        for (FoodItem item : getAllFoodItems()) {
            for (String tag : item.getCategory()) {
                if (normalize(tag).equals(normalized)) {
                    result.add(item);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Filters and sorts unique food items in a category.
     * @param category Category to search.
     * @return Unique, sorted list of items.
     */
    public List<FoodItem> getUniqueFoodItemsByCategory(String category) {
        List<FoodItem> filteredItems = getFoodItemsByCategory(category);
        Set<String> seenNames = new HashSet<>();
        List<FoodItem> uniqueItems = new ArrayList<>();
        for (FoodItem item : filteredItems) {
            String nameKey = normalize(item.getName()); 
            if (!seenNames.contains(nameKey)) {
                seenNames.add(nameKey);
                uniqueItems.add(item);
            }
        }
        uniqueItems.sort(foodComparator);
        return uniqueItems;
    }

    /**
     * Normalizes strings to lowercase, stripped of spaces and dashes.
     * @param text Input string.
     * @return Normalized version.
     */
    private String normalize(String text) {
        return text.trim().replaceAll("[\\s\\-]", "").toLowerCase();
    }
}