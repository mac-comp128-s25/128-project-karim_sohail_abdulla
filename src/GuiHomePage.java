import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GuiHomePage extends JPanel {

    private static final Color CornSilkBgColor = new Color(255, 245, 238);
    private static final Color ButtonColor = new Color(255, 155, 0);
    private static final Color ButtonHoovering = new Color(255, 69, 0);

    /**
     * Constructs the home page panel with a welcome message, images, and a start button.
     * Sets up the layout, styles, and adds the provided action listener to the button.
     * @param startButtonListener : The ActionListener to handle start button clicks.
     */

    public GuiHomePage(ActionListener startButtonListener) {
        setLayout(new BorderLayout());
        setBackground(CornSilkBgColor);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(CornSilkBgColor);
     
        mainPanel.setPreferredSize(new Dimension(1200, 900));
        mainPanel.setBorder(new EmptyBorder(30, 40, 40, 40)); 

        JLabel welcomeLabel = new JLabel(
        "🍽️ Begin your journey of fulfilled Meal Swipes with our Meal Swipe Decider! ",
    SwingConstants.CENTER
    );
        welcomeLabel.setFont(new Font("Ariel", Font.ITALIC, 25));
        welcomeLabel.setForeground(new Color(128, 0, 128)); 
        welcomeLabel.setBackground(new Color(255, 255, 255)); 
        welcomeLabel.setOpaque(true); 
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        Border lineBorder = BorderFactory.createLineBorder(Color.ORANGE, 4); // Thicker border
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 2, 5, 2);  // 2px left/right padding
        Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, paddingBorder);
        welcomeLabel.setBorder(compoundBorder);

   
        welcomeLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, welcomeLabel.getPreferredSize().height));
        mainPanel.add(welcomeLabel);

        

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); // 50px gap between images and arrow
        imagePanel.setOpaque(false);


        ImageIcon leftIcon = getScaledIcon("res/JosephRegretting.png", 500, 700);
        ImageIcon rightIcon = getScaledIcon("res/JosephHappy.png", 500, 700);
        JLabel leftPic = new JLabel(leftIcon);
        JLabel rightPic = new JLabel(rightIcon);

       
        JLabel arrow = new JLabel("➔");
        arrow.setFont(new Font("SansSerif", Font.BOLD, 48));
        arrow.setForeground(new Color(0, 140, 200));

        imagePanel.add(leftPic);
        imagePanel.add(arrow);
        imagePanel.add(rightPic);
        mainPanel.add(imagePanel);

     
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Space above button

        JButton startButton = createModernButton("LET'S GET STARTED!");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(400, 300));
        startButton.setFont(new Font("Georgia", Font.BOLD, 25));
        startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startButton.addActionListener(startButtonListener);

  
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Loads and scales an image from the specified file path to the given dimensions.
     * @param path : path The file path to the image.
     * @param width : width The desired image width in pixels.
     * @param height : The desired image height in pixels.
     * @return ImageIcon The scaled image icon.
     */


    private ImageIcon getScaledIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } else {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            return new ImageIcon(img);
        }
    }

    /**
     * Creates a modern-styled JButton with custom colors, font, and hover effects.
     * The button is styled for a consistent look and feel throughout the UI.
     * @param text : The label text to display on the button.
     * @return JButton The styled button instance.
     */

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(ButtonColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(14, 32, 14, 32));
        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 220, 255), 2, true));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ButtonHoovering);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(ButtonColor);
            }
        });
        return button;
    }
}
