package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Venditore;
import com.geobusiness.geobusiness.model.mo.Utente;

public interface VenditoreDAO extends UtenteDAO{

    public Venditore create(
            Integer id,
            String Username,
            String Paswword,  // non ho messo deleted
            String Indirizzo_spedizione
    );

    public void update(Venditore venditore);

    public void delete(Venditore venditore);

    public Utente findByUtenteId(Integer id);     // da rivedere
}
