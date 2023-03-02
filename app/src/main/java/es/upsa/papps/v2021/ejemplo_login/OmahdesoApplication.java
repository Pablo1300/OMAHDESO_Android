package es.upsa.papps.v2021.ejemplo_login;

import android.app.Application;

public class OmahdesoApplication extends Application {

    private Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();

        this.repository = new Repository();
    }

    public Repository getRepository() {
        return repository;
    }
}
