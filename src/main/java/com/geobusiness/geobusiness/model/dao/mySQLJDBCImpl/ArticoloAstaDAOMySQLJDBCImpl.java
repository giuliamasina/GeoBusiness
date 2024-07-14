package com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl;

import com.geobusiness.geobusiness.model.dao.ArticoloAstaDAO;
import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.ArticoloAsta;
import com.geobusiness.geobusiness.model.mo.ArticoloVendita;
import com.geobusiness.geobusiness.model.mo.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticoloAstaDAOMySQLJDBCImpl implements ArticoloAstaDAO {

    private final String COUNTER_ID = "id";
    Connection conn;

    public ArticoloAstaDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }
    @Override
    public ArticoloAsta create(Integer id,
                               String nome,
                               String categoria,
                               String immagine,
                               String description,
                               Date Data_scadenza
    ) {
        PreparedStatement ps;
        ArticoloAsta articoloasta = new ArticoloAsta();
        //utente.setId(id);  // PROBABILMENTE NON SERVE, SUL DATABASE C'È AUTO_INCREMENT
        articoloasta.setNome(nome);
        articoloasta.setCategoria(categoria);
        articoloasta.setImmagine(immagine);
        articoloasta.setDescription(description);
        articoloasta.setData_scadenza(Data_scadenza);

        try{

            String sql
                    = " INSERT INTO ARTICOLO_ASTA "
                    + "   ( Nome,"
                    + "     Categoria,"
                    + "     Immagine,"
                    + "      Descrizione,"
                    + "      Data_scadenza"
                    + "   ) "
                    + " VALUES (?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i, articoloasta.getNome());
            ps.setString(i++, articoloasta.getCategoria());
            ps.setString(i++, articoloasta.getImmagine());
            ps.setString(i++, articoloasta.getDescription());
            ps.setDate(i++, articoloasta.getData_scadenza());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articoloasta;
    }

    @Override
    public void update(ArticoloVendita articolo) {

    }

    @Override
    public void delete(ArticoloVendita articolo) {

    }

    @Override
    public Articolo create(Integer id, String nome, String categoria, String immagine, String description) {
        return null;
    }

    @Override
    public void update(Articolo articolo) {

    }

    @Override
    public void delete(Articolo articolo) {

    }

    @Override
    public Articolo findByArticoloId(Integer id) {
        return null;
    }

    @Override
    public List<ArticoloAsta> findByCategoria(String categoria) {
        PreparedStatement ps;
        ArrayList<ArticoloAsta> articoliasta = new ArrayList<ArticoloAsta>();
        ArticoloAsta articoloasta;

        try {

            String sql
                    = "SELECT * FROM ARTICOLO JOIN ARTICOLO_ASTA WHERE Categoria=?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, categoria);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                articoloasta = read(resultSet);   // leggo il risultato della query (la traduco)
                articoliasta.add(articoloasta);      // aggiungo alla lista di articoli da restituire
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articoliasta;
    }

    @Override
    public List<Articolo> findBySearchString(Utente utente, String SearchString) {
        return null;
    }

    ArticoloAsta read(ResultSet rs) {
        ArticoloAsta articoloasta = new ArticoloAsta();
        //User user = new User();
        //contact.setUser(user);
        try {
            articoloasta.setId(rs.getInt("ID"));
        } catch (SQLException sqle) {
        }
        try {
            articoloasta.setNome(rs.getString("Nome"));
        } catch (SQLException sqle) {
        }
        try {
            articoloasta.setCategoria(rs.getString("Categoria"));
        } catch (SQLException sqle) {
        }
        try {
            articoloasta.setStatus(rs.getString("Status").equals("Y"));
        } catch (SQLException sqle) {
        }
        try {
            articoloasta.setImmagine(rs.getString("Immagine"));
        } catch (SQLException sqle) {
        }
        try {
            articoloasta.setDescription(rs.getString("Descrizione"));
        } catch (SQLException sqle) {
        }
        try {
            articoloasta.setDeleted(rs.getString("Deleted").equals("Y"));
        } catch (SQLException sqle) {
        }
        try {
            articoloasta.setData_scadenza(rs.getDate("Data_scadenza"));
        } catch (SQLException sqle) {

        }

        return articoloasta;
    }
}
