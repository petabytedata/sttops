package com.favoritemedium.sttops.model.repository;

import com.favoritemedium.sttops.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {



}
