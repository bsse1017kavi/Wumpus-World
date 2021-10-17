package gridPackage;

public class GridCell
{
    public Coordinate coordinate;

    public GridStatus pit = GridStatus.UNCONFIRMED;
    public GridStatus breeze = GridStatus.UNCONFIRMED;
    public GridStatus stench = GridStatus.UNCONFIRMED;
    public GridStatus wumpus = GridStatus.UNCONFIRMED;
    public GridStatus gold = GridStatus.UNCONFIRMED;

    public boolean visited = false;

    public double dangerScore = 0;

    public boolean safe = false;

    public void setPit(GridStatus pit) {
        this.pit = pit;
    }

    public void setWumpus(GridStatus wumpus) {
        this.wumpus = wumpus;
    }

    public void setGold(GridStatus gold) {
        this.gold = gold;
    }

    public int visit(){
        visited = true;
        if (wumpus == GridStatus.CONFIRMED || pit == GridStatus.CONFIRMED)
            return  -1000;
        else if (gold == GridStatus.CONFIRMED)
            return  1000;
        return 0;
    }
}
