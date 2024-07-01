package com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl;

import com.geobusiness.geobusiness.model.dao.UtenteDAO;
import com.geobusiness.geobusiness.model.mo.Utente;

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
    public Utente create(Integer id, String Username, String Paswword) {   // DA IMPLEMENTARE
        return null;
    }

    @Override
    public void update(Utente utente) {         // DA IMPLEMENTARE

    }

    @Override
    public void delete(Utente utente) {        // DA IMPLEMENTARE

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
