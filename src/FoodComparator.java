import java.util.Comparator;

/**
 * A customized comparator to sort FoodItem objects based on their ratings.
 * First it sorts by rating in descending order, but if ratings are equal, sorts alphabetically by name.
 */
public class FoodComparator implements Comparator<FoodItem> {


    /**
     * Compares two FoodItem objects for ordering.
     *
     * @param a the first FoodItem to compare
     * @param b the second FoodItem to compare
     * @return a numericl value showing the result of the comparison: negative(1st item > 2nd items), positive(2nd item > 1st item), zero(1st item = 2nd item).
     */
    @Override
    public int compare(FoodItem a, FoodItem b) {
        int ratingComparison = b.getRating() - a.getRating();
        if (ratingComparison != 0) {
            return ratingComparison;
        }
        return a.getName().compareTo(b.getName());
    }

}