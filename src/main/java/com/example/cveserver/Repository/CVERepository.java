package com.example.cveserver.Repository;

import com.example.cveserver.Entity.Cve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CVERepository extends JpaRepository<Cve, Long> {
    List<Cve> findCvesByCveIdIn(List<String> cveIds);
}
