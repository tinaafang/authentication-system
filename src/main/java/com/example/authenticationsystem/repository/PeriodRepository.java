package com.example.authenticationsystem.repository;

import com.example.authenticationsystem.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PeriodRepository extends JpaRepository<Period, Integer> {

    Optional<Period> findById(Integer id);

    @Query("""
            SELECT period from periods period 
            WHERE period.user.id = :userId 
            and period.deletedAt is null
            and period.startDate <= :endDate
            and period.endDate >= :startDate
            """)
    List<Period> searchBetween(Integer userId, LocalDate startDate, LocalDate endDate);

    @Query("""
            SELECT period from periods period 
            WHERE period.user.id = :userId 
            and period.deletedAt is null
            order by period.startDate
            """)
    List<Period> search(Integer userId);

    @Query(nativeQuery = true,value = """
            SELECT START_DATE as intervalEnd,
            LAG(START_DATE, 1) OVER (ORDER BY START_DATE) as intervalStart,
            DATEDIFF(START_DATE,LAG(START_DATE, 1) OVER (ORDER BY START_DATE)) as periodInterval
            FROM periods
            where USER_ID = :userId
            and DELETED_AT is null
            """)
    ArrayList<ArrayList> getInterval(Integer userId);

    @Transactional
    @Modifying
    @Query("UPDATE periods period " +
            "SET period.deletedAt = :now WHERE period.user.id = :userId")
    void deletePrevPeriods( LocalDateTime now, Integer userId);
}
