package application;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class CreateProfileController implements Initializable{

	Image img;

    @FXML
    private AnchorPane logIn;
    
    @FXML
    private TextField firstName;
    
    @FXML
    private TextField lastName;
    
    @FXML
    private TextField companyName;
    
    @FXML
    private TextField mobileNumber;
    
    @FXML
    private TextField Email;
    
    @FXML
    private TextField userName;
    
    @FXML
    private TextField Pword;
    
    @FXML
    private TextField companyNumber;
    
    @FXML
    private Button create;
    
    @FXML
    private Label error;

    private Connection connect() throws ClassNotFoundException {
        // SQLite connection string
		Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:storyboard.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void initialize(URL arg0, ResourceBundle arg1) {
    	try {
    		FileInputStream inputStream = new FileInputStream("C:/Users/Mahir Faris/Desktop/logo.jpg");
    		img = new Image(inputStream);
    		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
    	    BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
    	    Background background = new Background(backgroundImage);
    	    logIn.setBackground(background);
    	} catch (IOException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	create.setOnMouseClicked(new EventHandler<MouseEvent>( ) {
    		public void handle(MouseEvent e) {
    			if(firstName.getText() == null || firstName.getText().isEmpty()) {
    		    	if(lastName.getText() == null || lastName.getText().isEmpty()) {

    				error.setVisible(true);
    				error.setText("Invalid");
    			} else{
    				error.setText("");
    			}
    		}
    		}
    	
    	});
    }
}
    	
			
    	
   