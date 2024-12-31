// represents a cell in minesweeper game

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javalib.worldimages.CircleImage;
import javalib.worldimages.EquilateralTriangleImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayImage;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;

class Cell {
  
  boolean isMine; // whether or not this cell has a mine
  boolean isFlagged; // whether or not this cell is currently flagged
  boolean isCovered; // whether or not this cell is currently covered
  ArrayList<Cell> neighbors; // list of the 8 neighboring cells around this cell
  int cellSize; // pixel size of this cell; will use the Minesweeper cellSize field
  int adjMines; //number of mines adjacent to this cell
  
  // constructor that randomizes whether or not it has a mine
  Cell(int cellSize,  ArrayList<Cell> neighbors) {
    this.isFlagged = false; // equal to random mine for testing
    this.isCovered = true; // equal to random mine for testing
    this.cellSize = cellSize;
    this.neighbors = neighbors;
    this.isMine = randomMine();
  }
  
  // constructor with all the fields (for testing)
  Cell(boolean isMine, boolean isFlagged, boolean isCovered, int cellSize, 
      int adjMines, ArrayList<Cell> neighbors) {
    this.isMine = isMine;
    this.isFlagged = isFlagged;
    this.isCovered = isCovered;
    this.neighbors = neighbors;
    this.cellSize = cellSize;
    this.adjMines = adjMines;
  }
  
  // Returns a boolean based on if a mine is not flagged yet
  public boolean mineFlagged() {
    return isMine && !isFlagged;
  }
  // Changes the value of isFlagged in a cell
  public void changeFlag() {
    this.isFlagged = !this.isFlagged;
  }
  
  public void checkCell(MinesweeperWorld world) {
    if(this.isMine) {
      // world.WorldEnd() ~~ World End Stuff
    }
    
    if (this.nearbyBombs() == 0) {
      for (int i = 0; i < this.neighbors.size(); i++) {
        this.neighbors.get(i).checkCell(world);
        
      }
    }
    
    if(!this.isFlagged) {
      this.isCovered = false;
    }
  }
  
  // generates a random true or false for the isMine field so mines are
  // randomly generated 20% of the time
  // duplicate method randomMineTest is used to test this method
  public boolean randomMine() {
    Random rand = new Random();
    return ((rand.nextInt(100) % 5) == 0);
  }
  
  // duplicate method only for testing that takes in a random
  public boolean randomMineTest(Random rand) {
    return ((rand.nextInt(100) % 5) == 0);
  }
  
  // draws a tile based on the cell's field values
  public WorldImage makeTile() {
    WorldImage baseTile = new OverlayImage(new RectangleImage(this.cellSize, this.cellSize,
        OutlineMode.OUTLINE, Color.BLACK), new RectangleImage(this.cellSize, this.cellSize,
            OutlineMode.SOLID, Color.DARK_GRAY));
    
    if (this.isFlagged && this.isCovered) { // Displays flag if tile is flagged
      return new OverlayImage(new EquilateralTriangleImage(this.cellSize / 2, OutlineMode.SOLID,
          Color.ORANGE), baseTile);
    }
    
    else if (this.isMine && !this.isCovered) { // Displays mine if uncovered and a mine
      return new OverlayImage(new CircleImage(this.cellSize / 4, OutlineMode.SOLID,
          Color.BLACK), baseTile);
    }
    
    else if (!this.isCovered) { // Displays blank or number of nearby mines if uncovered
      return new OverlayImage(this.tileNum(), new OverlayImage(
          new RectangleImage(this.cellSize, this.cellSize,
              OutlineMode.OUTLINE, Color.BLACK), 
          new RectangleImage(this.cellSize, this.cellSize,
              OutlineMode.SOLID, Color.GRAY))); 
    }
    
    else {
      return baseTile;
    } 
  }
  
  // draws the number that will be put on the tile
  public WorldImage tileNum() {
    int numBombs = this.nearbyBombs(); 
    if (numBombs == 1) {
      return new TextImage("" + numBombs, this.cellSize / 2, Color.BLUE);
    }
    
    else if (numBombs == 2) {
      return new TextImage("" + numBombs, this.cellSize / 2, Color.GREEN);
    }
    
    else if (numBombs == 3) {
      return new TextImage("" + numBombs, this.cellSize / 2, Color.RED);
    }
    
    else if (numBombs == 0) {
      return new TextImage("", this.cellSize / 2, Color.GRAY);    
    }
    
    else { // any number 4 and higher will appear magenta
      return new TextImage("" + numBombs, this.cellSize / 2, Color.MAGENTA);    
    }
  }
  
  // adds the given cell into this cell's list of neighbors
  public void addNeighbor(Cell newNeighbor) {
    this.neighbors.add(newNeighbor);
  }
  
  // calculates the number of nearby bombs
  public int nearbyBombs() {
    int bombs = 0;
    for (int i = 0; i < this.neighbors.size(); i++) {
      if (this.neighbors.get(i).isMine) {
        bombs++;
      }
    }
    return bombs;
  }
  
  public void floodFill() {
    if (this.isCovered && !this.isFlagged) {
      this.isCovered = false;
      if (this.nearbyBombs() == 0) {
        for (Cell c : this.neighbors) {
          c.floodFill();
        }
      }
    }
  }
  
  public boolean explosion() {
    return this.isMine && !this.isCovered;
  }
  
}






