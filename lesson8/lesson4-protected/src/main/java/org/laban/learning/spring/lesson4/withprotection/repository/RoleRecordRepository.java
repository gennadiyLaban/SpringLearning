package org.laban.learning.spring.lesson4.withprotection.repository;

import org.laban.learning.spring.lesson4.withprotection.model.RoleRecord;
import org.laban.learning.spring.lesson4.withprotection.model.RoleType;
import org.laban.learning.spring.lesson4.withprotection.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public interface RoleRecordRepository extends JpaRepository<RoleRecord, Long> {
    default void setUserRoles(User user, Set<RoleType> roleTypes) {
        saveAll(roleTypes.stream()
                .map(roleType -> RoleRecord.builder()
                        .roleType(roleType)
                        .user(user)
                        .build())
                .collect(Collectors.toSet()));
    }
}
