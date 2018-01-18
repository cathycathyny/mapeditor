package rvme.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;
import rvme.data.DataManager;
import rvme.data.image;
import rvme.data.subRegion;

/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Siqing Lee
 * @version 1.0
 */
public class FileManager implements AppFileComponent {
    
    //
    static final String JSON_NAME = "name";
    static final String JSON_HAVECAPITAL = "subregions_have_capitals";
    static final String JSON_HAVEFLAG = "subregions_have_flags";
    static final String JSON_HAVELEADER = "subregions_have_leaders";
    static final String JSON_SUBREGIONS = "subregions";
    static final String JSON_CAPITAL = "capital";
    static final String JSON_FLAG = "flag";
    static final String JSON_LEADER = "leader";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_IMAGE = "image";
    static final String JSON_PATH = "path";
    
    //ALL CONTROLS
    static final String JSON_CONTROLS = "Controls";
    static final String JSON_BACKGROUNDCOLOR = "background-color";
    static final String JSON_BORDERCOLOR = "border-color";
    static final String JSON_BORDERWIDTH = "border-width";
    static final String JSON_MAPWIDTH = "map-width";
    static final String JSON_MAPHEIGHT = "map-height";
    static final String JSON_MAPZOOM= "zoom-level";
    
    //All FILE Data
    static final String JSON_FILE = "file Path";
    static final String JSON_PARENTFILE = "parent-directory";
    static final String JSON_DATAFILE = "map-data";
    static final String JSON_IMAGEFILE = "imagefile";
    static final String JSON_ANTHEMFILE = "anthem";
    
    // FOR JSON LOADING
    static final String JSON_NUMBER_OF_SUBREGIONS = "NUMBER_OF_SUBREGIONS";
    static final String JSON_SUBREGION = "SUBREGIONS";
    static final String JSON_NUMBER_OF_SUBREGION_POLYGONS = "NUMBER_OF_SUBREGION_POLYGONS";
    static final String JSON_SUBREGION_POLYGONS = "SUBREGION_POLYGONS";
    static final String JSON_X = "X";
    static final String JSON_Y = "Y";
    
    /**
     * This method is for saving user work.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
	// GET THE DATA
	DataManager dataManager = (DataManager)data;
        
	// FIRST THE LIST NAME AND OWNER
	String mapName = dataManager.getMapName();
        Boolean haveCapital = dataManager.getIsHaveCapital();
        Boolean haveFlag = dataManager.getIsHaveFlags();
        Boolean haveLeader = dataManager.getIsHaveLeader();
        
	// NOW BUILD THE JSON ARRAY FOR THE LIST
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	ObservableList<subRegion> mapData = dataManager.getSubRegion();
	for (subRegion map : mapData) {	    
	    JsonObject itemJson = Json.createObjectBuilder()
		    .add(JSON_NAME, map.getName())
		    .add(JSON_CAPITAL, map.getCapital())
		    .add(JSON_LEADER, map.getLeader())
		    .add(JSON_RED, map.getRed())
		    .add(JSON_GREEN, map.getGreen())
                    .add(JSON_BLUE, map.getBlue()).build();
	    arrayBuilder.add(itemJson);
	}
	JsonArray subregionArray = arrayBuilder.build();
        
        // NOW BUILD THE JSON ARRAY FOR THE LIST
	ObservableList<image> image = dataManager.getImageDetail();
	for (image map : image) {	    
	    JsonObject imagep = Json.createObjectBuilder()
		    .add(JSON_PATH, map.getPath())
                    .add(JSON_X, map.getX())
                    .add(JSON_Y, map.getY()).build();
	    arrayBuilder.add(imagep);
	}
	JsonArray imageArray = arrayBuilder.build();
	
        // NOW BUILD THE JSON ARRAY FOR THE CONTROLS   
	JsonObject control = Json.createObjectBuilder()
		    .add(JSON_BACKGROUNDCOLOR, dataManager.getBackgroundColor())
		    .add(JSON_BORDERCOLOR, dataManager.getBorderColor())
		    .add(JSON_BORDERWIDTH, dataManager.getBorderWidth())
		    .add(JSON_MAPWIDTH, dataManager.getMapWidth())
		    .add(JSON_MAPHEIGHT, dataManager.getMapHeight())
                    .add(JSON_MAPZOOM, dataManager.getMapZoom()).build();
	arrayBuilder.add(control);
	JsonArray controlArray = arrayBuilder.build();
	
        // NOW BUILD THE JSON ARRAY FOR THE CONTROLS   
	JsonObject datapath = Json.createObjectBuilder()
		    .add(JSON_PARENTFILE, dataManager.getParentDirectory())
		    .add(JSON_DATAFILE, dataManager.getMapDataFile())
		    .add(JSON_IMAGEFILE, dataManager.getImageFile())
		    .add(JSON_ANTHEMFILE, dataManager.getAnthemFile()).build();
	arrayBuilder.add(datapath);
	JsonArray dataArray = arrayBuilder.build();
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_NAME, mapName)
                .add(JSON_HAVECAPITAL, haveCapital)
		.add(JSON_HAVEFLAG, haveFlag)
                .add(JSON_HAVELEADER, haveLeader)
                .add(JSON_IMAGE, imageArray)
                .add(JSON_SUBREGIONS, subregionArray)
                .add(JSON_CONTROLS, controlArray)
                .add(JSON_FILE, dataArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    /**
     * This method loads data from a JSON formatted file into the data 
     * management component and then forces the updating of the workspace
     * such that the user may edit the data.
     * 
     * @param data Data management component where we'll load the file into.
     * 
     * @param filePath Path (including file name/extension) to where
     * to load the data from.
     * 
     * @throws IOException Thrown should there be an error reading
     * in data from the file.
     */
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	DataManager dataManager = (DataManager)data;
	dataManager.reset();
        
