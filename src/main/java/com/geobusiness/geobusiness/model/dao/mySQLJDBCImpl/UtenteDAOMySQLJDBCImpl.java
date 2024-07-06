package com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl;

import com.geobusiness.geobusiness.model.dao.UtenteDAO;
import com.geobusiness.geobusiness.model.mo.Utente;
//import com.geobusiness.geobusiness.model.dao.exception.DuplicatedObjectException;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class UtenteDAOMySQLJDBCImpl implements UtenteDAO {

    Connection conn;

    public UtenteDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }
    @Override
    public Utente create(
            Integer id,      // Probabilmente dovrò toglierlo, non lo uso per creare un utente, lo crea direttamente il database
            String Username,
            String Password
    ) {
        PreparedStatement ps;
        Utente utente = new Utente();
        //utente.setId(id);  // PROBABILMENTE NON SERVE, SUL DATABASE C'È AUTO_INCREMENT
        utente.setUsername(Username);
        utente.setPassword(Password);

        try{

            String sql
                    = " SELECT ID "
                    + " FROM UTENTE "
                    + " WHERE "
                    + " Deleted ='N' AND "
                    + " Username = ? AND"
                    + " Password = ? AND";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i, utente.getUsername());
            ps.setString(i++, utente.getPassword());

            ResultSet resultSet = ps.executeQuery();

            boolean exist;
            exist = resultSet.next();
            resultSet.close();

            if (exist) { // devo aggiungere una cartella 'exception' per definire questa eccezione
                //throw new DuplicatedObjectException("ContactDAOJDBCImpl.create: Tentativo di inserimento di un contatto già esistente.");
            }

            sql
                    = " INSERT INTO UTENTE "
                    + "   ( Username,"
                    + "     Password,"
                    + "   ) "
                    + " VALUES (?,?)";

            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setString(i, utente.getUsername());
            ps.setString(i++, utente.getPassword());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
    }

    return utente;


    }  // La devo mettere per l'utente gestone, per venditore e compratore ne specifico un'altra

    @Override
    public void update(Utente utente) {// DA IMPLEMENTARE

        PreparedStatement ps;

        try {
            String sql;
            sql               // PROBABILMENTE NON SERVE
                    = " SELECT ID "
                    + " FROM UTENTE "
                    + " WHERE "
                    + " Deleted ='N' AND "
                    + " Username = ? AND"
                    + " Password = ? AND"
                    + " ID <> ?";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i, utente.getUsername());
            ps.setString(i++, utente.getPassword());
            ps.setInt(i++, utente.getId());

            ResultSet resultSet = ps.executeQuery();

            boolean exist;
            exist = resultSet.next();
            resultSet.close();

            if (exist) {
                //throw new DuplicatedObjectException("ContactDAOJDBCImpl.create: Tentativo di aggiornamento in un contatto già esistente.");
            }

            sql
                    = " UPDATE  UTENTE"
                    + " SET "
                    + "   Username = ?, "
                    + "   Paasword = ? "
                    + " WHERE "
                    + "   ID = ? ";

            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setString(i, utente.getUsername());
            ps.setString(i++, utente.getPassword());
            ps.setInt(i++, utente.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Utente utente) {
        PreparedStatement ps;

        try {

            String sql
                    = " UPDATE UTENTE "
                    + " SET Deleted='Y' "
                    + " WHERE "
                    + " ID=?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, utente.getId());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Utente findLoggedUtente() {       // NON SI IMPLEMENTA, serviva per i coockie, non il database
        return null;
    }

    @Override
    public Utente findByUtenteId(Integer id) {    // DA IMPLEMENTARE, non le ho implementate sui coockie, lo faccio qui
        PreparedStatement ps;
        Utente user = null;

        try {

            String sql
                    = " SELECT * "
                    + "   FROM UTENTE "
                    + " WHERE "
                    + "   ID = ?";

            ps = conn.prepareStatement(sql);   // preparo lo statement sulla connessione database con la stringa sql appena fatta
            ps.setLong(1, id);     // settare le varabili nel statement

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {      // se ritorna più cose (per esempio voglio trovare per categoria e quindi seleziono più righe) al posto di if metto while
                user = read(resultSet);   // e qui metterei una list, esempio: List<utente> ...
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;

    }

    Utente read(ResultSet rs) {

        Utente utente = new Utente();
        try {
            utente.setId(rs.getInt("ID"));
        } catch (SQLException sqle) {
        }
        try {
            utente.setUsername(rs.getString("Username"));
        } catch (SQLException sqle) {
        }
        try {
            utente.setPassword(rs.getString("Password"));
        } catch (SQLException sqle) {
        }
        try {
            utente.setDeleted(rs.getString("Deleted").equals("Y"));
        } catch (SQLException sqle) {
        }
        return utente;
    }
}
