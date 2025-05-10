import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


/**
 * Main application class for the CafeMac Meal Swipe Decider. It is a GUI-based tool to browse and vote on
 * the menu items. It supports an autocomplete search, top 5 food items view, and category filtering features.
 */
public class MainApp {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel suggestionsPanel;
    private CardLayout cardLayout;
    private Menu menu;
    private JLabel topItemLabel;
    private JsoupScraper jsoupScraper;
    private AutoComplete autoComplete;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton topFiveButton;
    private JLabel topFiveLabel;
    private static final Color MacOrange = new Color(0xFF8200);
    private static final Color MacBlue = new Color(0x003865);
    private static final Color BackgroundColor = new Color(252, 252, 252);
    private JPanel menuPanel;
    private String currentCategory = null;

    /**
     * Constructs the main application window and initializes the user interface components.
     */
    public MainApp() {
        frame = new JFrame("CafeMac Meal Swipe Decider");
        frame.setFont(new Font("Poppins", Font.BOLD, 40));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        suggestionsPanel = new JPanel();

        GuiHomePage homepage = new GuiHomePage(e -> handleStartButtonClick());
        mainPanel.add(homepage, "Splash");
        mainPanel.add(createMainAppPanel(), "MainApp");

        frame.setContentPane(mainPanel);
        frame.setVisible(true);

        autoComplete = new AutoComplete();
    }

    /**
     * Manage the transition of the application from the splash screen (welcome screen) to the main application panel.
     */
    private void handleStartButtonClick() {
        cardLayout.show(mainPanel, "MainApp");
    }

