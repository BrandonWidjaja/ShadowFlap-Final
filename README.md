# ShadowFlap

ShadowFlap is an imitation of the classic Flappy Bird game.


Designed and wrote Java code for a video game called Shadow Flap, a modified version of Flappy Bird. This project involved using object-oriented programming concepts to create a playable video game based off given specifications.

Demo:

https://user-images.githubusercontent.com/80396203/180389053-5f14f1a6-e67c-47cc-a83e-c65a2542b490.mp4  
Footage is of stage one only (no weapons, or flame steel pipes yet).

## Installation

The ShadowFlap game uses BAGEL(The Basic Academic Game Engine Library). Documentation on the library [here](https://people.eng.unimelb.edu.au/mcmurtrye/bagel-doc/).

## How to Play

Press SPACE bar to start the game, and to fly up. Upon reaching the peak of the flight, gravity pulls you down to a preset terminal velocity. To win, pass through the pipes without touching them.

Your score increases each time you pass a set of pipes. Upon reaching a score of 10, a level-up occurs. In level 2, pipes may spawn at completely random heights and steel flamethrower pipes may spawn.

Weapons are also spawned: a rock, which may be used to destroy plastic pipes, and a bomb that may be used to destroy both plastic and steel pipes. Destroying a pipe grants a point.

The game is won upon reaching a score of 30 in level 1.

Time scale may also be adjusted by pressing 'l' (to increase) and 's' (to decrease). Timescale affects the speed at which pipes move across the screen and can be used to adjust the difficulty of the game.
In each level, you are granted a certain number of lives. A life is lost upon exiting the playable area, and/or flying into a pipe or flames.

## Settings

All settings such as spawn location, flying initial velocity, gravitational acceleration, max terminal velocity etc. can be set from the set of variables throughout the code (depicted below).
This game will require different values on different devices to ensure a playable framerate.
``` Java
private final double birdFlySpeed = 4; // starting speed for flying (value modified to suit my devices frame rate)
private final double birdSpeedMax = 6; // maximum falling speed (value modified to suit my devices frame rate)
private final double birdAcceleration = 0.1; // acceleration constant (value modified to suit my devices frame rate)
```
