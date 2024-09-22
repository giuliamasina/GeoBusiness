package com.geobusiness.geobusiness.model.mo;

import java.sql.Date;
import java.sql.Timestamp;

public class Recensione {

    private Integer id;
    private Integer valutazione;
    private String commento;
    private Timestamp Data_pubblicazione;
    private Integer id_venditore;
    private Integer id_compratore;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getValutazione() { return valutazione; }

    public void setValutazione(Integer valutazione) { this.valutazione = valutazione; }

    public String getCommento() { return commento; }

    public void setCommento(String commento) { this.commento = commento; }

    public Timestamp getDataPubblicazione() { return Data_pubblicazione; }

    public void setDataPubblicazione(Timestamp Data_pubblicazione) {
        this.Data_pubblicazione = Data_pubblicazione;
    }

    public Integer getVenditoreId() { return id_venditore; }

    public void setVenditoreId(Integer id_venditore) { this.id_venditore = id_venditore; }

    public Integer getCompratoreId() { return id_compratore; }

    public void setCompratoreId(Integer id_compratore) { this.id_compratore = id_compratore; }

}

