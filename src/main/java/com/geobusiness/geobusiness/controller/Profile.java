package com.geobusiness.geobusiness.controller;

import com.geobusiness.geobusiness.model.dao.*;
import com.geobusiness.geobusiness.model.mo.*;
import com.geobusiness.geobusiness.services.config.Configuration;
import com.geobusiness.geobusiness.services.logservice.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Profile {

    private Profile(){

    }

    public static void view(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        List<Articolo> articoli = new ArrayList<>();
        List<ArticoloVendita> articolivendita = new ArrayList<>();
        List<ArticoloAsta> articoliasta = null;
        Integer utenteId;
        Compratore compratore = null;
        Venditore venditore = null;

        Logger logger = LogService.getApplicationLogger();
        try{

            Map sessionFactoryParameters=new HashMap<String,Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            if(sessionUserDAO!=null){
                loggedUser = sessionUserDAO.findLoggedUtente();
            }
            else{
                Utente utente = new Utente();
                utente.setUsername("Guest");
                loggedUser=null;
            }
            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            ArticoloVenditaDAO articoloVenditaDAO = daoFactory.getArticoloVenditaDAO();
            ArticoloAstaDAO articoloAstaDAO = daoFactory.getArticoloAstaDAO();
            CompratoreDAO compratoreDAO = daoFactory.getCompratoreDAO();
            VenditoreDAO venditoreDAO = daoFactory.getVenditoreDAO();
            utenteId = Integer.parseInt(request.getParameter("utenteId"));
            compratore = compratoreDAO.findByUtenteId(utenteId);
            venditore = venditoreDAO.findByUtenteId(utenteId);

            if(compratore != null && venditore == null){
                articolivendita = compratoreDAO.findArticoliComprati(utenteId);
                articoliasta = compratoreDAO.findOfferte(utenteId);
                request.setAttribute("viewUrl", "Profile/viewCompratore");
            }
            if(venditore != null && compratore == null){
                articolivendita = venditoreDAO.findArticoliInVendita(utenteId);
                articoliasta = venditoreDAO.findArticoliInAsta(utenteId);
                request.setAttribute("viewUrl", "Profile/viewVenditore");
            }

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "Shopping/view");
            request.setAttribute("articoli",articoli);
            request.setAttribute("articolivendita",articolivendita);
            request.setAttribute("articoliasta",articoliasta);

        }catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            try {
                if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);

        } finally {
            try {
                if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }
    }
}
