package com.mygdx.game.Entidades;

public class Mapa {
    private int col;
    private int lin;
    private Espaco grid[][];



    public Mapa(int lin, int col) {
        this.lin = lin;
        this.col = col;
        grid = new Espaco[col][];

        for(int i = 0; i < col; i++) {
            grid[i] = new Espaco[lin];
            for(int j = 0; j < lin; j++) {
                grid[i][j] = new Espaco(i, j);
            }
        }
    }

    public int getCol() {
        return col;
    }

    public int getLin() {
        return lin;
    }

    public Espaco[][] getGrid() {
        return grid;
    }

    public void setEscudo(int i, int j) {
        grid[i][j].setEscudo(30);
        if(i-1 >= 0)
            grid[i-1][j].setEscudo(30);
        if(i+1 < lin)
            grid[i+1][j].setEscudo(30);
        if(j-1 >= 0)
            grid[i][j-1].setEscudo(30);
        if(j+1 < col)
            grid[i][j+1].setEscudo(30);
    }

    public void setTipo(int i, int j, int tipo) {
        grid[i][j].setTipo(tipo);
    }
    public void setPersonagem(int i, int j, boolean p) {
        grid[i][j].setPersonagem(p);
    }
}
