package example;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import processing.event.MouseEvent;

public class New extends Visual {

    // spectrum strength 
    float strength = 0.10f;

    boolean test2 = true;

    // color code
    int fBase = 0;
    int fSet = 80;

    int numCubes;
    Cube[] cubes;

    int numSph;
    Sphere[] sph;

    int nbMurs = 500;
    Wall[] walls;

    public void settings() {
        // size(800, 800, P3D);
        fullScreen(P3D, SPAN);
    }

    public void keyPressed() {
        if (key == ' ') {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }

        if (key == '1'){
            test2 = true;
        }
        
        if (key == '2'){
            test2 = false;
        }
    }

    public void mouseWheel(MouseEvent event) {
        float e = event.getCount();
        if (e > 0)
        {
            fBase += (event.getCount() + 5);
            fSet += (event.getCount() + 5);
            fBase = fBase % 255;
            fSet = fSet % 255;
        }

        if (e < 0)
        {
            fBase += (event.getCount() - 5);
            fSet += (event.getCount() - 5);
        }

        if (fBase <= 0)
        {
            fBase = 255;
        }

        if (fSet <= 0)
        {
            fSet = 255;
        }
      }
      
    public void setup() {
        colorMode(HSB);

        startMinim();
        loadAudio("add.mp3");
        // getAp().play();
        // startListening();

        numCubes = (int)(getSpecSize() * strength);
        cubes = new Cube[numCubes];

        numSph = (int)((getSpecSize() * strength) * 2);
        sph = new Sphere[numSph];

        // So many walls we want
        walls = new Wall[nbMurs];

        for(int i = 0; i < numCubes; i++)
        {
            cubes[i] = new Cube();
        }

        for(int i = 0; i < numSph; i++)
        {
            sph[i] = new Sphere();
        }

        // Create the walls objects
        // Left Walls
        for(int j = 0; j < nbMurs; j++)
        {
            for (int i = 0; i <nbMurs; i += 4) 
            {
                walls[i] = new Wall(0, height / 2, 10, height); 
            }
                
                // Walls rights
            for (int i = 1; i <nbMurs; i += 4) 
            {
                walls[i] = new Wall(width, height / 2, 10, height); 
            }
                
                // low Walls
            for (int i = 2; i <nbMurs; i += 4)
            {
                walls[i] = new Wall(width / 2, height, width, 10); 
            }
                
                // High Walls
            for (int i = 3; i <nbMurs; i += 4)
            {
                walls[i] = new Wall(width / 2, 0, width, 10); 
            }
        }
    }

    public void draw()
    {
        background(0);
        calculateAverageAmplitude();
        calculateFrequencyBands();
        try {
            calculateFFT();
        } catch (VisualException e) {
            e.printStackTrace();
        }
        
            
        if(test2)
        {
            for(int i = 0; i < numCubes; i++)
            {
                cubes[i].display();
            }
        }
        else
        {
            for(int i = 0; i < numSph; i++)
            {
                sph[i].display();
            }

            noStroke();
            fill(map(getAmplitude() * 255, 0, 255, fBase, fSet), 255, 255, getAmplitude() * 100);
            ellipse(width / 2, height / 2, 400, 200 + (1000 * getAmplitude()));
            ellipse(width / 2, height / 2, 400, 200 + (1200 * getAmplitude()));
            ellipse(width / 2, height / 2, 400, 200 + (1400 * getAmplitude()));

            fill(map(getAmplitude() * 255, 0, 255, fBase, fSet), 255, 255, getAmplitude() * 100);
            ellipse(width / 2, height / 2, 200 + (1000 * getAmplitude()), 400);
            ellipse(width / 2, height / 2, 200 + (1200 * getAmplitude()), 400);
            ellipse(width / 2, height / 2, 200 + (1400 * getAmplitude()), 400);
            
            fill(0, 200);
            ellipse(width / 2, height / 2, 300 + (500 * getSmoothedAmplitude()), 300 + (500 * getSmoothedAmplitude()));
        
            }

        for (int i = 0; i < nbMurs; i++)
        {
            float intensity = fft.getBand(i%((int)(getSpecSize() * strength)));
            walls[i].display(intensity);
        }
    }
     

    class Cube
    {
        // Position of cube spawn
        float startingZ = -10000;
        float maxZ = 1000;

        float x, y, z;
        float rotX, rotY, rotZ;
        float sumRotX, sumRotY, sumRotZ;

