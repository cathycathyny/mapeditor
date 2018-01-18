package rvme.gui;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javax.swing.JOptionPane;
import rvme.controller.MapandTableController;
import rvme.data.DataManager;
import properties_manager.PropertiesManager;
import saf.ui.AppGUI;
import saf.AppTemplate;
import saf.components.AppWorkspaceComponent;
import rvme.PropertyType;
import rvme.controller.EditToolbarController;
import rvme.data.subRegion;
import static saf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static saf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static saf.settings.AppStartupConstants.FILE_PROTOCOL;
import static saf.settings.AppStartupConstants.PATH_IMAGES;
import saf.ui.AppMessageDialogSingleton;
import saf.ui.ChangeDimensionDialog;
/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Siqing Lee
 * @version 1.0
 */
public class Workspace extends AppWorkspaceComponent {
    // THESE CONSTANTS ARE FOR TYING THE PRESENTATION STYLE OF
    // THIS Workspace'S COMPONENTS TO A STYLE SHEET THAT IT USES
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_MAPNAME_BOX = "mapNameBox";
    static final String CLASS_MAPNAME_LABEL = "mapNameLabel";
    static final String CLASS_LABEL ="label";
    static final String CLASS_PADDING = "padding";
    static final String CLASS_MAPNAME_TEXTFIELD = "mapNameTextfield";
    static final String CLASS_BORDER_SLIDER = "borderSlider";
    static final String CLASS_ZOOM_SLIDER = "zoomSlider";

    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;
    
    // THIS CONTROLLER PROVIDES THE RESPONSES TO INTERACTIONS
    EditToolbarController  editToolbarController;
    MapandTableController mapandTableController;
    
    //CHANGE MAP NAME TEXTFIELD
    FlowPane mapNameBox;
    Label mapNameLabel;
    TextField mapName;
    
    //EDIT TOOLBAR 
    HBox buttonBox;
    Button addImageButton;
    Button removeImageButton;
    Button playButton;
    Button pauseButton;
    Button bgColorButton;
    Button borderColorButton;
    ColorPicker bgColorPicker;
    ColorPicker borderColorPicker;
    Slider borderThicknessSlider;
    Slider mapZoomingSlider;
    Button randomColor;
    Button changeDimensionButton;        
    
    Label backgroundColorLabel;
    Label borderColorLabel;
    Label borderThicknessLabel;
    Label zoomLabel;
    
    //STACK PANE FOR TABLE AND MAP
    StackPane spLeft;
    Group group;
    Pane map;
    Pane imagePane;
    Image newImage;
    int imageNumber;
    int dragNumber;
    int currentSelect;
    
    StackPane spRight;
    
    //FOR THE MAP
    
    ImageView imageView;
    
    SplitPane sp;
    
    //TABLE FOR SUBREGION DETAILS
    TableView<subRegion> subRegionTable;
    TableColumn subRegionNameColumn;
    TableColumn subRegionCapitalColumn;
    TableColumn subRegionLeaderColumn;
    
    protected FlowPane editToolbarPane;
    Rectangle rect;
    
    int beforerow;
    subRegion beforeRegion;
    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public Workspace(AppTemplate initApp) throws IOException {
	// KEEP THIS FOR LATER
	app = initApp;

	// KEEP THE GUI FOR LATER
	gui = app.getGUI();
       
        //INIT EDIT TOOLBAR
        initEditToolbar();
        
        // INIT ALL WORKSPACE COMPONENTS
	layoutGUI();
        
        // AND SETUP EVENT HANDLING
	setupHandlers();
    }
    
