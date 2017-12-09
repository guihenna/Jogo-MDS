package com.mygdx.game.Entidades;

public class Mapa {
    private int col;
    private int lin;
    private Espaco grid[][];

    public Mapa(int lin, int col) {
        this.lin = lin;
        this.col = col;
        grid = new Espaco[lin][];

        for(int i = 0; i < lin; i++) {
            grid[i] = new Espaco[col];
            for(int j = 0; j < col; j++) {
                grid[i][j] = new Espaco(i, j);
            }
        }
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
}
