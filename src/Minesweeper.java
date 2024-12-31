import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;
import java.util.*;
import tester.*;

// represents a minesweeper game
class MinesweeperWorld extends World {
  
  int numRow;
  int numCol;
  int numMines;
  // "you may put restrictions on how big or how small these 
  // numbers can be, but you should be able to handle a 30x16 grid."
  ArrayList<ArrayList<Cell>> cells;
  ArrayList<ArrayList<Cell>> cellTest; // only for testing createCells()
  int cellSize; // pixel size of all cells
  WorldScene scene;
  
  // constructor
  MinesweeperWorld(int numRow, int numCol, int cellSize) {
    this.numRow = numRow;
    this.numCol = numCol;
    this.cellSize = cellSize;
    this.cellTest = this.createCellTesting();
    this.cells = this.createCells();
    this.neighborHood();
    this.numMines = numOfMines();
    this.scene = new WorldScene(500,500);
    // this.winCheck(); check is numMines is 0 meaning all mines have been found!
  }
 
  
  // testing constructors
  MinesweeperWorld(int numRow, int numCol, int numMines,
      int cellSize) {
    this.numRow = numRow;
    this.numCol = numCol;
    this.numMines = numMines;
    this.cellSize = cellSize;
    this.cells = createCells();
    this.scene = new WorldScene(500,500);
    this.neighborHood();
  }
  
  MinesweeperWorld(int numRow, int numCol, int numMines,
      int cellSize, ArrayList<ArrayList<Cell>> cells) {
    this.numRow = numRow;
    this.numCol = numCol;
    this.numMines = numMines;
    this.cellSize = cellSize;
    this.cells = cells;
    this.scene = new WorldScene(500,500);
    this.neighborHood();
  }
  
  // adds the cells to the board according to how many rows/cols there are
  // this method is duplicated and tested as the createCellTesting() method
  public ArrayList<ArrayList<Cell>> createCells() {
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    for (int i = 0; i < numRow; i++) {
      ArrayList<Cell> tempRow = new ArrayList<Cell>();
      for (int j = 0; j < numCol; j++) {
        tempRow.add(new Cell(this.cellSize, new ArrayList<Cell>()));
      }
      board.add(tempRow);
    }
    return board;
  }
  
  
  // adds specific (not randomized) cells to the board according to how 
  // many rows/cols there are
  // only used for testing
  public ArrayList<ArrayList<Cell>> createCellTesting() {
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    for (int i = 0; i < numRow; i++) {
      ArrayList<Cell> tempRow = new ArrayList<Cell>();
      for (int j = 0; j < numCol; j++) {
        tempRow.add(new Cell(false, false, false, this.cellSize, 0,
            new ArrayList<Cell>()));
      }
      board.add(tempRow);
    }
    return board;
  }
  
  // traverses the cells to add neighborhoods to all cells
  public void neighborHood() {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        Cell current = this.cells.get(i).get(j);
        if (i > 0) { // north cell
          current.addNeighbor(this.cells.get(i - 1).get(j));
        }
        
        if (j < numCol - 1) { // east cell
          current.addNeighbor(this.cells.get(i).get(j + 1));
        }
        
        if (i < numRow - 1) { // south cell
          current.addNeighbor(this.cells.get(i + 1).get(j));
        }
        
        if (j > 0) { // west cell
          current.addNeighbor(this.cells.get(i).get(j - 1));
        }
        
        if (i > 0 && j > 0) { // northwest cell
          current.addNeighbor(this.cells.get(i - 1).get(j - 1));
        }
        
        if (i > 0 && j < numCol - 1) { // northeast cell 
          current.addNeighbor(this.cells.get(i - 1).get(j + 1));
        }
        
        if (i < numRow - 1 & j > 0) { // south west cell
          current.addNeighbor(this.cells.get(i + 1).get(j - 1));
        }
        
        if (i < numRow - 1 && j < numCol - 1) { // south east cell
          current.addNeighbor(this.cells.get(i + 1).get(j + 1));
        }
      }
    }
  }
  
  // counts the number of current mines 
  public int numOfMines() {
    int mines = 0;
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        if (this.cells.get(i).get(j).mineFlagged()) {
          mines++;
        }
      }
    }
    return mines;  
  }
  
  // creates the scene
  public WorldScene makeScene() {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        this.scene = this.scene
            .placeImageXY(this.cells.get(i).get(j).makeTile(), 
            j * cellSize + this.cellSize/2, i * cellSize + this.cellSize/2);
      }
    }
    return this.scene;
  }
  
  // handles when the user types words
  @Override
  public MinesweeperWorld onMouseClicked(Posn pos, String button) {
    
    int row = (pos.x / this.cellSize);
    int col = (pos.y / this.cellSize);
    
    if (this.validClick(pos)) {
      if (button.equals("RightButton")) {
        this.cells.get(col).get(row).changeFlag();
      }
      
      else if(button.equals("LeftButton")) {
        this.cells.get(col).get(row).floodFill();
      }
    }
    
    return this;
  }
  
  // checks if the click is valid
  public boolean validClick(Posn p) {
    if (p.x < this.numCol * this.cellSize && p.y < this.numRow * this.cellSize) {
      return true;
    }
    
    return false;
  }
  
  
  // stops the game when a word hits the bottom of the screen
  @Override
  public WorldEnd worldEnds() {
    boolean won = true;
    for (int i = 0; i < this.numRow; i ++) {
      for (int j = 0; j < this.numCol; j ++) {
        if (this.cells.get(i).get(j).explosion()) {
          return this.loss();
        }
        
        else if (this.cells.get(i).get(j).isCovered && !this.cells.get(i).get(j).isMine) {
          won = false;
        }
      }
    }
    
    if (won == true) {
      return this.win();
    }
    
    return new WorldEnd(false, this.makeScene());
  }
  
  public WorldEnd loss() {
    WorldScene s = this.makeScene();
    s = s.placeImageXY(new RectangleImage(cellSize * this.numCol, cellSize * 2, "solid", Color.white),
        cellSize * this.numCol / 2, cellSize * this.numRow / 2)
        .placeImageXY(new TextImage("YOU LOST", 25, Color.RED), 
            cellSize * this.numCol / 2, cellSize * this.numRow / 2);
    return new WorldEnd(true, s);
  }
  
  public WorldEnd win() {
    WorldScene s = this.makeScene();
    s = s.placeImageXY(new RectangleImage(cellSize * this.numCol, cellSize * 2, "solid", Color.white),
        cellSize * this.numCol / 2, cellSize * this.numRow / 2)
        .placeImageXY(new TextImage("YOU WON", 25, Color.blue), 
            cellSize * this.numCol / 2, cellSize * this.numRow / 2);
    return new WorldEnd(true, s);
  }
  
  
}