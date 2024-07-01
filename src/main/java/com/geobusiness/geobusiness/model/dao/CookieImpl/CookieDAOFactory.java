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

        try {
            this.request=(HttpServletRequest) factoryParameters.get("request");
            this.response=(HttpServletResponse) factoryParameters.get("response");;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void commitTransaction() {
            // si lascia vuoto
    }

    @Override
    public void rollbackTransaction() {
            // si lascia vuoto
    }

    @Override
    public void closeTransaction() {
        // si lascia vuoto
    }

    // DECIDERE SE USARE COOKIE OPPURE NO
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
