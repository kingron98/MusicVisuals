# Music Visualiser Project

Name: Aaron Fu Siang Ann

Student Number: D19126659

## Instructions
- Fork this repository and use it a starter project for your assignment
- Create a new package named your student number and put all your code in this package.
- You should start by creating a subclass of ie.tudublin.Visual
- There is an example visualiser called MyVisual in the example package
- Check out the WaveForm and AudioBandsVisual for examples of how to call the Processing functions from other classes that are not subclasses of PApplet

# Description of the assignment
This assignment is about creating something beautiful to enjoy while listening to music. I have made visuals that responds to music. My preference for visuals if something plain and simple, while also engages you into the visual such as when a beat drops. There are 2 themes to select in this program. Both of them gives off the vibe of being in a music box, they have rectangle walls. But different objects, first one is simple with cubes reacting to music. Second option is a unorthodox circle that visualizes as a black hole in the middle, while stars pours out from it's core. Or you can have all of them display at the same time
# Instructions
Before playing the music you will need to choose the music of your choice in the terminal.
Simply type in the number of the music you want, i have put in a couple of my favourites!
Hit enter and the visuals will be drawn.

Press space to play music.
Press numpad 1 to select cubes theme.
Press numpad 2 to select blackhole theme.
Press numpad 3 to toggle boxes on and off.
Press numpad 4 to toggle stars on and off.
Press numpad 5 to display both cubes and blackhole themes together.

If chose option 5, must toggle off before able to select the theme again.  
Scroll mouse up or down to select color of your choice.
Enjoy!

Code for the controls: 
```Java
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
```

# How it works
The code in this assignments is organised into classes that uses inheritance and polymorphism. The visuals created are synchronised to the music. The objects created are called in the draw method as display(). The way these objects react to music is by using the getAmplitude(), which is a method defined in Visual.java. MyVisual extends Visual which makes it able to use the functions defined in Visual. The objects reacts depending on the amplitude and frequency bands of the music, if it's strength is low it will be less aggressive. If the strength is high, the objects will react more aggressively and the visual of the music box will be shown as if it's travelling faster. The objects each have their own separate classes and most of the number of objects are created based off the spectrum. Each of these object consists of a constructor. The constructors defines the values of the object and creates it. The object is later called in draw() along with display().

Star constructor example: 
```Java
Star(New n)
{
    this.n = n;
    x = n.random(0, n.width / 2);
    y = n.random(0, n.height / 2);
    z = n.random(startingZ, maxZ);
}
```

How star is created in setup():
```Java
numStar = (int)((getSpecSize() * strength) * 4);
star = new Star[numStar];

for(int i = 0; i < numStar; i++)
{
    star[i] = new Star(this);
}
```

# What I am most proud of in the assignment
Personally, i have found this assignment to be pretty exciting as i love looking at beautiful visuals while listening to good music. I am proud that from this assignment i have discovered a new interest and understand more about processing.

A couple of things that i have learned:
1. Better understanding of using minim
2. The way push pop matrix work
3. Organising classes that use inheritance and polymorphism.
4. The use of scale()
5. How to get objects moving towards screen at the speed of amplitude
6. Creating complicated visuals, e.g class Box, which requires 2 box and one of them is scaling for the frequency effect

- The box is the object i am most proud of as it was the hardest to figure out, the calculation for the scale method which uses intensity was the most confusing because i had get the box not to scale over the other box. Issue i had was that it scales really fast when i first used only getAmplitude, then i tried using getBand along with modulo spectrum multiplied with strength to keep it from expanding wildly.

# YouTube VideoLink
The video is 6 minute long but if you can finish it please do! There are some parts that looks really cool because of the music. 

[![YouTube](https://i9.ytimg.com/vi/go3X9Us29ig/mq2.jpg?sqp=CIvRhvYF&rs=AOn4CLDjGT2lR9Fk8XTHiKFAGN-p6iFy2A)](https://youtu.be/go3X9Us29ig)
