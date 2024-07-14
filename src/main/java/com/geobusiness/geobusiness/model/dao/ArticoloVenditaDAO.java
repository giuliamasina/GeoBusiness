package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.ArticoloVendita;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Venditore;

import java.util.List;

public interface ArticoloVenditaDAO extends ArticoloDAO{

    public ArticoloVendita create(
            Integer id,
            String nome,
            String categoria,
            String immagine,
            String description, // non ho messo deleted e status perchè alla crezione hanno un default
            Float prezzo
    );

    public void update(ArticoloVendita articolo);

    public void delete(ArticoloVendita articolo);

    public Articolo findByArticoloId(Integer id);    // da riveredere

    public List<ArticoloVendita> findByCategoria(String categoria);        // da riveredere

    // public List<Articolo> findBySearchString(Utente utente, String SearchString);       // da riveredere
}
