import java.util.Scanner;
import java.util.Random;

class MapCreator {
    private static MapCreator instance;
    private char[][] map;
    private int playerX, playerY;
    private int exitX, exitY;

    private MapCreator() {}

    public static MapCreator getInstance() {
        if (instance == null) {
            instance = new MapCreator();
        }
        return instance;
    }

    public void generateMap(int size) {
        map = new char[size][size];
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = (rand.nextInt(100) < 25) ? '#' : ' ';
            }
        }

        playerX = 0;
        playerY = 0;
        map[playerX][playerY] = 'P';

        exitX = size - 1;
        exitY = size - 1;
        map[exitX][exitY] = 'E';
    }

    public char[][] getMap() { return map; }
    public int getPlayerX() { return playerX; }
    public int getPlayerY() { return playerY; }
    public void setPlayerPos(int x, int y) { playerX = x; playerY = y; }
    public int getExitX() { return exitX; }
    public int getExitY() { return exitY; }
}

public class LabirintoGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MapCreator creator = MapCreator.getInstance();

        int level = 1;
        int maxLevels = 3;

        creator.generateMap(5);

        while (level <= maxLevels) {
            char[][] map = creator.getMap();
            boolean venceu = false;

            while (!venceu) {
                for (char[] row : map) {
                    for (char c : row) {
                        System.out.print(c + " ");
                    }
                    System.out.println();
                }

                System.out.println("Use W/A/S/D para mover:");
                char move = sc.next().toUpperCase().charAt(0);

                int x = creator.getPlayerX();
                int y = creator.getPlayerY();
                int newX = x, newY = y;

                switch (move) {
                    case 'W': newX--; break;
                    case 'S': newX++; break;
                    case 'A': newY--; break;
                    case 'D': newY++; break;
                }

                if (newX >= 0 && newY >= 0 && newX < map.length && newY < map.length && map[newX][newY] != '#') {
                    map[x][y] = ' ';
                    map[newX][newY] = 'P';
                    creator.setPlayerPos(newX, newY);
                }


                if (creator.getPlayerX() == creator.getExitX() && creator.getPlayerY() == creator.getExitY()) {
                    System.out.println("Você encontrou a saída do nível " + level + "!");
                    venceu = true;
                }
            }

            level++;
            if (level <= maxLevels) {
                System.out.println("Gerando novo mapa...");
                creator.generateMap(5 + level);
            }
        }

        System.out.println("Parabéns! Você venceu todos os labirintos!");
        sc.close();
    }
}

