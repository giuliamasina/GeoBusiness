package com.geobusiness.geobusiness.model.mo;

public class Venditore extends Utente{

    private String Indirizzo_spedizione;

    private ArticoloVendita[] articolivendita;

    private ArticoloAsta[] articoliasta;

    private Recensione[] recensioni;

    public String getIndirizzo_spedizione() { return Indirizzo_spedizione; }

    public void setIndirizzo_spedizione(String Indirizzo_spedizione) { this.Indirizzo_spedizione = Indirizzo_spedizione; }

    public ArticoloVendita[] getArticolivendita() { return articolivendita; }

    public void setArticolivendita(ArticoloVendita[] articolivendita) { this.articolivendita = articolivendita; }

    public ArticoloVendita getArticolovendita(int index) { return articolivendita[index]; }

    public void setArticolovendita(int index, ArticoloVendita articolovendita) { this.articolivendita[index] = articolovendita; }

    public ArticoloAsta[] getArticoliasta() { return articoliasta; }

    public void setArticoliasta(ArticoloAsta[] articoliasta) { this.articoliasta = articoliasta; }

    public ArticoloAsta getArticoloasta(int index) { return articoliasta[index]; }

    public void setArticoloasta(int index, ArticoloAsta articoloasta) { this.articoliasta[index] = articoloasta; }

    public Recensione[] getRecensioni() { return recensioni; }

    public void setRecensioni(Recensione[] recensioni) { this.recensioni = recensioni; }

    public Recensione getRecensione(int index) { return recensioni[index]; }

    public void setRecensione(int index, Recensione recensione) { this.recensioni[index] = recensione; }
}
