package br.com.famel.boardgame;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board() {}

    public Board(int rows, int columns) {
        if(rows <= 1 || columns <= 1)
            throw new IllegalArgumentException("Board must have at least 1 rows and columns");
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int column) {
        if(!positionExists(row, column)){
            throw new IllegalArgumentException("Position does not exist");
        }
        return pieces[row][column];
    }

    public Piece piece(Position position) {
        if(thereIsAPiece(position)){
            throw new IllegalArgumentException("There is already a piece in this position" + position);
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position) {
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    private boolean positionExists(int row, int column){
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position) {
        if(!positionExists(position)){
            throw new IllegalArgumentException("Position does not exist");
        }
        return piece(position) != null;
    }

}
