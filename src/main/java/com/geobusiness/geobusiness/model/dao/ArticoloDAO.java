package com.geobusiness.geobusiness.model.dao;
import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.Utente;

import java.util.List;

public interface ArticoloDAO {

    public Articolo create(
            Integer id,
            String nome,
            String categoria,
            String immagine,
            String description  // non ho messo deleted e status perchè alla crezione hanno un default, se voglio cambiarli faccio una query (esempio in utenteDAOMySQLImpl)
    );

    public void update(Articolo articolo);

    public void delete(Articolo articolo);

    public Articolo findByArticoloId(Integer id);

    public List<Articolo> findByCategoria(String categoria);

    public List<Articolo> findBySearchString(Utente utente, String SearchString);


}