    private void initEditToolbar(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        DataManager data = (DataManager)app.getDataComponent();
        
        // EDIT TOOLBAR FOR ALL THE EDIT STAFF
        editToolbarPane = new FlowPane();

        //CHANGE MAP NAME
        mapNameLabel = setImageLabel(editToolbarPane, PropertyType.PENCIL_ICON.toString(), PropertyType.PENCIL_TOOLTIP.toString());
        mapName = new TextField();
        mapName.setPromptText(props.getProperty(PropertyType.MAP_NAME_LABEL));
        editToolbarPane.getChildren().add(mapName);
        
        //COLOR PICKER FOR BACKGROUND COLOR CHANGE
        backgroundColorLabel = setImageLabel(editToolbarPane, PropertyType.BACKGROUND_COLOR_ICON.toString(), PropertyType.BACKGROUND_COLOR_TOOLTIP.toString());
        bgColorPicker = new ColorPicker();
        editToolbarPane.getChildren().add(bgColorPicker);
        
        //COLOR PICKER FOR BORDER COLOR CHANGE
        borderColorLabel = setImageLabel(editToolbarPane, PropertyType.BORDER_COLOR_ICON.toString(), PropertyType.BORDER_COLOR_TOOLTIP.toString());
        borderColorPicker = new ColorPicker();
        editToolbarPane.getChildren().add(borderColorPicker);
        
        buttonBox = new HBox();
        //RANDCOLO BUTTON
        randomColor = gui.initChildButton(buttonBox, PropertyType.RANDOM_ICON.toString(), PropertyType.RANDOM_TOOLTIP.toString(), false);
        //ADD IMAGE AND REMOVE IMAGE BUTTON
        addImageButton = gui.initChildButton(buttonBox, PropertyType.ADD_ICON.toString(), PropertyType.ADD_ITEM_TOOLTIP.toString(), false);
        removeImageButton = gui.initChildButton(buttonBox, PropertyType.REMOVE_ICON.toString(), PropertyType.REMOVE_ITEM_TOOLTIP.toString(), true);
        changeDimensionButton = gui.initChildButton(buttonBox, PropertyType.SIZING_ICON.toString(), PropertyType.SIZING_TOOLTIP.toString(), false);
        playButton = gui.initChildButton(buttonBox, PropertyType.PLAY_ICON.toString(), PropertyType.REMOVE_ITEM_TOOLTIP.toString(), false);
        pauseButton = gui.initChildButton(buttonBox, PropertyType.PAUSE_ICON.toString(), PropertyType.SIZING_TOOLTIP.toString(), true);
        editToolbarPane.getChildren().add(buttonBox);
        
        // BORDER THICKNESS SLIDER
        borderThicknessLabel = setImageLabel(editToolbarPane, PropertyType.BORDER_THICKNESS_ICON.toString(), PropertyType.BORDER_THICKNESS_TOOLTIP.toString());
        borderThicknessSlider = new Slider();
        borderThicknessSlider.setMin(0);
        borderThicknessSlider.setBlockIncrement(0.0001);
        borderThicknessSlider.setMax(0.01);
        borderThicknessSlider.setShowTickLabels(true);
        borderThicknessSlider.setShowTickMarks(true);
        editToolbarPane.getChildren().add(borderThicknessSlider);
        
        // ZOOM MAP SLIDER
        zoomLabel = setImageLabel(editToolbarPane, PropertyType.ZOOM_ICON.toString(), PropertyType.ZOOM_TOOLTIP.toString());
        mapZoomingSlider = new Slider();
        mapZoomingSlider.setMin(1);
        mapZoomingSlider.setMax(5000);
        mapZoomingSlider.setValue(1);
        mapZoomingSlider.setShowTickLabels(true);
        mapZoomingSlider.setShowTickMarks(true);
        mapZoomingSlider.setBlockIncrement(100);
        editToolbarPane.getChildren().add(mapZoomingSlider);
        
        workspace = new VBox();
        workspace.getChildren().add(editToolbarPane);
        
    }
    
    private void layoutGUI() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        DataManager data = (DataManager)app.getDataComponent();
        
        // AND NOW SETUP THE SPLITE PANE FOR THE WORKSPACE
        sp = new SplitPane();
        sp.setPrefSize(gui.getAppPane().getWidth(), gui.getAppPane().getHeight());
        
