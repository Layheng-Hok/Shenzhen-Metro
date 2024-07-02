package com.sustech.cs307.project2.shenzhenmetro.repository;

import com.sustech.cs307.project2.shenzhenmetro.object.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, String> {
}
