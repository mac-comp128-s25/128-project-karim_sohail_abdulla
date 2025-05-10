import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Provides autocomplete functionality for food items using a Trie data structure.
 */
public class AutoComplete {
    private Trie trie;

    /**
     * Initializes the autocomplete structure with an empty Trie.
     */
    public AutoComplete() {
        trie = new Trie();
    }

    /**
     * Adds all unique food items from the menu into the trie.
     * @param menu The menu to extract items from.
     */
    public void addMenuItems(Menu menu) {
        if (menu == null) return;
        List<String> seen = new ArrayList<>();
        for (String meal : menu.getMenue().keySet()) {
            for (FoodItem item : menu.getMenuForMeal(meal)) {
                if (item != null && item.getName() != null) {
                    String name = item.getName().toLowerCase();
                    if (!seen.contains(name)) {
                        seen.add(name);
                        trie.insert(name, item);
                    }
                }
            }
        }
    }

    /**
     * Retrieves a list of food items that match the given prefix.
     * @param query The prefix to search.
     * @return List of matching food items.
     */
    public List<FoodItem> searchByPrefix(String query) {
        if (query == null) {
            return new ArrayList<>();
        }
        return trie.searchByPrefix(query.toLowerCase());
    }

    /**
     * Displays the search results in a given JPanel container.
     * @param results List of matched food items.
     * @param container Panel where the results are shown.
     */
    public void displaySearchResults(List<FoodItem> results, JPanel container) {
        container.removeAll();
        if (results == null || results.isEmpty()) {
            JLabel noResult = new JLabel("No matches found.");
            noResult.setFont(new Font("Poppins", Font.ITALIC, 14));
            container.add(noResult);
        } else {
            for (FoodItem item : results) {
                JLabel label = new JLabel("• " + item.getName());
                label.setFont(new Font("Poppins", Font.PLAIN, 14));
                label.setForeground(new Color(60, 60, 60));
                container.add(label);
            }
        }
        container.revalidate();
        container.repaint();
    }

}