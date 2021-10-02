package gridPackage;

import java.util.Random;

public class Board
{
    final int size = 10;

    GridCell[][] board = new GridCell[size][size];

    public Board()
    {
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                board[i][j] = new GridCell();
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
            }
        }

        while(pitCounter > 0)
        {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            if(board[x][y].pit != GridStatus.CONFIRMED && board[x][y].wumpus != GridStatus.CONFIRMED)
            {
                board[x][y].setPit(GridStatus.CONFIRMED);
                pitCounter--;
            }
        }

        while(wumpusCounter > 0)
        {
            int x = random.nextInt(size);
            int y = random.nextInt(size);

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

    public void generateEnvironment()
    {
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
}
