# Snake (Java)

A small, classic Snake game implemented in Java + Swing. This repo contains a lightweight playable version used for learning and experimentation.

**What's new**
- Press SPACE when the game is over to restart the game (reset snake, place new food, reset direction).
- A running high score is tracked and displayed under the current score.

## Project layout
- `src/` - Java source files (`App.java`, `SnakeGame.java`)
- `bin/` - compiled class files (output by `javac`)
- `lib/` - external libraries (if any)

## Controls
- Arrow keys: move the snake
- SPACE: when `Game Over` is shown, press SPACE to restart

## Run / Build
From the project root (where `src` and `bin` live) use these commands in PowerShell:

```powershell
# compile
javac -d bin src\*.java

# run
java -cp bin App
```

If you run the game with additional JVM flags (preview features, etc.), add them to the `java` command as you normally do.

## Notes for developers
- The main game logic is in `src/SnakeGame.java`. The panel listens for key events and uses a `Timer` as the game loop.
- `highScore` is kept in memory for the current application session only. If you'd like persistent high scores across runs, consider saving to a small file (e.g., `highscore.txt`).

## License / Attribution
This project is small demo code — feel free to modify and use it for learning. No license file is included; add one if you intend to redistribute.
