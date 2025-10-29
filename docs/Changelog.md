# Changelog

## Version 1 - 29/10/2025
As listed below, three issues were identified in the initial version and have been fixed. The code on the left side of the arrow (`->`) shows the original, broken code, and the code on the right side shows the corrected version.   
 - Fix1: Method name typo in `CyborgPlatform.java`   
  `game.loadImgs();`-> `loadImages();`
 - Fix2: File name typo in `Game.java` (`clod.png` -> `cloud.png`)  
   `canvas.cloud = ImageIO.read(new File("src/main/resources/Sprites/clod.png"));`
   ->`canvas.cloud = ImageIO.read(new File("src/main/resources/Sprites/cloud.png"));`
 - Fix3: Map file name typo in `MapBlocks.java` (`Map.txt` -> `Maps.txt`)   
   `File map1 = new File("src/main/resources/Map.txt");`
   ->`File map1 = new File("src/main/resources/Maps.txt");`