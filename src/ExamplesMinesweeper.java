import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import tester.*;
import java.util.*;

//examples class
class ExamplesMinesweeper {

// examples

Cell c1;
Cell c2;
Cell c3;
Cell c4;
Cell c5;
Cell c6;
Cell c7;
Cell c8;
Cell c9;
Cell c10; // no neighbors

MinesweeperWorld ex1;
MinesweeperWorld ex2;
MinesweeperWorld ex3;
MinesweeperWorld ex4;
MinesweeperWorld ex5;

ArrayList<Cell> al1;
ArrayList<Cell> al2;
ArrayList<ArrayList<Cell>> alc;

// reset method
void reset() {
 this.ex1 = new MinesweeperWorld(2, 2, 1, 40);
 this.ex2 = new MinesweeperWorld(8, 8, 10, 40);
 this.ex3 = new MinesweeperWorld(3, 3, 5, 40);
 this.ex4 = new MinesweeperWorld(2, 2, 8, 40);

 this.c1 = new Cell(true, false, true, 40, 1, new ArrayList<Cell>());
 this.c2 = new Cell(false, false, true, 40, 2, new ArrayList<Cell>());
 this.c3 = new Cell(true, false, true, 40, 1, new ArrayList<Cell>());
 this.c4 = new Cell(false, false, true, 40, 2, new ArrayList<Cell>());

 this.c5 = new Cell(false, false, true, 60, 0, new ArrayList<Cell>());
 this.c6 = new Cell(true, false, true, 40, 0, new ArrayList<Cell>());
 this.c7 = new Cell(true, false, true, 40, 0, new ArrayList<Cell>());
 this.c8 = new Cell(true, false, true, 40, 0, new ArrayList<Cell>());
 this.c9 = new Cell(false, false, true, 40, 0, new ArrayList<Cell>());

 c10 = new Cell(false, false, true, 40, 0, new ArrayList<Cell>());
}

// setting c1-c9 as neighbors to each other
void setNeighbors() {
 this.c1.addNeighbor(this.c2);
 this.c1.addNeighbor(this.c3);
 this.c1.addNeighbor(this.c4);
 
 this.c2.addNeighbor(this.c1);
 this.c2.addNeighbor(this.c3);
 this.c2.addNeighbor(this.c4);
 
 this.c3.addNeighbor(this.c1);
 this.c3.addNeighbor(this.c2);
 this.c3.addNeighbor(this.c4);
 
 this.c4.addNeighbor(this.c1);
 this.c4.addNeighbor(this.c2);
 this.c4.addNeighbor(this.c3);
 
 this.c5.addNeighbor(this.c1);
 this.c5.addNeighbor(this.c3);
 this.c5.addNeighbor(this.c6);
 
 this.c9.addNeighbor(this.c1);
 this.c9.addNeighbor(this.c3);
 this.c9.addNeighbor(this.c6);
 this.c9.addNeighbor(this.c7);
 this.c9.addNeighbor(this.c8);
}


// testing

// testing neighborHood method
void testNeighborHood(Tester t) {
 reset();
 al1 = new ArrayList<Cell>(Arrays.asList(this.c1, this.c2));
 al2 = new ArrayList<Cell>(Arrays.asList(this.c3, this.c4));
 alc = new ArrayList<ArrayList<Cell>>(Arrays.asList(al1, al2));
 this.ex5 = new MinesweeperWorld(2, 2, 8, 40, alc);
 t.checkExpect(this.ex5.cells, this.alc);
 
 setNeighbors();
 this.ex5.neighborHood();
 t.checkExpect(this.ex5.cells, this.alc);
}

// testing createCells method
void testCreateCells(Tester t) {
 
 reset();
 
 Cell base = new Cell(false, false, false, 40, 0, new ArrayList<Cell>());
 
 t.checkExpect(this.ex1.cellTest,
     new ArrayList<ArrayList<Cell>>(Arrays.asList(
         new ArrayList<Cell>(Arrays.asList(base, base)),
         new ArrayList<Cell>(Arrays.asList(base, base)))));     
 
 t.checkExpect(this.ex2.cellTest,
     new ArrayList<ArrayList<Cell>>(Arrays.asList(
         new ArrayList<Cell>(Arrays.asList(base, base, base, base, 
             base, base, base, base)),
         new ArrayList<Cell>(Arrays.asList(base, base, base, base, 
             base, base, base, base)),
         new ArrayList<Cell>(Arrays.asList(base, base, base, base, 
             base, base, base, base)),
         new ArrayList<Cell>(Arrays.asList(base, base, base, base, 
             base, base, base, base)),
         new ArrayList<Cell>(Arrays.asList(base, base, base, base, 
             base, base, base, base)),
         new ArrayList<Cell>(Arrays.asList(base, base, base, base, 
             base, base, base, base)),
         new ArrayList<Cell>(Arrays.asList(base, base, base, base, 
             base, base, base, base)),
         new ArrayList<Cell>(Arrays.asList(base, base, base, base, 
             base, base, base, base)))));
}

// testing makeScene method
void testMakeScene(Tester t) {
 reset();
 
 CircleImage mine = new CircleImage(10, OutlineMode.SOLID, Color.BLACK);
 RectangleImage cell = new RectangleImage(40, 40, OutlineMode.OUTLINE, Color.BLACK);
 RectangleImage cover = new RectangleImage(40, 40, OutlineMode.SOLID, Color.GRAY);
 EquilateralTriangleImage flag = new EquilateralTriangleImage(
     20, OutlineMode.SOLID, Color.ORANGE);
 
 WorldScene result1 = new WorldScene(500, 500);
 result1.placeImageXY(new RectangleImage(500, 500, OutlineMode.OUTLINE, Color.BLACK), 250, 250);

/*result1.placeImageXY(this.ex4.cells.get(0).get(0).makeTile(), 100, 100);
 result1.placeImageXY(this.ex4.cells.get(0).get(1).makeTile(), 140, 100);
 result1.placeImageXY(this.ex4.cells.get(1).get(0).makeTile(), 100, 140);
 result1.placeImageXY(this.ex4.cells.get(1).get(1).makeTile(), 140, 140);*/
 
 WorldScene result2 = new WorldScene(500, 500);
 result2.placeImageXY(this.ex3.cells.get(0).get(0).makeTile(), 100, 100);
 result2.placeImageXY(this.ex3.cells.get(0).get(1).makeTile(), 140, 100);
 result2.placeImageXY(this.ex3.cells.get(0).get(2).makeTile(), 180, 100);
 result2.placeImageXY(this.ex3.cells.get(1).get(0).makeTile(), 100, 140);
 result2.placeImageXY(this.ex3.cells.get(1).get(1).makeTile(), 140, 140);
 result2.placeImageXY(this.ex3.cells.get(1).get(2).makeTile(), 180, 140);
 result2.placeImageXY(this.ex3.cells.get(2).get(0).makeTile(), 100, 180);
 result2.placeImageXY(this.ex3.cells.get(2).get(1).makeTile(), 140, 180);
 result2.placeImageXY(this.ex3.cells.get(2).get(2).makeTile(), 180, 180);
 
 t.checkExpect(this.ex4.makeScene(), result1);
 t.checkExpect(this.ex3.makeScene(), result2);
}

// testing makeTile method
boolean testMakeTile(Tester t) {
 
 WorldImage baseTile = new OverlayImage(new RectangleImage(40, 40,
     OutlineMode.OUTLINE, Color.BLACK), new RectangleImage(40, 40,
         OutlineMode.SOLID, Color.DARK_GRAY));
 
 Cell flagged = new Cell(false, true, false, 40, 0, new ArrayList<Cell>());
 Cell mine = new Cell(true, false, false, 40, 0, new ArrayList<Cell>());
 Cell blank = new Cell(false, false, false, 40, 0, new ArrayList<Cell>());
 Cell covered = new Cell(false, false, false, 40, 0, new ArrayList<Cell>());
 Cell elseCase = new Cell(false, false, false, 40, 0, new ArrayList<Cell>());
 
 return t.checkExpect(flagged.makeTile(), 
     new OverlayImage(new EquilateralTriangleImage(20, OutlineMode.SOLID,
         Color.ORANGE), baseTile))
     && t.checkExpect(mine.makeTile(),
         new OverlayImage(new CircleImage(10, OutlineMode.SOLID,
             Color.BLACK), baseTile))
     && t.checkExpect(covered.makeTile(), 
         new OverlayImage(covered.tileNum(), new OverlayImage(new RectangleImage(40, 40,
             OutlineMode.OUTLINE, Color.BLACK), new RectangleImage(40, 40,
                 OutlineMode.SOLID, Color.GRAY))))
     && t.checkExpect(elseCase.makeTile(), 
         new OverlayImage(elseCase.tileNum(), new OverlayImage(new RectangleImage(40, 40,
             OutlineMode.OUTLINE, Color.BLACK), new RectangleImage(40, 40,
                 OutlineMode.SOLID, Color.GRAY))));
}

// testing tileNum method
boolean testTileNum(Tester t) {
 reset();
 setNeighbors();
 
 return t.checkExpect(c1.tileNum(),
     new TextImage("1", 20, Color.BLUE))
     && t.checkExpect(c2.tileNum(),
         new TextImage("2", 20, Color.GREEN))
     && t.checkExpect(c5.tileNum(),
         new TextImage("3", 30, Color.RED))
     && t.checkExpect(c6.tileNum(),
         new TextImage("", 20, Color.GRAY))
     && t.checkExpect(c9.tileNum(),
         new TextImage("5", 20, Color.MAGENTA));
     
     
}

// testing randomMines method
boolean testRandomMine(Tester t) {
 reset();
 
 return t.checkExpect(c1.randomMineTest(new Random(20)), false)
     && t.checkExpect(c2.randomMineTest(new Random(100)), true);
     
}

// testing addNeighbor method
void testAddNeighbor(Tester t) {
 reset();
 setNeighbors();
 
 t.checkExpect(this.c1.neighbors,
     new ArrayList<Cell>(Arrays.asList(
         this.c2, this.c3, this.c4)));
 
 t.checkExpect(this.c2.neighbors,
     new ArrayList<Cell>(Arrays.asList(
         this.c1, this.c3, this.c4)));
 
 t.checkExpect(this.c3.neighbors,
     new ArrayList<Cell>(Arrays.asList(
         this.c1, this.c2, this.c4)));
 
 t.checkExpect(this.c4.neighbors,
     new ArrayList<Cell>(Arrays.asList(
         this.c1, this.c2, this.c3)));
 
 t.checkExpect(this.c10.neighbors,
     new ArrayList<Cell>());
 
}

// testing nearbyBombs method
boolean testNearbyBombs(Tester t) {
 reset();
 setNeighbors();
 
 return t.checkExpect(this.c1.nearbyBombs(), 1)
     && t.checkExpect(this.c2.nearbyBombs(), 2)
     && t.checkExpect(this.c10.nearbyBombs(), 0);
 
}

// testing big bang!
boolean testBigBang(Tester t) {
  reset();
  setNeighbors();
  
 MinesweeperWorld scene = this.ex2;
 int width = 500;
 int height = 500;
 double tickRate = 0.4;
 return scene.bigBang(width, height, tickRate);
}
}