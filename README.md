# Brick Breaker Bonanza
New game attempt: "Brick Breaker Bonanza"

Works on desktop. Not finished of course. 

In this game you can move a paddle from left to right. Shoot your ball into the direction off the mouse cursor and start bouncing the ball back and forth between the paddle and the bricks that need to be broken.

Made with Libgdx and Ashley libraries.

All entities have their own components and systems. Some components and system are shared, like: collision, physics, rendering.

Entities in the game now: Player(paddle), Bricks, Ball, Obstacle, Power, Scenery(walls).

All entities are rendered in the Visualizer class.
All entities have their own way off behaving, this is handled in their respective systems.

All entities are made in the EntityFactory class, with help from they BodyFactory class, where we can make different kind of shapes for bodies (Box2d).

The Player (paddle) is initialised in the LevelModel class, where we handle the rules and the look for a level. The rest of the entities are made within the LevelFactory class.

The LevelGenerationSystem handles the entire game, from starting to ending the game. A new LGS is started every time we begin a new game, so that everything will be cleared and reset to the first position.

*Game Rules*
When the ball is in its initial position (on top of the paddle), shoot the ball towards the brick you need to break. All bricks must be broken for the level to be completed. The player has three lives. Lives will be lost if the player can not return the ball towards the brick (ball lands underneath the player). 
Each brick that you break has a different score to give. Every bounce on the paddle gets counted, and after a while this will give the player a multiplier for the score. 
Different kind of power ups will help or annoy you. 

Have fun!
