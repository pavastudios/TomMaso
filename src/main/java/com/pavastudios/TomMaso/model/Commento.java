package com.pavastudios.TomMaso.model;

import org.w3c.dom.Text;

public class Commento {
    private int idCommento,mittente,pagina;
    private Text testo;

    public int getIdCommento() {
        return idCommento;
    }

    public void setIdCommento(int idCommento) {
        this.idCommento = idCommento;
    }

    public int getMittente() {
        return mittente;
    }

    public void setMittente(int mittente) {
        this.mittente = mittente;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public Text getTesto() {
        return testo;
    }

    public void setTesto(Text testo) {
        this.testo = testo;
    }
}
