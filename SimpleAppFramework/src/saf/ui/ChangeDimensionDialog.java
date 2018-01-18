/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saf.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Cathy
 */
public class ChangeDimensionDialog extends Stage{
    static ChangeDimensionDialog singleton = null;
    Scene newScene;
    GridPane mapDimensionPane;
    Label mapWidth;
    Label mapHeight;
    Button okButton;
    Button cancelButton;
    TextField widthSpinner;
    TextField heightSpinner;
    
    public static final String OK = "OK";
    public static final String CANCEL = "CANCEL";
    String selection;
    int w;
    int h;
    
    private ChangeDimensionDialog() {}
    
    public static ChangeDimensionDialog getSingleton() {
	if (singleton == null)
	    singleton = new ChangeDimensionDialog();
	return singleton;
    }
    
    public void init(Stage owner) {
        // MAKE IT MODAL
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
        // LABEL TO DISPLAY THE CUSTOM MESSAGE
        mapWidth = new Label("Map Width:");
        mapHeight = new Label("Map Height:");
        widthSpinner = new TextField();
        heightSpinner = new TextField();
        EventHandler loadWorkspace = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            ChangeDimensionDialog.this.selection = sourceButton.getText();
            ChangeDimensionDialog.this.close();
        };

        
        // CLOSE BUTTON
        okButton = new Button(OK);
        okButton.setOnAction(e->{ change();});
        cancelButton = new Button(CANCEL);
        cancelButton.setOnAction(e->{ ChangeDimensionDialog.this.close(); });

        // WE'LL PUT EVERYTHING HERE
        mapDimensionPane = new GridPane();
        mapDimensionPane.setPadding(new Insets(20));
        mapDimensionPane.setHgap(5);
        mapDimensionPane.setVgap(30);
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 250, 300);
        column2.setHgrow(Priority.ALWAYS);
        mapDimensionPane.getColumnConstraints().addAll(column1, column2);
        
        // MAKE IT LOOK NICE
        GridPane.setHalignment(mapWidth, HPos.LEFT);
        mapDimensionPane.add(mapWidth, 0, 0);
        GridPane.setHalignment(widthSpinner, HPos.LEFT);
        mapDimensionPane.add(widthSpinner, 1, 0);
        
        GridPane.setHalignment(mapHeight, HPos.LEFT);
        mapDimensionPane.add(mapHeight, 0, 1);
        GridPane.setHalignment(heightSpinner, HPos.LEFT);
        mapDimensionPane.add(heightSpinner, 1, 1);
        
        GridPane.setHalignment(okButton, HPos.RIGHT);
        mapDimensionPane.add(okButton, 0,4 );
        GridPane.setHalignment(cancelButton, HPos.LEFT);
        mapDimensionPane.add(cancelButton, 1, 4);
        
        //newMapPane.setHalignment(newMapLabel, HPos.LEFT);

        // AND PUT IT IN THE WINDOW
        newScene = new Scene(mapDimensionPane);
        this.setScene(newScene);
    }
    
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
        //okButton.getStyleClass().add(CLASS_EDITTOOLBAR_PANE);
    }
    
    private void change(){
        w = Integer.parseInt(widthSpinner.getText());
        h = Integer.parseInt(heightSpinner.getText());
        ChangeDimensionDialog.this.close();
    }
    
    public int getW() {
        return w;
    }
    
    public int getH() {
        return h;
    }
    
    public String getSelection() {
        return selection;
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
