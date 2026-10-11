package org.iut.mastermind.domain.tirage;

public class ServiceTirageMot {
    private final MotsRepository repository;
    private final ServiceNombreAleatoire nbAleatoire;

    public ServiceTirageMot(MotsRepository repository, ServiceNombreAleatoire nbAleatoire) {
        this.repository = repository;
        this.nbAleatoire = nbAleatoire;
    }

    public String tirageMotAleatoire() {
        int nbMots = repository.nbMaxMots();
        int numero = nbAleatoire.next(nbMots);
        return repository.getMotByIndex(numero);
    }
}
