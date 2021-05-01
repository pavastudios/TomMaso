package com.pavastudios.TomMaso.model;

public class Chat {
    private int idChat;
    private Utente utente1,utente2;

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public Utente getUtente1() {
        return utente1;
    }

    public void setUtente1(Utente utente1) {
        this.utente1 = utente1;
    }

    public Utente getUtente2() {
        return utente2;
    }

    public void setUtente2(Utente utente2) {
        this.utente2 = utente2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return idChat == chat.idChat;
    }

}
