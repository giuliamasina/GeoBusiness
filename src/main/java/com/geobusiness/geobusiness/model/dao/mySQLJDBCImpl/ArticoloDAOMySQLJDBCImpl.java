package com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl;

import com.geobusiness.geobusiness.model.dao.ArticoloDAO;
import com.geobusiness.geobusiness.model.dao.UtenteDAO;
import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticoloDAOMySQLJDBCImpl implements ArticoloDAO {

    private final String COUNTER_ID = "id";
    Connection conn;

    public ArticoloDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Articolo create(Integer id,  // da fare su Articolo_vendita, Articolo_asta
                           String nome,
                           String categoria,
                           String immagine,
                           String description
    ) {
        PreparedStatement ps;
        Articolo articolo = new Articolo();
        //utente.setId(id);  // PROBABILMENTE NON SERVE, SUL DATABASE C'È AUTO_INCREMENT
        articolo.setNome(nome);
        articolo.setCategoria(categoria);
        articolo.setImmagine(immagine);
        articolo.setDescription(description);

        try{

            String sql
                    = " INSERT INTO ARTICOLO "
                    + "   ( Nome,"
                    + "     Categoria,"
                    + "     Immagine,"
                    + "      Descrizione,"
                    + "   ) "
                    + " VALUES (?,?,?,?)";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i, articolo.getNome());
            ps.setString(i++, articolo.getCategoria());
            ps.setString(i++, articolo.getImmagine());
            ps.setString(i++, articolo.getDescription());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articolo;
    }

    @Override
    public void update(Articolo articolo) {

    }

    @Override
    public void delete(Articolo articolo) {
        PreparedStatement ps;

        try {

            String sql
                    = " UPDATE ARTICOLO "
                    + " SET Deleted='Y' "
                    + " WHERE "
                    + " ID=?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, articolo.getId());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Articolo findByArticoloId(Integer id) {
        PreparedStatement ps;
        Articolo articolo = null;

        try {

            String sql
                    = " SELECT *"
                    + " FROM articolo "
                    + " WHERE "
                    + "   id = ? AND "
                    + "   deleted  = 'N' ";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                articolo = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articolo;
    }

    @Override
    public List<Articolo> findByCategoria(String categoria) {
        PreparedStatement ps;
        ArrayList<Articolo> articoli = new ArrayList<Articolo>();
        Articolo articolo;

        try {

            String sql
                    = "SELECT * FROM ARTICOLO WHERE Categoria=?";

            ps = conn.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                articolo = read(resultSet);   // leggo il risultato della query (la traduco)
                articoli.add(articolo);      // aggiungo alla lista di articoli da restituire
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articoli;
    }

    @Override
    public List<Articolo> findBySearchString(Utente utente, String SearchString) {  // guardare su sito rubrica del prof
        return null;
    }

    Articolo read(ResultSet rs) {
        Articolo articolo = new Articolo();
        //User user = new User();
        //contact.setUser(user);
        try {
            articolo.setId(rs.getInt("ID"));
        } catch (SQLException sqle) {
        }
        try {
            articolo.setNome(rs.getString("Nome"));
        } catch (SQLException sqle) {
        }
        try {
            articolo.setCategoria(rs.getString("Categoria"));
        } catch (SQLException sqle) {
        }
        try {
            articolo.setStatus(rs.getString("Status").equals("Y"));
        } catch (SQLException sqle) {
        }
        try {
            articolo.setImmagine(rs.getString("Immagine"));
        } catch (SQLException sqle) {
        }
        try {
            articolo.setDescription(rs.getString("Descrizione"));
        } catch (SQLException sqle) {
        }
        try {
            articolo.setDeleted(rs.getString("Deleted").equals("Y"));
        } catch (SQLException sqle) {
        }

        return articolo;
    }


}
