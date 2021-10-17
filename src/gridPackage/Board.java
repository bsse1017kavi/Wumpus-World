package gridPackage;

import java.util.ArrayList;
import java.util.Random;

public class Board
{
    public int size = 10;

    //public int size = 4;

    public GridCell[][] board = new GridCell[size][size];

    public Board()
    {
        initBoard();
    }

    private void initBoard() {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                board[i][j] = new GridCell();
                board[i][j].coordinate = new Coordinate(i,j);
            }
        }
    }

    public void generateRandomBoard(int numberOfPits, int numberOfWumpus, int numberOfGold)
    {
        int pitCounter = numberOfPits;
        int wumpusCounter = numberOfWumpus;
        int goldCounter = numberOfGold;

        Random random = new Random();

        //Coordinate [] pits = new Coordinate[size];

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                board[i][j].setPit(GridStatus.NOT_CONTAINS);
                board[i][j].setWumpus(GridStatus.NOT_CONTAINS);
                board[i][j].setGold(GridStatus.NOT_CONTAINS);
            }
        }

        while(pitCounter > 0)
        {
            int x = random.nextInt(size-1)+1;
            int y = random.nextInt(size-1)+1;

            if(board[x][y].pit != GridStatus.CONFIRMED && board[x][y].wumpus != GridStatus.CONFIRMED)
            {
                board[x][y].setPit(GridStatus.CONFIRMED);
                pitCounter--;
            }
        }

        while(wumpusCounter > 0)
        {
            int x = random.nextInt(size-1)+1;
            int y = random.nextInt(size-1)+1;

            if(board[x][y].pit != GridStatus.CONFIRMED && board[x][y].wumpus != GridStatus.CONFIRMED)
            {
                board[x][y].setWumpus(GridStatus.CONFIRMED);
                wumpusCounter--;
            }
        }

        while(goldCounter > 0)
        {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            if(board[x][y].pit != GridStatus.CONFIRMED && board[x][y].wumpus != GridStatus.CONFIRMED && board[x][y].gold != GridStatus.CONFIRMED)
            {
                board[x][y].setGold(GridStatus.CONFIRMED);
                goldCounter--;
            }
        }

        generateEnvironment();
    }

    public void generateTestBoard(Coordinate wumpus, Coordinate gold, Coordinate[] pits)
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                board[i][j].setPit(GridStatus.NOT_CONTAINS);
            }
        }

        for(Coordinate c: pits)
            board[c.x][c.y].setPit(GridStatus.CONFIRMED);

        board[wumpus.x][wumpus.y].setWumpus(GridStatus.CONFIRMED);
        board[gold.x][gold.y].setGold(GridStatus.CONFIRMED);

        generateEnvironment();
    }

    public void generateEnvironment()
    {

        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].breeze = GridStatus.UNCONFIRMED;
                board[i][j].stench = GridStatus.UNCONFIRMED;
            }
        }

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                //breeze
                if(board[i][j].pit == GridStatus.CONFIRMED)
                {
                    //left
                    if(j>0)
                    {
                        board[i][j-1].breeze = GridStatus.CONFIRMED;
                    }

                    //right
                    if(j<size-1)
                    {
                        board[i][j+1].breeze = GridStatus.CONFIRMED;
                    }

                    //up
                    if(i>0)
                    {
                        board[i-1][j].breeze = GridStatus.CONFIRMED;
                    }

                    //down
                    if(i<size-1)
                    {
                        board[i+1][j].breeze = GridStatus.CONFIRMED;
                    }
                }
//                else {
//                    //left
//                    if(j>0)
//                    {
//                        board[i][j-1].breeze = GridStatus.UNCONFIRMED;
//                    }
//
//                    //right
//                    if(j<size-1)
//                    {
//                        board[i][j+1].breeze = GridStatus.UNCONFIRMED;
//                    }
//
//                    //up
//                    if(i>0)
//                    {
//                        board[i-1][j].breeze = GridStatus.UNCONFIRMED;
//                    }
//
//                    //down
//                    if(i<size-1)
//                    {
//                        board[i+1][j].breeze = GridStatus.UNCONFIRMED;
//                    }
//                }

                //stench
                if(board[i][j].wumpus == GridStatus.CONFIRMED)
                {
                    //left
                    if(j>0)
                    {
                        board[i][j-1].stench = GridStatus.CONFIRMED;
                    }

                    //right
                    if(j<size-1)
                    {
                        board[i][j+1].stench = GridStatus.CONFIRMED;
                    }

                    //up
                    if(i>0)
                    {
                        board[i-1][j].stench = GridStatus.CONFIRMED;
                    }

                    //down
                    if(i<size-1)
                    {
                        board[i+1][j].stench = GridStatus.CONFIRMED;
                    }
                }
//                else {
//                    //left
//                    if(j>0)
//                    {
//                        board[i][j-1].stench = GridStatus.UNCONFIRMED;
//                    }
//
//                    //right
//                    if(j<size-1)
//                    {
//                        board[i][j+1].stench = GridStatus.UNCONFIRMED;
//                    }
//
//                    //up
//                    if(i>0)
//                    {
//                        board[i-1][j].stench = GridStatus.UNCONFIRMED;
//                    }
//
//                    //down
//                    if(i<size-1)
//                    {
//                        board[i+1][j].stench = GridStatus.UNCONFIRMED;
//                    }
//                }
            }
        }
    }

    public void printBoard()
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                boolean empty = true;

                if(board[i][j].pit == GridStatus.CONFIRMED)
                {
                    System.out.print("P");
                    empty = false;
                }

                if(board[i][j].wumpus == GridStatus.CONFIRMED)
                {
                    System.out.print("W");
                    empty = false;
                }

                if(board[i][j].breeze == GridStatus.CONFIRMED)
                {
                    System.out.print("B");
                    empty = false;
                }

                if(board[i][j].stench == GridStatus.CONFIRMED)
                {
                    System.out.print("S");
                    empty = false;
                }

                if(board[i][j].gold == GridStatus.CONFIRMED)
                {
                    System.out.print("G");
                    empty = false;
                }

                if(empty)
                {
                    System.out.print("-");
                }

                System.out.print("      ");
            }

            System.out.println();
        }
    }

    public ArrayList<GridCell> getAdjacentCells(int x, int y ) {
        ArrayList<GridCell> list = new ArrayList<>();
        if(x < size-1)
            list.add(board[x+1][y]);
        if(x > 0)
            list.add(board[x-1][y]);

        if(y < size-1)
            list.add(board[x][y+1]);
        if(y > 0)
            list.add(board[x][y-1]);

        return list;
    }
}
