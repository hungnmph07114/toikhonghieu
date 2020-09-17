package com.qlns.qlnsitsol.Service;

import com.qlns.qlnsitsol.entity.NhanVien;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NhanVienService {
    List<NhanVien> list();
    List<NhanVien> timkiem(String name);
    Optional<NhanVien> getOne(long id);
    Optional<NhanVien> getByName(String s);
    void  save(NhanVien phongBan);
    void delete(long id);
    boolean existsById(long id);
    boolean existsByName(String s);
    Page<NhanVien> page(Pageable pageable);
}
