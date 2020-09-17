package com.qlns.qlnsitsol.serviceIplm;

import com.qlns.qlnsitsol.Service.KhenThuongService;
import com.qlns.qlnsitsol.entity.KhenThuongKl;
import com.qlns.qlnsitsol.repository.RepositoryKhenThuong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class KhenThuongIplm implements KhenThuongService {
    @Autowired
    RepositoryKhenThuong repositoryKhenThuong;
    @Override
    public List<KhenThuongKl> list() {
        return null;
    }

    @Override
    public Optional<KhenThuongKl> getOne(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<KhenThuongKl> getByName(String s) {
        return Optional.empty();
    }

    @Override
    public void save(KhenThuongKl khenThuongKl) {
        repositoryKhenThuong.save(khenThuongKl);

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
    public Page<KhenThuongKl> page(Pageable pageable) {
        return null;
    }
}
