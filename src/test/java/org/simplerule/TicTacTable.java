/*
 *
 *
 */

package org.simplerule;

/**
 * Created by jcgarciam on 5/27/16.
 */
public class TicTacTable {
    public enum TicTacTableState{
        NOT_STARTED, STARTED, FINISHED;

    }

    private char[][] board = new char[][]{"---".toCharArray(),"---".toCharArray(), "---".toCharArray()};

    private Player[] players;
    private int current;
    private TicTacTableState tableState = TicTacTableState.NOT_STARTED;
    private Player winner;
    public TicTacTable(Player _player1, Player _player2 ){
        players = new Player[]{_player1, _player2};
        _player1.setSymbol('O');
        _player2.setSymbol('X');
        current = 0;
    }
    public void startGame() {
        this.tableState = TicTacTableState.STARTED;
    }
    public void switchUser() {
        current = (current + 1) % 2;
    }

    public int getCurrent() {
        return current;
    }

    public TicTacTableState getTableState() {
        return tableState;
    }
    public Player getCurrentPlayer(){
        return players[current];
    }

    public boolean isAvailable(final int _pos) {
        int i = _pos / 3;
        int j = _pos % 3;
        return board[i][j] == '-';
    }

    public void mark(final int _pos) {
        int i = _pos / 3;
        int j = _pos % 3;
        System.out.println(String.format("Your move is on: %s,%s",i,j));
        board[i][j] = getCurrentPlayer().getSymbol();
    }
    public Player getWinner() {
        return null;
    }

    public void endGame() {
        this.tableState = TicTacTableState.FINISHED;
    }


    public void printBoard() {
        System.out.println("----------");
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                System.out.print(board[i][j]+ " ");
            }
            System.out.println(" ");
        }
        System.out.println("----------");
    }

    public boolean stillPendingMove() {
        //check who won?
        for(int i=0;i<3;i++){
            if(board[i][0] != '-' && board[i][0] == board[i][1] && board[i][0] == board[i][2]){ //horiz
                if(board[i][0] == players[0].getSymbol()){
                    winner = players[0];
                }else {
                    winner = players[1];
                }
                return false;
            }
            if(board[0][i] != '-' && board[0][i] == board[0][i] && board[0][i] == board[2][i]){ //vert
                if(board[0][i] == players[0].getSymbol()){
                    winner = players[0];
                }else {
                    winner = players[1];
                }
                return false;
            }
        }

        //

        //still pending
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++) {
                if(board[i][j] == '-')
                    return true;
            }
        }
        return false;
    }
}
