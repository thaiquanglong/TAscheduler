package application;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;




public class TAscheduler extends Application 
{  
	@Override
	public void start(Stage primaryStage) 
	{  // for ui
		try 
		{
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			Scene scene = new Scene(root,600.0,400.0);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("TA selector");
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
    
    
    public static void main(String[] args) 
    {
    	
    	launch(TAscheduler.class); // for ui
    	
    }
}