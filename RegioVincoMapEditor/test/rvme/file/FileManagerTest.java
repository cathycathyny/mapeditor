/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvme.file;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rvme.data.DataManager;
import rvme.data.image;
import rvme.data.subRegion;
import static rvme.test.TestSave.hardCodeData;

/**
 *
 * @author Cathy
 */
public class FileManagerTest {
    
    public FileManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of saveData method, of class FileManager.
     */
    @Test
    public void TestAndorra() throws Exception {
        System.out.println("Test Andorra:");
        DataManager testdata = new DataManager();
        hardCodeData(testdata);
        DataManager data = new DataManager();
        data = testdata;
        String filePath = "./work/Andorra.rvm";
        FileManager file = new FileManager();
        file.saveData(data, filePath);
        file.loadData(data, filePath);
        assertEquals(testdata.getMapName(),data.getMapName());
        assertEquals(testdata.getIsHaveCapital(),data.getIsHaveCapital());
        assertEquals(testdata.getIsHaveFlags(),data.getIsHaveFlags());
        assertEquals(testdata.getIsHaveLeader(),data.getIsHaveLeader());
        
        for(int i=0; i<testdata.getSubRegion().size();i++){
            assertEquals(testdata.getSubRegion().get(i).getName(),data.getSubRegion().get(i).getName());
            assertEquals(testdata.getSubRegion().get(i).getCapital(),data.getSubRegion().get(i).getCapital());
            assertEquals(testdata.getSubRegion().get(i).getLeader(),data.getSubRegion().get(i).getLeader());
            assertEquals(testdata.getSubRegion().get(i).getRed(),data.getSubRegion().get(i).getRed());
            assertEquals(testdata.getSubRegion().get(i).getGreen(),data.getSubRegion().get(i).getGreen());
            assertEquals(testdata.getSubRegion().get(i).getBlue(),data.getSubRegion().get(i).getBlue());
        }
        
        assertEquals(testdata.getBackgroundColor(),data.getBackgroundColor());
        assertEquals(testdata.getBorderColor(),data.getBorderColor());
        assertEquals(testdata.getBorderWidth(),data.getBorderWidth(),2);
        assertEquals(testdata.getMapWidth(),data.getMapWidth());
        assertEquals(testdata.getMapHeight(),data.getMapHeight());
        assertEquals(testdata.getMapZoom(),data.getMapZoom(),2);
        assertEquals(testdata.getParentDirectory(),data.getParentDirectory());
        assertEquals(testdata.getMapDataFile(),data.getMapDataFile());
        assertEquals(testdata.getImageFile(),data.getImageFile());
        assertEquals(testdata.getAnthemFile(),data.getAnthemFile());
    }

    /**
     * Test of loadData method, of class FileManager.
     */
    @Test
    public void TestSanMarino() throws Exception {
        System.out.println("Test San Marino:");
        DataManager testdata = new DataManager();
        testdata.setMapName("San Marino");
        testdata.setHaveCapitals(Boolean.FALSE);  
        testdata.setHaveFlags(Boolean.TRUE);
        testdata.setHaveLeaders(Boolean.TRUE);
        
        testdata.addImageDetails(new image("./export/The World/Europe/San Marino Flag.png",0,0));
        
        //subregions details
        testdata.addSubregions(new subRegion("Acquaviva", "","Lucia Tamagnini",225,225,225));
        testdata.addSubregions(new subRegion("Borgo Maggiore","", "Sergio Nanni",200,200,200));
        testdata.addSubregions(new subRegion("Chiesanuova","", "Franco Santi",175,175,175));
        testdata.addSubregions(new subRegion("Domagnano","", "Gabriel Guidi",150,150,150));
        testdata.addSubregions(new subRegion("Faetano","", "Pier Mario Bedetti",125,125,125));
        testdata.addSubregions(new subRegion("Fiorentino", "","Gerri Fabbri",100,100,100));
        testdata.addSubregions(new subRegion("Montegiardino","", "Marta Fabbri",75,75,75));         
        testdata.addSubregions(new subRegion("City of San Marino","", "Maria Teresa Beccari",50,50,50));    
        testdata.addSubregions(new subRegion("Serravalle","", "Leandro Maiani",25,25,25));    
            
        // all controls 
        testdata.setBackgroundColor("#ff6600");
        testdata.setBorderColor("#000000");
        testdata.setBorderWidth(15);
        testdata.setMapWidth(802);
        testdata.setMapHeight(536);
        testdata.setMapZoom(2.0);
        
        //all file path
        testdata.setParentDirectory("./export/The World/Europe/SanMarino");
        testdata.setMapDataFile("./raw_map_data/San Marino.json");
        testdata.setImageFile("./export/The World/Europe/San Marino");
        testdata.setAnthemFile("");
        
        DataManager data = new DataManager();
        data = testdata;
        String filePath = "./work/SanMarino.rvm";
        FileManager file = new FileManager();
        file.saveData(data, filePath);
        file.loadData(data, filePath);
        assertEquals(testdata.getMapName(),data.getMapName());
        //assertEquals(testdata.getIsHaveCapital(),data.getIsHaveCapital());
        assertEquals(testdata.getIsHaveFlags(),data.getIsHaveFlags());
        assertEquals(testdata.getIsHaveLeader(),data.getIsHaveLeader());
        
        for(int i=0; i<testdata.getSubRegion().size();i++){
            assertEquals(testdata.getSubRegion().get(i).getName(),data.getSubRegion().get(i).getName());
            assertEquals(testdata.getSubRegion().get(i).getCapital(),data.getSubRegion().get(i).getCapital());
            assertEquals(testdata.getSubRegion().get(i).getLeader(),data.getSubRegion().get(i).getLeader());
            assertEquals(testdata.getSubRegion().get(i).getRed(),data.getSubRegion().get(i).getRed());
            assertEquals(testdata.getSubRegion().get(i).getGreen(),data.getSubRegion().get(i).getGreen());
            assertEquals(testdata.getSubRegion().get(i).getBlue(),data.getSubRegion().get(i).getBlue());
        }
        
        assertEquals(testdata.getBackgroundColor(),data.getBackgroundColor());
        assertEquals(testdata.getBorderColor(),data.getBorderColor());
        assertEquals(testdata.getBorderWidth(),data.getBorderWidth(),2);
        assertEquals(testdata.getMapWidth(),data.getMapWidth());
        assertEquals(testdata.getMapHeight(),data.getMapHeight());
        assertEquals(testdata.getMapZoom(),data.getMapZoom(),2);
        assertEquals(testdata.getParentDirectory(),data.getParentDirectory());
        assertEquals(testdata.getMapDataFile(),data.getMapDataFile());
        assertEquals(testdata.getImageFile(),data.getImageFile());
        assertEquals(testdata.getAnthemFile(),data.getAnthemFile());
   }