        //LEFT PANE FOR THE MAP
        spLeft = new StackPane();
        
        map = new Pane();
        //map.setMinSize(802, 536);
       
	group = new Group();
        Polygon polygon = new Polygon();
        //data.setPolygon(map.getChildren());
        //group.getChildren().add(polygon);
        rect = new Rectangle(data.getMapWidth(), data.getMapHeight());
        rect.setFill(Color.WHITE);
        //map.getChildren().add(group);
        
        Image image = new Image("file:./images/maps.png");
        imageView = new ImageView();
        imageView.setImage(image);
        
        imagePane = new Pane();
        spLeft.getChildren().add(rect);
        spLeft.getChildren().add(group);
        //spLeft.getChildren().add(map);
        spLeft.getChildren().add(imagePane);
        
        //RIGHT PANE FOR THE TABLE
        subRegionTable = new TableView();
        
        subRegionNameColumn = new TableColumn(props.getProperty(PropertyType.NAME_COLUMN_HEADING));
        subRegionCapitalColumn = new TableColumn(props.getProperty(PropertyType.CAPITAL_COLUMN_HEADING));
        subRegionLeaderColumn = new TableColumn(props.getProperty(PropertyType.LEADER_COLUMN_HEADING));
        
        subRegionNameColumn.setMinWidth(200);
        subRegionCapitalColumn.setMinWidth(200);
        subRegionLeaderColumn.setMinWidth(200);
        
        subRegionNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        subRegionCapitalColumn.setCellValueFactory(new PropertyValueFactory<>("capital"));
        subRegionLeaderColumn.setCellValueFactory(new PropertyValueFactory<>("leader"));
       
        subRegionTable.getColumns().add(subRegionNameColumn);
        subRegionTable.getColumns().add(subRegionCapitalColumn);
        subRegionTable.getColumns().add(subRegionLeaderColumn);
        
        subRegionTable.setItems(data.getSubRegion());
        subRegionTable.setPadding(new Insets(10));
        spRight = new StackPane();
        spRight.getChildren().add(subRegionTable);
        
