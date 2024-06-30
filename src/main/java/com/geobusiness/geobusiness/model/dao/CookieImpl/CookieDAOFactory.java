package com.geobusiness.geobusiness.model.dao.CookieImpl;

import com.geobusiness.geobusiness.model.dao.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
public class CookieDAOFactory extends DAOFactory {

    private Map factoryParameters;

    private HttpServletRequest request;
    private HttpServletResponse response;

    public CookieDAOFactory(Map factoryParameters) {
        this.factoryParameters=factoryParameters;
    }
    @Override
    public void beginTransaction() {

    }

    @Override
    public void commitTransaction() {

    }

    @Override
    public void rollbackTransaction() {

    }

    @Override
    public void closeTransaction() {

    }

    // DECIDERE SE USARE COOKI OPPURE NO
    @Override
    public UtenteDAO getUtenteDAO() {
        return null;
    }

    @Override
    public VenditoreDAO getVenditoreDAO() {
        return null;
    }

    @Override
    public CompratoreDAO getCompratoreDAO() {
        return null;
    }

    @Override
    public ArticoloDAO getArticoloDAO() {
        return null;
    }

    @Override
    public ArticoloVenditaDAO getArticoloVenditaDAO() {
        return null;
    }

    @Override
    public ArticoloAstaDAO getArticoloAstaDAO() {
        return null;
    }

    @Override
    public RecensioneDAO getRecensioneDAO() {
        return null;
    }
}
