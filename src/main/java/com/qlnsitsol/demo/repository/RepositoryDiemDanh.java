package com.qlnsitsol.demo.repository;


import com.qlnsitsol.demo.entity.DiemDanh;
import com.qlnsitsol.demo.entity.DiemDanhId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryDiemDanh extends JpaRepository<DiemDanh, DiemDanhId> {
    Optional<DiemDanh> countByIdAndDilam (DiemDanhId id,boolean bl);

//   List<DiemDanh> getAllByDilamAndNhanVienIdAndDateLike(boolean bl,long id, String date);
}
