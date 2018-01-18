package saf.ui;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import properties_manager.PropertiesManager;
import static saf.components.AppStyleArbiter.CLASS_EDITTOOLBAR_PANE;
import static saf.settings.AppStartupConstants.PATH_EXPORT;
import static saf.settings.AppStartupConstants.PATH_RAWDATA;

/**
 * This class serves to present a new map Dialog for
 * user to create a new map
 * 
 * @author Siqing Lee
 * @version 1.0
 */
public class NewMapDialog extends Stage {
    // HERE'S THE SINGLETON OBJECT
    static NewMapDialog singleton = null;
    
    // HERE ARE THE DIALOG COMPONENTS
    GridPane newMapPane;
    Scene newMapScene;
    Label newMapLabel;
    
    //Ask for the map name
    Label newMapName;
    TextField newMapNameInput;
    Label dataPath;
    
    //Ask for the map data
    Label newMapData;
    Button dataButton;
    Label filePath;
            
    //Ask for the map parent directory
    Label newMapDirectoryParent;
    Button directoryButton;
    
    //Button for okay and cancel
    Button okButton;
    Button closeButton;
    
    public static final String OK = "OK";
    public static final String CANCEL = "CANCEL";
    String selection;
    
    String dataFile;
    File directoryFile;
    Stage stage;
    
    
    private NewMapDialog() {}
    
    public static NewMapDialog getSingleton() {
	if (singleton == null)
	    singleton = new NewMapDialog();
	return singleton;
    }
    
    public void init(Stage owner) {
        // MAKE IT MODAL
        initModality(Modality.WINDOW_MODAL);
        initOwner(owner);
        
        // LABEL TO DISPLAY THE CUSTOM MESSAGE
        newMapLabel = new Label();
        newMapName = new Label("Map Name:");
        newMapNameInput = new TextField();
        
        newMapData = new Label("Map Data:");
        dataButton = new Button("Select file");
        
        dataPath = new Label();
        filePath = new Label();

        newMapDirectoryParent = new Label("Map Parent Directory:");
        directoryButton = new Button("Select Path");
        
        EventHandler loadWorkspace = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            NewMapDialog.this.selection = sourceButton.getText();
            NewMapDialog.this.close();
        };
        
        dataButton.setOnAction(e->{ chooseDataFile();});
        directoryButton.setOnAction(e->{ chooseDirectory();});
        
        // CLOSE BUTTON
        okButton = new Button(OK);
        okButton.setOnAction(loadWorkspace);
        //closeButton = new Button(CANCEL);
        //closeButton.setOnAction(e->{ NewMapDialog.this.close(); });
        
        // WE'LL PUT EVERYTHING HERE
        newMapPane = new GridPane();
        newMapPane.setPadding(new Insets(20));
        newMapPane.setHgap(5);
        newMapPane.setVgap(20);
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(50, 250, 300);
        column2.setHgrow(Priority.ALWAYS);
        newMapPane.getColumnConstraints().addAll(column1, column2);
        
// MAKE IT LOOK NICE
        GridPane.setHalignment(newMapName, HPos.LEFT);
        newMapPane.add(newMapName, 0, 0);
        GridPane.setHalignment(newMapNameInput, HPos.LEFT);
        newMapPane.add(newMapNameInput, 1, 0);
        
        GridPane.setHalignment(newMapData, HPos.LEFT);
        newMapPane.add(newMapData, 0, 1);
        
        GridPane.setHalignment(dataPath, HPos.LEFT);
        newMapPane.add(dataPath, 1, 1);
        
        GridPane.setHalignment(dataButton, HPos.LEFT);
        newMapPane.add(dataButton, 1, 2);
        
        GridPane.setHalignment(newMapDirectoryParent, HPos.LEFT);
        newMapPane.add(newMapDirectoryParent , 0, 3);
        
        GridPane.setHalignment(filePath, HPos.LEFT);
        newMapPane.add(filePath, 1, 3);
        
        GridPane.setHalignment(directoryButton, HPos.LEFT);
        newMapPane.add(directoryButton, 1, 4);
        
        GridPane.setHalignment(okButton, HPos.LEFT);
        newMapPane.add(okButton, 1,5 );
//        GridPane.setHalignment(closeButton, HPos.LEFT);
//        newMapPane.add(closeButton, 1, 5);
        
        //newMapPane.setHalignment(newMapLabel, HPos.LEFT);

        // AND PUT IT IN THE WINDOW
        newMapScene = new Scene(newMapPane);
        this.setScene(newMapScene);
    }
    
    private void chooseDataFile() {
	// WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_RAWDATA));
        File selectedFile = fc.showOpenDialog(stage);
        
        dataFile = selectedFile.getAbsolutePath();
        dataPath.setText(dataFile);
    }
    
    private void chooseDirectory(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        chooser.setInitialDirectory(new File(PATH_EXPORT));
        File selectedDirectory = chooser.showDialog(stage);
        
        directoryFile = selectedDirectory;
        filePath.setText(selectedDirectory.getAbsolutePath());
    }
    
    // map name
    public String getMapName() {
        return newMapNameInput.getText();
    }
    
    public String getDataFile() {
        return dataFile;
    }
    
    public File getDirectoryFile() {
        return directoryFile;
    }
    
    // get selecttion
    public String getSelection() {
        return selection;
    }
 
    public void initStyle() {
        okButton.getStyleClass().add(CLASS_EDITTOOLBAR_PANE);
    }

    public void show(String title) {
	setTitle(title);
        showAndWait();
    }
}