package org.zerock.j1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.j1.domain.Sample;

//JpaRepository<Sample, String>
//             Entity클래스명, Id의 타입
public interface SampleRepository extends JpaRepository<Sample, String> {
  
}
