package com.geobusiness.geobusiness.model.dao.CookieImpl;

import com.geobusiness.geobusiness.model.dao.CompratoreDAO;
import com.geobusiness.geobusiness.model.mo.Compratore;
import com.geobusiness.geobusiness.model.mo.Utente;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class CompratoreDAOCoockiImpl extends UtenteDAOCookieImpl {
    public CompratoreDAOCoockiImpl(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    //HttpServletRequest request;
    //HttpServletResponse response;

    /*public CompratoreDAOCookieImpl(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    } */
    /*@Override
    public Compratore create(
            Integer id,
            String Username,
            String Paswword,
            String Indirizzo_consegna
    ) {
        return null;
    }

    @Override
    public void update(Compratore compratore) {

    }

    @Override
    public void delete(Compratore compratore) {

    }

    @Override
    public Utente create(Integer id, String Username, String Paswword) {
        return null;
    }

    @Override
    public void update(Utente utente) {

    }

    @Override
    public void delete(Utente utente) {

    }

    @Override
    public Utente findByUtenteId(Integer id) {
        return null;
    } */

    /*private String encode(Compratore loggedUser) {

        String encodedLoggedUser;
        encodedLoggedUser = loggedUser.getUserId() + "#" + loggedUser.getFirstname() + "#" + loggedUser.getSurname();
        return encodedLoggedUser;

    }

    private User decode(String encodedLoggedUser) {

        User loggedUser = new User();

        String[] values = encodedLoggedUser.split("#");

        loggedUser.setUserId(Long.parseLong(values[0]));
        loggedUser.setFirstname(values[1]);
        loggedUser.setSurname(values[2]);

        return loggedUser;

    } */
}
