# Cyborg Platformer Game
**Author:** Kaiyun Liu

## Brief project description
This is a zombie shooter platformer demo game, which is built entirely from scratch using **Java Swing**—no external libraries, game engines, or external assistance involved. 
In the game, you navigate a level, avoiding obstacles and zombies and tracking your score. It features:

- **Physics-Based Movement**: Simple gravity and collision detection mechanics made by hand.
- **Zombie Shooter**: Engage with enemies while platforming through the levels.
- **HUD**: Displays health, score, attempts, and a timer.
- **Dynamic Level Loading**: Levels are loaded from a text file, where each character represents a different platform or object type.

## Project Installation and Run
- Clone the repository from COMP2013 2025 Coursework and open it in Java Idea.  
- To play, ensure all required image files are organized in the `src` directory as per the setup instructions, and launch the game by running `CyborgPlatform.java`.

   ```bash
   cd path/to/src
   javac *.java
   java CyborgPlatform
   ```

## Gameplay Preview
- Use WACD to move.
- You can double jump.
- Use space to shoot.
- Press escape to restart.

![Gameplay GIF](gifs/StartGif.gif)
![Gameplay GIF2](gifs/EnemyExampleGif.gif)

## Credits
All sprites and images are from [CraftPix](https://craftpix.net/freebies/). Big thanks to them for providing these awesome assets for free!

## Other Markdown Files
- [Changelog](./docs/Changelog.md)
- [SoftwareDesign](./docs/SoftwareDesign.md)
- [Testing](./docs/Testing.md)