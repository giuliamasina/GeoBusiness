package com.geobusiness.geobusiness.model.mo;

import java.sql.Date;

public class Recensione {

    private Integer id;
    private Integer valutazione;
    private String commento;
    private Date Data_pubblicazione;
    private Venditore venditore;
    private Compratore compratore;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public Integer getValutazione() { return valutazione; }

    public void setValutazione(Integer valutazione) { this.valutazione = valutazione; }

    public String getCommento() { return commento; }

    public void setCommento(String commento) { this.commento = commento; }

    public Date getDataPubblicazione() { return Data_pubblicazione; }

    public void setDataPubblicazione(Date Data_pubblicazione) {
        this.Data_pubblicazione = Data_pubblicazione;
    }

    public Venditore getVenditore() { return venditore; }

    public void setVenditore(Venditore venditore) { this.venditore = venditore; }

    public Compratore getCompratore() { return compratore; }

    public void setCompratore(Compratore compratore) { this.compratore = compratore; }

}