        sp.getItems().addAll(spLeft, spRight);
        sp.setDividerPositions(0.5f);
        //imageView.setFitWidth(spLeft.getWidth());
        //map.fitWidthProperty().bind(spLeft.widthProperty()); 
        imageView.fitHeightProperty().bind(spLeft.heightProperty());
        workspace.getChildren().add(sp);
    }
   
    
    private void setupHandlers() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        DataManager data = (DataManager)app.getDataComponent();
        
        // MAKE THE CONTROLLER
        editToolbarController = new EditToolbarController(app);
        mapandTableController = new MapandTableController(app);
        
        // CHANGE THE MAP NAME 
        mapName.textProperty().addListener((observable, oldValue, newValue) -> {
             editToolbarController.changeMapName(newValue);
        });
        
        // CHANGE BACKGROUND COLOR
        bgColorPicker.setOnAction(e->{
            editToolbarController.changeBackgroundColor(bgColorPicker.getValue());
        });
        
        // CHANGE BORDER COLOR
        borderColorPicker.setOnAction(e->{
            editToolbarController.changeBorderColor(borderColorPicker.getValue());
        });
        
        // RANDOM COLOR
        randomColor.setOnAction(e->{
            editToolbarController.randomColor();
        });
        
        // CHANGE BORDER THICKNESS 
        borderThicknessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
             editToolbarController.changeBorderThickness((double) newValue);
        });
        
        // ADD IMAGES TO THE MAP
        addImageButton.setOnAction(e->{
            try {
                editToolbarController.addImage(spLeft);
            } catch (IOException ex) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        });
        
        // HIGHLIGH THE IMAGE WHEN MOUSECLICKED
        imagePane.setOnMouseClicked(e->{
            for (int i=0;i<data.getImageDetail().size();i++){
                if (e.getTarget()==imagePane.getChildren().get(i)){
                    if(currentSelect==i){
                        imageNumber= 999;
                        deselectImage((ImageView) imagePane.getChildren().get(i));
                        removeImageButton.setDisable(true);
                        currentSelect = 999;
                    }else if (currentSelect==999){
                        imageNumber= i;
                        selectImage((ImageView) imagePane.getChildren().get(i));
                        removeImageButton.setDisable(false);
                        currentSelect = i;
                    }else{
                        imageNumber = i;
                        selectImage((ImageView) imagePane.getChildren().get(i));
                        deselectImage((ImageView) imagePane.getChildren().get(currentSelect));
                        removeImageButton.setDisable(false);
                        currentSelect = i;
                    }
                }
            }
        });
        
        // REMOVE IMAGE BUTTON
        removeImageButton.setOnAction(e->{
            editToolbarController.removeImage(imageNumber);
            removeImageButton.setDisable(true);
        });
        
        // DRAG AND DROP THE IMAGE
        imagePane.setOnMouseDragged(e->{
            for (int i = 0;i<data.getImageDetail().size();i++){
                if (e.getTarget()==imagePane.getChildren().get(i)){
                    dragNumber = i;
                    selectImage((ImageView) imagePane.getChildren().get(i));
                    imagePane.getChildren().get(i).setTranslateX(e.getX());
                    imagePane.getChildren().get(i).setTranslateY(e.getY());
                    editToolbarController.updateImagePlace(i,  imagePane.getChildren().get(i).getTranslateX(),  imagePane.getChildren().get(i).getTranslateY());
                }
            }
        });
        
        AudioManager audio = new AudioManager();
	//play music
        playButton.setOnAction(e->{
            try{
                audio.loadAudio("NA", data.getAnthemFile());
                audio.play("NA", true);
            }catch(Exception i){
                JOptionPane.showMessageDialog(null, i.getStackTrace());
            }
            pauseButton.setDisable(false);
        });
        
        // pause music
        pauseButton.setOnAction(e->{
            audio.stop("NA");
            pauseButton.setDisable(true);
        });
        
        
        //map zoom
         mapZoomingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
             //editToolbarController.changeBorderThickness((double) newValue);
             group.setScaleX((double) newValue);
             group.setScaleY((double) newValue);
             data.setMapZoom((double) newValue);
        });
        
        changeDimensionButton.setOnAction(e->{
            ChangeDimensionDialog newDimension = ChangeDimensionDialog.getSingleton();
            newDimension.show("Change Dimension");
            String selection = newDimension.getSelection();
            
        });
                
        
        spLeft.setOnMouseClicked((MouseEvent e)->{
            imagePane.setPickOnBounds(false);
            for(int p =0;p<data.getPolygon().size();p++){
                Polygon changePolygon = new Polygon();
                changePolygon = (Polygon) data.getPolygon().get(p);
                if(changePolygon.getFill().equals(Color.GREEN)){
                    changePolygon.setFill(Color.rgb(data.getSubRegion().get(p).getRed(), data.getSubRegion().get(p).getGreen(), data.getSubRegion().get(p).getBlue()));
                }
                
                if(e.getClickCount() == 1 && e.getTarget()==changePolygon){
                    changePolygon.setFill(Color.GREEN);
                    subRegion viewRegion = (subRegion) data.getSubRegion().get(p);
                    subRegionTable.getSelectionModel().select(viewRegion);
                }
                
                if (e.getClickCount() == 2 && e.getTarget()==changePolygon) {
                    subRegion viewRegion = (subRegion) data.getSubRegion().get(p);
                    mapandTableController.viewSubregionDetails(viewRegion, data.getIndex(viewRegion));
                    
                }
            }
        });
        
        
        spLeft.setOnKeyPressed((KeyEvent ke) -> {
            if(ke.getCode().equals(KeyCode.LEFT)){
                group.setTranslateX(map.getTranslateX()-5);
            }
            
            if(ke.getCode().equals(KeyCode.RIGHT)){
                group.setTranslateX(map.getTranslateX()+5);
            }
            
            if(ke.getCode().equals(KeyCode.UP)){
                group.setTranslateY(map.getTranslateY()-5);
            }
            
            if(ke.getCode().equals(KeyCode.DOWN)){
                group.setTranslateY(map.getTranslateY()+5);
            }
        });
        
        
        
        // view subregion details from the table
        subRegionTable.setOnMousePressed(e->{
            if (e.getClickCount() == 1) {
                subRegion viewRegion = (subRegion) subRegionTable.getSelectionModel().getSelectedItem();
                int currentrow = subRegionTable.getSelectionModel().getSelectedIndex();
                if(currentrow!=beforerow){
                for(int p =0;p<data.getPolygon().size();p++){
                    Polygon changePolygon = new Polygon();
                    changePolygon = (Polygon) data.getPolygon().get(p);
                        
                    if(changePolygon.getFill().equals(Color.GREEN)){
                        changePolygon.setFill(Color.rgb(beforeRegion.getRed(),beforeRegion.getGreen(),beforeRegion.getBlue()));
                    }
                        if(changePolygon.getFill().equals(Color.rgb(viewRegion.getRed(),viewRegion.getGreen(),viewRegion.getBlue()))){
                            changePolygon.setFill(Color.GREEN);
                            beforerow = subRegionTable.getSelectionModel().getSelectedIndex();
                            beforeRegion = subRegionTable.getSelectionModel().getSelectedItem();
                        }
                }
                }
            }
            if (e.getClickCount() == 2) {
                subRegion viewRegion = (subRegion) subRegionTable.getSelectionModel().getSelectedItem();
                mapandTableController.viewSubregionDetails(viewRegion, data.getIndex(viewRegion));
            }
        });
    }
    
    public Label setImageLabel(Pane toolbar, String icon, String tooltip){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
        Image buttonImage = new Image(imagePath);
	
	// NOW MAKE THE BUTTON
        Label label = new Label();
        label.setGraphic(new ImageView(buttonImage));
        Tooltip labelTooltip = new Tooltip(props.getProperty(tooltip));
        label.setTooltip(labelTooltip);
	
	// PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(label);
	
	// AND RETURN THE COMPLETED BUTTON
        return label;
    }
   
    
    public void setImage(ButtonBase button, String fileName) {
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + fileName;
        Image buttonImage = new Image(imagePath);
	
	// SET THE IMAGE IN THE BUTTON
        button.setGraphic(new ImageView(buttonImage));	
    }
    
    public void selectImage(ImageView image){
        DropShadow ds = new DropShadow( 20, Color.AQUA );
        image.setEffect(ds);
    }
    
    public void deselectImage(ImageView image){
        image.setEffect(null);
    }
    
    /**
     * This function specifies the CSS style classes for all the UI components
     * known at the time the workspace is initially constructed. Note that the
     * tag editor controls are added and removed dynamicaly as the application
     * runs so they will have their style setup separately.
     */
    @Override
    public void initStyle() {
	// NOTE THAT EACH CLASS SHOULD CORRESPOND TO
	// A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
	// CSS FILE
        
        // FOR CHANGING THE ITEMS INSIDE THE EDIT TOOLBAR
        editToolbarPane.getStyleClass().add(CLASS_EDITTOOLBAR_PANE);
        
        mapNameLabel.getStyleClass().add(CLASS_MAPNAME_LABEL);
        mapName.getStyleClass().add(CLASS_MAPNAME_TEXTFIELD);
        backgroundColorLabel.getStyleClass().add(CLASS_LABEL);
        borderColorLabel.getStyleClass().add(CLASS_LABEL);
        borderThicknessLabel.getStyleClass().add(CLASS_LABEL);
        zoomLabel.getStyleClass().add(CLASS_MAPNAME_LABEL);
        borderThicknessSlider.getStyleClass().add(CLASS_BORDER_SLIDER);
        mapZoomingSlider.getStyleClass().add(CLASS_ZOOM_SLIDER);
        buttonBox.getStyleClass().add(CLASS_PADDING);
        // FIRST THE WORKSPACE PANE
        workspace.getStyleClass().add(CLASS_BORDERED_PANE);
    }

    

    @Override
    public void selectRegion(int red, int green, int blue) {
        DataManager data = (DataManager)app.getDataComponent();
        Polygon polygon = new Polygon();
        polygon.setFill(Color.rgb(red, green, blue));
        DropShadow ds = new DropShadow( 50, Color.YELLOW );
        
        for (int p = 0; p<data.getPolygon().size();p++){
        Polygon changePolygon = new Polygon();
        changePolygon = (Polygon) data.getPolygon().get(p);
            if(changePolygon.getFill().equals(polygon.getFill())){
                changePolygon.setFill(Color.YELLOW);
            }
        }
    }
    
    @Override
    public void removeSelectRegion(int red, int green, int blue) {
        DataManager data = (DataManager)app.getDataComponent();
        Polygon polygon = new Polygon();
        polygon.setFill(Color.YELLOW);
        
        for (int p = 0; p<data.getPolygon().size();p++){
        Polygon changePolygon = new Polygon();
        changePolygon = (Polygon) data.getPolygon().get(p);
            if(changePolygon.getFill().equals(polygon.getFill())){
                changePolygon.setFill(Color.rgb(red,green,blue));
            }
        }
    }
    
    
    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {
	DataManager data = (DataManager)app.getDataComponent();
        mapName.setText(data.getMapName());
        bgColorPicker.setValue(Color.web(data.getBackgroundColor()));
        borderColorPicker.setValue(Color.web(data.getBorderColor()));
        //map.setMinSize(data.getMapWidth(), data.getMapHeight());
        //map.setMaxSize(data.getMapWidth(), data.getMapHeight());
        map.setBackground(new Background(new BackgroundFill(Color.web(data.getBackgroundColor()), null, null)));
        
        //map.setBorder(new Border(new BorderStroke(Color.web(data.getBorderColor()), BorderStrokeStyle.SOLID, null, new BorderWidths(data.getBorderWidth()))));
        mapZoomingSlider.setValue(data.getX());
        borderThicknessSlider.setValue(data.getBorderWidth());
        
        for (int p = 0; p<data.getPolygon().size();p++){
            Polygon changePolygon = new Polygon();
            changePolygon = (Polygon) data.getPolygon().get(p);
            changePolygon.setStrokeWidth(data.getBorderWidth());
            changePolygon.setStroke(Color.web(data.getBorderColor()));
            changePolygon.setFill(Color.rgb(data.getSubRegion().get(p).getRed(), data.getSubRegion().get(p).getGreen(), data.getSubRegion().get(p).getBlue()));
        }
        
        imagePane.getChildren().clear();
        for(int i=0;i<data.getImageDetail().size();i++){
            newImage = new Image("file:"+data.getImageDetail().get(i).getPath());
            ImageView iv1 = new ImageView();
            iv1.setImage(newImage);
            iv1.setTranslateX(data.getImageDetail().get(i).getX());
            iv1.setTranslateY(data.getImageDetail().get(i).getY());
            imagePane.getChildren().add(iv1);
        }
        
        group.getChildren().clear();
        for (int k = 0; k<data.getSubRegion().size();k++){
            Polygon polygon = (Polygon) data.getPolygon().get(k);
            group.getChildren().add(polygon);
        }
        subRegionTable.setItems(data.getSubRegion());
        //group.resize(802, 536);
        group.setScaleX(data.getMapZoom());
        group.setScaleY(data.getMapZoom());
    }

}
