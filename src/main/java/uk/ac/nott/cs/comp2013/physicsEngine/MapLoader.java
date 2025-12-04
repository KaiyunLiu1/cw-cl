package uk.ac.nott.cs.comp2013.physicsEngine;

import uk.ac.nott.cs.comp2013.model.MapBlock;
import uk.ac.nott.cs.comp2013.model.TileMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 从文本文件加载地图：每一行是一行 tile 字符。
 */
public class MapLoader {

    public TileMap load(String path) {
        List<MapBlock> blocks = new ArrayList<>();
        int tileY = 0;
        int mapWidth = 0;

        try (BufferedReader reader = openReader(path)) {

            String line;
            while ((line = reader.readLine()) != null) {
                char[] row = line.toCharArray();
                int tileX = 0;

                mapWidth = row.length * MapBlock.TILE_SIZE;

                for (char type : row) {
                    if (type != '0') {
                        blocks.add(new MapBlock(type, tileX, tileY));
                    }
                    tileX += MapBlock.TILE_SIZE;
                }

                tileY += MapBlock.TILE_SIZE;
            }

        } catch (IOException ex) {
            throw new RuntimeException("Failed to load map", ex);
        }

        return new TileMap(blocks, mapWidth);
    }

    private BufferedReader openReader(String path) throws IOException {
        InputStream in = getClass().getResourceAsStream(path);
        if (in != null) {
            return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        }

        String normalized = path.startsWith("/") ? path.substring(1) : path;
        Path filePath = Paths.get("src", "main", "resources", normalized);
        if (Files.exists(filePath)) {
            return Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
        }

        throw new IOException("Map file not found: " + path);
    }
}