    /**
     * Creates a styled button with school colors and visual effects (hovering effects).
     *
     * @param text the text to display on the button
     * @return a styled JButton with custom appearance
     */
    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        
        button.setFocusPainted(false);
        button.setBackground(MacOrange);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        button.setFont(new Font("Poppins", Font.BOLD, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 220, 255), 2, true));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(255, 110, 0));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(MacOrange);
            }
        });
        
        return button;
    }

    /**
     * Constructs the main application panel containing the menu, search, and filter controls.
     *
     * @return a JPanel containing the main application UI
     */
    private JPanel createMainAppPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BackgroundColor);

        JLabel title = new JLabel("Today's Cafe Mac Menu", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 32));
        title.setForeground(MacBlue);
        title.setBorder(new EmptyBorder(20, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        menuPanel = new JPanel();
        menuPanel.setBackground(BackgroundColor);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panel.add(scrollPane, BorderLayout.CENTER);

        topItemLabel = new JLabel("Top Food: (Not Loaded)");
        topItemLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        topItemLabel.setForeground(MacOrange);
        topItemLabel.setBorder(new EmptyBorder(10, 30, 10, 0));
        panel.add(topItemLabel, BorderLayout.SOUTH);

        JButton loadMenuBtn = createModernButton("Load Menu");
        loadMenuBtn.setMaximumSize(new Dimension(180, 40));
        loadMenuBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(loadMenuBtn);
        panel.add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        suggestionsPanel.setLayout(new BoxLayout(suggestionsPanel, BoxLayout.Y_AXIS));
        suggestionsPanel.setBackground(BackgroundColor);
        topPanel.add(suggestionsPanel);

        searchTextField = new JTextField("What are you looking for today?");
        searchTextField.setPreferredSize(new Dimension(300, 30));
        topPanel.add(searchTextField);

        searchButton = createModernButton("Search");
        topPanel.add(searchButton);
        
        searchButton.addActionListener(e -> {
            suggestionsPanel.removeAll();
            String query = searchTextField.getText().trim().toLowerCase();
            
            if (query.isEmpty()) {
                suggestionsPanel.revalidate();
                suggestionsPanel.repaint();
                return;
            }
            
            List<FoodItem> results = autoComplete.searchByPrefix(query);
            autoComplete.displaySearchResults(results, suggestionsPanel);
            filterAndShowSearchResults(results);
        });

        loadMenuBtn.addActionListener(e -> {
            menuPanel.removeAll();
            loadMenu();
            autoComplete.addMenuItems(menu);
            currentCategory = null;
            showMenu(menuPanel);
            panel.revalidate();
            panel.repaint();
        });

        JPanel topFivePanel = new JPanel();
        topFivePanel.setLayout(new BoxLayout(topFivePanel, BoxLayout.Y_AXIS));
        topFivePanel.setOpaque(false);
        panel.add(topFivePanel, BorderLayout.WEST);

        topFiveLabel = new JLabel("Top 5 Items");
        topFiveLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        topFiveLabel.setForeground(MacBlue);
        topFiveLabel.setVisible(false);
        topFivePanel.add(topFiveLabel);

        topFiveButton = createModernButton("Toggle Top 5");
        topFivePanel.add(topFiveButton);
        
        topFiveButton.addActionListener(e -> {
            if (topFiveLabel.isVisible()) {
                topFiveLabel.setVisible(false);
                topFivePanel.removeAll();
                topFivePanel.add(topFiveButton);
                topFivePanel.revalidate();
                topFivePanel.repaint();
            } else {
                topFiveLabel.setVisible(true);
                menu.updateTopKFoodItems(5);
                menu.displayTopK(topFivePanel);
                topFivePanel.revalidate();
                topFivePanel.repaint();
            }
        });

        JPanel categoryFilterPanel = new JPanel();
        categoryFilterPanel.setLayout(new GridLayout(0, 1, 0, 10));
        categoryFilterPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        categoryFilterPanel.setBackground(BackgroundColor);

        JButton vegButton = createModernButton("Vegetarian");
        JButton veganButton = createModernButton("Vegan");
        JButton glutenButton = createModernButton("Gluten-Free");
        JButton halalButton = createModernButton("Halal");
        JButton showAllButton = createModernButton("Show All");

        categoryFilterPanel.add(vegButton);
        categoryFilterPanel.add(veganButton);
        categoryFilterPanel.add(glutenButton);
        categoryFilterPanel.add(halalButton);
        categoryFilterPanel.add(showAllButton);

        panel.add(categoryFilterPanel, BorderLayout.EAST);

        vegButton.addActionListener(e -> filterAndShowCategory("Vegetarian"));
        veganButton.addActionListener(e -> filterAndShowCategory("Vegan"));
        glutenButton.addActionListener(e -> filterAndShowCategory("Gluten-Free"));
        halalButton.addActionListener(e -> filterAndShowCategory("Halal"));
        showAllButton.addActionListener(e -> {
            currentCategory = null;
            menuPanel.removeAll();
            showMenu(menuPanel);
            panel.revalidate();
            panel.repaint();
        });

        JButton clearButton = createModernButton("Clear");
        topPanel.add(clearButton);

        clearButton.addActionListener(e -> {
            searchTextField.setText("");
            suggestionsPanel.removeAll();
            suggestionsPanel.revalidate();
            suggestionsPanel.repaint();
            currentCategory = null;
            menuPanel.removeAll();
            showMenu(menuPanel);
            panel.revalidate();
            panel.repaint();
        });

        return panel;
    }

    /**
     * Filters the menu by the specified dietary category and updates the display panl.
     *
     * @param category the dietary category to filter by (e.g., "Vegetarian", "Vegan")
     */
    private void filterAndShowCategory(String category) {
        currentCategory = category;
        List<FoodItem> uniqueItems = menu.getUniqueFoodItemsByCategory(category);
        displayFoodItems(uniqueItems, menuPanel, category, false);
        menuPanel.getParent().revalidate();
        menuPanel.getParent().repaint();
        updateTopItem();
    }

    /**
     * Displays search results in the menu panel.
     *
     * @param results the list of food items matching the search query
     */
    private void filterAndShowSearchResults(List<FoodItem> results) {
        currentCategory = null;
        displayFoodItems(results, menuPanel, "Search Results", false);
        menuPanel.getParent().revalidate();
        menuPanel.getParent().repaint();
        updateTopItem();
    }

    /**
     * Loads menu data from the website using the JsoupScraper class. It also handles cases where scraping fails.
     */
    private void loadMenu() {
        jsoupScraper = new JsoupScraper();
        menu = jsoupScraper.scrapeMenu();
        if (menu.getMenue().isEmpty()) {
            menuPanel.removeAll();
            JLabel errorLabel = new JLabel("Failed to load menu!");
            errorLabel.setFont(new Font("Poppins", Font.ITALIC, 18));
            menuPanel.add(errorLabel);
            menuPanel.revalidate();
            menuPanel.repaint();
        } else {
            simulateVotes();
        }
    }

    /**
     * Displays the full menu organized by meal type in the specified panel.
     *
     * @param menuPanel the panel to display the menu in
     */
    private void showMenu(JPanel menuPanel) {
        menuPanel.removeAll();
        for (String mealType : menu.getMenue().keySet()) {
            displayFoodItems(menu.getMenuForMeal(mealType), menuPanel, mealType, true);
        }
        menuPanel.revalidate();
        menuPanel.repaint();
        updateTopItem();
    }

    /**
     * Displays a list of food items in the specified panel with voting buttons and scores.
     *
     * @param items the list of food items to display
     * @param panel the panel to display the items in
     * @param headerText the header text for the section
     * @param append whether to append items or clear the panel first. We made the method this way so we can use its logic for both displaying the main menu and the categorized menu
     */
    private void displayFoodItems(List<FoodItem> items, JPanel panel, String headerText, boolean append) { 
        if (!append) {
            panel.removeAll();
        }
        
        if (headerText != null) {
            JLabel header = new JLabel(headerText.toUpperCase());
            header.setFont(new Font("Poppins", Font.BOLD, 20));
            header.setForeground(MacOrange);
            header.setBorder(new EmptyBorder(15, 15, 8, 8));
            panel.add(header);
        }
        
        int count = 1;
        
        if (items == null || items.isEmpty()) {
            JLabel noItems = new JLabel("No items available for " + (headerText != null ? headerText : "this category"));
            noItems.setFont(new Font("Poppins", Font.ITALIC, 18));
            panel.add(noItems);
        } else {
            for (FoodItem item : items) {
                JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 2));
                itemPanel.setOpaque(false);
                
                JLabel itemLabel = new JLabel(count + ". " + item.getName());
                itemLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
                itemLabel.setPreferredSize(new Dimension(320, 22));
                
                JLabel scoreLabel = new JLabel("Score: " + item.getRating());
                scoreLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
                scoreLabel.setForeground(MacBlue);
                
                JButton yesBtn = createModernButton("★");
                yesBtn.setPreferredSize(new Dimension(50, 32));
                yesBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
                yesBtn.addActionListener(e -> {
                    item.upVote();
                    scoreLabel.setText("Score: " + item.getRating());
                    updateTopItem();
                });
                
                JButton noBtn = createModernButton("☆");
                noBtn.setPreferredSize(new Dimension(50, 32));
                noBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
                noBtn.addActionListener(e -> {
                    item.downVote();
                    scoreLabel.setText("Score: " + item.getRating());
                    updateTopItem();
                });
                
                itemPanel.add(itemLabel);
                itemPanel.add(yesBtn);
                itemPanel.add(noBtn);
                itemPanel.add(scoreLabel);
                
                panel.add(itemPanel);
                count++;
            }
        }
        
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Updates the top item label based on the current category or overall menu.
     */
    private void updateTopItem() {
        FoodItem top = null;
    
        if (currentCategory != null) {
            top = menu.getTopItemInCategory(currentCategory);
        } else {
            menu.updateTopKFoodItems(5);
            List<FoodItem> topList = menu.getTopKFoodItems();
            if (!topList.isEmpty()) {
                top = topList.get(0);
            }
        }
    
        if (top != null) {
            topItemLabel.setText("Top Food: " + top.getName() + " (Score: " + top.getRating() + ")");
        }
    }

    /**
     * Simulates random user votes on menu items to initialize the ratings. 
     */
    private void simulateVotes() {
        Random rand = new Random();
        for (String mealType : menu.getMenue().keySet()) {
            for (FoodItem item : menu.getMenuForMeal(mealType)) {
                int votes = rand.nextInt(100);
                for (int i = 0; i < votes; i++) {
                    if (rand.nextBoolean()) {
                        item.upVote();
                    } else {
                        item.downVote();
                    }
                }
            }
        }
    }

    /**
     * runs the application.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        new MainApp();
    }
}