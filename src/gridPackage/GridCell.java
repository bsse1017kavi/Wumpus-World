package gridPackage;

public class GridCell
{
    Coordinate coordinate;

    public GridStatus pit = GridStatus.UNCONFIRMED;
    public GridStatus breeze = GridStatus.UNCONFIRMED;
    public GridStatus stench = GridStatus.UNCONFIRMED;
    public GridStatus wumpus = GridStatus.UNCONFIRMED;
    public GridStatus glitter = GridStatus.UNCONFIRMED;
    public GridStatus gold = GridStatus.UNCONFIRMED;

    public boolean visited = false;

    public void setPit(GridStatus pit) {
        this.pit = pit;
    }

    public void setWumpus(GridStatus wumpus) {
        this.wumpus = wumpus;
    }

    public void setGold(GridStatus gold) {
        this.gold = gold;
    }

    public void visit(){
        visited = true;
    }
}