	// LOAD THE JSON FILE WITH ALL THE MAP SIMPLE DATA
	JsonObject json = loadJSONFile(filePath);
        String mapName = json.getString(JSON_NAME);
	Boolean capital = json.getBoolean(JSON_HAVECAPITAL);
        Boolean flag= json.getBoolean(JSON_HAVEFLAG);
        Boolean leader = json.getBoolean(JSON_HAVELEADER);
        dataManager.setMapName(mapName);
        dataManager.setHaveCapitals(capital);
        dataManager.setHaveFlags(flag);
        dataManager.setHaveLeaders(leader);
        
        JsonArray jsonImageArray = json.getJsonArray(JSON_IMAGE);
        for (int i = 0; i < jsonImageArray.size(); i++) {
            JsonObject jsonImage = jsonImageArray.getJsonObject(i);
            image imageDetail = loadImage(jsonImage);
	    dataManager.addImageDetails(imageDetail);
        }
        
        // AND NOW LOAD ALL THE SUBREGIONS DATA
        JsonArray jsonSubregionArray = json.getJsonArray(JSON_SUBREGIONS);
        for (int i = 0; i < jsonSubregionArray.size(); i++) {
            JsonObject jsonSr = jsonSubregionArray.getJsonObject(i);
	    subRegion sr = loadSubregion(jsonSr);
	    dataManager.addSubregions(sr);
        }
        
        // AND NOW LOAD ALL THE SUBREGIONS DATA
        JsonArray jsonControlArray = json.getJsonArray(JSON_CONTROLS);
        JsonObject control = jsonControlArray.getJsonObject(0);
	dataManager.setBackgroundColor(control.getString(JSON_BACKGROUNDCOLOR));
        dataManager.setBorderColor(control.getString(JSON_BORDERCOLOR));
        //dataManager.setBorderWidth(control.(JSON_BORDERWIDTH));
        dataManager.setBorderWidth(getDataAsDouble(control,JSON_BORDERWIDTH));
        dataManager.setMapWidth(control.getInt(JSON_MAPWIDTH));
        dataManager.setMapHeight(control.getInt(JSON_MAPHEIGHT));
        dataManager.setMapZoom(control.getInt(JSON_MAPZOOM));
        
        // AND NOW LOAD ALL THE FILE PATH
        JsonArray jsonFileArray = json.getJsonArray(JSON_FILE);
        JsonObject file = jsonFileArray.getJsonObject(0);
	dataManager.setParentDirectory(file.getString(JSON_PARENTFILE));
        dataManager.setMapDataFile(file.getString(JSON_DATAFILE));
        dataManager.setImageFile(file.getString(JSON_IMAGEFILE));
        dataManager.setAnthemFile(file.getString(JSON_ANTHEMFILE));
        
