package com.geobusiness.geobusiness.model.mo;

public class Articolo {

    private Integer id;
    private String nome;
    private String categoria;
    private Integer status;
    private String immagine;
    private String description;
    private boolean deleted;

    // getters and setters
    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public String getImmagine() { return immagine; }

    public void setImmagine(String immagine) { this.immagine = immagine; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public boolean isDeleted() { return deleted; }

    public void setDeleted(boolean deleted) { this.deleted = deleted; }

}
