package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.Utente;

public interface UtenteDAO {

    public Utente create(
            Integer id,
            String Username,
            String Paswword  // non ho messo deleted
    );

    public void update(Utente utente);

    public void delete(Utente utente);

    public Utente findByUtenteId(Integer id);

}
