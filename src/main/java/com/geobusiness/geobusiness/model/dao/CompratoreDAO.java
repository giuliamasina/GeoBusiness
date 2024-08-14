package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Utente;
import com.geobusiness.geobusiness.model.mo.Venditore;

import java.sql.Date;

public interface CompratoreDAO extends UtenteDAO{

    public Compratore create(
            //Integer id,
            String Username,
            String Paswword,  // non ho messo deleted
            String Indirizzo_consegna
    );

    public void update(Compratore compratore);

    public void delete(Compratore compratore);

    public Compratore findByUtenteId(Integer id);     // da rivedere

    public Compratore findByUsername(String username);

    public void compra(Integer id_comp, Integer id_articolp, Date data);

    public void faofferta(Integer id_comp, Integer id_asta, Float offerta, Date data);
}
