package com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl;

import com.geobusiness.geobusiness.model.dao.ArticoloAstaDAO;
import com.geobusiness.geobusiness.model.mo.*;

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
                    = " INSERT INTO ART_IN_ASTA "
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
    public ArticoloAsta findByArticoloId(Integer id) {
        PreparedStatement ps;
        ArticoloAsta articoloasta = null;

        try {

            String sql
                    = " SELECT *"
                    + " FROM ARTICOLO NATURAL JOIN ART_IN_ASTA "
                    + " WHERE "
                    + "   ID = ? AND "
                    + "   Deleted  = 'N' ";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                articoloasta = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articoloasta;
    }

    @Override
    public List<ArticoloAsta> findByCategoria(String categoria) {
        PreparedStatement ps;
        ArrayList<ArticoloAsta> articoliasta = new ArrayList<ArticoloAsta>();
        ArticoloAsta articoloasta;

        try {

            String sql
                    = "SELECT * FROM ARTICOLO NATURAL JOIN ART_IN_ASTA WHERE Categoria=?";

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
    public List<ArticoloAsta> selectAll() {
        PreparedStatement ps;
        ArrayList<ArticoloAsta> articoliasta = new ArrayList<ArticoloAsta>();
        ArticoloAsta articoloasta;

        try {

            String sql
                    = "SELECT * FROM ARTICOLO NATURAL JOIN ART_IN_ASTA";

            ps = conn.prepareStatement(sql);

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
    public List<Float> getOffersById(Integer id){
        PreparedStatement ps;
        List<Float> offerte = new ArrayList<>();
        Float offerta = null;

        try{
            String sql
                    = "SELECT Offerta FROM FA_OFFERTA WHERE Id_asta=? ORDER BY Data DESC";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                try {
                    offerta = resultSet.getFloat("Offerta");
                }catch (SQLException sqle) {
                }
                offerte.add(offerta);
            }

            resultSet.close();
            ps.close();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return offerte;
    }

    public List<Date> getDateOffersById(Integer id){
        PreparedStatement ps;
        List<Date> date = new ArrayList<>();
        Date data = null;

        try{
            String sql
                    = "SELECT Data FROM FA_OFFERTA WHERE Id_asta=? ORDER BY Data DESC";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                try {
                    data = resultSet.getDate("Data");
                }catch (SQLException sqle) {
                }
                date.add(data);
            }

            resultSet.close();
            ps.close();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return date;
    }

    public List<Compratore> getOfferingCompratoriById(Integer id){
        PreparedStatement ps;
        List<Compratore> compratori = null;
        Compratore compratore = null;

        try{
            String sql
                    = "SELECT C.ID, Username, Password, Indirizzo_cons " +
                      "FROM FA_OFFERTA JOIN COMPRATORE AS C ON C.ID = Id_compratore NATURAL JOIN UTENTE " +
                      "WHERE Id_asta=? " +
                      "ORDER BY Data DESC";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {

                compratore = readCompratore(resultSet);
                compratori.add(compratore);
            }

            resultSet.close();
            ps.close();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compratori;
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

    public Venditore findVenditoreById(Integer id){
        PreparedStatement ps;
        Venditore venditore = null;

        try {

            String sql
                    = "SELECT V.ID, Username, Password, Deleted, Indirizzo_spediz\n" +
                    "FROM UTENTE NATURAL JOIN VENDITORE AS V JOIN METTE_IN_ASTA ON V.ID=Id_venditore JOIN ART_IN_ASTA AS A ON A.ID = Id_asta\n" +
                    "WHERE A.ID = ?";

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

    Compratore readCompratore(ResultSet rs){
        Compratore compratore = new Compratore();

        try {
            compratore.setId(rs.getInt("ID"));
        } catch (SQLException sqle) {
        }
        try {
            compratore.setUsername(rs.getString("Username"));
        } catch (SQLException sqle) {
        }
        try {
            compratore.setPassword(rs.getString("Password"));
        } catch (SQLException sqle) {
        }
        try {
            compratore.setDeleted(rs.getString("Deleted").equals("Y"));
        } catch (SQLException sqle) {
        }
        try {
            compratore.setIndirizzo_consegna(rs.getString("Indirizzo_cons"));
        } catch (SQLException sqle) {
        }

        return compratore;
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
}
