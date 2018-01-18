package rvme.data;



import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import saf.components.AppDataComponent;
import saf.AppTemplate;

/**
 * This class serves as the data management component for this application.
 *
 * @author Siqing Lee
 * @version 1.0
 */
public class DataManager implements AppDataComponent {
    
    //Name of the map
    String mapName;
    
    //subregions details T/F
    Boolean subregions_have_capitals;
    Boolean subregions_have_flags;
    Boolean subregions_have_leaders;
    
    // All controls objects
    String backgroundColor;
    String borderColor;
    double borderWidth;
    int mapWidth;
    int mapHeight;
    double mapZoom;
    
    // file path
    String parentDirectory;
    String mapDataFile;
    String imageFile;
    String anthemFile;
    
    double x;
    double y;
    
    //polygon for the map
    ObservableList<Node> polygon = FXCollections.observableArrayList();
    
    ObservableList<image> imageDetail=FXCollections.observableArrayList();
    
    // THESE ARE THE ITEMS IN THE TODO LIST
    ObservableList<subRegion> map =FXCollections.observableArrayList();
    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;
    
    /**
     * THis constructor creates the data manager and sets up the
     *
     *
     * @param initApp The application within which this data manager is serving.
     * @throws java.lang.Exception
     */
    public DataManager(AppTemplate initApp) throws Exception {
	// KEEP THE APP FOR LATER
	app = initApp;
        mapWidth = 802;
        mapHeight = 536;
    }

    public DataManager() {}
    
    public ObservableList<subRegion> getSubRegion() {
	return map;
    }
    
    public ObservableList<image> getImageDetail() {
	return imageDetail;
    }
    
    public void addImageDetails(image newImage) {
        imageDetail.add(newImage);
    }
    // add subregions to the map
    public void addSubregions(subRegion regions) {
        map.add(regions);
    }
    
    // set and get method for each variable
    public void setMapName(String mapName){
        this.mapName = mapName;
    }
    
    public String getMapName(){
        return mapName;
    }
    
    public void setHaveCapitals(Boolean isHaveCapital){
        this.subregions_have_capitals = isHaveCapital;
    }
    
    public Boolean getIsHaveCapital(){
        for(int i=0;i<map.size();i++){
            if(map.get(i).getCapital()==null || "".equals(map.get(i).getCapital()))
                return false;
        }
        return subregions_have_capitals;
    }
    
    public void setHaveFlags(Boolean isHaveFlag){
        this.subregions_have_flags = isHaveFlag;
    }
    
    public Boolean getIsHaveFlags(){
        for(int i=0;i<map.size();i++){
            File file = new File(("./export/The World/Europe/" + getMapName() + "/" + map.get(i).getName() +" Flag.png"));
            boolean exists = file.exists();
            //System.out.println(map.get(i).getName());
            //System.out.println(exists);
            if (!exists) {return false;}
        }
        return subregions_have_flags;
    }
    
    public void setHaveLeaders(Boolean isHaveLeader){
        this.subregions_have_leaders = isHaveLeader;
    }
    
    public Boolean getIsHaveLeader(){
        for(int i=0;i<map.size();i++){
            if(map.get(i).getLeader()==null || "".equals(map.get(i).getLeader()))
                return false;
            File file = new File(("./export/The World/Europe/" + getMapName() + "/" + map.get(i).getLeader() +".png"));
            boolean exists = file.exists();
            //System.out.println(map.get(i).getLeader());
            //System.out.println(exists);
            if (!exists) {return false;}
        }
        return subregions_have_leaders;
    }
    
    // get and set for all controls
    public void setBackgroundColor(String bgColor){
        this.backgroundColor = bgColor;
    }
    
    public String getBackgroundColor(){
        return backgroundColor;
    }
    
    public void setBorderColor(String bColor){
        this.borderColor = bColor;
    }
    
    public String getBorderColor(){
        return borderColor;
    }
    
    public void setBorderWidth(double width){
        this.borderWidth = width;
    }
    
    public double getBorderWidth(){
        return borderWidth;
    }
    
    public void setMapWidth(int width){
        this.mapWidth = width;
    }
    
    public int getMapWidth(){
        return mapWidth;
    }
    
    public void setMapHeight(int height){
        this.mapHeight = height;
    }
    
    public int getMapHeight(){
        return mapHeight;
    }
    
    public void setMapZoom(double zoom){
        this.mapZoom = zoom;
    }
    
    public double getMapZoom(){
        return mapZoom;
    }
    
    // get and set file path
    public void setParentDirectory(String pD){
        this.parentDirectory = pD;
    }
    
    public String getParentDirectory(){
        return parentDirectory;
    }
    
    public void setMapDataFile(String mD){
        this.mapDataFile = mD;
    }
    
    public String getMapDataFile(){
        return mapDataFile;
    }
    
    public void setImageFile(String iF){
        this.imageFile = iF;
    }
    
    public String getImageFile(){
        return imageFile;
    }
    
    public void setAnthemFile(String aF){
        this.anthemFile = aF;
    }
    
    public String getAnthemFile(){
        return anthemFile;
    }
    
    public ObservableList<Node> getPolygon() {
	return polygon;
    }
    
    public void setPolygon(ObservableList<Node> initPolygon) {
	polygon = initPolygon;
    }
    
    public void addPolygon(Polygon polygonToAdd) {
	polygon.add(polygonToAdd);
    }
    
    public int getIndex(subRegion regionPos) {
        return map.indexOf(regionPos);
    }
    
    public void setX(double value){
        this.x = value;
    }
    
    public double getX(){
        return x;
    }
    
    public void setY(double value){
        this.y = value;
    }
    
    public double getY(){
        return y;
    }
    
    /**
     * 
     */
    @Override
    public void reset() {
	// NOW MAKE THE NODES
	//Workspace workspace = (Workspace) app.getWorkspaceComponent();
	// reset
	mapName = "";
        subregions_have_capitals = false;
        subregions_have_flags    = false;
        subregions_have_leaders  = false;
        
        backgroundColor = "#19b1ca";
        borderColor     = "#000000";
        borderWidth     = 0.01;
        mapWidth        = 802;
        mapHeight       = 536;
        mapZoom         = 0;
        
        parentDirectory = "";
        mapDataFile     = "";
        imageFile       = "";
        anthemFile      = "";
        x=500;
        y=500;
        
        //clear all the element inside the list
        map.clear();
        polygon.clear();
        imageDetail.clear();
        //workspace.reloadWorkspace();
    }
}
