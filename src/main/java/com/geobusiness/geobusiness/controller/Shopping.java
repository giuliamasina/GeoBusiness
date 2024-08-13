package com.geobusiness.geobusiness.controller;

import java.sql.Date;
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

        List<Articolo> articoli = new ArrayList<>();
        List<ArticoloVendita> articolivendita = null;
        List<ArticoloAsta> articoliasta = null;

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

            articolivendita = articoloVenditaDAO.findByCategoria("*");
            articoliasta = articoloAstaDAO.findByCategoria("*");

            articoli.addAll(articolivendita);
            articoli.addAll(articoliasta);
            //commonview(daoFactory,sessionDAOFactory,request);   // potrei usarla solo se uso il filtro

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

    public static void itemview(HttpServletRequest request, HttpServletResponse response){
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
            request.setAttribute("viewUrl", "Shopping/item");
            request.setAttribute("articolovendita",articolovendita);
            request.setAttribute("venditore",venditore);
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

    public static void auctionview(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        Integer Idarticolo = null;
        ArticoloAsta articoloasta = null;
        Venditore venditore = null;
        List<Float> offerte = new ArrayList<>();
        List<Date> date_offerte = new ArrayList<>();
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

            Idarticolo = Integer.parseInt(request.getParameter("articoloasta"));
            ArticoloAstaDAO articoloAstaDAO = daoFactory.getArticoloAstaDAO();
            articoloasta = articoloAstaDAO.findByArticoloId(Idarticolo);
            venditore = articoloAstaDAO.findVenditoreById(Idarticolo);
            offerte = articoloAstaDAO.getOffersById(Idarticolo);
            date_offerte = articoloAstaDAO.getDateOffersById(Idarticolo);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "Shopping/auction");
            request.setAttribute("articoloasta",articoloasta);
            request.setAttribute("venditore",venditore);
            request.setAttribute("offerte", offerte);
            request.setAttribute("date_offerte", date_offerte);

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

    public static void buyview(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        Integer Idarticolo = null;
        ArticoloVendita articolovendita = null;
        Venditore venditore = null;
        String consegna = null;
        Compratore compratore = new Compratore();
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
            CompratoreDAO compratoreDAO = daoFactory.getCompratoreDAO();
            compratore = compratoreDAO.findByUsername(loggedUser.getUsername());   // trovo info compratore loggato usando username del loggeduser
            consegna = compratore.getIndirizzo_consegna();


            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "Shopping/buy");
            request.setAttribute("articolovendita",articolovendita);
            request.setAttribute("venditore",venditore);
            request.setAttribute("consegna", consegna);

        }catch(Exception e){
            logger.log(Level.SEVERE, "Controller Error", e);
            try {
                if (sessionDAOFactory != null) sessionDAOFactory.rollbackTransaction();
            } catch (Throwable t) {
            }
            throw new RuntimeException(e);
        }finally {
            try {
                if (sessionDAOFactory != null) sessionDAOFactory.closeTransaction();
            } catch (Throwable t) {
            }
        }

    }

    public static void offerview(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        Integer Idarticolo = null;
        ArticoloAsta articoloasta = null;
        Venditore venditore = null;
        List<Float> offerte = new ArrayList<>();
        List<Date> date_offerte = new ArrayList<>();
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

            Idarticolo = Integer.parseInt(request.getParameter("articoloasta"));
            ArticoloAstaDAO articoloAstaDAO = daoFactory.getArticoloAstaDAO();
            articoloasta = articoloAstaDAO.findByArticoloId(Idarticolo);
            venditore = articoloAstaDAO.findVenditoreById(Idarticolo);
            offerte = articoloAstaDAO.getOffersById(Idarticolo);
            date_offerte = articoloAstaDAO.getDateOffersById(Idarticolo);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "Shopping/offer");
            request.setAttribute("articoloasta",articoloasta);
            request.setAttribute("venditore",venditore);
            request.setAttribute("offerte", offerte);
            request.setAttribute("date_offerte", date_offerte);

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
    public static void filterview(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;

        List<Articolo> articoli = new ArrayList<>();
        List<ArticoloVendita> articolivendita = null;
        List<ArticoloAsta> articoliasta = null;

        List<ArticoloVendita> articolivenditafinal;
        List<ArticoloAsta> articoliastafinal;

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

            // filtro per categoria
            articolivendita = articoloVenditaDAO.findByCategoria(request.getParameter("categoria"));
            articoliasta = articoloAstaDAO.findByCategoria(request.getParameter("categoria"));

            // filtro per prezzo
            if(request.getParameter("da").isEmpty() && request.getParameter("a").isEmpty()){
                articolivenditafinal = articolivendita;
                articoliastafinal = articoliasta;
            }else {
                articolivenditafinal = priceRange(articolivendita, request);
                articoliastafinal = maxOfferRange(daoFactory, articoliasta, request);
            }

            // filtro per tipo articolo
            if(request.getParameter("tipoarticolo") == null){
                articoli.addAll(articolivenditafinal);
                articoli.addAll(articoliastafinal);
            } else if(request.getParameter("tipoarticolo").equals("fisso")){
                articoliastafinal = null;
                articoli.addAll(articolivenditafinal);
            }else if( request.getParameter("tipoarticolo").equals("asta")){
                articolivenditafinal = null;
                articoli.addAll(articoliastafinal);
            }

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "Shopping/view");
            request.setAttribute("articoli",articoli);
            request.setAttribute("articolivendita",articolivenditafinal);
            request.setAttribute("articoliasta",articoliastafinal);

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
        //Utente user = userDAO.findByUtenteId(loggedUser.getId());    // da' eccezione


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

    public static List<ArticoloVendita> priceRange(List<ArticoloVendita> articolivendita, HttpServletRequest request){
        List<ArticoloVendita> articolivenditafinal = null;

        Float da=0f, a=13000.0f;

        if(!request.getParameter("da").isEmpty() && !request.getParameter("da").isEmpty()){
            da = Float.parseFloat(request.getParameter("da"));
            a = Float.parseFloat(request.getParameter("a"));
        }else if(!request.getParameter("da").isEmpty() && request.getParameter("da").isEmpty()){
            da = Float.parseFloat(request.getParameter("da"));
            a = 13000.0f;
        }else if(request.getParameter("da").isEmpty() && !request.getParameter("da").isEmpty()){
            da = 0.0f;
            a = Float.parseFloat(request.getParameter("a"));
        }

        int i;
        for(i=0;i<articolivendita.size();i++){
            if(articolivendita.get(i).getPrezzo() >= da && articolivendita.get(i).getPrezzo() <= a){
                articolivenditafinal.add(articolivendita.get(i));
            }
        }

        return articolivenditafinal;
    }
    public static List<ArticoloAsta> maxOfferRange(DAOFactory daoFactory, List<ArticoloAsta> articoliasta, HttpServletRequest request){

        List<ArticoloAsta> articoliastada = null;
        List<ArticoloAsta> articoliastaa = null;
        ArticoloAstaDAO articoloAstaDAO = daoFactory.getArticoloAstaDAO();
        int i, j;
        int id;
        Float da, a;
        List<Float> offerte;
        List<Float> offerteda = null;
        List<Float> offertea;
        List<Float> offertefinal;
        List<Date> date;
        List<Compratore> compratori;
        for(i=0;i<articoliasta.size();i++){
            id = articoliasta.get(i).getId();
            offerte = articoloAstaDAO.getOffersById(id);
            //date = articoloAstaDAO.getDateOffersById(id);
            //compratori = articoloAstaDAO.getOfferingCompratoriById(id);

            /*if(!(request.getParameter("da") == null)){
                da = Integer.parseInt(request.getParameter("da"));
                for(j=0; j<offerte.size(); i++){
                    if(offerte.get(j) >= da){
                        offerteda.add(offerte.get(j));
                    }
                    else{
                        date.remove(j);
                        compratori.remove(j);
                    }
                }*/

            if(!(request.getParameter("da").isEmpty())){
                da = Float.parseFloat(request.getParameter("da"));
                if(offerte.get(0) >= da) articoliastada.add(articoliasta.get(i));
            }else{
                articoliastada.add(articoliasta.get(i));
            }
            if(!(request.getParameter("a").isEmpty())){
                da = Float.parseFloat(request.getParameter("a"));
                if(offerte.get(0) >= da) articoliastaa.add(articoliasta.get(i));
            }else{
                articoliastaa.add(articoliasta.get(i));
            }
        }

        articoliastada.retainAll(articoliastaa);
        return articoliastada;
    }
}
