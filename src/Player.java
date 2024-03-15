import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Player
{
    private String name;
    private List<Ship> ships;
    private int[][] guessBoard;

    public Player(String name)
    {
        this.name = name;
        ships = new ArrayList<Ship>();
        guessBoard = new int[10][10];

        this.populateShips();
    }

    public String getName()
    {
        return name;
    }

    /**
     * Returns how many ships are currently in this
     *   Player's ships List.
     *
     * @return
     */
    public int getNumberOfShips()
    {
        return ships.size();
    }

    /**
     * Returns the ship that occupies the specified
     *   Location loc.  Returns null if this Player
     *   does not control a ship at Location loc.
     *
     * @param loc
     * @return
     */
    public Ship getShip(Location loc)
    {
        for (Ship s : ships) {
            for (Location shipL : s.getLocations())
                if (loc.equals(shipL))
                    return s;
        }
        return null;
    }

    public void addShip(Ship ship)
    {
        ships.add(ship);
    }

    public void removeShip(Ship ship)
    {
        ships.remove(ship);
    }

    /**
     * Returns true if this Player controls a ship
     *   at the specified Location loc; false
     *   otherwise.
     *
     * @param loc
     * @return
     */
    public boolean hasShipAtLocation(Location loc)
    {
        return getShip(loc) != null;
    }

    public int[][] getGuessBoard()
    {
        return guessBoard;
    }

    public ArrayList<String> printGuessBoard() {
        ArrayList<String> guesses = new ArrayList<>();
        guesses.add("\n");
        for (int i = 0; i < guessBoard.length; i++) {
            for (int j = 0; j < guessBoard[i].length; j++) {
                guesses.add(guessBoard[i][j] + "");
            }
            guesses.add("\n");
        }

        return guesses;
    }

    /**
     * Returns true if obj is an instanceof Player and
     *   instance variables are equivalent.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            return  p.name.equals(this.name) &&
                    p.ships == this.ships &&
                    p.guessBoard == this.guessBoard;
        }
        else
            return false;

    }

    /**
     * Attack the specified Player at the specified Location.
     *
     * Return true if the attack resulted in a ship sinking;
     *   false otherwise.
     *
     * @param enemy
     * @param loc
     * @return
     */
    public abstract boolean attack(Player enemy, Location loc);

    /**
     * Construct an instance of
     *
     *   AircraftCarrier,
     *   Destroyer,
     *   Submarine,
     *   Cruiser, and
     *   PatrolBoat
     *
     * and add them to this Player's list of ships.
     */
    public abstract void populateShips();

    @Override
    public String toString() {
        ArrayList<String> guesses = new ArrayList<>();
        guesses.add("\n");
        for (int i = 0; i < guessBoard.length; i++) {
            for (int j = 0; j < guessBoard[i].length; j++) {
                guesses.add(guessBoard[i][j] + "");
            }
            guesses.add("\n");
        }
        return  "Player (" + name + "):" + '\n' +
                "Ships: " + ships + '\n' +
                "GuessBoard: " + guesses + '\n';

    }

    protected boolean attackHelper(Location loc, Player enemy, int r, int c) {
        if (enemy.hasShipAtLocation(loc)) {
            guessBoard[r][c] = 1;       // mark guessBoard as hit

            Ship eShip = enemy.getShip(loc);
            eShip.takeHit(loc);     // adds hit to enemy ship
            if (eShip.isSunk()) {    // if ship is sunk
                enemy.removeShip(eShip);        // remove ship
                return true;
            }
        }
        else {
            guessBoard[r][c] = -1;      // mark guessBoard as miss
        }
        return false;
    }
}
