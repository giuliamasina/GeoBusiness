package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.*;

import java.util.List;

public interface VenditoreDAO extends UtenteDAO{

    public Venditore create(
            // ho tolto l'id
            String Username,
            String email,
            String Password,  // non ho messo deleted
            String Indirizzo_spedizione
    );

    public void update(Venditore venditore);

    public void delete(Venditore venditore);

    public Venditore findByUtenteId(Integer id);     // da rivedere

    public Venditore findByUsername(String username);

    public List<ArticoloVendita> findArticoliInVendita(Integer id);

    public List<ArticoloAsta> findArticoliInAsta(Integer id);
}
