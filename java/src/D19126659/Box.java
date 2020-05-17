package D19126659;

import processing.core.*;

public class Box 
    {
        MyVisual n;

        // minimum and maximum position Z
        float startingZ = -10000;
        float maxz = 50;
        
        // position values
        float x, y, z;
        float sizeX, sizeY;
        
        // Constructor
        Box(MyVisual n, float x, float y, float sizeX, float sizeY) 
        {
            this.n = n;
            // make the line appear in the specified location
            this.x = x;
            this.y = y;
            // random depth
            this.z = n.random(startingZ, maxz); 
            
            // determines the size because the box from top to bottom have a different size than those on the sides
            this.sizeX = sizeX;
            this.sizeY = sizeY;
        }

        void display(float intensity) 
        {
            n.fill(PApplet.map(n.getAmplitude() * 255, 0, 255, n.fBase, n.fSet), 255, 255, n.getAmplitude() * 500);
            n.noStroke();
            
            n.pushMatrix();
            
            n.translate (x, y, z);
            
            // expansion 
            if (intensity > 100) 
            {
                intensity = 100;
            }
            n.scale(sizeX * (intensity / 100), sizeY * (intensity / 100), 20);
            
            // creates the box
            n.box (1);
            n.popMatrix();
            
            n.fill(PApplet.map(n.getAmplitude() * 255, 0, 255, n.fBase, n.fSet), 255, 255, n.getAmplitude() * 50);

            n.pushMatrix ();
            
            n.translate(x, y, z);
            
            n.scale(sizeX, sizeY, 10);
            
            // create the box
            n.box (1);
            n.popMatrix ();
        
            // move z
            z += (maxz * (n.getAmplitude() * 4)) + 1;
            if (z >= maxz) 
            {
                z = startingZ; 
            }
        }
    }
    
