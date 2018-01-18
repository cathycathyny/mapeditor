/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saf.ui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeArray.map;
import properties_manager.PropertiesManager;
import static saf.settings.AppPropertyType.NEW_ICON;
import static saf.settings.AppPropertyType.NEW_TOOLTIP;
import static saf.settings.AppStartupConstants.FILE_PROTOCOL;
import static saf.settings.AppStartupConstants.PATH_IMAGES;

/**
 *
 * @author Cathy
 */
public class SubRegionDialog extends Stage{
    static SubRegionDialog singleton = null;
    Scene newScene;
    Pane pane;
    FlowPane buttonPane;
    GridPane mapDimensionPane;
    Button prev;
    Button next;
    Label nameLabel;
    TextField nameInput;
    Label capitalLabel;
    TextField capitalInput;
    Label leaderLabel;
    TextField leaderInput;
    FlowPane imageBox;
    Label image;
    
    Button okButton;
    Button cancelButton;
    
    public static final String OK = "OK";
    public static final String CANCEL = "CANCEL";
    
    private SubRegionDialog() {}
    
    public static SubRegionDialog getSingleton() {
	if (singleton == null)
	    singleton = new SubRegionDialog();
	return singleton;
    }
    
    public void init(Stage owner) {
        // MAKE IT MODAL
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
        buttonPane = new FlowPane();
        prev = new Button("Previos Subregion");
        next = new Button("Next Subregion");
        buttonPane.getChildren().addAll(prev,next);
        
        nameLabel = new Label("Sub Region Name:");
        capitalLabel = new Label("Capital:");
        leaderLabel = new Label("Leader:");
        image = new Label("Image:");
        
        nameInput = new TextField();
        capitalInput = new TextField();
        leaderInput = new TextField();
        
        Image lImage = new Image("file:./images/leader.jpg");
        Image fImage = new Image("file:./images/flag.png");
        ImageView leaderImage = new ImageView();
        ImageView flagImage = new ImageView();
        leaderImage.setImage(lImage);
        flagImage.setImage(fImage);
        leaderImage.setFitHeight(100);
        leaderImage.setFitWidth(100);
        flagImage.setFitHeight(100);
        flagImage.setFitWidth(100);
        imageBox = new FlowPane();
        imageBox.getChildren().addAll(leaderImage,flagImage);
        
        // CLOSE BUTTON
        okButton = new Button(OK);
        okButton.setOnAction(e->{ SubRegionDialog.this.close();});
        cancelButton = new Button(CANCEL);
        cancelButton.setOnAction(e->{ SubRegionDialog.this.close(); });

        // WE'LL PUT EVERYTHING HERE
        mapDimensionPane = new GridPane();
        mapDimensionPane.setPadding(new Insets(20));
        mapDimensionPane.setHgap(10);
        mapDimensionPane.setVgap(30);
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(30, 300, 300);
        column2.setHgrow(Priority.ALWAYS);
        mapDimensionPane.getColumnConstraints().addAll(column1, column2);
        
        // MAKE IT LOOK NICE
        GridPane.setHalignment(buttonPane, HPos.LEFT);
        mapDimensionPane.add(buttonPane, 1, 0);

        GridPane.setHalignment(nameLabel, HPos.LEFT);
        mapDimensionPane.add(nameLabel, 0, 1);
        GridPane.setHalignment(nameInput, HPos.LEFT);
        mapDimensionPane.add(nameInput, 1, 1);
        
        GridPane.setHalignment(capitalLabel, HPos.LEFT);
        mapDimensionPane.add(capitalLabel, 0, 2);
        GridPane.setHalignment(capitalInput, HPos.LEFT);
        mapDimensionPane.add(capitalInput, 1, 2);
        
        GridPane.setHalignment(leaderLabel, HPos.LEFT);
        mapDimensionPane.add(leaderLabel, 0, 3);
        GridPane.setHalignment(leaderInput, HPos.LEFT);
        mapDimensionPane.add(leaderInput, 1, 3);
        
        GridPane.setHalignment(image, HPos.LEFT);
        mapDimensionPane.add(image, 0, 4);
        GridPane.setHalignment(imageBox, HPos.LEFT);
        mapDimensionPane.add(imageBox, 1, 4);
        
        
        GridPane.setHalignment(okButton, HPos.RIGHT);
        mapDimensionPane.add(okButton, 0,5 );
        GridPane.setHalignment(cancelButton, HPos.LEFT);
        mapDimensionPane.add(cancelButton, 1, 5);
        
        //newMapPane.setHalignment(newMapLabel, HPos.LEFT);

        // AND PUT IT IN THE WINDOW
        pane = new Pane();
        pane.getChildren().add(mapDimensionPane);
        newScene = new Scene(pane);
        this.setScene(newScene);
    }
    
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
        pane.getStyleClass().add("edit_toolbar_pane");
    }
    
    public Button initChildButton(Pane toolbar, String icon, String tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
        Image buttonImage = new Image(imagePath);
	
	// NOW MAKE THE BUTTON
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        button.setTooltip(buttonTooltip);
	
	// PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);
	
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }

    public void show(String title) {
	// SET THE DIALOG TITLE BAR TITLE
	setTitle(title);
        
	// AND OPEN UP THIS DIALOG, MAKING SURE THE APPLICATION
	// WAITS FOR IT TO BE RESOLVED BEFORE LETTING THE USER
	// DO MORE WORK.
        showAndWait();
    }
}
