package com.cts.mba.repos;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.cts.mba.entities.Theatre;





@Transactional
public interface TheatreRepository extends JpaRepository<Theatre, Integer> {



}
