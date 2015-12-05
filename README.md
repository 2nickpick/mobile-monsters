# Mobile Monsters
Mobile Monsters is an Android game developed with the AndEngine framework. It is heavily inspired by the Nintendo game, Pokemon. 

This game was developed as part of the Development of Games and Mobile Applications course at the University of North Florida. 

## Gameplay
The user selects an avatar who controls a team of three monsters to do battle against a team of monsters controlled by an AI opponent. On their turn throughout the battle, the user can select from several different attacks based on the attacks type, attack strength, and the defending monster’s type. 
	
When the user opens the application, they will first see the New Game screen (Fig. 1-2). Initially we planned on having a save and load feature but was later scraped due to time constraints. After selecting New Game, the user then selects one out of three avatars who controls three different monsters (Fig. 1-3). Tapping on one will display their monsters and their types, while also giving the option to go back to the previous menu or to continue into battle (Fig. 1-4). Once an avatar is selected and battle begins, the user is able to choose an attack or to switch their current monster with another that is still alive in their inventory (Fig. 1-5). The monster with the greatest amount of points in their speed stat will be able to attack first. Each attack has a type (Water, Fire, Grass, Ground, Electric, Psychic, Fighting, Normal, Flying) and each type has a weakness or strength against another type, which helps determine how effective the attack is to the opposing monster. If the attack is super effective (water against fire), it will deal double the damage. If the monster has high resistance to the attack type (water vs. grass), it will deal half the damage. Lastly, if the monster is immune to the damage (electric vs. rock), it will deal no damage. Once a monster has reached zero health points (HP), the user or AI will have to switch their monster out to one that is still alive. Once all three monsters are defeated, the victory or defeat condition (Fig. 1-6) is met and the user is prompted to exit the game. 

## Goals
	Our goal with this project was to see if we would be capable of reproducing Pokémon’s battle system onto Android utilizing the AndEngine. Throughout the build process we encountered many issues, from copyright problems with utilizing official Pokémon sprites, creating our own sprites using GIMP, animating and creating sprite sheets with Spriter, finding freeware music and sound effects, and to the coding of multiple difficulties. For a brief moment we worked in the Unity engine, but due to the unfamiliarity of it, we decided to switch back to AndEngine. One of our main features, the “Hard” AI utilizes the information it knows about the monsters on the field to determine its next action. Utilizing a weighted point system, it takes into account their current monster in play and its attack types, the monsters in their inventory and their types, and the user’s current monster on the field to determine what would be the most effective move in order to win. The “Easy” AI essentially “mashes buttons” as it’s thought process. 


## Learning Outcomes

	The development of this project resulted in experience in several facets of game development including sprite-based animation, the game life cycle, user input on mobile devices, asset creation and allocation, multimedia, resource management, user interface, usability, legal and ethical issues, and artificial intelligence. 

## Screenshots
   
![Figure 1-1: Logic Flow](https://github.com/2nickpick/mobile-monsters/blob/master/poster/logic-flow.png)						
![Figure 1-2: Initial View, New Game](https://github.com/2nickpick/mobile-monsters/blob/master/poster/initial-view.png)

![Figure 1-3 : Avatar selection](https://github.com/2nickpick/mobile-monsters/blob/master/poster/choose-character.png)

![Figure 1-4: Monster View](https://github.com/2nickpick/mobile-monsters/blob/master/poster/monster-view.png)
  
![Figure 1-5: Battle Begins](https://github.com/2nickpick/mobile-monsters/blob/master/poster/battle.png)

![Figure 1-6: Win Condition is met](https://github.com/2nickpick/mobile-monsters/blob/master/poster/win.png)

