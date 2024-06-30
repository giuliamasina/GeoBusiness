package com.geobusiness.geobusiness.model.mo;

import java.sql.Date;

public class ArticoloVendita extends Articolo{

    private Float prezzo;

    private Venditore venditore;

    private Compratore compratore;

    public Float getPrezzo() { return prezzo; }

    public void setPrezzo(Float prezzo) { this.prezzo = prezzo; }

    public Venditore getVenditore() { return venditore; }

    public void setVenditore(Venditore venditore) { this.venditore = venditore; }

    public Compratore getCompratore() { return compratore; }

    public void setCompratore(Compratore compratore) { this.compratore = compratore; }

}
