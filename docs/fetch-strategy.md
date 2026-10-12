# Stratégie de chargement (Fetch) et Cascade - AutoLoc

| Association | Fetch | Cascade | Justification |
| :--- | :--- | :--- | :--- |
| **Contrat → Paiement** | LAZY | ALL + orphanRemoval | Un paiement n'existe que rattaché à son contrat ; supprimer le contrat supprime ses paiements et retirer un paiement de la liste le supprime (orphelin)[cite: 6]. |
| **Agence → Vehicule** | LAZY | Aucune | Un véhicule survit à la suppression de son agence[cite: 5]. |
| **Agence → Employe** | LAZY | Aucune | Un employé peut être rattaché ou réaffecté sans supprimer l'agence[cite: 5]. |
| **Vehicule → Equipement** | LAZY | Aucune | Les équipements sont partagés entre plusieurs véhicules (ManyToMany)[cite: 5]. |
| **Client → Reservation** | LAZY | PERSIST | Enregistrer un client peut propager l'enregistrement de ses nouvelles réservations[cite: 5]. |
| **Reservation → Vehicule** | LAZY | Aucune | La réservation pointe vers un véhicule existant, pas de cascade de suppression[cite: 5]. |
| **Reservation → Contrat** | LAZY | ALL | Le contrat est lié à une réservation unique, le cycle de vie est géré ensemble[cite: 5]. |
| **Vehicule → Maintenance** | LAZY | PERSIST | Enregistrer un véhicule peut persister ses fiches de maintenance initiales[cite: 5]. |
```[cite: 5, 6]