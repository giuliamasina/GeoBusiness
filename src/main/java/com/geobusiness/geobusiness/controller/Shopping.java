package com.geobusiness.geobusiness.controller;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.geobusiness.geobusiness.model.dao.CompratoreDAO;
import com.geobusiness.geobusiness.model.dao.VenditoreDAO;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Venditore;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import com.geobusiness.geobusiness.services.config.Configuration;
import com.geobusiness.geobusiness.services.logservice.LogService;
import com.geobusiness.geobusiness.model.mo.Utente;
import com.geobusiness.geobusiness.model.dao.UtenteDAO;
import com.geobusiness.geobusiness.model.dao.DAOFactory;

import com.geobusiness.geobusiness.model.dao.ArticoloDAO;
import com.geobusiness.geobusiness.model.dao.ArticoloAstaDAO;
import com.geobusiness.geobusiness.model.dao.ArticoloVenditaDAO;
import com.geobusiness.geobusiness.model.mo.Articolo;
import com.geobusiness.geobusiness.model.mo.ArticoloAsta;
import com.geobusiness.geobusiness.model.mo.ArticoloVendita;
public class Shopping {

    private Shopping(){

    }

    public static void view(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

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

            commonview(daoFactory,sessionDAOFactory,request);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "Shopping/view");

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

    public static void commonview(DAOFactory daoFactory, DAOFactory sessionDAOFactory, HttpServletRequest request){
        List<Articolo> articoli = null;
        List<ArticoloVendita> articolivendita;
        List<ArticoloAsta> articoliasta;

        UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
        Utente loggedUser = sessionUserDAO.findLoggedUtente();

        UtenteDAO userDAO = daoFactory.getUtenteDAO();
        Utente user = userDAO.findByUtenteId(loggedUser.getId());

        ArticoloDAO articoloDAO = daoFactory.getArticoloDAO();
        ArticoloVenditaDAO articoloVenditaDAO = daoFactory.getArticoloVenditaDAO();
        ArticoloAstaDAO articoloAstaDAO = daoFactory.getArticoloAstaDAO();

        if(request.getParameter("categoria").equals("nessuna")){
            //articoli = articoloDAO.findByCategoria("*");
            articolivendita = articoloVenditaDAO.findByCategoria("*");
            articoliasta = articoloAstaDAO.findByCategoria("*");
        }else{
            articolivendita = articoloVenditaDAO.findByCategoria(request.getParameter("categoria"));
            articoliasta = articoloAstaDAO.findByCategoria(request.getParameter("categoria"));
        }

        articoli.addAll(articolivendita);
        articoli.addAll(articoliasta);



        request.setAttribute("articoli",articoli);
        request.setAttribute("articolivendita",articolivendita);
        request.setAttribute("articoliasta",articoliasta);
    }
}
