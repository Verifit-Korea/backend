package com.verifit.verifit.point.dao;

import com.verifit.verifit.point.domain.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long>, CustomPointRepository {
}
