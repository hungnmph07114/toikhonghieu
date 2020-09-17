package com.qlns.qlnsitsol.serviceIplm;

import com.qlns.qlnsitsol.Service.LuongService;
import com.qlns.qlnsitsol.entity.DiemDanh;
import com.qlns.qlnsitsol.entity.DiemDanhId;
import com.qlns.qlnsitsol.entity.Luong;
import com.qlns.qlnsitsol.entity.LuongId;
import com.qlns.qlnsitsol.repository.RepositoryLuong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LuongIplm implements LuongService {
    @Autowired
    RepositoryLuong repositoryLuong;
    @Override
    public List<Luong> list() {
        return null;
    }

    @Override
    public Optional<Luong> getOne(LuongId id) {
        return repositoryLuong.findById(id);
    }

    @Override
    public Optional<Luong> getByName(String s) {
        return Optional.empty();
    }

    @Override
    public void save(Luong luong) {
        repositoryLuong.save(luong);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public boolean existsById(int id) {
        return false;
    }

    @Override
    public boolean existsByName(String s) {
        return false;
    }

    @Override
    public Page<Luong> page(Pageable pageable) {
        return null;
    }
}
