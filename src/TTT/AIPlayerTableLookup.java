/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #5
 * 1 - 5026231158 - Kayla Putri Maharani
 * 2 - 5026231170 - Tahiyyah Mufhimah
 * 3 - 5026231206 - Rafael Dimas K
 */
package TTT;
public class AIPlayerTableLookup extends AIPlayer {

    private int[][] preferredMoves = {
            {1, 1}, {0, 0}, {0, 2}, {2, 0}, {2, 2}, {0, 1}, {1, 0}, {1, 2}, {2, 1}
    };

    public AIPlayerTableLookup(Board board) {
        super(board);
    }

    @Override
    public int[] move() {
        for (int[] move : preferredMoves) {
            if (getBoard().cells[move[0]][move[1]].content == Seed.NO_SEED) {
                return move;
            }
        }
        assert false : "No empty cell?!";
        return null;
    }
}