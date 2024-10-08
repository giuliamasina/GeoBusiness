package com.geobusiness.geobusiness.model.dao.CookieImpl;

import com.geobusiness.geobusiness.model.dao.CompratoreDAO;
import com.geobusiness.geobusiness.model.dao.DAOFactory;
import com.geobusiness.geobusiness.model.dao.UtenteDAO;
import com.geobusiness.geobusiness.model.dao.VenditoreDAO;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Utente;
import com.geobusiness.geobusiness.model.mo.Venditore;
import com.geobusiness.geobusiness.services.config.Configuration;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UtenteDAOCookieImpl implements UtenteDAO {

    HttpServletRequest request;
    HttpServletResponse response;

    public UtenteDAOCookieImpl(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
    @Override
    public Utente create(
            // ho tolto l'id
            String Username,
            String Email,
            String Paswword
    ) {
        Utente loggedUser = new Utente();
        DAOFactory daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
        daoFactory.beginTransaction();
        /*UtenteDAO utenteDAO = daoFactory.getUtenteDAO();
        Utente utente = null;
        utente = utenteDAO.findByUsername(Username);
        System.out.println("username utente: "+ "  " + Username);
        System.out.println("id utente: "+ "  " + utente.getId()); */
        CompratoreDAO compratoreDAO = daoFactory.getCompratoreDAO();
        VenditoreDAO venditoreDAO = daoFactory.getVenditoreDAO();
        Compratore compratore = null;
        Venditore venditore = null;
        venditore = venditoreDAO.findByUsername(Username);
        compratore = compratoreDAO.findByUsername(Username);
        daoFactory.commitTransaction();

        if(venditore != null && compratore == null){
            loggedUser.setId(venditore.getId());
        }else if(compratore != null && venditore == null){
            loggedUser.setId(compratore.getId());
        }
        //loggedUser.setId(utente.getId());
        System.out.println("l'id dell'utente: "+loggedUser.getId());
        loggedUser.setUsername(Username);
        loggedUser.setEmail(Email);

        Cookie cookie;
        cookie = new Cookie("loggedUser", encode(loggedUser));
        cookie.setPath("/");
        response.addCookie(cookie);

        return loggedUser;
    }

    @Override
    public void update(Utente loggedUser) {
        Cookie cookie;
        cookie = new Cookie("loggedUser", encode(loggedUser));
        cookie.setPath("/");
        response.addCookie(cookie);

    }

    @Override
    public void delete(Integer utente_id) {
        Cookie cookie;
        cookie = new Cookie("loggedUser", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public Utente findLoggedUtente() {
        Cookie[] cookies = request.getCookies();
        Utente loggedUser = null;

        if (cookies != null) {
            for (int i = 0; i < cookies.length && loggedUser == null; i++) {
                if (cookies[i].getName().equals("loggedUser")) {
                    loggedUser = decode(cookies[i].getValue());
                }
            }
        }

        return loggedUser;
    }

    @Override
    public Utente findByUtenteId(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Utente findByUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Utente findByEmail(String Email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String encode(Utente loggedUser) {

        String encodedLoggedUser;
        encodedLoggedUser = loggedUser.getId() + "#" + loggedUser.getUsername() + "#" + loggedUser.getEmail();
        return encodedLoggedUser;  // ho tolto getId perchè non so come recuperarlo

    }

    private Utente decode(String encodedLoggedUser) {

        Utente loggedUser = new Utente();

        String[] values = encodedLoggedUser.split("#");

        loggedUser.setId(Integer.parseInt(values[0]));
        //loggedUser.setId(101);
        loggedUser.setUsername(values[1]);
        loggedUser.setEmail(values[2]);

        return loggedUser;

    }
}
