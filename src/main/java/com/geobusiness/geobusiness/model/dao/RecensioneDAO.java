package com.geobusiness.geobusiness.model.dao;


import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.Recensione;
import com.geobusiness.geobusiness.model.mo.Venditore;
import com.geobusiness.geobusiness.model.mo.Compratore;

import java.sql.Date;
import java.util.List;

public interface RecensioneDAO {

    public Recensione create(
            Integer id,
            String commento,
            Date data_pubblicazione
    );

    public void update(Recensione recensione);

    public void delete(Recensione recensione);

    public Articolo findByRecensioneId(Integer id);

    public List<Recensione> findByVenditore(Venditore venditore);
}
