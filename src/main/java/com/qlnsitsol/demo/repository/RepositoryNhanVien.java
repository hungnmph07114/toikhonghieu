package com.qlnsitsol.demo.repository;

import com.qlnsitsol.demo.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryNhanVien extends JpaRepository<NhanVien,Long> {
   Optional<NhanVien> findByTenNhanVien (String s);
    List<NhanVien> getAllByChuVuLikeAndActionAndPhongBan_Action(String s, boolean bl, boolean ba);
    List<NhanVien> getAllByAction(boolean bl);
    List<NhanVien>getAllByActionAndTenNhanVienLike(Boolean action, String sadsa);
    Page<NhanVien> findAllByPhongBanId(long id, Pageable pageable);

   Boolean existsByTenNhanVien(String s);
}
