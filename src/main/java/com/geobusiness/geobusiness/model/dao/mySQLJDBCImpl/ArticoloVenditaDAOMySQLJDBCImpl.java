package com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl;

import com.geobusiness.geobusiness.model.dao.ArticoloVenditaDAO;
import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.ArticoloVendita;
import com.geobusiness.geobusiness.model.mo.Utente;
import com.geobusiness.geobusiness.model.mo.Venditore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class ArticoloVenditaDAOMySQLJDBCImpl implements ArticoloVenditaDAO {

    private final String COUNTER_ID = "id";
    Connection conn;

    public ArticoloVenditaDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
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
    public List<Articolo> findBySearchString(Utente utente, String SearchString) {
        return null;
    }

    @Override
    public ArticoloVendita create(Integer id,
                                  String nome,
                                  String categoria,
                                  String immagine,
                                  String description,
                                  Float prezzo
    ) {
        PreparedStatement ps;
        ArticoloVendita articolovendita = new ArticoloVendita();
        //utente.setId(id);  // PROBABILMENTE NON SERVE, SUL DATABASE C'È AUTO_INCREMENT
        articolovendita.setNome(nome);
        articolovendita.setCategoria(categoria);
        articolovendita.setImmagine(immagine);
        articolovendita.setDescription(description);
        articolovendita.setPrezzo(prezzo);

        try{

            String sql
                    = " INSERT INTO ART_IN_VENDITA "
                    + "   ( Nome,"
                    + "     Categoria,"
                    + "     Immagine,"
                    + "      Descrizione,"
                    + "      Prezzo"
                    + "   ) "
                    + " VALUES (?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i, articolovendita.getNome());
            ps.setString(i++, articolovendita.getCategoria());
            ps.setString(i++, articolovendita.getImmagine());
            ps.setString(i++, articolovendita.getDescription());
            ps.setFloat(i++, articolovendita.getPrezzo());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articolovendita;
    }

    @Override
    public void update(ArticoloVendita articolo) {  // ANCORA DA FARE

    }

    @Override
    public void delete(ArticoloVendita articolo) {  // ANCORA DA FARE

    }

    @Override
    public ArticoloVendita findByArticoloId(Integer id) { // ANCORA DA FARE
        PreparedStatement ps;
        ArticoloVendita articolovendita = null;

        try {

            String sql
                    = " SELECT *"
                    + " FROM ARTICOLO NATURAL JOIN ART_IN_VENDITA "
                    + " WHERE "
                    + "   ID = ? AND "
                    + "   Deleted  = 'N' ";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                articolovendita = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articolovendita;
    }

    @Override
    public List<ArticoloVendita> findByCategoria(String categoria) {
        PreparedStatement ps;
        ArrayList<ArticoloVendita> articolivendita = new ArrayList<ArticoloVendita>();
        ArticoloVendita articolovendita;

        try {

            String sql
                    = "SELECT * FROM ARTICOLO NATURAL JOIN ART_IN_VENDITA WHERE Categoria=?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, categoria);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                articolovendita = read(resultSet);   // leggo il risultato della query (la traduco)
                articolivendita.add(articolovendita);      // aggiungo alla lista di articoli da restituire
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return articolivendita;
    }

    public Venditore findVenditoreById(Integer id){
        PreparedStatement ps;
        Venditore venditore = null;

        try {

            String sql
                    = "SELECT V.ID, Username, Password, Deleted, Indirizzo_spediz\n" +
                    "FROM UTENTE NATURAL JOIN VENDITORE AS V JOIN VENDE ON V.ID=Id_venditore JOIN ART_IN_VENDITA AS A ON A.ID = Id_articolo\n" +
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
    ArticoloVendita read(ResultSet rs) {
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
            articolovendita.setStatus(rs.getString("Status").equals("Y"));
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
