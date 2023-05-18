package com.cts.mba.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.cts.mba.entities.Ticket;

@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
