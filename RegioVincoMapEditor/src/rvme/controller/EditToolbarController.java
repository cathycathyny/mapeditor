package rvme.controller;


import java.io.File;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import rvme.data.DataManager;
import rvme.data.image;
import saf.AppTemplate;
import rvme.gui.Workspace;
import static saf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static saf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static saf.settings.AppStartupConstants.PATH_EXPORT;
import saf.ui.AppMessageDialogSingleton;


/**
 * This class responds to interactions with todo list editing controls.
 * 
 * @author McKillaGorilla
 * @author Siqing Lee
 * @version 1.0
 */
public class EditToolbarController {
    AppTemplate app;
    
    public EditToolbarController(AppTemplate initApp) {
	app = initApp;
    }
    
    //  CHANGE THE NAME OF THE MAP
    public void changeMapName(String mapName){
        DataManager dataManager = (DataManager)app.getDataComponent();
        dataManager.setMapName(mapName);
    }
    
    // CHANGE THE BACKGROUND COLOR OF THE MAP
    public void changeBackgroundColor(Color newColor){
        DataManager dataManager = (DataManager)app.getDataComponent();
        dataManager.setBackgroundColor(newColor.toString());
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();
    }
    
    // CHANGE THE BORDER COLOR OF THE MAP
    public void changeBorderColor(Color newColor){
        DataManager dataManager = (DataManager)app.getDataComponent();
        dataManager.setBorderColor(newColor.toString());
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();
    }
    
    // GENERATE RANDOM COLOR FOR MAP'S BACKGROUND AND BORDER COLOR
    public void randomColor(){
        DataManager dataManager = (DataManager)app.getDataComponent();
        for(int i=0;i<dataManager.getSubRegion().size();i++){
        int R = (int)(Math.random()*256);
        
        dataManager.getSubRegion().get(i).setRed(R);
        dataManager.getSubRegion().get(i).setGreen(R);
        dataManager.getSubRegion().get(i).setBlue(R);
        }
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();
    }
    
    // CHANGE MAP BORDER THICKNESS
    public void changeBorderThickness(double newValue){
        DataManager dataManager = (DataManager)app.getDataComponent();
        dataManager.setBorderWidth(newValue);
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();
    }
    
    // ADD IMAGE TO THE MAP
    public void addImage(StackPane pane) throws IOException{
        DataManager dataManager = (DataManager)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_EXPORT));
	fc.setTitle("Add Image");
        File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());
        Image newImage = null;
        
        if (selectedFile != null) {
            try{
                System.out.print(selectedFile.getAbsolutePath());
                newImage = new Image("file:"+selectedFile.getAbsolutePath());
                dataManager.addImageDetails(new image(selectedFile.getAbsolutePath(),0,0));
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
        
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();
    }
    
    // REMOVE IMAGE ON THE MAP
    public void removeImage(int number){
        DataManager dataManager = (DataManager)app.getDataComponent();
        dataManager.getImageDetail().remove(number);
        Workspace workspace = (Workspace)app.getWorkspaceComponent();
        workspace.reloadWorkspace();
    }
    
    // MOVE THE IMAGE
    public void updateImagePlace(int number, double x, double y){
        DataManager dataManager = (DataManager)app.getDataComponent();
        dataManager.getImageDetail().get(number).setX(x);
        dataManager.getImageDetail().get(number).setY(y);
    }
    
    public void playAnthem(){
        
    }
    
    public void pauseAnthem(){
        
    }
    
    public void zoom(double newValue){
//        DataManager dataManager = (DataManager)app.getDataComponent();
//        dataManager.setMapZoom(newValue);
//        Workspace workspace = (Workspace)app.getWorkspaceComponent();
//        workspace.reloadWorkspace();
    }
    
    public void changeMapSize(){
        
    }
}
