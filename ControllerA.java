package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.scene.control.TitledPane;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


public class ControllerA implements Initializable {
    
	@FXML
	private Pane canvas;
	
	
	
    /*@FXML
    private ImageView env1;

    @FXML
    private ImageView env2;

    @FXML
    private ImageView env3;

    @FXML
    private ImageView env4;*/
    
    @FXML
    private TitledPane enBox;
    
    //private ArrayList<ImageView> environmentIV = new ArrayList<ImageView>();
    

	private Image img;
	private GridPane enGrid = new GridPane();
    
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
     
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		/*environmentIV.add(env1);
		environmentIV.add(env2);
		environmentIV.add(env3);
		environmentIV.add(env4);*/
		String sql = "SELECT environmentPicture FROM environment";
        int counter = 0;
        try (
        		Connection conn = this.connect();
        		Statement stmt  = conn.createStatement();
        		ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
            	try {
            		FileInputStream inputStream = new FileInputStream(rs.getString("environmentPicture"));
         		   	img = new Image(inputStream); 
         		   	test(counter, rs.getString("environmentPicture"));
            	} catch (IOException e) {
            		System.out.println(e.getMessage());
            	}
                //environmentIV.get(counter).setImage(img);
                counter++;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        /*env1.setOnMousePressed(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent e) {
		    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		    	BackgroundImage backgroundImage = new BackgroundImage(env1.getImage(), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		    	Background background = new Background(backgroundImage);
		    	canvas.setBackground(background);
		    }
		});
        env2.setOnMousePressed(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent e) {
		    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		    	BackgroundImage backgroundImage = new BackgroundImage(env2.getImage(), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		    	Background background = new Background(backgroundImage);
		    	canvas.setBackground(background);
		    }
		});
        env3.setOnMousePressed(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent e) {
		    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		    	BackgroundImage backgroundImage = new BackgroundImage(env3.getImage(), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		    	Background background = new Background(backgroundImage);
		    	canvas.setBackground(background);
		    }
		});
        env4.setOnMousePressed(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent e) {
		    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		    	BackgroundImage backgroundImage = new BackgroundImage(env4.getImage(), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		    	Background background = new Background(backgroundImage);
		    	canvas.setBackground(background);
		    }
		});*/
        //ImageView en5 = test(14, 106);
	}
	
	@FXML
	public void setBackgroundOnClick(ActionEvent e) {
		ImageView iv = (ImageView) e.getSource();
	    BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
	    BackgroundImage backgroundImage = new BackgroundImage(iv.getImage(), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	    Background background = new Background(backgroundImage);
	    canvas.setBackground(background);
	}
	
	public void test(int position, String path) {
		ImageView iv = new ImageView();
		iv.setFitWidth(80);
		iv.setFitHeight(40);
		//iv.setLayoutX(x);
		//iv.setLayoutY(y);
		try {
    		FileInputStream inputStream = new FileInputStream(path);
 		   	img = new Image(inputStream); 
    	} catch (IOException e) {
    		System.out.println(e.getMessage());
    	}
        iv.setImage(img);
        enGrid.setHgap(4);
        enGrid.setVgap(4);
        enGrid.setPadding(new Insets(5, 5, 5, 5));
        enGrid.add(iv,position/2, position%2);
		enBox.setContent(enGrid);
		iv.setOnMousePressed(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent e) {
		    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		    	BackgroundImage backgroundImage = new BackgroundImage(iv.getImage(), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		    	Background background = new Background(backgroundImage);
		    	canvas.setBackground(background);
		    }
		});
		//return iv;
	}
}
