package com.geobusiness.geobusiness.controller;

import com.geobusiness.geobusiness.model.dao.*;
import com.geobusiness.geobusiness.model.mo.*;
import com.geobusiness.geobusiness.services.config.Configuration;
import com.geobusiness.geobusiness.services.logservice.LogService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@MultipartConfig
public class Sell {

    private Sell(){

    }

    public static void view(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
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

            VenditoreDAO venditoreDAO = daoFactory.getVenditoreDAO();
            Integer Id_vend = Integer.parseInt(request.getParameter("Id_vend"));
            venditore = venditoreDAO.findByUtenteId(Id_vend);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();

            String type = request.getParameter("type");
            if(type.equals("sell")){
                request.setAttribute("viewUrl", "Sell/sellitem");
            } else if (type.equals("auction")) {
                request.setAttribute("viewUrl", "Sell/sellAuction");
            }
            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
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

    public static void sell(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
        Venditore venditore = null;

        System.out.println("Sell.sell action invoked!");

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

            ArticoloVenditaDAO articolovenditaDAO = daoFactory.getArticoloVenditaDAO();
            Integer Id_vend = Integer.parseInt(request.getParameter("Id_vend"));
            String nome = request.getParameter("nome");
            String categoria = request.getParameter("categoria");
            //String immagine = request.getParameter("immagine");
            String description = request.getParameter("descrizione");
            Float prezzo = Float.parseFloat(request.getParameter("prezzo"));
            Timestamp data_pubbl = Timestamp.valueOf(LocalDateTime.now());

            System.out.println(nome);
            System.out.println(categoria);
            System.out.println(prezzo);

            // Ricevi il file immagine dal form
            Part filePart = request.getPart("immagine"); // Ottieni il file dalla richiesta
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Nome file
            //String uploadPath = request.getServletContext().getRealPath("") + File.separator + "images/uploads"; // Cartella uploads
            String uploadPath = "/home/giuggiu/GeoBusiness/src/main/webapp/images";
            System.out.println(uploadPath);

            // ATTENZIONE se l'immagine viene salvata su uploads ma non viene aggiunto l'articolo sul database allora devo eliminare l'immagine su uploads
            // Salva il file nella cartella uploads
            String filePath = uploadPath + File.separator + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, Paths.get(filePath));
            }

            // Salva solo il percorso relativo nel database
            String relativeFilePath = "images/" + fileName;

            articolovenditaDAO.metteInVendita(Id_vend, nome, categoria, relativeFilePath, description, prezzo, data_pubbl);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();
            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "Home/view");
            request.setAttribute("isVenditore",true);

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

    public static void publicAuction(HttpServletRequest request, HttpServletResponse response){
        DAOFactory sessionDAOFactory= null;
        DAOFactory daoFactory = null;
        Utente loggedUser;
        String applicationMessage = null;
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

            ArticoloAstaDAO articoloastaDAO = daoFactory.getArticoloAstaDAO();
            Integer Id_vend = Integer.parseInt(request.getParameter("Id_vend"));
            String nome = request.getParameter("nome");
            String categoria = request.getParameter("categoria");
            //String immagine = request.getParameter("immagine");
            String description = request.getParameter("description");
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            //java.util.Date utilDate = format.parse(request.getParameter("data_scad"));
            //Date data_scad = new Date(utilDate.getTime());  // non so se sta roba è giusta
            // 1. Definiamo il formato di input: 'YYYY-MM-DDTHH:MM'
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            // 2. Parse la stringa nel tipo LocalDateTime
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("data_scad"), inputFormatter);
            // 3. Convertire LocalDateTime in java.sql.Timestamp
            Timestamp data_scad = Timestamp.valueOf(dateTime);
            Timestamp data_pubbl = Timestamp.valueOf(LocalDateTime.now());

            // Ricevi il file immagine dal form
            Part filePart = request.getPart("immagine"); // Ottieni il file dalla richiesta
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Nome file
            //String uploadPath = request.getServletContext().getRealPath("") + File.separator + "images/uploads"; // Cartella uploads
            String uploadPath = "/home/giuggiu/GeoBusiness/src/main/webapp/images";
            System.out.println(uploadPath);

            // ATTENZIONE se l'immagine viene salvata su uploads ma non viene aggiunto l'articolo sul database allora devo eliminare l'immagine su uploads
            // Salva il file nella cartella uploads
            String filePath = uploadPath + File.separator + fileName;
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, Paths.get(filePath));
            }

            // Salva solo il percorso relativo nel database
            String relativeFilePath = "images/" + fileName;

            articoloastaDAO.metteInAsta(Id_vend, nome, categoria, relativeFilePath, description, data_scad, data_pubbl);

            daoFactory.commitTransaction();
            sessionDAOFactory.commitTransaction();
            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "Home/view");
            request.setAttribute("isVenditore",true);

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
