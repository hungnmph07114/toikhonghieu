package com.qlns.qlnsitsol.repository;

import com.qlns.qlnsitsol.entity.DiemDanh;
import com.qlns.qlnsitsol.entity.DiemDanhId;
import com.qlns.qlnsitsol.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryDiemDanh extends JpaRepository<DiemDanh, DiemDanhId> {
    Optional<DiemDanh> countByIdAndDilam (DiemDanhId id,boolean bl);

//   List<DiemDanh> getAllByDilamAndNhanVienIdAndDateLike(boolean bl,long id, String date);
}
