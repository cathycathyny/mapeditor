package rvme.controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rvme.data.DataManager;
import saf.AppTemplate;
import rvme.data.subRegion;
import rvme.gui.Workspace;
import saf.ui.SubRegionDialog;
import static saf.ui.SubRegionDialog.CANCEL;
import static saf.ui.SubRegionDialog.OK;

/**
 * This class responds to interactions with todo list editing controls.
 * 
 * @author McKillaGorilla
 * @author Siqing Lee
 * @version 1.0
 */
public class MapandTableController {
    AppTemplate app;
    
    Stage dialogStage;
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
    public MapandTableController(AppTemplate initApp) {
	app = initApp;
    }
    
    public void viewSubregionDetails(subRegion viewRegion, int pos) {
        createDialog();
        updateSubregionDialog(viewRegion, pos);
    }
    
    public void createDialog(){
        dialogStage = new Stage();
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
        
        
        imageBox = new FlowPane();
        //imageBox.getChildren().addAll(leaderImage,flagImage);
        
        // CLOSE BUTTON
        okButton = new Button(OK);
        cancelButton = new Button(CANCEL);
        

        // WE'LL PUT EVERYTHING HERE
        mapDimensionPane = new GridPane();
        mapDimensionPane.setMinSize( 600, 500);
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
        dialogStage.setScene(newScene);
        dialogStage.show();
    }
    
    public void updateSubregionDialog(subRegion viewRegion, int pos){
        DataManager dataManager = (DataManager)app.getDataComponent();
        
        nameInput.setText(dataManager.getSubRegion().get(pos).getName());
        capitalInput.setText(dataManager.getSubRegion().get(pos).getCapital());
        leaderInput.setText(dataManager.getSubRegion().get(pos).getLeader());
        
        if(dataManager.getIsHaveLeader()){
            Image lImage = new Image("file:"+dataManager.getImageFile()+"/"+viewRegion.getLeader()+".png");
            ImageView leaderImage = new ImageView();
            leaderImage.setImage(lImage);
            leaderImage.setFitHeight(100);
            leaderImage.setFitWidth(100);
            imageBox.getChildren().addAll(leaderImage);
        }
        
        if(dataManager.getIsHaveFlags()){
            Image fImage = new Image("file:"+dataManager.getImageFile()+"/"+viewRegion.getName()+" Flag.png");
            ImageView flagImage = new ImageView();
            flagImage.setImage(fImage);
            flagImage.setFitHeight(100);
            flagImage.setFitWidth(100);
            imageBox.getChildren().addAll(flagImage);
        }
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.selectRegion(viewRegion.getRed(),viewRegion.getGreen(),viewRegion.getBlue());
        
        if(pos==0){
            prev.setDisable(true);
            next.setDisable(false);
        }else if(pos==dataManager.getSubRegion().size()-1){
            prev.setDisable(false);
            next.setDisable(true);
        }else{
            prev.setDisable(false);
            next.setDisable(false);
        }
        
        buttonHandle(viewRegion,pos);
    }
    
    public void buttonHandle(subRegion viewRegion, int pos){
        DataManager dataManager = (DataManager)app.getDataComponent();
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        
        okButton.setOnAction(e->{
            viewRegion.setName(nameInput.getText());
            viewRegion.setCapital(capitalInput.getText());
            viewRegion.setLeader(leaderInput.getText());
            workspace.reloadWorkspace();
            //workspace.removeSelectRegion(viewRegion.getRed(),viewRegion.getGreen(),viewRegion.getBlue());
            dialogStage.close();
            
        });
        
        cancelButton.setOnAction(e->{
            workspace.removeSelectRegion(viewRegion.getRed(),viewRegion.getGreen(),viewRegion.getBlue());
            dialogStage.close();
        });
        
        prev.setOnAction(e->{
            imageBox.getChildren().clear();
            workspace.removeSelectRegion(viewRegion.getRed(),viewRegion.getGreen(),viewRegion.getBlue());
            subRegion prevSubregion = dataManager.getSubRegion().get(pos-1);
            updateSubregionDialog(prevSubregion, pos-1);
        });
        
        next.setOnAction(e->{
            imageBox.getChildren().clear();
            workspace.removeSelectRegion(viewRegion.getRed(),viewRegion.getGreen(),viewRegion.getBlue());
            subRegion nextSubregion = dataManager.getSubRegion().get(pos+1);
            updateSubregionDialog(nextSubregion, pos+1);
        });
        
         dialogStage.setOnHiding(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        workspace.removeSelectRegion(viewRegion.getRed(),viewRegion.getGreen(),viewRegion.getBlue());
                    }
                });
            }
        });
    }
    
}
