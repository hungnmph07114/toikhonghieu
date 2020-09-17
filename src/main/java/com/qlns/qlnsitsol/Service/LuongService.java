package com.qlns.qlnsitsol.Service;

import com.qlns.qlnsitsol.entity.DiemDanhId;
import com.qlns.qlnsitsol.entity.Luong;
import com.qlns.qlnsitsol.entity.LuongId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LuongService {
    List<Luong> list();
    Optional<Luong> getOne(LuongId id);
    Optional<Luong> getByName(String s);
    void  save(Luong luong);
    void delete(int id);
    boolean existsById(int id);
    boolean existsByName(String s);
    Page<Luong> page(Pageable pageable);
}
