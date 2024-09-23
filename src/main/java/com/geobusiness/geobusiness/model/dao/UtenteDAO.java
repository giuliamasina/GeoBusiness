package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.Utente;

public interface UtenteDAO {

    public Utente create(
            String Username,
            String Email,
            String Paswword  // non ho messo deleted
    );

    public void update(Utente utente);

    public void delete(Integer utente_id);

    public Utente findLoggedUtente();     // coockie

    public Utente findByUtenteId(Integer id);    // sql impl

    public Utente findByUsername(String username);
}
