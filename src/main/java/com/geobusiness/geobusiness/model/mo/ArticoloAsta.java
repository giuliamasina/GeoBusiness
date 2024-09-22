package com.geobusiness.geobusiness.model.mo;

import java.sql.Date;
import java.sql.Timestamp;

public class ArticoloAsta extends Articolo{

    private Timestamp Data_scadenza;

    private Venditore venditore;

    private Compratore[] compratori;  // Offerte dei compratori

    public Timestamp getData_scadenza() { return Data_scadenza; }

    public void setData_scadenza(Timestamp Data_scadenza) { this.Data_scadenza = Data_scadenza; }

    public Venditore getVenditore() { return venditore; }

    public void setVenditore(Venditore venditore) { this.venditore = venditore; }

    public Compratore[] getCompratori() { return compratori; }

    public void setCompratori(Compratore[] compratori) { this.compratori = compratori; }

    public Compratore getCompratore(int index) { return compratori[index]; }

    public void setCompratore(int index, Compratore compratori) { this.compratori[index] = compratori; }

}
