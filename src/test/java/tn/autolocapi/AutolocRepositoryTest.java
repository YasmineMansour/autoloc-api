package tn.autolocapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autolocapi.domain.*;
import tn.esprit.autolocapi.repository.IAgenceRepository;
import tn.esprit.autolocapi.repository.IClientRepository;
import tn.esprit.autolocapi.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AutolocRepositoryTest {

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;

    @Test
    void testAgenceRepository() {
        // 1. Créer une agence
        Agence agence = new Agence();
        agence.setNom("Agence Tunis Centre");
        agence.setVille("Tunis");
        agence.setAdresse("Avenue Habib Bourguiba");
        agence.setTelephone("71123456");

        // 2. Enregistrer avec save()
        Agence savedAgence = agenceRepository.save(agence);
        assertNotNull(savedAgence.getIdAgence(), "L'ID de l'agence ne doit pas être null après l'enregistrement");
        System.out.println("Agence enregistrée avec succès : ID = " + savedAgence.getIdAgence());

        // 3. Afficher toutes les agences avec findAll()
        List<Agence> agences = agenceRepository.findAll();
        assertFalse(agences.isEmpty(), "La liste des agences ne doit pas être vide");

        // 4. Récupérer avec findById()
        Optional<Agence> foundAgence = agenceRepository.findById(savedAgence.getIdAgence());
        assertTrue(foundAgence.isPresent(), "L'agence doit être trouvée par son ID");

        // 5. Vérifier l'existence avec existsById()
        assertTrue(agenceRepository.existsById(savedAgence.getIdAgence()));

        // 6. Afficher le nombre d'agences avec count()
        long count = agenceRepository.count();
        assertTrue(count > 0);

        // 7. Supprimer avec deleteById() et vérifier
        agenceRepository.deleteById(savedAgence.getIdAgence());
        assertFalse(agenceRepository.existsById(savedAgence.getIdAgence()), "L'agence doit être supprimée");
    }

    @Test
    void testClientRepository() {
        // 1. Créer deux clients
        Client client1 = new Client();
        client1.setNom("Ben Salah");
        client1.setPrenom("Mohamed");
        client1.setEmail("mohamed.bensalah@esprit.tn");
        client1.setTelephone("98123456");
        client1.setNumPermis("TN123456");
        client1.setDateInscription(LocalDate.now());

        Client client2 = new Client();
        client2.setNom("Mansour");
        client2.setPrenom("Yasmine");
        client2.setEmail("yasmine.mansour@esprit.tn");
        client2.setTelephone("98654321");
        client2.setNumPermis("TN654321");
        client2.setDateInscription(LocalDate.now());

        clientRepository.save(client1);
        clientRepository.save(client2);

        // 2. Afficher la liste avec findAll()
        List<Client> clients = clientRepository.findAll();
        assertFalse(clients.isEmpty());

        // 3. Rechercher avec findById()
        Client found = clientRepository.findById(client1.getIdClient()).orElse(null);
        assertNotNull(found);
        assertEquals("Mohamed", found.getPrenom());

        // 4. Tester existsById()
        assertTrue(clientRepository.existsById(client1.getIdClient()));
        assertFalse(clientRepository.existsById(9999L));

        // 5. Compter
        long count = clientRepository.count();
        assertTrue(count >= 2);
    }

    @Test
    void testVehiculeRepository() {
        // 1. Créer un véhicule
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("123-TU-4567");
        vehicule.setMarque("Peugeot");
        vehicule.setModele("208");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(BigDecimal.valueOf(90.00));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        Vehicule savedVehicule = vehiculeRepository.save(vehicule);
        assertNotNull(savedVehicule.getIdVehicule());

        // 2. Afficher tous les véhicules & count
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        assertFalse(vehicules.isEmpty());
        assertTrue(vehiculeRepository.count() > 0);

        // 3. Modifier une propriété (ex: tarif) puis save()
        savedVehicule.setTarifJournalier(BigDecimal.valueOf(110.00));
        Vehicule updatedVehicule = vehiculeRepository.save(savedVehicule);
        assertEquals(0, BigDecimal.valueOf(110.00).compareTo(updatedVehicule.getTarifJournalier()));

        // 4. Supprimer le véhicule
        vehiculeRepository.deleteById(updatedVehicule.getIdVehicule());
        assertFalse(vehiculeRepository.existsById(updatedVehicule.getIdVehicule()));
    }
}