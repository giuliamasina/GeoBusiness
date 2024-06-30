package com.geobusiness.geobusiness.model.mo;

import java.sql.Date;

public class ArticoloAsta extends Articolo{

    private Date Data_scadenza;

    private Venditore venditore;

    private Compratore[] compratori;  // Offerte dei compratori

    public Date getData_scadenza() { return Data_scadenza; }

    public void setData_scadenza(Date Data_scadenza) { this.Data_scadenza = Data_scadenza; }

    public Venditore getVenditore() { return venditore; }

    public void setVenditore(Venditore venditore) { this.venditore = venditore; }

    public Compratore[] getCompratori() { return compratori; }

    public void setCompratori(Compratore[] compratori) { this.compratori = compratori; }

    public Compratore getCompratore(int index) { return compratori[index]; }

    public void setCompratore(int index, Compratore compratori) { this.compratori[index] = compratori; }

}
