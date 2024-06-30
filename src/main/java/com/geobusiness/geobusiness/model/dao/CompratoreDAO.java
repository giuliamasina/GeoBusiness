package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Utente;

public interface CompratoreDAO extends UtenteDAO{

    public Compratore create(
            Integer id,
            String Username,
            String Paswword,  // non ho messo deleted
            String Indirizzo_consegna
    );

    public void update(Compratore compratore);

    public void delete(Compratore compratore);

    public Utente findByUtenteId(Integer id);     // da rivedere
}
