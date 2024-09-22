package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.ArticoloVendita;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Venditore;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface ArticoloVenditaDAO extends ArticoloDAO{

    public ArticoloVendita create(
            // tolto id
            String nome,
            String categoria,
            String immagine,
            String description, // non ho messo deleted e status perchè alla crezione hanno un default
            Float prezzo
    );

    public void update(ArticoloVendita articolo);

    public void delete(ArticoloVendita articolo);

    public ArticoloVendita findByArticoloId(Integer id);    // da riveredere

    public List<ArticoloVendita> findByCategoria(String categoria);        // da riveredere

    public List<ArticoloVendita> selectAll();

    public Venditore findVenditoreById(Integer id);

    public void metteInVendita(Integer Id_vend, String nome, String categoria, String immagine, String description, Float prezzo, Timestamp data_pubbl);

    // public List<Articolo> findBySearchString(Utente utente, String SearchString);       // da riveredere
}
