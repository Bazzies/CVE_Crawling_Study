package com.example.cveservertest.Repository;

import com.example.cveservertest.Entity.Cve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CVERepository extends JpaRepository<Cve, Long> {
    List<Cve> findCvesByCveIdIn(List<String> cveIds);
}
