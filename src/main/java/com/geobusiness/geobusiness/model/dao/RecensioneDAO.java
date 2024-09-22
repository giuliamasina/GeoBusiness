package com.geobusiness.geobusiness.model.dao;


import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.Recensione;
import com.geobusiness.geobusiness.model.mo.Venditore;
import com.geobusiness.geobusiness.model.mo.Compratore;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface RecensioneDAO {

    public Recensione create(

            Integer valutazione,
            String commento,
            Timestamp data_pubblicazione,
            Integer id_c,
            Integer id_v
    );

    public void update(Recensione recensione);

    public void delete(Recensione recensione);

    public Articolo findByRecensioneId(Integer id);

    public List<Recensione> findByVenditoreId(Integer id);

    public List<Recensione> findByCompratoreId(Integer id);

    public void fa_recensione(Integer id_c, Integer id_v, Integer valutazione, String commento, Timestamp data_pubbl);

    public Recensione checkIfAlreadyExists(Integer id_c, Integer id_v);
}
