package de.othr.easytownhall.models.response;

public class RolesResponse {
    private boolean isAdmin;
    private boolean isServiceWorker;

    public RolesResponse(boolean isAdmin, boolean isServiceWorker) {
        this.isAdmin = isAdmin;
        this.isServiceWorker = isServiceWorker;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isServiceWorker() {
        return isServiceWorker;
    }

    public void setServiceWorker(boolean serviceWorker) {
        isServiceWorker = serviceWorker;
    }
}

