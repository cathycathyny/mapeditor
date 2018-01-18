package rvme.test;


import java.io.IOException;
import rvme.data.DataManager;
import rvme.data.image;
import rvme.data.subRegion;
import rvme.file.FileManager;
import saf.AppTemplate;
import saf.components.AppDataComponent;
 

/**
 *
 * @author Siqing Lee
 */
public class TestSave {
    
    AppDataComponent dataComponent;
    AppTemplate app;
    
    public TestSave() {
        
    }
    
    public static void hardCodeData(DataManager dataManager){
        //map details
        dataManager.setMapName("Andorra");
        dataManager.setHaveCapitals(Boolean.TRUE);  
        dataManager.setHaveFlags(Boolean.TRUE);
        dataManager.setHaveLeaders(Boolean.TRUE);
        
        //subregions details
        dataManager.addSubregions(new subRegion("Ordino", "Ordino (town)","Ventura Espot",200,200,200));
        dataManager.addSubregions(new subRegion("Carillo","Carillo (town)", "Enric Casadevall Medrano",198,198,198));
        dataManager.addSubregions(new subRegion("Encamp","Encamp (town)", "Miquel Alís Font",196,196,196));
        dataManager.addSubregions(new subRegion("Escaldes-Engordany","Escaldes-Engordany (town)", "Montserrat Capdevila Pallarés",194,194,194));
        dataManager.addSubregions(new subRegion("La Massana","La Massana (town)", "Josep Areny",192,192,192));
        dataManager.addSubregions(new subRegion("Andorra la Vella","Andorra la Vella (city)", "Maria Rosa Ferrer Obiols",190,190,190));
        dataManager.addSubregions(new subRegion("Sant Julia de Loria","Sant Julia de Loria (town)", "Josep Pintat Forné",188,188,188));         
        
        // all controls 
        dataManager.setBackgroundColor("#ff6600");
        //System.out.println(dataManager.getBackgroundColor());
        dataManager.setBorderColor("#000000");
        dataManager.setBorderWidth(5);
        dataManager.setMapWidth(802);
        dataManager.setMapHeight(536);
        dataManager.setMapZoom(2.0);
        
        //all file path
        dataManager.setParentDirectory("./export/The World/Europe/Andorra");
        dataManager.setMapDataFile("./raw_map_data/Andorra.json");
        dataManager.setImageFile("./export/The World/Europe/Andorra");
        dataManager.setAnthemFile("./export/The World/Europe/Andorra/Andorra National Anthem.mid");
        dataManager.addImageDetails(new image("./export/The World/Europe/Andorra Flag.png",150,150));
        dataManager.addImageDetails(new image("./export/The World/Europe/Andorra Flag.png",200,200));
    }
    
    public static void main(String[] args) throws IOException {
        DataManager dataManager = new DataManager();
        hardCodeData(dataManager);
        FileManager file = new FileManager();
        file.saveData(dataManager, "./work/Andorra.rvm");
    }
    
//    @Override
//    public void start(Stage primaryStage) throws IOException, Exception{
//        
//        primaryStage.setTitle("Andora Data");
//        //FOR THE MAP
//        Pane map = new Pane();
//        map.setMaxSize(802, 536);
//        map.setPrefWidth(802);
//        map.setStyle("-fx-background-color: lightblue;-fx-border-color: #2e8b57;-fx-border-width: 20px;");
//        String mapName = "Andorra";
//        Boolean capitals = true;
//        Boolean flags = true;
//        Boolean leaders = true;
//        
//        String mapPath = "./raw_map_data/Andorra.json";
//        String img1Path = "";
//        
//        
//        ObservableList<subRegion> data =FXCollections.observableArrayList(
//            new subRegion("Ordino", "Ordino (town)","Ventura Espot",200,200,200),
//            new subRegion("Canillo","Canillo (town)", "Enric Casadevall Medrano",198,198,198),
//            new subRegion("Encamp","Encamp (town)", "Miquel Alís Font",196,196,196),
//            new subRegion("Escaldes-Engordany","Escaldes-Engordany (town)", "Montserrat Capdevila Pallarés",194,194,194),
//            new subRegion("La Massana","La Massana (town)", "Josep Areny",192,192,192),
//            new subRegion("Andorra la Vella","Andorra la Vella (city)", "Maria Rosa Ferrer Obiols",190,190,190),
//            new subRegion("Sant Julia de Loria","Sant Julia de Loria (town)", "Josep Pintat Forné",188,188,188)
//        );
//        
//        
//    }

    
}
