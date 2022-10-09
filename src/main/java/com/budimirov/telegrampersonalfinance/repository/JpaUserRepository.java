package com.budimirov.telegrampersonalfinance.repository;

import com.budimirov.telegrampersonalfinance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface JpaUserRepository extends JpaRepository<User, Long> {
    Optional<User> getByChatId(Long chatId);
}
