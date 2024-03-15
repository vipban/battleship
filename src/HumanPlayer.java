import java.util.ArrayList;

public class HumanPlayer extends Player
{
    public HumanPlayer(String name)
    {
        super(name);
    }

    /**
     * Attack the specified Location loc.  Marks
     *   the attacked Location on the guess board
     *   with a positive number if the enemy Player
     *   controls a ship at the Location attacked;
     *   otherwise, if the enemy Player does not
     *   control a ship at the attacked Location,
     *   guess board is marked with a negative number.
     *
     * If the enemy Player controls a ship at the attacked
     *   Location, the ship must add the Location to its
     *   hits taken.  Then, if the ship has been sunk, it
     *   is removed from the enemy Player's list of ships.
     *
     * Return true if the attack resulted in a ship sinking;
     *   false otherwise.
     *
     * @param enemy
     * @param loc
     * @return
     */
    @Override
    public boolean attack(Player enemy, Location loc)
    {
        int r = loc.getRow();
        int c = loc.getCol();

        return attackHelper(loc, enemy, r, c);
    }

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
    @Override
    public void populateShips()
    {
        // set location coordinates
        int[][] acLocs = {{1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}};
        int[][] dLocs = {{5, 4}, {4, 4}, {3, 4}, {2, 4}};
        int[][] sLocs = {{8, 7}, {8, 8}, {8, 9}};
        int[][] cLocs = {{3, 6}, {3, 7}, {3, 8}};
        int[][] pbLocs = {{4, 2}, {4, 3}};

        // initialize Ship objects and set Locations
        addShip(new AircraftCarrier(locArray(acLocs)));
        addShip(new Destroyer(locArray(dLocs)));
        addShip(new Submarine(locArray(sLocs)));
        addShip(new Cruiser(locArray(cLocs)));
        addShip(new PatrolBoat(locArray(pbLocs)));
    }

    private Location[] locArray(int[][] locs) {
        Location[] locList = new Location[locs.length];
        for (int i = 0; i < locList.length; i ++) {
            locList[i] = (new Location(locs[i][0], locs[i][1]));
        }
        return locList;
    }
}
