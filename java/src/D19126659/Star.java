package D19126659;

public class Star {

        MyVisual n;

        // random spawn between two values
        float startingZ = -10000;
        float maxZ = 1000;

        float x, y, z;

        // Constructor
        Star(MyVisual n)
        {
            this.n = n;
            x = n.random(0, n.width / 2);
            y = n.random(0, n.height / 2);
            z = n.random(startingZ, maxZ);
        }

        void display()
        {
            n.fill(255, 255);
            n.noStroke();
            n.pushMatrix();
            n.translate(x, y, z);
            n.ellipse(x, y, 5, 5);
            n.popMatrix();

            z += ((maxZ * n.getAmplitude()) * 0.4f) + 1;
            if (z >= maxZ)
            {
                z = startingZ;
            }
        }
    }
    
