package gridPackage;

public class GridCell
{
    Coordinate coordinate;

    GridStatus pit = GridStatus.UNCONFIRMED;
    GridStatus breeze = GridStatus.UNCONFIRMED;
    GridStatus stench = GridStatus.UNCONFIRMED;
    GridStatus wumpus = GridStatus.UNCONFIRMED;
    GridStatus glitter = GridStatus.UNCONFIRMED;
    GridStatus gold = GridStatus.UNCONFIRMED;

    public void setPit(GridStatus pit) {
        this.pit = pit;
    }

    public void setWumpus(GridStatus wumpus) {
        this.wumpus = wumpus;
    }

    public void setGold(GridStatus gold) {
        this.gold = gold;
    }
}
