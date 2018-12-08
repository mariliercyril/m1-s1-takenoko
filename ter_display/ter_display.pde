String[] board;
PImage panda;
PImage gardener;
String[] currentBoard;
int currentLine;
boolean end;
void setup() {  
  board = loadStrings("savedGames/game-0.txt"); 
  panda = loadImage("pando.png");
  gardener = loadImage("gardener.png");
  imageMode(CENTER);
  shapeMode(CENTER);
  panda.resize(30, 30);
  gardener.resize(30, 30);
  size(1200, 800);
  currentLine = 0;
  end = false;
  background(20);
}

void draw() {
  if (currentLine < board.length) {
    translate(width/2, height/2);
    while (currentLine < board.length && !board[currentLine].equals("###")) {
      parseLine(board[currentLine]);
      currentLine++;
    }
  } else {
    end = true;
  }
}

/////// Methods

void drawHex(float x, float y, float gs) {
  beginShape();
  vertex(x - gs, y - sqrt(3) * gs);
  vertex(x + gs, y - sqrt(3) * gs);
  vertex(x + 2 * gs, y);
  vertex(x + gs, y + sqrt(3) * gs);
  vertex(x - gs, y + sqrt(3) * gs);
  vertex(x - 2 * gs, y);
  endShape(CLOSE);
}

void placeHex(float x, float y) {
  // (1, 0) -> (2, 1)
  // (0, 1) -> (0, -2)

  float pos_x = 2 * x;
  float pos_y = x - 2 * y;
  float size = 34;
  drawHex(size*pos_x, (size+5)*pos_y, 20);
}

void parseLine(String line) {
  float x;
  float y;
  float pos_x;
  float pos_y;
  String[] values;
  values = split(line, '\t');
  x = parseInt(values[1]);
  y = parseInt(values[2]);
  pos_x = 2 * x;
  pos_y = x - 2 * y;
  if (values[0].equals("PDA")) {
    image(panda, 33*pos_x-15, 38*pos_y);
  } else if (values[0].equals("GRD")) {
    image(gardener, 33*pos_x+15, 38*pos_y);
  } else if (values[0].equals("TRN")) {
    fill(20);
    stroke(20);
    rect(-300,-200,-300, -200);
    fill(255);
    textSize(20);
    text("turn " + values[1], -580, -380);
    text("player " + values[2], -580, -350);
  } else {
    fill(colors.valueOf(values[3]).getColor());
    if (values[4].equals("I")) {
      strokeWeight(4);
      stroke(#2D95FA);
    } else {
      strokeWeight(3);
      stroke(255);
    }

    placeHex(x, y);
    if (!values[5].equals("0")) {
      fill(255);
      textSize(18);
      text(values[5], 33*pos_x+5, 38*pos_y-15);
    }
  }
}

void parse(String[] game) {
  for (String line : game) {
    parseLine(line);
  }
}

void keyPressed() {
  if (keyCode == LEFT) {
    currentLine--;
  } 
  
  if (keyCode == RIGHT) {
    currentLine++;
  }
}

enum colors {
  GREEN(#00FF00), PINK(#F00FB8), YELLOW(#F5EA16), BLUE(#0000FF);

  private int hexColor;

  private colors(int hxc) {
    hexColor = hxc;
  }

  public int getColor() {
    return hexColor;
  }
}
