package D19126659;

import processing.core.*;

public class Cube 
{
        MyVisual n;
        // position of cube spawn
        float startingZ = -10000;
        float maxZ = 1000;

        float x, y, z;
        float rotX, rotY, rotZ;
        float sumRotX, sumRotY, sumRotZ;

        // Constructor
        Cube(MyVisual n)
        {
            this.n = n;
            x = n.random(0 , n.width);
            y = n.random(0 , n.height);
            z = n.random(startingZ, maxZ);

            rotX = n.random (0, 1);
            rotY = n.random (0, 1);
            rotZ = n.random (0, 1);
        }

        void display()
        {
            n.fill(PApplet.map(n.getAmplitude() * 255, 0, 255, n.fBase, n.fSet), 255, 255, n.getAmplitude() * 250);
        
            n.stroke(n.color(255,  100 - (500 * n.getAmplitude())));
            n.strokeWeight(1 + (n.getAmplitude() * 10));
            n.pushMatrix();
            n.translate(x, y, z);

            
            sumRotX += n.getAmplitude() * (rotX / 5);
            sumRotY += n.getAmplitude() * (rotY / 5);
            sumRotZ += n.getAmplitude() * (rotZ / 5);


            n.rotateX(sumRotX);
            n.rotateY(sumRotY);
            n.rotateZ(sumRotZ);
            float boxSize = 100 + (200 * n.getSmoothedAmplitude());
            n.box(boxSize);
            n.popMatrix();


            z += ((maxZ * n.getAmplitude()) * 0.4f) + 1;
            if (z >= maxZ)
            {
                z = startingZ;
            }
        }
    }

