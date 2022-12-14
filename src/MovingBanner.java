import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MovingBanner extends Application{
	private Text info;
	private TextField input;
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {		
		info = new Text(200,55,"Hello world ");
		info.setFont(new Font("Cursive", 26));
		Rectangle rect = new Rectangle(0,0,500,100);
		rect.setFill(Color.ANTIQUEWHITE);
		Pane pane = new Pane(rect,info);
		
		Label color = new Label("Select color");
		ComboBox<String> colorComboBox = new ComboBox<>();
		colorComboBox.getItems().addAll("red","green","blue","brown","purple");
		colorComboBox.setOnAction(event->{
			String colorResult = colorComboBox.getValue();
			switch(colorResult) {
			case "red":
				info.setFill(Color.RED);
				break;
			case "green":
				info.setFill(Color.GREEN);
				break;
			case "blue":
				info.setFill(Color.BLUE);
				break;
			case "brown":
				info.setFill(Color.BROWN);
				break;
			case "purple":
				info.setFill(Color.PURPLE);
				break;
			default:
				info.setFill(Color.BLACK);
			}						
		});
		
		Label content = new Label("content");
		input = new TextField();
		HBox inputHbox = new HBox(10,content, input);
		inputHbox.setAlignment(Pos.CENTER);
		
		Button okay = new Button("Okay");
		okay.setOnAction(new OkayButtonHandler());
		Button exit = new Button("Exit");
		exit.setOnAction(new ExitButtonHandler());
		HBox buttonHbox = new HBox(10,okay, exit);
        buttonHbox.setAlignment(Pos.CENTER);
        buttonHbox.setPadding(new Insets(10));
        
        Label listView = new Label("Select font size");
        ListView<String> fontSizeList = new ListView<>();
		fontSizeList.setPrefHeight(150);
		fontSizeList.getItems().addAll("20","23","26","30","33","36");
		fontSizeList.getSelectionModel().selectedItemProperty().addListener(event->{
			int size = Integer.parseInt(fontSizeList.getSelectionModel().getSelectedItem());
			info.setFont(new Font("Cursive",size));
		});
			
		
		VBox vbox = new VBox(10,pane,color,colorComboBox,inputHbox,buttonHbox,listView,fontSizeList);
		vbox.setPadding(new Insets(10));
		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.show();
		Thread taskThread = new Thread(new Runnable() {
		      @Override
		      public void run() {
		        double progress = 0;
		        while(true) {
		          try {
		            Thread.sleep(300);
		          } catch (InterruptedException e) {
		            e.printStackTrace();
		          }
		          progress += 0.1;
		          final double reportedProgress = progress;

		          Platform.runLater(new Runnable() {
		            @Override
		            public void run() {
		              String str = info.getText();
		              String first = str.substring(0,1);
		              String result = str.substring(1) + first;
		              info.setText(result);		            
		              }
		          });
		        }
		      }
		    });

		    taskThread.start();
	}
	public class OkayButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			String str = input.getText();
			info.setText(str);
		}
	}
	public class ExitButtonHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			Platform.exit();
		}
	}
}