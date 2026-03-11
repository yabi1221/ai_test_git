# AI_TEST

This project contains two Java Swing features in one application:

- Minesweeper
- Board CRUD

## Features

### Minesweeper

- Difficulty selection
- New game reset
- Safe first click
- Right-click flag toggle
- Flood reveal for empty cells

### Board CRUD

- Create post
- Read post list and detail
- Update post
- Delete post

## Run

Compile and run from the project root:

```powershell
$files = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $files
java -cp out app.Main
```

## Structure

```text
src/
  app/
    Main.java
    MultiAppFrame.java
    board/
      BoardPanel.java
      BoardPost.java
    minesweeper/
      CellButton.java
      Difficulty.java
      MinesweeperPanel.java
```
