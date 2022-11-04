package com.lock.test.demo.home;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;


@Repository
public interface HomeRepository extends JpaRepository<Home, Integer> {
    //무방비
    Home findByName(String name);

    //PESSIMISTIC WRITE(비관적 락)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select h from Home h where h.name = :name")
    Home findWithNameForUpdate(@Param("name") String name);

}
