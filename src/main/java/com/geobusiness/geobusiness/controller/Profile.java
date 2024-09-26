package com.geobusiness.geobusiness.controller;

import com.geobusiness.geobusiness.model.dao.*;
import com.geobusiness.geobusiness.model.mo.*;
import com.geobusiness.geobusiness.services.config.Configuration;
import com.geobusiness.geobusiness.services.logservice.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        List<ArticoloAsta> articoliasta = new ArrayList<>();
        List<Recensione> recensioni = new ArrayList<>();
        String username;
        Compratore compratore = null;
        Venditore venditore = null;
        List<Float> offerte = new ArrayList<>();

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
            RecensioneDAO recensioneDAO = daoFactory.getRecensioneDAO();
            VenditoreDAO venditoreDAO = daoFactory.getVenditoreDAO();
            username = request.getParameter("username");
            compratore = compratoreDAO.findByUsername(username);
            venditore = venditoreDAO.findByUsername(username);

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            if(compratore != null && venditore == null){
                articolivendita = compratoreDAO.findArticoliComprati(compratore.getId());
                articoliasta = compratoreDAO.findOfferte(compratore.getId());
                recensioni = recensioneDAO.findByCompratoreId(compratore.getId());
                request.setAttribute("compratore",compratore);
                request.setAttribute("recensioni",recensioni);
                request.setAttribute("viewUrl", "Profile/viewCompratore");
            } else if(venditore != null && compratore == null){
                articolivendita = venditoreDAO.findArticoliInVendita(venditore.getId());
                articoliasta = venditoreDAO.findArticoliInAsta(venditore.getId());
                recensioni = recensioneDAO.findByVenditoreId(venditore.getId());
                request.setAttribute("venditore", venditore);
                request.setAttribute("recensioni",recensioni);
                request.setAttribute("viewUrl", "Profile/viewVenditore");
            } else if(venditore == null && compratore == null){
                request.setAttribute("viewUrl", "Home/view");
            }
            else if(venditore != null && compratore != null){
                request.setAttribute("viewUrl", "Shopping/view");
            }

            int i;
            int result = 0;
            for(i=0;i<articoliasta.size();i++){
                result = articoliasta.get(i).getData_scadenza().compareTo(Timestamp.valueOf(LocalDateTime.now()));
                if(result < 0 && articoliasta.get(i).getStatus() != 1){
                    offerte = articoloAstaDAO.getOffersById(articoliasta.get(i).getId());
                    if(!offerte.isEmpty()){
                        compratoreDAO = daoFactory.getCompratoreDAO();
                        compratoreDAO.vinciAsta(articoliasta.get(i).getId(), Timestamp.valueOf(LocalDateTime.now()));
                        articoliasta.remove(articoliasta.get(i));
                    }
                }
                result = 0;
            }
            //request.setAttribute("viewUrl", "Shopping/view");
            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();


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

    public static void viewVendPerComp(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        List<Articolo> articoli = new ArrayList<>();
        List<ArticoloVendita> articolivendita = new ArrayList<>();
        List<ArticoloAsta> articoliasta = new ArrayList<>();
        List<Recensione> recensioni = new ArrayList<>();
        Integer id_c;
        Integer id_v;
        Compratore compratore = null;
        Venditore venditore = null;
        Recensione recensione = null;
        List<Float> offerte = new ArrayList<>();
        boolean has_bought;

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

            CompratoreDAO compratoreDAO = daoFactory.getCompratoreDAO();
            RecensioneDAO recensioneDAO = daoFactory.getRecensioneDAO();
            VenditoreDAO venditoreDAO = daoFactory.getVenditoreDAO();
            // devo cercare di passare sia l'id del compratore sia quello del venditore
            id_v = Integer.parseInt(request.getParameter("Id_venditore"));
            id_c = Integer.parseInt(request.getParameter("Id_compratore"));
            compratore = compratoreDAO.findByUtenteId(id_c);
            venditore = venditoreDAO.findByUtenteId(id_v);

            articolivendita = venditoreDAO.findArticoliInVendita(venditore.getId());
            articoliasta = venditoreDAO.findArticoliInAsta(venditore.getId());
            recensioni = recensioneDAO.findByVenditoreId(venditore.getId());
            has_bought = compratoreDAO.hacompratoda(compratore.getId(),venditore.getId());
            recensione = recensioneDAO.checkIfAlreadyExists(compratore.getId(),venditore.getId());
            ArticoloAstaDAO articoloAstaDAO = daoFactory.getArticoloAstaDAO();

            int i;
            int result = 0;
            for(i=0;i<articoliasta.size();i++){
                result = articoliasta.get(i).getData_scadenza().compareTo(Timestamp.valueOf(LocalDateTime.now()));
                if(result < 0 && articoliasta.get(i).getStatus() != 1){
                    offerte = articoloAstaDAO.getOffersById(articoliasta.get(i).getId());
                    if(!offerte.isEmpty()){
                        compratoreDAO = daoFactory.getCompratoreDAO();
                        compratoreDAO.vinciAsta(articoliasta.get(i).getId(), Timestamp.valueOf(LocalDateTime.now()));
                        articoliasta.remove(articoliasta.get(i));
                    }
                }
                result = 0;
            }

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("venditore", venditore);
            request.setAttribute("recensione",recensione);
            request.setAttribute("articoli",articoli);
            request.setAttribute("articolivendita",articolivendita);
            request.setAttribute("articoliasta",articoliasta);
            request.setAttribute("has_bought",has_bought);
            request.setAttribute("recensioni", recensioni);
            request.setAttribute("viewUrl", "Profile/viewVendPerComp");

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

    public static void viewArtComprato(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        Integer Idarticolo = null;
        ArticoloVendita articolovendita = null;
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

            Idarticolo = Integer.parseInt(request.getParameter("articolovendita"));
            ArticoloVenditaDAO articoloVenditaDAO = daoFactory.getArticoloVenditaDAO();
            articolovendita = articoloVenditaDAO.findByArticoloId(Idarticolo);
            venditore = articoloVenditaDAO.findVenditoreById(Idarticolo);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("articolovendita",articolovendita);
            request.setAttribute("venditore",venditore);
            request.setAttribute("viewUrl", "Profile/viewArtComprato");
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

    public static void pubblicaRecensione(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory=null;
        DAOFactory daoFactory = null;
        Utente loggedUser;

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

            String a = request.getParameter("Id_compratore");
            Integer Id_comp = Integer.parseInt(a);
            a = request.getParameter("Id_venditore");
            Integer Id_vend = Integer.parseInt(a);
            a = request.getParameter("rating");
            Integer valutazione = Integer.parseInt(a);
            String commento = request.getParameter("comment");
            Timestamp data_pubbl = Timestamp.valueOf(LocalDateTime.now());

            RecensioneDAO recensioneDAO = daoFactory.getRecensioneDAO();
            recensioneDAO.fa_recensione(Id_comp,Id_vend,valutazione,commento,data_pubbl);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            //request.setAttribute("Id_compratore",request.getParameter("Id_compratore"));
            //request.setAttribute("Id_venditore",request.getParameter("Id_venditore"));
            viewVendPerComp(request, response);
            // NON È FINITO, PER FAR RIAPRIRE viewVendPerComp DEVO RIPASSARE I PARAMETRI CHE GLI SERVONO E NON SO COME FARE
            //request.setAttribute("viewUrl", "Profile/viewVendPerComp");


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

    public static void deleteItem(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory=null;
        DAOFactory daoFactory = null;
        Utente loggedUser;

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

            ArticoloDAO articoloDAO = daoFactory.getArticoloDAO();
            VenditoreDAO venditoreDAO = daoFactory.getVenditoreDAO();
            RecensioneDAO recensioneDAO = daoFactory.getRecensioneDAO();
            ArticoloAstaDAO articoloAstaDAO = daoFactory.getArticoloAstaDAO();
            articoloDAO.delete(Integer.parseInt(request.getParameter("Id_articolo")));
            Venditore venditore = null;
            List<Float> offerte = new ArrayList<>();
            List<Articolo> articoli = new ArrayList<>();
            List<ArticoloVendita> articolivendita = new ArrayList<>();
            List<ArticoloAsta> articoliasta = new ArrayList<>();
            List<Recensione> recensioni = new ArrayList<>();
            venditore = venditoreDAO.findByUtenteId(Integer.parseInt(request.getParameter("Id_venditore")));
            articolivendita = venditoreDAO.findArticoliInVendita(venditore.getId());
            articoliasta = venditoreDAO.findArticoliInAsta(venditore.getId());
            recensioni = recensioneDAO.findByVenditoreId(venditore.getId());

            int i;
            int result = 0;
            for(i=0;i<articoliasta.size();i++){
                result = articoliasta.get(i).getData_scadenza().compareTo(Timestamp.valueOf(LocalDateTime.now()));
                if(result < 0 && articoliasta.get(i).getStatus() != 1){
                    offerte = articoloAstaDAO.getOffersById(articoliasta.get(i).getId());
                    if(!offerte.isEmpty()){
                        CompratoreDAO compratoreDAO = daoFactory.getCompratoreDAO();
                        compratoreDAO.vinciAsta(articoliasta.get(i).getId(), Timestamp.valueOf(LocalDateTime.now()));
                        articoliasta.remove(articoliasta.get(i));
                    }
                }
                result = 0;
            }

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("venditore",venditore);
            request.setAttribute("articoli",articoli);
            request.setAttribute("articolivendita",articolivendita);
            request.setAttribute("articoliasta",articoliasta);
            request.setAttribute("recensioni",recensioni);
            request.setAttribute("viewUrl", "Profile/viewVenditore");

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

    public static void deleteProfile(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory=null;
        DAOFactory daoFactory = null;
        Utente loggedUser;


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

            Integer id_utente = Integer.parseInt(request.getParameter("ID"));
            VenditoreDAO venditoreDAO = daoFactory.getVenditoreDAO();
            Venditore venditore = null;
            venditore = venditoreDAO.findByUtenteId(id_utente);

            if(venditore == null){
                UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
                utenteDAO.delete(id_utente);
            }
            else{
                venditoreDAO.deleteVend(id_utente);
            }

            sessionUserDAO.delete(null);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("isVenditore",false);
            request.setAttribute("loggedOn",false);
            request.setAttribute("loggedUser", null);
            request.setAttribute("viewUrl", "Home/view");

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
