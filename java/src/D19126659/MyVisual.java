package D19126659;

import java.util.Scanner;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import processing.core.PFont;
import processing.event.MouseEvent;

public class MyVisual extends Visual 
{
    // spectrum strength 
    float strength = 0.10f;
    PFont font;

    // theme select
    boolean check = true;

    // turn box on off
    boolean turn = true;
    int tCount = 0;

    // turn stars on off
    boolean stars = true;
    int sCount = 0;

    // display both themes
    boolean all = false;
    int allCount = 0;

    // color code
    int fBase = 0;
    int fSet = 80;

    int numCubes;
    Cube[] cubes;

    int numStar;
    Star[] star;

    int numWalls = 500;
    Box[] box;

    Bass bass;

    public void settings() 
    {
        // size(800, 800, P3D);
        fullScreen(P3D, SPAN);
    }

    public void keyPressed() 
    {
        if (key == ' ') 
        {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }

        if (key == '1')
        {
            check = true;
        }
        
        if (key == '2')
        {
            check = false;
        }

        if (key == '3')
        {
            turn = false;
            tCount++;
            if(tCount == 2)
            {
                turn = true;
                tCount = 0;
            }
        }

        if (key == '4')
        {
            stars = false;
            sCount++;
            if(sCount == 2)
            {
                stars = true;
                sCount = 0;
            }
        }

        if (key == '5')
        {
            all = true;
            allCount++;
            check = true;
            if(allCount == 2)
            {
                all = false;
                allCount = 0;
                check = false;
            }
        }
    }

    public void mouseWheel(MouseEvent event) 
    {
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
      
    public void setup() 
    {
        colorMode(HSB);
        noCursor();
        startMinim();

        // menu
        System.out.println("1. Illenium - Afterlife");
        System.out.println("2. Illenium - Crawl Outta Love");
        System.out.println("3. Zedd - Done With Love");
        System.out.println("4. Zedd - Addicted to a Memory");
        System.out.println("5. Zedd - Beautiful Now");
        System.out.println("6. Hero Planet");
        System.out.println("7. Deadmau5 - I Remember");

        Scanner ie = new Scanner(System.in);
        String result = ie.nextLine();

        loadAudio(result + ".mp3");
        // getAp().play();
        // startListening();

        font = loadFont("BrushScriptMT-48.vlw");
        textFont(font);

        bass = new Bass(this);

        numCubes = (int)(getSpecSize() * strength);
        cubes = new Cube[numCubes];

        numStar = (int)((getSpecSize() * strength) * 4);
        star = new Star[numStar];

        box = new Box[numWalls];

        for(int i = 0; i < numCubes; i++)
        {
            cubes[i] = new Cube(this);
        }

        for(int i = 0; i < numStar; i++)
        {
            star[i] = new Star(this);
        }

        // creating the box, left, right, bottom, top respectively
        for(int j = 0; j < numWalls; j++)
        {
            for (int i = 0; i <numWalls; i += 4) 
            {
                box[i] = new Box(this, 0, height / 2, 10, height); 
            }
                
            for (int i = 1; i <numWalls; i += 4) 
            {
                box[i] = new Box(this, width, height / 2, 10, height); 
            }
                
            for (int i = 2; i <numWalls; i += 4)
            {
                box[i] = new Box(this, width / 2, height, width, 10); 
            }
                
            for (int i = 3; i <numWalls; i += 4)
            {
                box[i] = new Box(this, width / 2, 0, width, 10); 
            }
        }
    }

    public void draw()
    {
        background(0);
        calculateAverageAmplitude();
        calculateFrequencyBands();
        try 
        {
            calculateFFT();
        } 
        catch (VisualException e) 
        {
            e.printStackTrace();
        }
        
            
        if(check)
        {
            for(int i = 0; i < numCubes; i++)
            {
                cubes[i].display();
            }
        }
        else 
        {
            bass.display();
        }
        
        if(stars)
        {
            for(int i = 0; i < numStar; i++)
            {
                star[i].display();
            }
        }

        if(turn)
        {
            for (int i = 0; i < numWalls; i++)
            {
                float intensity = fft.getBand(i%((int)(getSpecSize() * strength)));
                box[i].display(intensity);
            }
        }

        if(all)
        {
            for(int i = 0; i < numCubes; i++)
            {
                cubes[i].display();
            }
            bass.display();
        }
    }
}  
        


          
        


