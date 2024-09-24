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
    public ArticoloAsta create(//Integer id,
                               String nome,
                               String categoria,
                               String immagine,
                               String description,
                               Timestamp Data_scadenza
    ) {
        PreparedStatement ps;
        ArticoloAsta articoloasta = new ArticoloAsta();
        ResultSet resultSet = null;
        //utente.setId(id);  // PROBABILMENTE NON SERVE, SUL DATABASE C'È AUTO_INCREMENT
        articoloasta.setNome(nome);
        articoloasta.setCategoria(categoria);
        articoloasta.setStatus(0);
        articoloasta.setImmagine(immagine);
        articoloasta.setDescription(description);
        articoloasta.setDeleted(false);
        articoloasta.setData_scadenza(Data_scadenza);

        try{

            String sql
                    = " INSERT INTO ARTICOLO "
                    + "   ( Nome,"
                    + "     Categoria,"
                    + "     Status,"
                    + "     Immagine,"
                    + "      Descrizione,"
                    + "      Deleted"
                    + "   ) "
                    + " VALUES (?,?,?,?,?,'N')";

            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 1;
            ps.setString(1, articoloasta.getNome());
            ps.setString(2, articoloasta.getCategoria());
            ps.setInt(3, 0);
            ps.setString(4, articoloasta.getImmagine());
            ps.setString(5, articoloasta.getDescription());
            //ps.setString(6, "N");

            ps.executeUpdate();

            /*sql
                    = "SELECT MAX(ID) AS max_id "
                    + "FROM ARTICOLO";

            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery(); // questo è sbagliato devo mettere resultset.next()

             */

            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                int last_id_item = resultSet.getInt(1);  // Recupera l'ultimo ID generato
                articoloasta.setId(last_id_item);
            }

            sql
                    = "INSERT INTO ART_IN_ASTA"
                    + "    ( ID,"
                    + "      Data_scadenza";

            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setInt(1, articoloasta.getId());
            ps.setTimestamp(2, articoloasta.getData_scadenza());

            ps.executeUpdate();

            ps.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articoloasta;
    }

    @Override
    public void update(ArticoloAsta articolo) {

    }

    @Override
    public void deleteAsta(Integer articolo_id) {
        PreparedStatement ps;

        try {

            String sql
                    = " UPDATE ARTICOLO "
                    + " SET Deleted='Y' "
                    + " WHERE "
                    + " ID=?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, articolo_id);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Articolo create(Integer id, String nome, String categoria, String immagine, String description) {
        return null;
    }

    @Override
    public void update(Articolo articolo) {

    }

    @Override
    public void delete(Integer articolo_id) {

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

    public List<Timestamp> getDateOffersById(Integer id){
        PreparedStatement ps;
        List<Timestamp> date = new ArrayList<>();
        Timestamp data = null;

        try{
            String sql
                    = "SELECT Data FROM FA_OFFERTA WHERE Id_asta=? ORDER BY Data DESC";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                try {
                    data = resultSet.getTimestamp("Data");
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
            articoloasta.setData_scadenza(rs.getTimestamp("Data_scadenza"));
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

    @Override
    public void metteInAsta(Integer Id_vend, String nome, String categoria, String immagine, String description, Timestamp data_scad, Timestamp data_pubbl) {
        PreparedStatement ps;
        ArticoloAsta articoloasta = null;

        try{

            articoloasta = create(nome, categoria, immagine, description, data_scad);

            String sql
                    = "INSERT INTO METTE_IN_ASTA"
                    + "   (Id_venditore,"
                    + "    Id_asta,"
                    + "    Data_pubbl"
                    + "     )"
                    + "VALUES (?,?,?)";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setInt(i, Id_vend);
            ps.setInt(i++, articoloasta.getId());
            ps.setTimestamp(i++, data_pubbl);

            ps.executeUpdate();

            //resultSet.close();  (non so se serve)
            ps.close();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
