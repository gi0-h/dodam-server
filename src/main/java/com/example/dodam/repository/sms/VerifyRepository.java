package com.example.dodam.repository.sms;

import com.example.dodam.domain.sms.Verification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyRepository extends CrudRepository<Verification, String > {

}
