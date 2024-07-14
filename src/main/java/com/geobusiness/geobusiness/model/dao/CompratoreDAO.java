package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Utente;
import com.geobusiness.geobusiness.model.mo.Venditore;

public interface CompratoreDAO extends UtenteDAO{

    public Compratore create(
            // ho tolto l'id
            String Username,
            String Paswword,  // non ho messo deleted
            String Indirizzo_consegna
    );

    public void update(Compratore compratore);

    public void delete(Compratore compratore);

    public Utente findByUtenteId(Integer id);     // da rivedere

    public Compratore findByUsername(String username);
}
