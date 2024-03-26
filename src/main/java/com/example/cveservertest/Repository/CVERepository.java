package com.example.cveservertest.Repository;

import com.example.cveservertest.Entity.Cve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVERepository extends JpaRepository<Cve, Long> {
    Cve findCveByCveId(String cveId);
}
