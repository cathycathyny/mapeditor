/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rvme.data;

/**
 *
 * @author Cathy
 */
public class image {
    String path;
    
    //x y coordinate
    double x = 50;
    double y = 50;
    
        public image(){}
    
        public image(String p, double x, double y) {
            this.path = p;
            this.x = x;
            this.y = y;
        }
 
        // get and set method
        public String getPath() {
            return path;
        }
 
        public void setPath(String p) {
            this.path= p;
        }
 
        public double getX(){
            return x;
        }
        
        public void setX(double xValue){
            this.x = xValue;
        }
        
        public double getY(){
            return y;
        }
        
        public void setY(double yValue){
            this.y = yValue;
        }
        
        public void reset() {
       
        }
    
}
