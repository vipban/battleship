public class Location
{
    private int row;
    private int col;

    public Location(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Location) {      // if explicit parameter is a Location
            Location l = (Location) obj;
            return  l.row == this.row &&
                    l.col == this.col;
        } else
            return false;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
