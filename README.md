# CafeMac Meal Swipe Companion


*Team Members:* [Abdullah Fare, Karim Amra, Sohail Mohammed]


## Project Description
A Java desktop application that allows Macalester students to make informed meal swipe decisions by displaying real-time Cafe Mac menus with community reviews and nutritional filtration.


## Technical Guide


### Requirements
•⁠  ⁠Java 17 or later (required for Swing components)
•⁠  ⁠Active internet connection (needed for menu scraping)
•⁠  ⁠No additional libraries beyond the standard Java libraries and Jsoup (included in project)


### Running the Program
1.⁠ ⁠Clone the repository
2.⁠ ⁠Open the project in your preferred Java IDE (Eclipse, IntelliJ, etc.)
3.⁠ ⁠Run the ⁠ MainApp.java ⁠ class


## Features
•⁠  ⁠Real-time menu scraping from Bon Appétit's website
•⁠  ⁠Community-driven voting system to rate meals
•⁠  ⁠Filtering options for dietary preferences (vegan, halal, gluten-free, etc.)
•⁠  ⁠Fast search functionality using custom Trie data structure
•⁠  ⁠Top-rated meal recommendations


## Acknowledgments
We would like to thank Professor Bret Jackson and preceptors for guidance throughout this project.


### Resources Used
•⁠  ⁠*Web Scraping:*
 * Jsoup library for HTML parsing
 * Web scraping tutorials and documentation from GeeksforGeeks
•⁠  ⁠*Java Swing UI:*
 * Oracle's Java Swing documentation
 * Various UI layout tutorials and examples
•⁠  ⁠*Full list of resources*
   https://www.youtube.com/watch?v=riZ2GAaMDGM
   https://www.geeksforgeeks.org/web-scraping-in-java-with-jsoup/
   https://jsoup.org/apidocs/org/jsoup/nodes/Document.html
   https://jsoup.org/apidocs/org/jsoup/select/Selector.html
   https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/package-summary.html
   https://youtu.be/4PfDdJ8GFHI?feature=shared
   https://stackhowto.com/borderlayout-java-swing-example/
   https://examples.javacodegeeks.com/java-development/desktop-java/swing/java-swing-boxlayout-example/
   https://stackoverflow.com/questions/13212431/jpanel-vs-jframe-in-java
   https://codingtechroom.com/question/hover-effect-jbutton-java-swing
   https://examples.javacodegeeks.com/java-development/desktop-java/swing/jscrollpane/jscrollpane-swing-example/
   https://stackoverflow.com/questions/3720534/grid-layout-with-box-inside
   https://youtu.be/oPruCxQAQSs?feature=shared
   https://codehs.com/tutorial/david/basic-java-swing-layouts
   https://www.codejava.net/java-se/swing/jpanel-basic-tutorial-and-examples#google_vignette
   https://www.tutorialspoint.com/swing/swing_jpanel.html
   https://zetcode.com/java/imageicon/
   https://www.tutorialspoint.com/swing/swing_borderlayout.htm
   https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
   https://www.tutorialspoint.com/how-can-we-implement-a-jlabel-text-with-different-color-and-font-in-java
   https://www.tutorialspoint.com/swingexamples/example_of_boxlayout.html
   https://andrewbridge.wordpress.com/2012/03/26/java-styling-a-swing-jlabel/
   https://www.youtube.com/watch?v=rJMLTUEGRaU
   https://steemit.com/utopian-io/@will-ugo/event-handling-event-and-listener-actionlistener




## Known Issues



### Design Limitations:
•⁠  ⁠Voting data is stored locally and not synchronized across multiple users
•⁠  ⁠No persistent user accounts or authentication system


### Bugs/Shortcomings:
•⁠  ⁠Occasional parsing errors when Bon Appétit changes their website format
•⁠  ⁠Search functionality doesn't account for misspellings
•⁠  ⁠UI scaling issues on high-resolution displays


## Societal Impact


### Accessibility Considerations
Our application is mostly based on visual engagement, which presents difficulties for visually impaired users. While Java Swing has some compatibility with screen readers, we have not optimized for this experience. In future editions, we intend to include keyboard shortcuts and screen reader compatibility to make the application more accessible.


### Equitable Access
We understand that our application presume that all users have stable internet connectivity and a compatible computer. This may eliminate pupils with low technological resources. A web-based or mobile solution would help to overcome this barrier and improve accessibility.


### Privacy and Data Collection
The voting system collects food preferences, which may expose dietary limitations that people wish to keep private. We developed the system to save this data locally rather than in a central database to safeguard user privacy, but also restricts the community's benefit from shared evaluations.


### Food Insecurity Awareness
While our software helps students get the most out of meal plans, we understand that many college students face food insecurity. Future enhancements could include information on Macalester's food assistance programs and community meal-sharing opportunities.
