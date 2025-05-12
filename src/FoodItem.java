import java.util.List;

/**
 * Represents a single item of food on the menu. It has attributes: nameand category (or categories if the item is part of multiple categories).
 */
public class FoodItem {

    /** the name of the food item */
    private String name;    

    /** list of categories this food items belongs to */
    private List<String> category;

    /** rating of this food item */
    private int rating;


    /**
     * constructor: creates a FoodItem instance with the specified name, description, and categories.
     *
     * @param name        the name of the food item
     * @param description the description of the food item
     * @param category    a list of categories (e.g: "Vegan", "Spicy", "Halal")
     */
    public FoodItem(String name, List<String> category){
        this.name = name;
        this.category = category;
    }

    /**
     * returns the name of the food item.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the food item.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }   

    /**
     * returns the current rating of the food item.
     *
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * sets the rating of the food item.
     *
     * @param rate the new rating
     */
    public void setRating(int rate) {
        this.rating = rate;
    }

    /**
     * increases the rating of the food item by one.
     */
    public void upVote(){
        rating++;
    }

    /**
     * decreases the rating of the food item by one.
     */
    public void downVote(){
        rating--;
    }

    /**
     * returns the list of categories associated with this food item.
     *
     * @return the category list
     */
    public List<String>  getCategory() {
        return category;
    }
    
    /**
     * sets the categories associated with this food item.
     *
     * @param category a list of category labels
     */
    public void setCategory(List<String> category) {
        this.category = category;
    }

    /**
     * returns the string representation of the food item, which is its name.
     *
     * @return the name of the food item
     */
    @Override
    public String toString() {
        return name;
    }
}
