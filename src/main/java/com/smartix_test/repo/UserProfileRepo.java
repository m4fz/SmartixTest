package com.smartix_test.repo;

import com.smartix_test.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {

}
