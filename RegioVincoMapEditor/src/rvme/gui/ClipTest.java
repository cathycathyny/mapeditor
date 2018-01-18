package rvme.gui;



import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class ClipTest extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    Group root = new Group();

    StackPane pane = new StackPane();

    pane.setMaxWidth(100);
    pane.setMaxHeight(100);
    pane.setLayoutX(50);
    pane.setLayoutY(50);


    Rectangle rect = new Rectangle(100, 100);

    rect.setFill(null);
    rect.setStroke(Color.RED);

    Rectangle rect2 = new Rectangle(150, 150);

    rect2.setFill(Color.BLUE);

    pane.getChildren().addAll(rect2, rect);

    root.getChildren().add(pane);


    Rectangle clip = new Rectangle(100, 100);
    clip.setLayoutX(50);
    clip.setLayoutY(50);
    pane.setClip(clip);

    Scene scene = new Scene(root, 250, 250);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}