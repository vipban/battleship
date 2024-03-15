public class HackerComputerPlayer extends ComputerPlayer {
    int[][] heatmap = new int[10][10];
    public HackerComputerPlayer(String name) {
        super(name);
    }

    @Override
    public boolean attack(Player enemy, Location loc) {
        Player e = enemy;
        int[][] gb = getGuessBoard();
        int r = 0;
        int c = 0;

        boolean[][] enemyLocs = new boolean[10][10];
        for (int i = 0; i < enemyLocs.length; i++) {        // find enemy ships
            for (int j = 0; j < enemyLocs[i].length; j++) {
                enemyLocs[i][j] = enemy.hasShipAtLocation(new Location(i, j));
            }
        }

        for (int i = 0; i < enemyLocs.length; i++) {
            for (int j = 0; j < enemyLocs[i].length; j++) {
                if (enemyLocs[i][j]) {
                    enemyLocs[i][j] = false;
                    return attackHelper(new Location(i, j), enemy, i, j);
                }
            }
        }

        return false;
    }
}
