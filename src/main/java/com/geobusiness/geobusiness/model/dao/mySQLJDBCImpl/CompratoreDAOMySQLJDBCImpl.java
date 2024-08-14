package com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl;

import com.geobusiness.geobusiness.model.dao.CompratoreDAO;
import com.geobusiness.geobusiness.model.mo.ArticoloAsta;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Utente;

import java.sql.*;

public class CompratoreDAOMySQLJDBCImpl implements CompratoreDAO {

    private final String COUNTER_ID = "id";
    Connection conn;

    public CompratoreDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }
    @Override
    public Compratore create(String Username, String Password, String Indirizzo_consegna) {
        PreparedStatement ps;
        Compratore compratore = new Compratore();
        //utente.setId(id);  // PROBABILMENTE NON SERVE, SUL DATABASE C'È AUTO_INCREMENT

        Integer id = findByUsername(Username).getId();   // per creare il compratore trovo l'id dell'utente che ho già creato
        compratore.setIndirizzo_consegna(Indirizzo_consegna);

        try{

            String sql
                    = " INSERT INTO COMPRATORE "
                    + "   ( ID,"
                    + "     Indirizzo_cons,"
                    + "   ) "
                    + " VALUES (?,?)";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setInt(i, id);
            ps.setString(i++, compratore.getIndirizzo_consegna());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compratore;
    }

    @Override
    public void update(Compratore compratore) {

    }

    @Override
    public void delete(Compratore compratore) {

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
    public Utente findLoggedUtente() {     // serve per i cookie
        return null;
    }

    @Override
    public Compratore findByUtenteId(Integer id) {
        PreparedStatement ps;
        Compratore compratore = null;

        try {

            String sql
                    = " SELECT *"
                    + " FROM UTENTE NATURAL JOIN COMPRATORE "
                    + " WHERE "
                    + "   ID = ? AND "
                    + "   Deleted  = 'N' ";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                 compratore = readCompratore(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compratore;
    }

    @Override
    public Compratore findByUsername(String username) {
        PreparedStatement ps;
        Compratore compratore = null;

        try {

            String sql
                    = " SELECT * "
                    + "   FROM UTENTE NATURAL JOIN COMPRATORE"
                    + " WHERE "
                    + "   Username = ?";

            ps = conn.prepareStatement(sql);   // preparo lo statement sulla connessione database con la stringa sql appena fatta
            ps.setString(1, username);     // settare le varabili nel statement

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {      // se ritorna più cose (per esempio voglio trovare per categoria e quindi seleziono più righe) al posto di if metto while
                compratore = readCompratore(resultSet);   // e qui metterei una list, esempio: List<utente> ...
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return compratore;
    }

    @Override
    public void compra(Integer id_comp, Integer id_articolo, Date data) {
        PreparedStatement ps;

        try{

            String sql
                    = " INSERT INTO COMPRA "
                    + "   ( Id_compratore,"
                    + "     Id_articolo,"
                    + "      Data_acquisto"
                    + "   ) "
                    + " VALUES (?,?,?)";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setInt(i, id_comp);
            ps.setInt(i++, id_articolo);
            ps.setDate(i++, data);
            ps.executeUpdate();


            sql
                    = "UPDATE ARTICOLO"
                    + "SET Status=1"
                    + "WHERE ID=?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id_articolo);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void faofferta(Integer id_comp, Integer id_asta, Float offerta, Date data) {
        PreparedStatement ps;

        try{

            String sql
                    = " INSERT INTO FA_OFFERTA "
                    + "   ( Id_compratore,"
                    + "     Id_aSTA,"
                    + "     Offerta,"
                    + "      Data"
                    + "   ) "
                    + " VALUES (?,?,?,?)";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setInt(i, id_comp);
            ps.setInt(i++, id_asta);
            ps.setFloat(i++, offerta);
            ps.setDate(i++, data);
            ps.executeUpdate();

        } catch (SQLException e) {
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
}
