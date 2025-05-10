import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a single node in a Trie(prefix tree).
 */
public class TrieNode {
    private Map<Character, TrieNode> children;
    private List<FoodItem> items;

    /**
     * Constructs an empty TrieNode.
     */
    public TrieNode() {
        children = new HashMap<>();
        items = new ArrayList<>();
    }

    /**
     * Returns the children of the node.
     * 
     * @return A map of characters to their corresponding child TrieNode.
     */
    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    /**
     * Returns the list of FoodItems associated with this node.
     * 
     * @return List of FoodItem objects.
     */
    public List<FoodItem> getItems() {
        return items;
    }
}