        // AND NOW LOAD ALL THE POLYGON
        JsonObject map = loadJSONFile(dataManager.getMapDataFile());
	JsonArray jsonPolygonArray = map.getJsonArray(JSON_SUBREGION);
        int minx=0;
        int miny=0;
        int wi = 0;
        int he = 0;
        List<Integer> pointw = new ArrayList<>();
        List<Integer> pointh = new ArrayList<>();
        List<Integer> xmin= new ArrayList<>();
        List<Integer> ymin = new ArrayList<>();
        for (int i = 0; i < jsonPolygonArray.size(); i++) {
            JsonObject jsonPoint = jsonPolygonArray.getJsonObject(i);
            JsonArray jsonPointArray = jsonPoint.getJsonArray(JSON_SUBREGION_POLYGONS);
            
            for (int j = 0; j < jsonPointArray.size(); j++){
                JsonArray values = jsonPointArray.getJsonArray(j);
                Polygon newPolygon = new Polygon();
                List<Double> pointx = new ArrayList<>();
                List<Double> pointy = new ArrayList<>();
                
                for (int k = 0; k < values.size(); k++) {
                    JsonObject jsonPointw = values.getJsonObject(k);
                    double x = getDataAsDouble(jsonPointw, JSON_X);
                    double y = getDataAsDouble(jsonPointw, JSON_Y);
                    //add the point to the array list
                    pointx.add(x*100);
                    pointy.add(y*100);
                }
                double min = Collections.min(pointx);
                double max = Collections.min(pointy);
                int w = (int) (Collections.max(pointx)-Collections.min(pointx));
                int h = (int) (Collections.max(pointy)-Collections.min(pointy));
                
                pointw.add(w);
                pointh.add(h);
                xmin.add((int) min);
                ymin.add((int) max);
                
                wi = (int) (Collections.max(pointw)-Collections.min(pointw));
                he = (int) (Collections.max(pointh)-Collections.min(pointh));
                minx = Collections.min(xmin);
                miny = Collections.min(ymin);
            }
	}
        
