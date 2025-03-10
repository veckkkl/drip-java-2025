class KnightBoardSolver {
    private static final int[][] DIRECTIONS = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
    };

    public static boolean knightBoardCapture(int[][] board) {
        int n = board.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 1) {
                    for (int[] direction : DIRECTIONS) {
                        int posI = i + direction[0];
                        int posJ = j + direction[1];

                        if (posI >= 0 && posI < n && posJ >= 0 && posJ < n) {
                            if (board[posI][posJ] == 1) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

}