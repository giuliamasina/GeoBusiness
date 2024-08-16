package com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.geobusiness.geobusiness.services.config.Configuration;

import com.geobusiness.geobusiness.model.dao.*;

public class MySQLJDBCDAOFactory extends DAOFactory {

    private Map factoryParameters;

    private Connection connection;

    public MySQLJDBCDAOFactory(Map factoryParameters) {
        this.factoryParameters=factoryParameters;
    }
    @Override
    public void beginTransaction() {
        try {
            Class.forName(Configuration.DATABASE_DRIVER);
            this.connection = DriverManager.getConnection(Configuration.DATABASE_URL);
            this.connection.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commitTransaction() {
        try {
            this.connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollbackTransaction() {
        try {
            this.connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closeTransaction() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UtenteDAO getUtenteDAO() {
        return new UtenteDAOMySQLJDBCImpl(connection);
    }

    @Override
    public VenditoreDAO getVenditoreDAO() { return new VenditoreDAOMySQLJDBCImpl(connection); }

    @Override
    public CompratoreDAO getCompratoreDAO() { return new CompratoreDAOMySQLJDBCImpl(connection); }

    @Override
    public ArticoloDAO getArticoloDAO() {
        return new ArticoloDAOMySQLJDBCImpl(connection);
    }

    @Override
    public ArticoloVenditaDAO getArticoloVenditaDAO() {
        return new ArticoloVenditaDAOMySQLJDBCImpl(connection);
    }

    @Override
    public ArticoloAstaDAO getArticoloAstaDAO() { return new ArticoloAstaDAOMySQLJDBCImpl(connection); }

    @Override
    public RecensioneDAO getRecensioneDAO() {
        return null;
    }
}
