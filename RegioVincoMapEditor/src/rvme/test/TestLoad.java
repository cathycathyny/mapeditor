/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvme.test;

import java.io.IOException;
import rvme.data.DataManager;
import rvme.file.FileManager;

/**
 *
 * @author Cathy
 */
public class TestLoad {
    
    TestLoad(){}
    
    
    public static void main(String[] args) throws IOException {
        
        DataManager dataManager =new DataManager();
        FileManager file = new FileManager();
        file.loadData(dataManager, "./work/Andorra.rvm");
        System.out.println(dataManager.getMapName());
        System.out.println(dataManager.getIsHaveCapital());
        System.out.println(dataManager.getIsHaveFlags());
        System.out.println(dataManager.getIsHaveLeader());
        
        for(int i=0; i<dataManager.getImageDetail().size();i++){
            System.out.println(dataManager.getImageDetail().get(i).getPath());
            System.out.println(dataManager.getImageDetail().get(i).getX());
            System.out.println(dataManager.getImageDetail().get(i).getY());
        }
        
        for(int i=0; i<dataManager.getSubRegion().size();i++){
            System.out.println(dataManager.getSubRegion().get(i).getName());
            System.out.println(dataManager.getSubRegion().get(i).getCapital());
            System.out.println(dataManager.getSubRegion().get(i).getLeader());
            System.out.println(dataManager.getSubRegion().get(i).getRed());
            System.out.println(dataManager.getSubRegion().get(i).getGreen());
            System.out.println(dataManager.getSubRegion().get(i).getBlue());
        }
        
        System.out.println(dataManager.getBackgroundColor());
        System.out.println(dataManager.getBorderColor());
        System.out.println(dataManager.getBorderWidth());
        System.out.println(dataManager.getMapWidth());
        System.out.println(dataManager.getMapHeight());
        System.out.println(dataManager.getMapZoom());
        
        System.out.println(dataManager.getParentDirectory());
        System.out.println(dataManager.getMapDataFile());
        System.out.println(dataManager.getImageFile());
        System.out.println(dataManager.getAnthemFile());
    }
    
}
