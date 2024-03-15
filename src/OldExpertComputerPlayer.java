import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OldExpertComputerPlayer extends ComputerPlayer {
    int[][] heatmap = new int[10][10];
    int[][] guessBoard = getGuessBoard();

    ArrayList<Integer> remainingShips = new ArrayList<Integer>(Arrays.asList(5, 4, 3, 3, 2));
    Location nextAttack = new Location(0, 0);

    public OldExpertComputerPlayer(String name) {
        super(name);
        heatmap[0][0] = 1;  // first attack
    }

    @Override
    public boolean attack(Player enemy, Location loc) {     // loc is ignored
        int locInt = getMaxHeat();
        int r = locInt / heatmap.length;
        int c = locInt % heatmap.length;
        Location attackLoc = new Location(r, c);

        System.out.println("Heat Value: " + locInt + ", Location: " + attackLoc);
        return attackHelper(attackLoc, enemy, r, c);
    }

    @Override
    public void populateShips() {       // add location determination algorithm *****
        super.populateShips();      // replace later
    }

    /**
     * Updates heatmap with data from the current attack
     *      -3 = hit location
     *      -2 = missed location
     *      -1 = do not attack
     *      0 = default value, not updated yet
     *      1 = possible ship location
     *      2 = likely ship location (orientation confirmed && (direction unsure || length unsure))
     *      3 = very likely ship location (orientation confirmed && (direction sure || length sure)
     *      4 = definite ship location (orientation, direction, and length confirmed)
     *
     * @param enemy Player to attack
     * @param loc Location to query enemy at
     */
    private void updateHeatmap(Player enemy, Location loc, boolean hit) {        // update heatmap *****
        int r = loc.getRow();       // attack Location row
        int c = loc.getCol();       // attack Location col

        Boolean horizontal = null;       // null = unsure, true = horizontal, false = vertical
        if (hit)
            heatmap[r][c] = -3;     // hit
        else
            heatmap[r][c] = -2;     // miss

        if (!hit && heatmap[r][c - 1] == -3) {
            nextAttack = new Location(r + 1, c - 1);
            return;
        }

        for (int i = 0; i < heatmap.length; i++) {
            for (int j = 0; j < heatmap[i].length; j++) {
                if (heatmap[i][j] == -3) {      // hit
                    System.out.println("HIT - Last Attack: (" + nextAttack.getRow() + ", " + nextAttack.getCol() + ")");
//                    Ship eShip = enemy.getShip(new Location(i, j));
//                    if (eShip.isSunk()) {       // null because ship is removed from enemy player's list...
//                        remainingShips.remove(eShip.getLocations().size());     // updates remainingShip ArrayList
//                    } else {
                        // Horizontal Orientation (positive)
                        int rLength = 1;
                        int rN = 1;
                        while (heatmap[i][j+rN] == -3 && j+rN < heatmap[i].length) {
                            rN++;
                            rLength++;
                        }

                        // Horizontal Orientation (negative)
                        int lLength = 1;
                        int lN = 1;
                        while (heatmap[i][j-lN] == -3 && j-lN >= 0) {
                            lN++;
                            lLength++;
                        }

                        // Vertical Length (negative)
                        int dLength = 1;
                        int dN = 1;
                        while (heatmap[i+dN][j] == -3 && i+dN < heatmap.length) {
                            dN++;
                            dLength++;
                        }

                        // Vertical Length (positive)
                        int uLength = 1;
                        int uN = 1;
                        while (heatmap[i-uN][j] == -3 && i-uN >= 0) {
                            uN++;
                            uLength++;
                        }

                        int[] lengths = {rLength, lLength, dLength, uLength};
                        int mostProbableLength = 0;
                        for (int v : lengths) {
                            if (v > mostProbableLength)
                                mostProbableLength = v;
                        }

                        // set nextAttack
                        if (mostProbableLength == rLength)
                            nextAttack = new Location(i, j+rN);
                        else if (mostProbableLength == lLength)
                            nextAttack = new Location(i, j-lN);
                        else if (mostProbableLength == dLength)
                            nextAttack = new Location(i + dN, j);
                        else if (mostProbableLength == uLength)
                            nextAttack = new Location(i - uN, j);
//                    }
                    return;
                }

            }
        }

        // iterate nextAttack by 1 space
        int nRow = nextAttack.getRow();
        int nCol = nextAttack.getCol();
        System.out.println("MISS - Last Attack: (" + nRow + ", " + nCol + ")");

        if (nRow == heatmap.length - 1 && nCol == heatmap[0].length - 1)
            return;     // shouldn't happen because game should finish first
        else if (nCol == heatmap[0].length - 1) {
            nRow++;
            nCol = 0;
        } else
            nCol++;

        nextAttack = new Location(nRow, nCol);
        heatmap[nextAttack.getRow()][nextAttack.getCol()] = 4;
        System.out.println("Next Attack " + nextAttack);

//        // surrounding locations
//        List<Location> surroundings = new ArrayList<Location>();
//        if (r - 1 >= 0)
//            surroundings.add(new Location(r - 1, c));       // up
//        if (r + 1 < heatmap.length)
//            surroundings.add(new Location(r + 1, c));       // down
//        if (c - 1 >= 0)
//            surroundings.add(new Location(r, c - 1));       // left
//        if (c + 1 < heatmap[0].length)
//            surroundings.add(new Location(r, c + 1));       // right

//        for (int i = 0; i < surroundings.size(); i++) {     // loop through surroundings and update heatmap accordingly *****
//            Location l = surroundings.get(i);
//            int row = l.getRow();       // surrounding Location row
//            int col = l.getCol();       // surrounding Location col
//
//            /*
//                Add a switch statement for each separate combination case
//                - requires 14 different cases
//             */
//            switch (heatmap[row][col]) {
//                case -3, -2, -1:
//                    break;
//                case 0:
//
//
//            }
//        }
    }

    @Override
    protected boolean attackHelper(Location loc, Player enemy, int r, int c) {
        if (enemy.hasShipAtLocation(loc)) {
            updateHeatmap(enemy, loc, true);        // update heatmap with a hit
            guessBoard[r][c] = 1;       // mark guessBoard as hit

            Ship eShip = enemy.getShip(loc);
            eShip.takeHit(loc);     // adds hit to enemy ship
            if (eShip.isSunk()) {    // if ship is sunk
                enemy.removeShip(eShip);        // remove ship
                remainingShips.remove(eShip.getLocations().size());
                return true;
            }
        }
        else {
            updateHeatmap(enemy, loc, false);       // update heatmap with a miss
            guessBoard[r][c] = -1;      // mark guessBoard as miss
        }
        return false;
    }

    private int getMaxHeat() {
        List<Integer> heats = new ArrayList<>();
        for (int[] row : heatmap) {      // add heatmap numbers to sorted
            for (int val : row) {
                heats.add(val);
            }
        }

        System.out.println("Heat List: " + heats);
        int maxHeat = Collections.max(heats);
        System.out.println("Max Heat Index " + heats.indexOf(maxHeat));
        return heats.indexOf(maxHeat);
    }

    public void printHeatmap() {
        List<String> rtn = new ArrayList<String>();
        for (int[] row : heatmap) {
            for (int val : row) {
                rtn.add(val + "");
            }
            rtn.add("\n");
        }
        System.out.println(rtn);
    }

    public Location getNextAttack() {
        return nextAttack;
    }
}