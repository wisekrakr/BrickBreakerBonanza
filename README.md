# The Penis Mightier
New game attempt: "The Penis Mightier"

Also works on desktop. Not finished of course. 

In this game you choose a dudebro (aka man with penis) and you have to dodge all the other men that try to bother you with their penis. Dodge all you like, but they will keep coming.

#Censoring is available

Made with Libgdx and Ashley libraries.

All entities have their own components and systems. Some components and system are shared, like: collision, physics, rendering.

Entities in the game now: Player, Enemy, Penis, Testicle, Obstacle, Power, Scenery(walls).

All entities are rendered in the Visualizer class.
All entities have their own way off behaving, this is handled in their respective systems.

All entities are made in the EntityFactory class, with help from they BodyFactory class, where we can make different kind of shapes for bodies (Box2d).

The Player is initialised in the LevelModel class, where we handle the rules and the look for a level. The rest of the entities are made within the LevelFactory class.

The LevelGenerationSystem handles the entire game, from starting to ending the game. A new LGS is started every time we begin a new game, so that everything will be cleared and reset to the first position.

Next to the game we have a MenuScreen where we choose a character. A PreferenceScreen for preferences and to reset the highscore.

*Game Rules*
Hit your Mighty Penis against another man's Penis. Keep doing this for a minute to score as much points as you can. Every level gets a bit harder and a bit faster. You begin with three lives, but there are lives that can be picked up during the game. You can enlarge your penis, but also reduce it. You can get faster, but also slower.  
In total there are 21 levels. In the latest stages, there are so many penises flinging about, there is no possible way to survive this penis jungle.
