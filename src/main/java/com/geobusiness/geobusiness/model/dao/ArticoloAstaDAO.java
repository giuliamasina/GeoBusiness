package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.ArticoloAsta;
import com.geobusiness.geobusiness.model.mo.ArticoloVendita;
import com.geobusiness.geobusiness.model.mo.Utente;

import java.sql.Date;
import java.util.List;

public interface ArticoloAstaDAO extends ArticoloDAO{

    public ArticoloAsta create(
            Integer id,
            String nome,
            String categoria,
            String immagine,
            String description, // non ho messo deleted e status perchè alla crezione hanno un default
            Date Data_scadenza
    );

    public void update(ArticoloVendita articolo);

    public void delete(ArticoloVendita articolo);

    public Articolo findByArticoloId(Integer id);    // da riveredere

    public List<Articolo> findByCategoria(String categoria);        // da riveredere

    public List<Articolo> findBySearchString(Utente utente, String SearchString);       // da riveredere
}
