package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        StdIn.setFile(file);

        int r = StdIn.readInt();//create grid
        int c = StdIn.readInt();
        grid = new boolean[r][c];

        for(int i = 0;i<grid.length;i++){

            for(int j=0;j<grid[i].length;j++){

                grid[i][j]= StdIn.readBoolean();

            }
        }
        

    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {
        return grid[row][col]; 
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        for(int i=0;i<grid.length;i++){

            for(int j=0;j<grid[i].length;j++){

                if(grid[i][j]==true){

                    return true;
                }
            }
        }

        return false; 
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        int n=0;

        int cLeft=col-1;
        int cRight=col+1;

        int rUp=row-1;
        int rDown=row+1;

        if(rUp==-1){
            rUp=grid.length-1;
        }
        if(rDown==grid.length){
            rDown=0;
        
        }
        if(cLeft==-1){
            cLeft=grid[0].length-1;

        }
        if(cRight==grid[0].length){
            cRight=0;
        
        }
        //find n of neighbors
        if(grid[rDown][col]){
            n++;
        }
        if(grid[rUp][cRight]){
            n++;
        }
        if(grid[row][cRight]){
            n++;
        }
        if(grid[rDown][cRight]){
            n++;
        }

        if(grid[rUp][cLeft]){
            n++;
        }
        if(grid[row][cLeft]){
            n++;
        }
        if(grid[rDown][cLeft]){
            n++;
        }
        if(grid[rUp][col]){
            n++;
        }
        return n; 
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {

        //create new array
        boolean[][] gNew;
        gNew = new boolean[grid.length][grid[0].length];

        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(getCellState(i,j)==true){
                    if(numOfAliveNeighbors(i,j)<2){
                        gNew[i][j] = false;                 
                    }else if(numOfAliveNeighbors(i,j)>3){
                        gNew[i][j]=false;
                    }else{
                        gNew[i][j]=grid[i][j];
                    }
                }else{
                    //final 
                    if(numOfAliveNeighbors(i,j)==3){
                        gNew[i][j]=true;

                    }else{
                        gNew[i][j]=grid[i][j];
                    }
                }
            }
        }


        return gNew;//just return array
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {
        //create boolean grid
       boolean[][] nGrid;
       nGrid=computeNewGrid();
       totalAliveCells=0;
        //nested forloop to update current grid based on n generations
       for(int i=0;i<grid.length;i++){
        for(int j=0;j<grid[0].length;j++){
            grid[i][j]=nGrid[i][j];
            if(grid[i][j]==true){
                totalAliveCells++;
            }
        }
       }
        
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {
        
        boolean[][] gNew;
        for(int i=0;i<n;i++){
            gNew=computeNewGrid();
            for(int j=0;j<grid.length;j++){
                for(int l=0;l<grid[0].length;l++){
                    grid[j][l]=gNew[j][l];
                }
            }
        }


    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {
         
        int num=0;
        for(int i =0;i<grid.length;i++){
            for(int j = 0;j<grid[0].length;j++){
                if(getCellState(i, j)==true){
                    if(getCellState(i-1,j-1)==true){
                        num++;
                    }
                }
            }
        }

        return num; 
        
        
    }
}
