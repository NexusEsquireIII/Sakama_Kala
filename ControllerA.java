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
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Screen;


public class ControllerA implements Initializable {
    
	@FXML
	private Pane canvas;
    
    @FXML
    private TitledPane enBox;
    
    @FXML
    private TitledPane characterIconBox;
    
    @FXML
    private ScrollPane characterPictureBox;
    
    @FXML
    private Button nextButton;
    
    @FXML
    private Button prevButton;
    
    //@FXML
    //private Button nextScreenButton;

	private Image img;
	private GridPane enGrid = new GridPane();
	private GridPane characterIconGrid = new GridPane();
	private GridPane characterPictureGrid = new GridPane();

	int component = 0;
	ImageView[] canvasImage = new ImageView[20];
	private int cursor;
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
		/*nextScreenButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("application/CreateProfile.fxml"));
		        primaryStage.setTitle("Main Phase");
		        primaryStage.setScene(new Scene(root, 800, 500));
		        
		        primaryStage.show();
		        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2); 
		        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 4);
			}
		});*/
		for (int i =0;i < 20; i++) {
			canvasImage[i] = new ImageView();
			canvasImage[i].setFitHeight(50);
			canvasImage[i].setFitWidth(50);
		}
		nextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if(component<20) {
					canvasImage[component].setOnDragDetected(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent e) {

						}
					});  
					canvasImage[component].setOnMouseEntered(new EventHandler<MouseEvent>() {
						   @Override
						   public void handle(MouseEvent e) {
						   }
					});
					
					component++;
					canvasImage[component].setOnDragDetected(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent e) {
							Dragboard db = canvasImage[component].startDragAndDrop(TransferMode.MOVE);
							ClipboardContent content = new ClipboardContent();
							content.putImage(canvasImage[component].getImage());
							db.setContent(content);
							e.consume();
						}
					});  
					canvasImage[component].setOnMouseEntered(new EventHandler<MouseEvent>() {
						   @Override
						   public void handle(MouseEvent e) {
							   canvasImage[component].setCursor(Cursor.HAND);
							   cursor = (int) e.getSceneX();
						   }
					});
					System.out.println(component);
				}
			}
		});
		prevButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if(component > 0) {
					canvasImage[component].setOnDragDetected(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent e) {

						}
					});  
					canvasImage[component].setOnMouseEntered(new EventHandler<MouseEvent>() {
						   @Override
						   public void handle(MouseEvent e) {
						   }
					});
					component--;
					canvasImage[component].setOnDragDetected(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent e) {
							Dragboard db = canvasImage[component].startDragAndDrop(TransferMode.MOVE);
							ClipboardContent content = new ClipboardContent();
							content.putImage(canvasImage[component].getImage());
							db.setContent(content);
							e.consume();
						}
					});  
					canvasImage[component].setOnMouseEntered(new EventHandler<MouseEvent>() {
						   @Override
						   public void handle(MouseEvent e) {
							   canvasImage[component].setCursor(Cursor.HAND);
							   cursor = (int) e.getSceneX();
						   }
					});
					System.out.println(component);
				}
			}
		});
		String enSql = "SELECT environmentPicture FROM environment";
        int enCounter = 0;
        try (
        		Connection conn = this.connect();
        		Statement stmt  = conn.createStatement();
        		ResultSet rs    = stmt.executeQuery(enSql)){
            
            // loop through the result set
            while (rs.next()) {
            	createBackgroundIV(enCounter, rs.getString("environmentPicture"));
                enCounter++;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        String characterIconSql = "SELECT DISTINCT character_pic_icon_Picture FROM character_pic";
        int characterIconCounter = 0;
        try (
        		Connection conn = this.connect();
        		Statement stmt  = conn.createStatement();
        		ResultSet rs    = stmt.executeQuery(characterIconSql)){
            
            // loop through the result set
            while (rs.next()) {
            	createCharacterIcon(characterIconCounter, rs.getString("character_pic_icon_Picture"));
                characterIconCounter++;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public void dragWithinCanvas(ImageView iv) {
		iv.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Dragboard db = iv.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				content.putImage(iv.getImage());
				db.setContent(content);
				e.consume();
			}
		});  
		iv.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				iv.setCursor(Cursor.HAND);
				cursor = (int) e.getSceneX();
			}
		});
	}
	
	public void createBackgroundIV(int position, String path) {
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
	}
	
	public void createCharacterIcon(int position, String path) {
		ImageView iv = new ImageView();
		iv.setFitWidth(50);
		iv.setFitHeight(50);
		try {
    		FileInputStream inputStream = new FileInputStream(path);
 		   	img = new Image(inputStream); 
    	} catch (IOException e) {
    		System.out.println(e.getMessage());
    	}
		iv.setImage(img);
		characterIconGrid.setHgap(6);
		characterIconGrid.setVgap(6);
		characterIconGrid.setPadding(new Insets(5, 5, 5, 5));
		characterIconGrid.add(iv,position/4, position%4);
        characterIconBox.setContent(characterIconGrid);
        iv.setOnMousePressed(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent e) {
		    	String sql = "SELECT character_pic_Picture FROM character_pic WHERE character_pic_icon_Picture = '" + path + "'";
		        int characterPicCounter = 0;
		    	try (
		        		Connection conn = this.connect();
		        		Statement stmt  = conn.createStatement();
		        		ResultSet rs    = stmt.executeQuery(sql)){
		            
		            // loop through the result set
		            while (rs.next()) {
		            	createCharacterPicture(characterPicCounter, rs.getString("character_pic_Picture"));
		                characterPicCounter++;
		            }
		        } catch (SQLException | ClassNotFoundException e1) {
		            System.out.println(e1.getMessage());
		        }
		    }

			private Connection connect() throws ClassNotFoundException {
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
		});
	}
	
	public void createCharacterPicture(int position, String path) {
		ImageView iv = new ImageView();
		iv.setFitWidth(50);
		iv.setFitHeight(50);
		//iv.setLayoutX(x);
		//iv.setLayoutY(y);
		try {
    		FileInputStream inputStream = new FileInputStream(path);
 		   	img = new Image(inputStream); 
    	} catch (IOException e) {
    		System.out.println(e.getMessage());
    	}
        iv.setImage(img);
        characterPictureGrid.setHgap(6);
        characterPictureGrid.setVgap(6);
        characterPictureGrid.setPadding(new Insets(5, 5, 5, 5));
        characterPictureGrid.add(iv,position/4, position%4);
        characterPictureBox.setFitToWidth(true);
        characterPictureBox.setContent(characterPictureGrid);
        iv.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				Dragboard db = iv.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				content.putImage(iv.getImage());
				db.setContent(content);
				e.consume();
			}
		});  
		iv.setOnMouseEntered(new EventHandler<MouseEvent>() {
			   @Override
			   public void handle(MouseEvent e) {
				   iv.setCursor(Cursor.HAND);
				   cursor = (int) e.getSceneX();
			   }
		});
			canvas.setOnDragOver(new EventHandler<DragEvent>() {
				   @Override
				   public void handle(DragEvent de) {
					   Dragboard db = de.getDragboard();
					    if(db.hasImage()) {
					    	de.acceptTransferModes(TransferMode.MOVE);
					    }
					    de.consume();
				   }
			});
		
			canvas.setOnDragDropped(new EventHandler<DragEvent>() {
				   @Override
				   public void handle(DragEvent de) {
					   Dragboard db = de.getDragboard();
					   if(db.hasImage()) {
						   canvasImage[component].setImage(db.getImage());
						   canvas.getChildren().remove(canvasImage[component]);
						   Point2D localPoint = canvas.sceneToLocal(new Point2D(de.getSceneX(), de.getSceneY()));
						   canvasImage[component].setX((int) (localPoint.getX() - canvasImage[component].getBoundsInLocal().getWidth()/2));
						   canvasImage[component].setY((int) (localPoint.getY() - canvasImage[component].getBoundsInLocal().getHeight()/2));
						   dragWithinCanvas(canvasImage[component]);
						   canvas.getChildren().add(canvasImage[component]);
						   de.setDropCompleted(true);
					   } else {
						   de.setDropCompleted(false);
					   }   
					   de.consume();
				   }
			});
		
	}
}
