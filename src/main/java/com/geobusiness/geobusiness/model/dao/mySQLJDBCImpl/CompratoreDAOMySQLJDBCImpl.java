package com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl;

import com.geobusiness.geobusiness.model.dao.CompratoreDAO;
import com.geobusiness.geobusiness.model.mo.ArticoloAsta;
import com.geobusiness.geobusiness.model.mo.ArticoloVendita;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Utente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompratoreDAOMySQLJDBCImpl implements CompratoreDAO {

    private final String COUNTER_ID = "id";
    Connection conn;

    public CompratoreDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }
    @Override
    public Compratore create(String Username, String Email, String Password, String Indirizzo_consegna) {
        PreparedStatement ps;
        Compratore compratore = new Compratore();
        ResultSet resultSet = null;
        //utente.setId(id);  // PROBABILMENTE NON SERVE, SUL DATABASE C'È AUTO_INCREMENT

        compratore.setUsername(Username);
        compratore.setEmail(Email);
        compratore.setPassword(Password);
        compratore.setIndirizzo_consegna(Indirizzo_consegna);
        compratore.setDeleted(false);

        try{

            String sql
                    = " INSERT INTO UTENTE "
                    + "   ( Username,"
                    + "     Password,"
                    + "     Deleted,"
                    + "     Email"
                    + "   ) "
                    + " VALUES (?,?,'N',?)";

            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 1;
            ps.setString(1, Username);
            ps.setString(2, Password);
            //ps.setString(i++, "N");
            ps.setString(3, Email);

            ps.executeUpdate();

            /*sql
                    = "SELECT MAX(ID) AS max_id "
                    + "FROM UTENTE";

            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()){
                Integer last_id =  resultSet.getInt("max_id");
                compratore.setId(last_id);
            }

             */

            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                int last_id = resultSet.getInt(1);  // Recupera l'ultimo ID generato
                compratore.setId(last_id);
            }

            sql
                    = "INSERT INTO COMPRATORE"
                    + "    ( ID,"
                    + "      Indirizzo_cons"
                    + "    ) "
                    + " VALUES (?,?)";

            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setInt(1, compratore.getId());
            ps.setString(2, compratore.getIndirizzo_consegna());

            ps.executeUpdate();

            ps.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return compratore;
    }

    @Override
    public void update(Compratore compratore) {

    }

    @Override
    public void deleteComp(Integer compratore_id) {
        PreparedStatement ps;

        try {

            String sql
                    = " UPDATE UTENTE "
                    + " SET Deleted='Y' "
                    + " WHERE "
                    + " ID=?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, compratore_id);
            ps.executeUpdate();
            ps.close();

            // per adesso non elimino la riga anche in COMPRATORE

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Utente create(String Username, String Email, String Paswword) {
        return null;
    }

    @Override
    public void update(Utente utente) {

    }

    @Override
    public void delete(Integer utente_id) {

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
                    + "   Username = ? AND Deleted = 'N'";

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
    public Utente findByEmail(String Email) {
        return null;
    }

    @Override
    public List<ArticoloVendita> findArticoliComprati(Integer id) {
        PreparedStatement ps;
        ArrayList<ArticoloVendita> articolivendita = new ArrayList<ArticoloVendita>();
        ArticoloVendita articolovendita;

        try {

            String sql
                    = "SELECT ID, Nome, Categoria, Status, Immagine, Descrizione, Deleted, Prezzo\n" +
                    "FROM ARTICOLO NATURAL JOIN ART_IN_VENDITA JOIN COMPRA ON ID=Id_articolo\n" +
                    "WHERE Id_compratore=?";

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
    public List<ArticoloAsta> findOfferte(Integer id) {
        PreparedStatement ps;
        ArrayList<ArticoloAsta> articoliasta = new ArrayList<ArticoloAsta>();
        ArticoloAsta articoloasta;

        try {

            String sql
                    = "SELECT ID, Nome, Categoria, Status, Immagine, Descrizione, Deleted, Data_scadenza\n" +
                    "FROM ARTICOLO NATURAL JOIN ART_IN_ASTA JOIN FA_OFFERTA ON ID=Id_asta\n" +
                    "WHERE Id_compratore=?";

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

    @Override
    public void compra(Integer id_comp, Integer id_articolo, Timestamp data) {
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
            ps.setInt(1, id_comp);
            ps.setInt(2, id_articolo);
            ps.setTimestamp(3, data);
            ps.executeUpdate();
            ps.close();


            sql
                    = "UPDATE ARTICOLO "
                    + "SET Status=1 "
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
    public void faofferta(Integer id_comp, Integer id_asta, Float offerta, Timestamp data) {
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
            ps.setInt(1, id_comp);
            ps.setInt(2, id_asta);
            ps.setFloat(3, offerta);
            ps.setTimestamp(4, data);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void vinciAsta(Integer id_articolo, Timestamp data) {
        PreparedStatement ps;
        List<Float> offerte = new ArrayList<>();
        List<Integer> id_compratori = new ArrayList<>();
        List<Integer> id_venditori = new ArrayList<>();
        Float offerta = null;
        Integer id_compratore = null;
        Integer id_venditore = null;
        ResultSet resultSet = null;

        try{

            String sql
                    = "SELECT Offerta, Id_compratore, Id_venditore FROM FA_OFFERTA NATURAL JOIN METTE_IN_ASTA WHERE Id_asta=? ORDER BY Data DESC";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id_articolo);

            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                try {
                    offerta = resultSet.getFloat("Offerta");
                    id_compratore = resultSet.getInt("Id_compratore");
                    id_venditore = resultSet.getInt("Id_venditore");
                }catch (SQLException sqle) {
                }
                offerte.add(offerta);
                id_compratori.add(id_compratore);
                id_venditori.add(id_venditore);
            }

            resultSet.close();
            ps.close();

            sql
                    = "INSERT INTO ART_IN_VENDITA"
                    + "    ( ID,"
                    + "      Prezzo"
                    + "    )"
                    + " VALUES (?,?)";

            PreparedStatement ps2 = conn.prepareStatement(sql);
            //i = 1;
            ps2.setInt(1, id_articolo);
            ps2.setFloat(2, offerte.get(0));

            ps2.executeUpdate();
            ps2.close();

            sql
                    = " INSERT INTO COMPRA "
                    + "   ( Id_compratore,"
                    + "     Id_articolo,"
                    + "      Data_acquisto"
                    + "   ) "
                    + " VALUES (?,?,?)";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setInt(1, id_compratori.get(0));
            ps.setInt(2, id_articolo);
            ps.setTimestamp(3, data);
            ps.executeUpdate();
            ps.close();

            sql
                    = " INSERT INTO VENDE "
                    + " ( Id_venditore,"
                    + " Id_articolo,"
                    + " data_pubbl"
                    + " ) "
                    + " VALUES (?,?,?) ";

            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setInt(1, id_venditori.get(0));
            ps.setInt(2, id_articolo);
            ps.setTimestamp(3, data);
            ps.executeUpdate();
            ps.close();


            sql
                    = "UPDATE ARTICOLO "
                    + "SET Status=1 "
                    + "WHERE ID=?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id_articolo);
            ps.executeUpdate();
            ps.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteOfferte(Integer id_compratore) {
        PreparedStatement ps;

        try{

            String sql
                    = "DELETE FROM FA_OFFERTA WHERE Id_compratore=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id_compratore);

            ps.close();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hacompratoda(Integer id_comp, Integer id_vend){
        PreparedStatement ps;

        try{

            String sql
                    = "SELECT Id_articolo "
                    + "FROM COMPRA NATURAL JOIN VENDE "
                    + "WHERE Id_compratore=? AND Id_venditore=?";

            ps = conn.prepareStatement(sql);
            int i = 1;
            System.out.println("ID_Compratore: " + id_comp);
            System.out.println("ID_Venditore: " + id_vend);
            ps.setInt(1, id_comp);
            ps.setInt(2, id_vend);

            ResultSet resultSet = ps.executeQuery();

            boolean exist;
            exist = resultSet.next();
            resultSet.close();

            if (exist) {
                return true;
            }else{
                return false;
            }

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
            compratore.setEmail(rs.getString("Email"));
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
            articoloasta.setData_scadenza(rs.getTimestamp("Data_scadenza"));
        } catch (SQLException sqle) {

        }

        return articoloasta;
    }
}