        for (int i = 0; i < jsonPolygonArray.size(); i++) {
            JsonObject jsonPoint = jsonPolygonArray.getJsonObject(i);
            JsonArray jsonPointArray = jsonPoint.getJsonArray(JSON_SUBREGION_POLYGONS);
            
            for (int j = 0; j < jsonPointArray.size(); j++){
                JsonArray values = jsonPointArray.getJsonArray(j);
                Polygon newPolygon = new Polygon();
                List<Double> points = new ArrayList<>();
                
                for (int k = 0; k < values.size(); k++) {
                    JsonObject jsonPointw = values.getJsonObject(k);
                    double x = getDataAsDouble(jsonPointw, JSON_X)*100;
                    double y = getDataAsDouble(jsonPointw, JSON_Y)*100;
                    //add the point to the array list
                    points.add((802/wi*(x-minx))/5+180);
                    points.add((536/he*(miny-y))/5+500);
                }
                newPolygon.setStroke(Color.BLACK);
                newPolygon.setStrokeWidth(0.1);
                newPolygon.setFill(Color.rgb(dataManager.getSubRegion().get(i).getRed(),dataManager.getSubRegion().get(i).getGreen(),dataManager.getSubRegion().get(i).getBlue()));
                newPolygon.getPoints().addAll(points);
                dataManager.addPolygon(newPolygon);
            }
	}
    }
    
    public subRegion loadSubregion(JsonObject jsonItem) {
	// GET THE DATA
	String name = jsonItem.getString(JSON_NAME);
	String capital = jsonItem.getString(JSON_CAPITAL);
        String leader= jsonItem.getString(JSON_LEADER);
        int red = jsonItem.getInt(JSON_RED);
        int green = jsonItem.getInt(JSON_GREEN);
        int blue = jsonItem.getInt(JSON_BLUE);
        
	// THEN USE THE DATA TO BUILD AN ITEM
        subRegion sr = new subRegion(name, capital, leader, red, green, blue);
        
	// ALL DONE, RETURN IT
	return sr;
    }
    
    public image loadImage(JsonObject jsonItem) {
	// GET THE DATA
	String path = jsonItem.getString(JSON_PATH);
        double x = getDataAsDouble(jsonItem,JSON_X);
        double y = getDataAsDouble(jsonItem,JSON_Y);
        
	// THEN USE THE DATA TO BUILD AN ITEM
        image newImage = new image(path,x,y);
        
	// ALL DONE, RETURN IT
	return newImage;
    }
    
    public double getDataAsDouble(JsonObject json, String dataName) {
	JsonValue value = json.get(dataName);
	JsonNumber number = (JsonNumber)value;
	return number.bigDecimalValue().doubleValue();	
    }
    
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    
    /**
     * This method would be used to export data to another format,
     * which we're not doing in this assignment.
     */
    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        // GET THE DATA
	DataManager dataManager = (DataManager)data;
        
	// FIRST THE LIST NAME AND OWNER
	String mapName = dataManager.getMapName();
        Boolean haveCapital = dataManager.getIsHaveCapital();
        Boolean haveFlag = dataManager.getIsHaveFlags();
        Boolean haveLeader = dataManager.getIsHaveLeader();
        
	// NOW BUILD THE JSON ARRAY FOR THE LIST
	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
	ObservableList<subRegion> mapData = dataManager.getSubRegion();
	for (subRegion map : mapData) {	    
	    JsonObject itemJson;
            JsonObjectBuilder b = Json.createObjectBuilder()
		    .add(JSON_NAME, map.getName());
                if(haveCapital){
		    b.add(JSON_CAPITAL, map.getCapital());
                }
                if(haveLeader){
		    b.add(JSON_LEADER, map.getLeader());
                }
		    b.add(JSON_RED, map.getRed())
		    .add(JSON_GREEN, map.getGreen())
                    .add(JSON_BLUE, map.getBlue());
            itemJson = b.build();
	    arrayBuilder.add(itemJson);
	}
	JsonArray subregionArray = arrayBuilder.build();
        
        // THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_NAME, mapName)
                .add(JSON_HAVECAPITAL, haveCapital)
		.add(JSON_HAVEFLAG, haveFlag)
                .add(JSON_HAVELEADER, haveLeader)
                .add(JSON_SUBREGIONS, subregionArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(dataManager.getParentDirectory());
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(dataManager.getParentDirectory());
	pw.write(prettyPrinted);
	pw.close();
    }
    
    /**
     * 
     */
    @Override
    public void newData(AppDataComponent data, String mapName, String filePath, File directory) throws IOException {
        // create the directory
        Path pathAbsolute = Paths.get(directory.getAbsolutePath()+"/"+mapName);
        Path pathBase = Paths.get(directory.getParent());
        Path pathRelative = pathBase.relativize(pathAbsolute);
        Files.createDirectories(pathRelative);
        DataManager dataManager = (DataManager)data;
        dataManager.reset();
        
        dataManager.setMapName(mapName);
        
        dataManager.setMapDataFile(filePath);
        dataManager.setParentDirectory("./"+pathRelative);
        dataManager.setImageFile("./"+pathRelative+"/"+mapName);
        dataManager.setAnthemFile("./"+pathRelative+"/"+mapName+".mid");
        
        JsonObject map = loadJSONFile(filePath);
	JsonArray jsonPolygonArray = map.getJsonArray(JSON_SUBREGION);
 
        int color = 255;
        int number = 0;
        for (int i = 0; i < jsonPolygonArray.size(); i++) {
            JsonObject jsonPoint = jsonPolygonArray.getJsonObject(i);
            JsonArray jsonPointArray = jsonPoint.getJsonArray(JSON_SUBREGION_POLYGONS);
            
            for (int j = 0; j < jsonPointArray.size(); j++){
                JsonArray values = jsonPointArray.getJsonArray(j);
                Polygon newPolygon = new Polygon();
                List<Double> points = new ArrayList<>();
                
                for (int k = 0; k < values.size(); k++) {
                    JsonObject jsonPointw = values.getJsonObject(k);
                    double x = getDataAsDouble(jsonPointw, JSON_X);
                    double y = getDataAsDouble(jsonPointw, JSON_Y);
                    //add the point to the array list
                    points.add(802/360*(180+x));
                    points.add(536/180*(90-y));
                }
                newPolygon.setStroke(Color.BLACK);
                newPolygon.setStrokeWidth(dataManager.getBorderWidth());
                newPolygon.setFill(Color.rgb(color,color,color));
                newPolygon.getPoints().addAll(points);
                dataManager.addPolygon(newPolygon);
                dataManager.addSubregions(new subRegion("Subregion Name"+number,"","",color,color,color));
                color-=6;
                number++;
            }
	}
    
    }
}