    @Test
    public void TestSlovakia() throws Exception {
        System.out.println("Test Slovakia:");
        DataManager testdata = new DataManager();
        testdata.setMapName("Slovakia");
        testdata.setHaveCapitals(Boolean.FALSE);  
        testdata.setHaveFlags(Boolean.FALSE);
        testdata.setHaveLeaders(Boolean.FALSE);
        
        //subregions details
        testdata.addSubregions(new subRegion("Bratislava", "","",250,250,250));
        testdata.addSubregions(new subRegion("Trnava","", "",249,249,249));
        testdata.addSubregions(new subRegion("Trencin","", "",248,248,248));
        testdata.addSubregions(new subRegion("Nitra","", "",247,247,247));
        testdata.addSubregions(new subRegion("Zilina","", "",246,246,246));
        testdata.addSubregions(new subRegion("Banska Bystrica", "","",245,245,245));
        testdata.addSubregions(new subRegion("Presov","", "",244,244,244));         
        testdata.addSubregions(new subRegion("Kosiceo","", "",242,242,242));  
         
        testdata.addImageDetails(new image("./export/The World/Europe/Slovakia Flag.png",0,0));
        // all controls 
        testdata.setBackgroundColor("#ff6600");
        testdata.setBorderColor("#000000");
        testdata.setBorderWidth(15);
        testdata.setMapWidth(802);
        testdata.setMapHeight(536);
        testdata.setMapZoom(2.0);
        
        //all file path
        testdata.setParentDirectory("./export/The World/Europe/Slovakia");
        testdata.setMapDataFile("./raw_map_data/Slovakia.json");
        testdata.setImageFile("./export/The World/Europe/Slovakia");
        testdata.setAnthemFile("./export/The World/Europe/Slovakia/Slovakia National Anthem.mid");
        
        DataManager data = new DataManager();
        data = testdata;
        String filePath = "./work/Slovakia.rvm";
        FileManager file = new FileManager();
        file.saveData(data, filePath);
        file.loadData(data, filePath);
        assertEquals(testdata.getMapName(),data.getMapName());
        //assertEquals(testdata.getIsHaveCapital(),data.getIsHaveCapital());
        assertEquals(testdata.getIsHaveFlags(),data.getIsHaveFlags());
        assertEquals(testdata.getIsHaveLeader(),data.getIsHaveLeader());
        
        for(int i=0; i<testdata.getSubRegion().size();i++){
            assertEquals(testdata.getSubRegion().get(i).getName(),data.getSubRegion().get(i).getName());
            assertEquals(testdata.getSubRegion().get(i).getCapital(),data.getSubRegion().get(i).getCapital());
            assertEquals(testdata.getSubRegion().get(i).getLeader(),data.getSubRegion().get(i).getLeader());
            assertEquals(testdata.getSubRegion().get(i).getRed(),data.getSubRegion().get(i).getRed());
            assertEquals(testdata.getSubRegion().get(i).getGreen(),data.getSubRegion().get(i).getGreen());
            assertEquals(testdata.getSubRegion().get(i).getBlue(),data.getSubRegion().get(i).getBlue());
        }
        
        assertEquals(testdata.getBackgroundColor(),data.getBackgroundColor());
        assertEquals(testdata.getBorderColor(),data.getBorderColor());
        assertEquals(testdata.getBorderWidth(),data.getBorderWidth(),2);
        assertEquals(testdata.getMapWidth(),data.getMapWidth());
        assertEquals(testdata.getMapHeight(),data.getMapHeight());
        assertEquals(testdata.getMapZoom(),data.getMapZoom(),2);
        assertEquals(testdata.getParentDirectory(),data.getParentDirectory());
        assertEquals(testdata.getMapDataFile(),data.getMapDataFile());
        assertEquals(testdata.getImageFile(),data.getImageFile());
        assertEquals(testdata.getAnthemFile(),data.getAnthemFile());
   }
    
}
