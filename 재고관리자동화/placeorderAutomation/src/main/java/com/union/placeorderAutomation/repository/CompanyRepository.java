package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    Company findCompanyByCompanyName(String companyName);
}