        // Constructor
        Cube()
        {
            x = random(0 , width);
            y = random(0 , height);
            z = random(startingZ, maxZ);

            rotX = random (0, 1);
            rotY = random (0, 1);
            rotZ = random (0, 1);
        }

        void display()
        {
            // fill(color((255 * getSmoothedAmplitude()), (255 * getSmoothedAmplitude()), (255 * getSmoothedAmplitude()), getAmplitude()) * 1000, 255);
            // fill(255 * getAmplitude(), 255, 255, getAmplitude() * 1000);
            // fill(map(getAmplitude() * 255, 0, 255, 150, 100), 255, 255, getAmplitude() * 250);
            fill(map(getAmplitude() * 255, 0, 255, fBase, fSet), 255, 255, getAmplitude() * 250);
        
            stroke(color(255,  100 - (500 * getAmplitude())));
            strokeWeight(1 + (getAmplitude() * 10));
            pushMatrix();
            translate(x, y, z);

            
            sumRotX += getAmplitude() * (rotX / 5);
            sumRotY += getAmplitude() * (rotY / 5);
            sumRotZ += getAmplitude() * (rotZ / 5);


            rotateX(sumRotX);
            rotateY(sumRotY);
            rotateZ(sumRotZ);
            float boxSize = 100 + (200 * getSmoothedAmplitude());
            box(boxSize);
            popMatrix();


            z += ((maxZ * getAmplitude()) * 0.4f) + 1;
            if (z >= maxZ)
            {
                z = startingZ;
            }
        }
    }

    class Wall
    {
        // minimum and maximum position Z
        float startingZ = -10000;
        float maxz = 50;
        
        // Position values
        float x, y, z;
        float sizeX, sizeY;
        
        // Constructor
        Wall (float x, float y, float sizeX, float sizeY) 
        {
            // Make the line appear in the specified location
            this.x = x;
            this.y = y;
            // random Depth
            this.z = random (startingZ, maxz); 
            
            // It determines the size because the walls to floors have a different size than those on the sides
            this.sizeX = sizeX;
            this.sizeY = sizeY;
        }

        // Display Function
        void display (float intensity) 
        {
            // color determined by the low sounds, medium and high
            // Opacity determined by the volume
            
            // Do the lines disappear off to give a fog of illusion
            // fill(map(getAmplitude() * 255, 0, 255, 200, 150), 255, 255, getAmplitude() * test);
            fill(map(getAmplitude() * 255, 0, 255, fBase, fSet), 255, 255, getAmplitude() * 500);
            noStroke();
            
            // First band, which moves according to the force
            // transform matrix
            pushMatrix();
            
            // Move
            translate (x, y, z);
            
            // Expansion
            if (intensity > 100) 
            {
                intensity = 100;
            }
            scale(sizeX * (intensity / 100), sizeY * (intensity / 100), 20);
            
        

            // Create the "box"
            box (1);
            popMatrix();
            
            // Second band, which is always the same size
            // displayColor = color (scoreLow * 0.5, 0.5 * scoreMid, scoreHi * 0.5, scoreGlobal);
            // fill (displayColor (scoreGlobal / 5000) * (255+ (z / 25)));
            // fill(map(getAmplitude() * 255, 0, 255, 200, 150), 255, 255, getAmplitude() * 50);
            fill(map(getAmplitude() * 255, 0, 255, fBase, fSet), 255, 255, getAmplitude() * 50);
            // transform matrix
            pushMatrix ();
            
            // Move
            translate (x, y, z);
            
            // Expansion
            scale (sizeX, sizeY, 10);
            
            // Create the "box"
            box (1);
            popMatrix ();
        
            // Move Z
            z += (maxz * (getAmplitude() * 4)) + 1;
            if (z >= maxz) 
            {
                z = startingZ; 
            }
        }
    }

    class Sphere
    {
        // Position of cube spawn
        float startingZ = -10000;
        float maxZ = 1000;

        float x, y, z;

        // Constructor
        Sphere()
        {
            x = random(0 , width);
            y = random(0 , height);
            z = random(startingZ, maxZ);
        }

        void display()
        {
            noFill();
            stroke(color(255,  50 - (100 * getAmplitude())));
            pushMatrix();
            translate(x, y, z);
            sphere(5);
            popMatrix();

            z += ((maxZ * getAmplitude()) * 0.4f) + 1;
            if (z >= maxZ)
            {
                z = startingZ;
            }
        }
    }
}  
        

          
        


