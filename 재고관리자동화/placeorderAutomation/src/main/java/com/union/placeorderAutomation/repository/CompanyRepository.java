package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
