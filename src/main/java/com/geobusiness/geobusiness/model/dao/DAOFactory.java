package com.geobusiness.geobusiness.model.dao;

//import com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl.MySQLJDBCDAOFactory;
import com.geobusiness.geobusiness.model.dao.CookieImpl.CookieDAOFactory;
import com.geobusiness.geobusiness.model.dao.mySQLJDBCImpl.MySQLJDBCDAOFactory;

import java.util.Map;

public abstract class DAOFactory {

    // List of DAO types supported by the factory
    public static final String MYSQLJDBCIMPL = "MySQLJDBCImpl";
    public static final String COOKIEIMPL= "CookieImpl";


    public abstract void beginTransaction();
    public abstract void commitTransaction();
    public abstract void rollbackTransaction();
    public abstract void closeTransaction();

    public abstract UtenteDAO getUtenteDAO();
    public abstract VenditoreDAO getVenditoreDAO();
    public abstract CompratoreDAO getCompratoreDAO();
    public abstract ArticoloDAO getArticoloDAO();
    public abstract ArticoloVenditaDAO getArticoloVenditaDAO();
    public abstract ArticoloAstaDAO getArticoloAstaDAO();
    public abstract RecensioneDAO getRecensioneDAO();


   public static DAOFactory getDAOFactory(String whichFactory,Map factoryParameters) {

        if (whichFactory.equals(MYSQLJDBCIMPL)) {
            return new MySQLJDBCDAOFactory(factoryParameters);
        } else if (whichFactory.equals(COOKIEIMPL)) {
            return new CookieDAOFactory(factoryParameters);
        } else {
            return null;
        }
    }
}
