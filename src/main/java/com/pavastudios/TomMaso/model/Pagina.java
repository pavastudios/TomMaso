package com.pavastudios.TomMaso.model;

public class Pagina {
    private int id_pagina;
    private Blog blog;
    private String url;

    public int getId_pagina() {
        return id_pagina;
    }

    public void setId_pagina(int id_pagina) {
        this.id_pagina = id_pagina;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
