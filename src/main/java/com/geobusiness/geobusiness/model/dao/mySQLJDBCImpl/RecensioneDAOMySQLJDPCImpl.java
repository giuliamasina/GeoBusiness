package com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl;

import com.geobusiness.geobusiness.model.dao.RecensioneDAO;
import com.geobusiness.geobusiness.model.mo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAOMySQLJDPCImpl implements RecensioneDAO {

    Connection conn;


    public RecensioneDAOMySQLJDPCImpl(Connection connection) {  conn = connection; }

    @Override
    public Recensione create(
            // id creato direttamente nel database
            Integer valutazione,
            String commento,
            Timestamp data_pubblicazione,
            Integer id_c,
            Integer id_v
    ) {
        PreparedStatement ps;
        Recensione recensione = new Recensione();
        ResultSet resultSet = null;
        //utente.setId(id);  // PROBABILMENTE NON SERVE, SUL DATABASE C'È AUTO_INCREMENT
        recensione.setValutazione(valutazione);
        recensione.setCommento(commento);
        recensione.setDataPubblicazione(data_pubblicazione);
        recensione.setVenditoreId(id_v);
        recensione.setCompratoreId(id_c);

        try{

            String sql
                    = " INSERT INTO RECENSIONE "
                    + "   ( Valutazione,"
                    + "     Commento,"
                    + "     Data_pubbl"
                    + "   ) "
                    + " VALUES (?,?,?)";

            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            int i = 1;
            ps.setInt(1, valutazione);
            ps.setString(2, commento);
            ps.setTimestamp(3, data_pubblicazione);

            ps.executeUpdate();

            /*sql
                    = "SELECT MAX(ID_recensione) AS max_id "
                    + "FROM RECENSIONE";

            ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            Integer last_id_review =  resultSet.getInt("max_id");

            recensione.setId(last_id_review);

             */

            resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                int last_id = resultSet.getInt(1);  // Recupera l'ultimo ID generato
                recensione.setId(last_id);
            }

            // per aggiornare FA_RECENSIONE lo faccio in un metodo a parte
            // perchè create serve solo a creare la recensione stessa, anche il controllo
            // si fa prima di aver fatto create

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recensione;
    }

    @Override
    public void update(Recensione recensione) {

    }

    @Override
    public void delete(Recensione recensione) {

    }

    @Override
    public Articolo findByRecensioneId(Integer id) {
        return null;
    }

    @Override
    public List<Recensione> findByVenditoreId(Integer id) {

        PreparedStatement ps;
        ArrayList<Recensione> recensioni = new ArrayList<>();
        Recensione recensione;

        try{

            String sql
                    = "SELECT ID_R, ID_Venditore, ID_Compratore, Valutazione, Commento, Data_pubbl "
                    + "FROM RECENSIONE JOIN FA_RECENSIONE ON ID_R = ID_recensione "
                    + "WHERE ID_Venditore = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                recensione = readRecensione(resultSet);   // leggo il risultato della query (la traduco)
                recensioni.add(recensione);      // aggiungo alla lista di articoli da restituire
            }

            resultSet.close();
            ps.close();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return recensioni;
    }

    @Override
    public List<Recensione> findByCompratoreId(Integer id) {
        PreparedStatement ps;
        ArrayList<Recensione> recensioni = new ArrayList<>();
        Recensione recensione;

        try{

            String sql
                    = "SELECT ID_R, ID_Venditore, ID_Compratore, Valutazione, Commento, Data_pubbl "
                    + "FROM RECENSIONE JOIN FA_RECENSIONE ON ID_R = ID_recensione "
                    + "WHERE ID_Compratore = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                recensione = readRecensione(resultSet);   // leggo il risultato della query (la traduco)
                recensioni.add(recensione);      // aggiungo alla lista di articoli da restituire
            }

            resultSet.close();
            ps.close();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return recensioni;
    }

    public void fa_recensione(Integer id_c, Integer id_v, Integer valutazione, String commento, Timestamp data_pubbl){
        PreparedStatement ps;
        Recensione recensione;

        try{
            String sql
                    = " SELECT ID_R "
                    + " FROM FA_RECENSIONE "
                    + " WHERE "
                    + " ID_Compratore = ? AND "
                    + " ID_Venditore = ?";

            ps = conn.prepareStatement(sql);
            //int i = 1;
            ps.setInt(1, id_c);
            ps.setInt(2, id_v);

            ResultSet resultSet = ps.executeQuery();

            boolean exist;
            exist = resultSet.next();
            resultSet.close();

            if (exist) { // devo aggiungere una cartella 'exception' per definire questa eccezione
                //throw new DuplicatedObjectException("ContactDAOJDBCImpl.create: Tentativo di inserimento di un contatto già esistente.");
            }

            // creo la recensione
            recensione = create(valutazione, commento, data_pubbl, id_c,id_v);

            Integer last_id_review = recensione.getId();

            // specifico quale compratore e quale venditore (FA_RECENSIONE)
            sql
                    = " INSERT INTO FA_RECENSIONE "
                    + "    ( ID_R,"
                    + "     ID_Venditore,"
                    + "     ID_Compratore"
                    + "   ) "
                    + "  VALUES (?,?,?) ";

            ps = conn.prepareStatement(sql);
            //i = 1;

            ps.setInt(1, last_id_review);
            ps.setInt(2, id_v);
            ps.setInt(3, id_c);

            ps.executeUpdate();

            //resultSet.close();
            ps.close();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Recensione checkIfAlreadyExists(Integer id_c, Integer id_v){
        PreparedStatement ps;
        Recensione recensione = null;

        try{
            String sql
                    = "SELECT ID_R, Valutazione, Commento, Data_pubbl, ID_Venditore, ID_Compratore "
                    + "FROM RECENSIONE JOIN FA_RECENSIONE ON ID_R=ID_recensione "
                    + " WHERE "
                    + " ID_Compratore = ? AND "
                    + " ID_Venditore = ?";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setInt(1, id_c);
            ps.setInt(2, id_v);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                recensione = readRecensione(resultSet);   // leggo il risultato della query (la traduco)
            }

            resultSet.close();
            ps.close();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return recensione;
    }

    Recensione readRecensione(ResultSet rs){
        Recensione recensione = new Recensione();
        try {
            recensione.setId(rs.getInt("ID_R"));
        } catch (SQLException sqle) {
        }
        try {
            recensione.setValutazione(rs.getInt("Valutazione"));
        } catch (SQLException sqle) {
        }
        try {
            recensione.setCommento(rs.getString("Commento"));
        } catch (SQLException sqle) {
        }
        try {
            recensione.setDataPubblicazione(rs.getTimestamp("Data_pubbl"));
        } catch (SQLException sqle) {
        }
        try {
            recensione.setVenditoreId(rs.getInt("ID_Venditore"));
        } catch (SQLException sqle) {
        }
        try {
            recensione.setCompratoreId(rs.getInt("ID_Compratore"));
        } catch (SQLException sqle) {
        }
        return recensione;
    }
}
