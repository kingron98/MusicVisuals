package example;

import ie.tudublin.Visual;
 

public class MyProject extends Visual
{
    float specLow = 0.03f; // 3%
    float specMid = 0.125f;  // 12.5%
    float specHi = 0.20f;   // 20%

    // Il reste donc 64% du spectre possible qui ne sera pas utilisé. 
    // Ces valeurs sont généralement trop hautes pour l'oreille humaine de toute facon.

    // Valeurs de score pour chaque zone
    float scoreLow = 0;
    float scoreMid = 0;
    float scoreHi = 0;

    // Valeur précédentes, pour adoucir la reduction
    float oldScoreLow = scoreLow;
    float oldScoreMid = scoreMid;
    float oldScoreHi = scoreHi;

    // Valeur d'adoucissement
    float scoreDecreaseRate = 25;

    // Cubes qui apparaissent dans l'espace
    int nbCubes;
    Cube[] cubes;
    
    public void settings()
    {
        //size(800, 800, P3D);
        fullScreen(P3D, SPAN);
    }

    public void keyPressed()
    {
        if (key == ' ')
        {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
    }

    public void setup()
    {
        calculateFrequencyBands();
        colorMode(HSB);
        noCursor();
        
        startMinim();
        loadAudio("beau.mp3");
        //getAp().play();
        //startListening(); 
  
        //Un cube par bande de fréquence
        nbCubes = (int)(getSpecSize()*specHi);
        cubes = new Cube[nbCubes];
        
        //Créer tous les objets
        //Créer les objets cubes
        for (int i = 0; i < nbCubes; i++) {
        cubes[i] = new Cube(); 
        }
        //Fond noir
        background(0);
    }

    public void draw()
    {
        fft.forward(getAudioPlayer().mix);

        oldScoreLow = scoreLow;
        oldScoreMid = scoreMid;
        oldScoreHi = scoreHi;

        scoreLow = 0;
        scoreMid = 0;
        scoreHi = 0;

        for(int i = 0; i < getSpecSize()*specLow; i++)
        {
            scoreLow += fft.getBand(i);
        }
        
        for(int i = (int)(getSpecSize()*specLow); i < getSpecSize()*specMid; i++)
        {
            scoreMid += fft.getBand(i);
        }
        
        for(int i = (int)(getSpecSize()*specMid); i < getSpecSize()*specHi; i++)
        {
            scoreHi += fft.getBand(i);
        }

        if (oldScoreLow > scoreLow) {
            scoreLow = oldScoreLow - scoreDecreaseRate;
          }
          
          if (oldScoreMid > scoreMid) {
            scoreMid = oldScoreMid - scoreDecreaseRate;
          }
          
          if (oldScoreHi > scoreHi) {
            scoreHi = oldScoreHi - scoreDecreaseRate;
          }

        
          float scoreGlobal = 0.66f*scoreLow + 0.8f*scoreMid + 1f*scoreHi;
          background(scoreLow/100, scoreMid/100, scoreHi/100);
        for(int i = 0; i < nbCubes; i++)
        {
            //Valeur de la bande de fréquence
            float bandValue = fft.getBand(i);
            System.out.println(bandValue);
            //La couleur est représentée ainsi: rouge pour les basses, vert pour les sons moyens et bleu pour les hautes. 
            //L'opacité est déterminée par le volume de la bande et le volume global.
            cubes[i].display(scoreLow, scoreMid, scoreHi, bandValue, scoreGlobal);
        }
    }
    class Cube{
        //Position Z de "spawn" et position Z maximale
          float startingZ = -10000;
          float maxZ = 1000;
          
          //Valeurs de positions
          float x, y, z;
          float rotX, rotY, rotZ;
          float sumRotX, sumRotY, sumRotZ;
          
          //Constructeur
          Cube() {
            //Faire apparaitre le cube à un endroit aléatoire
            x = random(0, width);
            y = random(0, height);
            z = random(startingZ, maxZ);
            
            //Donner au cube une rotation aléatoire
            rotX = random(0, 1);
            rotY = random(0, 1);
            rotZ = random(0, 1);
          }
          
          void display(float scoreLow, float scoreMid, float scoreHi, float intensity, float scoreGlobal) {
            int displayColor = color(scoreLow * 1f, scoreMid * 2f, scoreHi * 3f, intensity * 5);
            fill(displayColor, 255);
        
            stroke(color(255, 150-(20*intensity)));
            strokeWeight(1 + (scoreGlobal/300));
            
            //Création d'une matrice de transformation pour effectuer des rotations, agrandissements
            pushMatrix();
            
            //Déplacement
            translate(x, y, z);
            
            //Calcul de la rotation en fonction de l'intensité pour le cube
            sumRotX += intensity*(rotX/1000);
            sumRotY += intensity*(rotY/1000);
            sumRotZ += intensity*(rotZ/1000);
            
            //Application de la rotation
            rotateX(sumRotX);
            rotateY(sumRotY);
            rotateZ(sumRotZ);
            
            //Création de la boite, taille variable en fonction de l'intensité pour le cube
            box(100+(intensity/2));
            
            //Application de la matrice
            popMatrix();
            
            //Déplacement Z
            z+= (1+(intensity/5)+(pow((scoreGlobal/150), 2)));
            
            //Replacer la boite à l'arrière lorsqu'elle n'est plus visible
            if (z >= maxZ) {
              x = random(0, width);
              y = random(0, height);
              z = startingZ;
            }
          }
        }
}

