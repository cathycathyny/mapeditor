package rvme.data;


/**
 *
 * @author Siqing Lee
 */
public class subRegion {
    String name;
    String capital;
    String leader;
    
    // rgb color
    int red;
    int green;
    int blue;
    
        public subRegion(){}
    
        public subRegion(String n, String c, String l, int red, int green, int blue) {
            this.name = n;
            this.capital = c;
            this.leader = l;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
 
        // get and set method
        public String getName() {
            return name;
        }
 
        public void setName(String n) {
            this.name= n;
        }
 
        public String getCapital() {
            if (capital==null)
                return "";
            return capital;
        }
 
        public void setCapital(String c) {
            this.capital= c;
        }
 
        public String getLeader() {
            if(leader==null)
                return "";
            return leader;
        }
 
        public void setLeader(String l) {
            this.leader= l;
        }
        
        public int getRed(){
            return red;
        }
        
        public void setRed(int r) {
            this.red= r;
        }
        
        public int getGreen(){
            return green;
        }
        
        public void setGreen(int g) {
            this.green= g;
        }
        
        public int getBlue(){
            return blue;
        }
        
        public void setBlue(int b) {
            this.blue= b;
        }
        
        public void reset() {
       
        }
}
