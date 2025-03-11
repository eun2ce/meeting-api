package com.example.swaggerexam.repository;

import com.example.swaggerexam.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}
