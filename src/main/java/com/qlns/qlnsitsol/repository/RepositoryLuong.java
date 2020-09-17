package com.qlns.qlnsitsol.repository;

import com.qlns.qlnsitsol.entity.DiemDanhId;
import com.qlns.qlnsitsol.entity.Luong;
import com.qlns.qlnsitsol.entity.LuongId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositoryLuong extends JpaRepository<Luong, LuongId> {
    List<Luong> getAllById_NhanVien_ActionAndId_Date(Boolean bl,String  date);
    Boolean existsById_NhanVien_IdAndId_Date(long id,String date);
    boolean existsById_Date(String bl);
}
