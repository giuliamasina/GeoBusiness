package com.geobusiness.geobusiness.controller;

import java.util.HashMap;
import java.util.Map;
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

public class Home {

    private Home(){

    }

    public static void view(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        Venditore venditore = null;

        Logger logger = LogService.getApplicationLogger();

        try {

            Map sessionFactoryParameters=new HashMap<String,Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            if(sessionUserDAO!=null){
                loggedUser = sessionUserDAO.findLoggedUtente();
                if(loggedUser != null) {
                    daoFactory.beginTransaction();
                    VenditoreDAO venditoreDAO = daoFactory.getVenditoreDAO();
                    venditore = venditoreDAO.findByUsername(loggedUser.getUsername());
                    daoFactory.commitTransaction();
                }
            }
            else{
                Utente utente = new Utente();
                utente.setUsername("Guest");
                loggedUser=null;
            }

            sessionDAOFactory.commitTransaction();

            if(venditore!=null){
                request.setAttribute("isVenditore", true);
            } else if (venditore == null) {
                request.setAttribute("isVenditore", false);
            }
            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "Home/view");

        } catch (Exception e) {
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

    public static void viewsign(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        Utente loggedUser;

        Logger logger = LogService.getApplicationLogger();

        try{

            Map sessionFactoryParameters=new HashMap<String,Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);

            loggedUser=null;

            request.setAttribute("loggedOn", false);
            request.setAttribute("loggedUser", null);
            request.setAttribute("viewUrl", "Home/sign");


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

    public static void viewlogin(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        Utente loggedUser;

        Logger logger = LogService.getApplicationLogger();

        try{

            Map sessionFactoryParameters=new HashMap<String,Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);

            loggedUser=null;

            request.setAttribute("loggedOn", false);
            request.setAttribute("loggedUser", null);
            request.setAttribute("viewUrl", "Home/login");


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

    public static void logon(HttpServletRequest request, HttpServletResponse response){
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

            UtenteDAO sessionUtenteDAO = sessionDAOFactory.getUtenteDAO();
            //loggedUser = sessionUtenteDAO.findLoggedUtente();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            String username = request.getParameter("username");
            String password = request.getParameter("password");

            UtenteDAO userDAO = daoFactory.getUtenteDAO();
            VenditoreDAO venditoreDAO = daoFactory.getVenditoreDAO();
            Utente utente = userDAO.findByUsername(username);
            Venditore venditore = venditoreDAO.findByUtenteId(utente.getId());

            if (utente == null || !utente.getPassword().equals(password)) {
                sessionUtenteDAO.delete(null);
                applicationMessage = "Username e password errati!";
                loggedUser=null;
            } else {
                loggedUser = sessionUtenteDAO.create(utente.getUsername(), utente.getEmail(), null); // ho tolto l'id perchè non so come ricavarlo
            }

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            // devo farlo anche per il signin
            if(venditore!=null){
                request.setAttribute("isVenditore", true);
            } else if (venditore == null) {
                request.setAttribute("isVenditore", false);
            }
            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "Home/view");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            try {
                if (daoFactory != null) daoFactory.rollbackTransaction();
                if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);

        } finally {
            try {
                if (daoFactory != null) daoFactory.closeTransaction();
                if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;

        Logger logger = LogService.getApplicationLogger();

        try {

            Map sessionFactoryParameters=new HashMap<String,Object>();
            sessionFactoryParameters.put("request",request);
            sessionFactoryParameters.put("response",response);
            sessionDAOFactory = DAOFactory.getDAOFactory(Configuration.COOKIE_IMPL,sessionFactoryParameters);
            sessionDAOFactory.beginTransaction();

            UtenteDAO sessionUserDAO = sessionDAOFactory.getUtenteDAO();
            sessionUserDAO.delete(null);

            sessionDAOFactory.commitTransaction();

            request.setAttribute("isVenditore",false);
            request.setAttribute("loggedOn",false);
            request.setAttribute("loggedUser", null);
            request.setAttribute("viewUrl", "Home/view");

        } catch (Exception e) {
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

    public static void signin(HttpServletRequest request, HttpServletResponse response){
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

            UtenteDAO sessionUtenteDAO = sessionDAOFactory.getUtenteDAO();  // utente loggato
            loggedUser = sessionUtenteDAO.findLoggedUtente();

            daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
            daoFactory.beginTransaction();

            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String ruolo = request.getParameter("ruolo");
            String indirizzo = request.getParameter("indirizzo");  // ANCORA DA METTERE NEL FORM

            UtenteDAO userDAO = daoFactory.getUtenteDAO();
            Utente utente = userDAO.findByUsername(username);

            if(utente==null) {

                if (ruolo.equals("vendere")) {
                    VenditoreDAO utenteDAO = daoFactory.getVenditoreDAO();
                    Venditore venditore = utenteDAO.create(username, email, password, indirizzo);
                    loggedUser = sessionUtenteDAO.create(venditore.getUsername(), venditore.getEmail(), null); // ho tolto l'id perchè non so come ricavarlo

                }else if(ruolo.equals("comprare")){
                    CompratoreDAO utenteDAO = daoFactory.getCompratoreDAO();
                    Compratore compratore = utenteDAO.create(username, email, password, indirizzo);
                    loggedUser = sessionUtenteDAO.create(compratore.getUsername(), compratore.getEmail(), null); // ho tolto l'id perchè non so come ricavarlo
                }

            }
            else{
                sessionUtenteDAO.delete(null);
                applicationMessage = "Username già in utilizzo";
                loggedUser=null;
            }

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "Home/view");

        }catch(Exception e){
            logger.log(Level.SEVERE, "Controller Error", e);
            try {
                if (daoFactory != null) daoFactory.rollbackTransaction();
                if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);

        } finally {
            try {
                if (daoFactory != null) daoFactory.closeTransaction();
                if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }
    }
}
