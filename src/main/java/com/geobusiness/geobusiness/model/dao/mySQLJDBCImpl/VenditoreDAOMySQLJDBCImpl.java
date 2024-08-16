package com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl;

import com.geobusiness.geobusiness.model.dao.VenditoreDAO;
import com.geobusiness.geobusiness.model.mo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VenditoreDAOMySQLJDBCImpl implements VenditoreDAO {
    private final String COUNTER_ID = "id";
    Connection conn;

    public VenditoreDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Utente create(String Username, String Paswword) {
        return null;
    }

    @Override
    public void update(Utente utente) {

    }

    @Override
    public void delete(Utente utente) {

    }

    @Override
    public Utente findLoggedUtente() {
        return null;
    }

    @Override
    public Venditore create(String Username, String Paswword, String Indirizzo_spedizione) {
        return null;
    }

    @Override
    public void update(Venditore venditore) {

    }

    @Override
    public void delete(Venditore venditore) {

    }

    @Override
    public Venditore findByUtenteId(Integer id) {
        PreparedStatement ps;
        Venditore venditore = null;

        try {

            String sql
                    = " SELECT *"
                    + " FROM UTENTE NATURAL JOIN VENDITORE "
                    + " WHERE "
                    + "   ID = ? AND "
                    + "   Deleted  = 'N' ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                venditore = readVenditore(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return venditore;
    }

    @Override
    public Venditore findByUsername(String username) {
        PreparedStatement ps;
        Venditore venditore = null;


        try {

            String sql
                    = " SELECT *"
                    + " FROM UTENTE NATURAL JOIN VENDITORE "
                    + " WHERE "
                    + "   Username = ? AND "
                    + "   Deleted  = 'N' ";

            ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                venditore = readVenditore(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return venditore;
    }

    @Override
    public List<ArticoloVendita> findArticoliInVendita(Integer id) {
        PreparedStatement ps;
        ArrayList<ArticoloVendita> articolivendita = new ArrayList<ArticoloVendita>();
        ArticoloVendita articolovendita;

        try {

            String sql
                    = "SELECT ID, Nome, Categoria, Status, Immagine, Descrizione, Deleted, Prezzo\n" +
                    "FROM ARTICOLO NATURAL JOIN ART_IN_VENDITA JOIN VENDE ON ID=Id_articolo\n" +
                    "WHERE Id_venditore=?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                articolovendita = readArticoloVendita(resultSet);   // leggo il risultato della query (la traduco)
                articolivendita.add(articolovendita);      // aggiungo alla lista di articoli da restituire
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articolivendita;
    }

    @Override
    public List<ArticoloAsta> findArticoliInAsta(Integer id) {
        PreparedStatement ps;
        ArrayList<ArticoloAsta> articoliasta = new ArrayList<ArticoloAsta>();
        ArticoloAsta articoloasta;

        try {

            String sql
                    = "SELECT ID, Nome, Categoria, Status, Immagine, Descrizione, Deleted, Data_scadenza\n" +
                    "FROM ARTICOLO NATURAL JOIN ART_IN_ASTA JOIN METTE_IN_ASTA ON ID=Id_asta\n" +
                    "WHERE Id_venditore=?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                articoloasta = readArticoloAsta(resultSet);   // leggo il risultato della query (la traduco)
                articoliasta.add(articoloasta);      // aggiungo alla lista di articoli da restituire
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articoliasta;
    }

    Venditore readVenditore(ResultSet rs){
        Venditore venditore = new Venditore();

        try {
            venditore.setId(rs.getInt("ID"));
        } catch (SQLException sqle) {
        }
        try {
            venditore.setUsername(rs.getString("Username"));
        } catch (SQLException sqle) {
        }
        try {
            venditore.setPassword(rs.getString("Password"));
        } catch (SQLException sqle) {
        }
        try {
            venditore.setDeleted(rs.getString("Deleted").equals("Y"));
        } catch (SQLException sqle) {
        }
        try {
            venditore.setIndirizzo_spedizione(rs.getString("Indirizzo_spediz"));
        } catch (SQLException sqle) {
        }

        return venditore;
    }

    ArticoloVendita readArticoloVendita(ResultSet rs) {
        ArticoloVendita articolovendita = new ArticoloVendita();
        //User user = new User();
        //contact.setUser(user);
        try {
            articolovendita.setId(rs.getInt("ID"));
        } catch (SQLException sqle) {
        }
        try {
            articolovendita.setNome(rs.getString("Nome"));
        } catch (SQLException sqle) {
        }
        try {
            articolovendita.setCategoria(rs.getString("Categoria"));
        } catch (SQLException sqle) {
        }
        try {
            articolovendita.setStatus(rs.getInt("Status"));
        } catch (SQLException sqle) {
        }
        try {
            articolovendita.setImmagine(rs.getString("Immagine"));
        } catch (SQLException sqle) {
        }
        try {
            articolovendita.setDescription(rs.getString("Descrizione"));
        } catch (SQLException sqle) {
        }
        try {
            articolovendita.setDeleted(rs.getString("Deleted").equals("Y"));
        } catch (SQLException sqle) {
        }
        try {
            articolovendita.setPrezzo(rs.getFloat("Prezzo"));
        }catch(SQLException sqle){

        }

        return articolovendita;
    }

    ArticoloAsta readArticoloAsta(ResultSet rs) {
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
            articoloasta.setStatus(rs.getInt("Status"));
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
