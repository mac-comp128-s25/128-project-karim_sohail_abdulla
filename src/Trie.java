import java.util.ArrayList;
import java.util.List;

/**
 * Trie structure for fast prefix-based retrieval of FoodItems.
 * Each path through the Trie can store multiple food items at the end node.
 */
public class Trie {
    private TrieNode root;

    /**
     * Initializes the Trie with a root node.
     */
    public Trie() {
        root = new TrieNode();
    }

    /**
     * Inserts a word and its corresponding FoodItem into the Trie.
     * @param word The word to insert.
     * @param item The food item associated with the word.
     */
    public void insert(String word, FoodItem item) {
        if (word == null || item == null) return;
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.getChildren().putIfAbsent(c, new TrieNode());
            current = current.getChildren().get(c);
        }
        current.getItems().add(item);
    }

    /**
     * Searches for all food items matching the given prefix.
     * @param prefix The input prefix.
     * @return List of all food items under that prefix.
     */
    public List<FoodItem> searchByPrefix(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            current = current.getChildren().get(c);
            if (current == null) {
                return new ArrayList<>();
            }
        }
        return getAllItemsFromNode(current);
    }

    /**
     * Traverses all children nodes to collect items under a subtree.
     * @param node The subtree root.
     * @return All items in that subtree.
     */
    private List<FoodItem> getAllItemsFromNode(TrieNode node) {
        List<FoodItem> result = new ArrayList<>();
        if (node != null) {
            result.addAll(node.getItems());
            for (TrieNode child : node.getChildren().values()) {
                result.addAll(getAllItemsFromNode(child));
            }
        }
        return result;
    }

    /**
     * Clears the Trie and resets the root node.
     */
    public void clear() {
        root = new TrieNode();
    }
}