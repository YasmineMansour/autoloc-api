package tn.esprit.autolocapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autolocapi.domain.Agence;

@Repository
public interface IAgenceRepository extends JpaRepository<Agence, Long> {
}