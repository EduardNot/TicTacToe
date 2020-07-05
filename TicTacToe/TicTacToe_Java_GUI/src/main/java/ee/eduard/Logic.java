package ee.eduard;

public class Logic {
    private final boolean ai;

    private boolean firstPlayer = true;

    private final String COMPUTER;

    private final String HUMAN;

    private final String[][] board = new String[3][3];

    public Logic(boolean ai, boolean goFirst) {
        // Constructor
        this.ai = ai;
        if (goFirst) {
            COMPUTER = "O";
            HUMAN = "X";
        } else {
            COMPUTER = "X";
            HUMAN = "O";
            firstPlayer = false;
        }
    }

    public String getCOMPUTER() {
        return COMPUTER;
    }

    public String getHUMAN() {
        return HUMAN;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public boolean isAi() {
        return ai;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public int aiMove() {
        // Function that checks every empty cell and gets its minimax value
        // Then places best value move on board
        int bestScore = Integer.MIN_VALUE;
        Pair bestMove = new Pair(0, 0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    board[i][j] = COMPUTER;
                    int score = minimax(0, -1, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = null;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Pair(i, j);
                    }
                }
            }
        }
        board[bestMove.getX()][bestMove.getY()] = COMPUTER;
        return (bestMove.getX() + 1) * 3 + bestMove.getY() - 2;
    }

    public int minimax(int depth, int isMaximizer, int alpha, int beta) {
        // Minimax function
        String result = gameOver();
        if (result != null) {
            if (result.equals("tie")) {
                return 0;
            } else if (result.equals(COMPUTER)) {
                return 10 - depth;
            } else {
                return -10 + depth;
            }
        }
        int bestScore = isMaximizer == 1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    board[i][j] = isMaximizer == 1 ? COMPUTER : HUMAN;
                    int score = minimax(depth + 1, -isMaximizer, alpha, beta);
                    board[i][j] = null;
                    bestScore = isMaximizer == 1 ? Math.max(score, bestScore) : Math.min(score, bestScore);
                    if (isMaximizer == 1) {
                        alpha = Math.max(alpha, bestScore);
                    } else {
                        beta = Math.min(beta, bestScore);
                    }
                    if (alpha >= beta) {
                        return bestScore;
                    }
                }
            }
        }
        return bestScore;
    }

    public String gameOver() {
        // Checks if somebody won (horizontal, vertical and diagonal)
        int totalEmptyCells = emptyCell();
        if (totalEmptyCells < 5) {
            for (int i = 0; i < 3; i++) {
                if (board[i][0] != null && board[i][1] != null && board[i][2] != null && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
                    return board[i][0];
                } else if (board[0][i] != null && board[1][i] != null && board[2][i] != null && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i])) {
                    return board[0][i];
                }
            }
            if (board[0][0] != null && board[1][1] != null && board[2][2] != null && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
                return board[0][0];
            }
            if (board[2][0] != null && board[1][1] != null && board[0][2] != null && board[2][0].equals(board[1][1]) && board[1][1].equals(board[0][2])) {
                return board[2][0];
            }
        }
        if (totalEmptyCells == 0) {
            return "tie";
        }
        return null;
    }

    public int emptyCell() {
        // Gets count of empty cells on board
        int count = 0;
        for (String[] strings : board) {
            for (String string : strings) {
                if (string == null) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public boolean checkCell(int cell) {
        // Checks if cell is empty
        Pair pair = cellToPair(cell);
        return board[pair.getX()][pair.getY()] == null;
    }

    public void setOnBoardAIGame(Pair pair) {
        // Places move on board
        board[pair.getX()][pair.getY()] = HUMAN;
    }

    public void setOnBoard(Pair pair) {
        // Helper function
        setOnBoard(pair.getX(), pair.getY());
    }

    private void setOnBoard(int row, int cell) {
        // Places move on array
        if (firstPlayer) {
            board[row][cell] = "X";
        } else {
            board[row][cell] = "O";
        }
    }

    public Pair cellToPair(int cell) {
        // Converts int to x and y coordinate
        if (cell > 0 && cell < 4) {
            return new Pair(0, cell - 1);
        } else if (cell < 7) {
            return new Pair(1, cell - 4);
        } else if (cell < 10) {
            return new Pair(2, cell - 7);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
}
