package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.*;

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

    public ArticoloAsta findByArticoloId(Integer id);    // da riveredere

    public List<ArticoloAsta> findByCategoria(String categoria);        // da riveredere

    public List<ArticoloAsta> selectAll();

    public List<Float> getOffersById(Integer id);

    public List<Date> getDateOffersById(Integer id);

    public List<Compratore> getOfferingCompratoriById(Integer id);

    public List<Articolo> findBySearchString(Utente utente, String SearchString);       // da riveredere

    public Venditore findVenditoreById(Integer id);
}
