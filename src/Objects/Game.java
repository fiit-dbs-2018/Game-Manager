package Objects;

import java.util.Arrays;

public class Game {

    private int width;
    private int height;
    private String znak;
    private String naTahu;
    private char[][] playBoard;


    public Game() {
        //TODO i001 input kto zacne 1. ci "x" / "o"  (string)

        this.height = InputFromKeyboard.readInt("Zadajte X-ovu velkost plochy");
        this.width = InputFromKeyboard.readInt("Zadajte Y-ovu velkost plochy");
        this.playBoard = new char[width][height];
        //   printArray();  // toto sa vola iba pre server

        //test
        playBoard[2][0] = 'o';
        playBoard[1][1] = 'o';
        playBoard[0][2] = 'o';
        printArray();

     //   System.out.print(checkBoard_T(0, 2, 3));

//        //vertikalne
//        if (checkBoard(1,1,1,0,1,0,0,'o',3))
//            System.out.print("Vyhral si");
//            //horizintalne
//        else if (checkBoard(1,1,0,1,1,0,0,'o',3))
//            System.out.print("Vyhral si");
//            //diagonala 1
//        else if (checkBoard(1,1,1,1,1,0,0,'o',3))
//            System.out.print("Vyhral si");
//            //diagonala 2
//        else if (checkBoard(1,1,1,-1,1,0,0,'o',3))
//            System.out.print("Vyhral si");
//        else
//            System.out.print("Prehral si");
    }

    public Game(int x, int y, String znak_i, String naTahu_i) {
        height = x;
        width = y;
        znak = znak_i;
        naTahu = naTahu_i;
        this.playBoard = new char[width][height];
        printArray();
    }

    public void printArray() {
        System.out.println("\n" + Arrays.deepToString(playBoard).
                replace("], ", "]\n").
                replace("[[", "[").
                replace("]]", "]").
                replace("\0", "0").
                replace(",", ""));
    }

    ///////////////
    private boolean checkBoard_T(int x, int y, int count) {
        if (checkOneWay(x, y, 1, 0, count) || checkOneWay(x, y, 0, 1, count) || checkOneWay(x, y, 1, 1, count) || checkOneWay(x, y, -1, 1, count))
            return true;
        else
            return false;
    }

    private boolean checkOneWay(int x, int y, int up, int left, int count) {
        char tmp = playBoard[x][y];

        while(testPoint(x,y,tmp))
        {
            x+=up;
            y += left;
        }
        up *= -1;
        left *= -1;
        x+=up;
        y += left;
        int checkCount = 0;

        while(testPoint(x,y,tmp) && (checkCount < count)) {
            checkCount++;
            x += up;
            y += left;
        }
        if (checkCount == count) {
            return true;
        }
        return false;
    }

    private boolean testPoint(int x, int y, char mark) {
        if ((x >= 0) && (y >= 0) && (x < height) && (y < width) && (playBoard[y][x] == mark))
            return true;
        else
            return false;
    }
    //////////////////

    //je tam trochu vela parametrov ale nevedel som ako inak obist to aby sa zakazdym nuloval heck, count a step

    //index_X,index_Y -> suradnice mieesta pridaneho symbolu
    //inc_1,inc_2 -> smer posunu
    //step -> kolky prechod
    //check -> pomocna premenna pre zabezpecenie aby suradnice nevysli mimo pola
    //count -> pocet najdenych symbolov
    //symbol -> hladany symbol
    //numberToFind -> kolko symbolov v rade hladame
    public boolean checkBoard(int index_X, int index_Y, int inc_1, int inc_2, int step, int check, int count, char symbol, int numberToFind) {

        // 0,1,-1
        int xIncrement = inc_1;
        int yIncrement = inc_2;

        int x = index_X;
        int y = index_Y;

        while (x >= 0 && y >= 0 && x < playBoard.length && y < playBoard[x].length) {

            //ak najde hladany znak zvysi counter o 1
            if (playBoard[x][y] == symbol && check == 0)
                count++;
                //ak pri prvom prechode najde hladany pocet symbolov ukonci sa a vrati true
            else if (count == numberToFind && step == 1)
                return true;
                //ak pri druhom prechode najde hladany pocet symbolov ukonci sa a vrati true
            else if ((count - 1) == numberToFind && step == 2)
                return true;
                //ak ani po druhom prechode nenajde hladany pocet symbolov ukonci sa a vrati false
            else if ((count - 1) != numberToFind && (step == 2 || step > 2))
                return false;
                //ak po prvom prechode nenajde hladany pocet symbolov rekurzivne vykona opat chek ale opacnym smerom
            else {
                step++;
                if (checkBoard(index_X, index_Y, inc_1 * (-1), inc_2 * (-1), 2, 0, count, symbol, numberToFind))
                    return true;
            }

            //posun po polickach
            x += xIncrement;
            y += yIncrement;

            //podmienka aby sa suradnice nedostali mimo pola
            if (x < 0 || y < 0) {
                x = 0;
                y = 0;
                check = 1;
            }
        }

        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
