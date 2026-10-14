package org.iut.mastermind.domain.partie;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.iut.mastermind.domain.Mastermind;
import org.iut.mastermind.domain.tirage.ServiceTirageMot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test si une nouvelle partie:")
public class TestNouvellePartie {
    private static final Joueur JOUEUR = new Joueur("Alice");
    private static final String MOT_CORRECT = "SOLID";
    @Mock
    private PartieRepository partieRepository;
    @Mock
    private ServiceTirageMot serviceTirageMot;
    @InjectMocks
    private Mastermind mastermind;

    @Test
    @DisplayName("est correctement initialisée")
    void doitInitialiserNouvellePartie() {
        givenMotSelectionne(MOT_CORRECT);
        mastermind.nouvellePartie(JOUEUR);
        Partie partie = getPartieEnregistree();
        assertThat(partie.getMot()).isEqualTo(MOT_CORRECT);
        assertThat(partie.getNbEssais()).isZero();
        assertThat(partie.getJoueur()).isSameAs(JOUEUR);
    }

    @Test
    @DisplayName("ne peut pas redémarrer")
    void nePeutPasRedemarrerUnePartie() {
        when(partieRepository
                .getPartieEnregistree(eq(JOUEUR)))
                .thenReturn(Optional.of(Partie.nouvelle(JOUEUR, MOT_CORRECT)));
        var succes = mastermind.nouvellePartie(JOUEUR);
        assertThat(succes).isFalse();
    }

    private void givenMotSelectionne(String mot) {
        when(serviceTirageMot.tirageMotAleatoire()).thenReturn(mot);
    }

    private Partie getPartieEnregistree() {
        var argPartie = ArgumentCaptor.forClass(Partie.class);
        verify(partieRepository)
                .create(argPartie.capture());
        var partie = argPartie.getValue();
        return partie;
    }
}
