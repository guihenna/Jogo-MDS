package com.mygdx.game.Entidades;

public class Habilidade {
    protected String nome;
    protected int alcance;
    protected String tipo; // alvo, área, linha
    protected Boolean envenenar;
    protected Boolean congelar;
    protected Boolean danoCongelado;
    protected Boolean lentidao;
    protected Boolean stun;

    protected int mana;
    protected int dano;

    public Habilidade(String n, int a, String t, Boolean e, Boolean c, Boolean dc, Boolean l, Boolean s, int m, int d) {
        nome = n;
        alcance = a;
        tipo = t;
        envenenar = e;
        congelar = c;
        danoCongelado = dc;
        lentidao = l;
        mana = m;
        dano = d;
        stun = s;
    }

    public void Atacar(Personagem a, Personagem p) {
        a.mana -= mana;

        if(tipo.charAt(1) == 'F') { // Ataque físico
            p.levarDano(a.ataque + dano, 1);
        }
        else if(tipo.charAt(1) == 'M') { // Ataque mágico
            if(danoCongelado == true && p.congelado == true)
                dano += 10;
            p.levarDano(a.poder + dano, 2);
            if(lentidao == true)
                p.move--;
        }
        else if(tipo.charAt(1) == 'C') { // Ataque de status
            if(envenenar == true && p.resVeneno == false)
                p.veneno = 5;
            if(congelar == true)
                p.congelado = true;
            if(stun == true)
                p.stun = true;
        }
        else if(tipo.charAt(1) == 'V') { // Ataque físico + movimento
            p.levarDano(a.ataque + dano, 1);
            a.andar(1);
        }
        else if(tipo.charAt(1) == 'E') { // Escudo
            // Criar novo escudo no mapa
            // mapa.setEscudo(a.getX(), a.getY(), 30);
        }
    }

    public void setNome(String n) {
        nome = n;
    }
    public void setAlcance(int n) {
        alcance = n;
    }
    public void setTipo(String n) {
        tipo = n;
    }
    public void setEnvenenar(Boolean n) {
        envenenar = n;
    }
    public void setCongelar(Boolean n) {
        congelar = n;
    }
    public void setDanoCongelado(Boolean n) {
        danoCongelado = n;
    }
    public void setLentidao(Boolean n) {
        lentidao = n;
    }
    public void setMana(int n) {
        mana = n;
    }
    public void setDano(int n) {
        dano = n;
    }
    public void setStun(Boolean n) {
        stun = n;
    }

    public String getNome() {
        return nome;
    }
    public int getAlcance() {
        return alcance;
    }
    public String getTipo() {
        return tipo;
    }
    public Boolean getEnvenenar() {
        return envenenar;
    }
    public Boolean getCongelar() {
        return congelar;
    }
    public Boolean getDanoCongelado() {
        return danoCongelado;
    }
    public Boolean getLentidao() {
        return lentidao;
    }
    public int getMana() {
        return mana;
    }
    public int getDano() {
        return dano;
    }
    public Boolean getStun() {
        return stun;
    }
}
