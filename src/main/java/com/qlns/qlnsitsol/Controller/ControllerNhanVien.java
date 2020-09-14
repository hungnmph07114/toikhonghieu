package com.qlns.qlnsitsol.Controller;

import com.qlns.qlnsitsol.DTO.NhanVienDTO;
import com.qlns.qlnsitsol.DTO.PhongBanDTO;
import com.qlns.qlnsitsol.Service.NhanVienService;
import com.qlns.qlnsitsol.entity.DiemDanh;
import com.qlns.qlnsitsol.entity.KhenThuongKl;
import com.qlns.qlnsitsol.entity.Luong;
import com.qlns.qlnsitsol.entity.NhanVien;
import com.qlns.qlnsitsol.messager.Messager;
import com.qlns.qlnsitsol.repository.RepositoryDiemDanh;
import com.qlns.qlnsitsol.repository.RepositoryKhenThuong;
import com.qlns.qlnsitsol.repository.RepositoryLuong;
import com.qlns.qlnsitsol.repository.RepositoryNhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nhanvien")
public class ControllerNhanVien {
    @Autowired
    NhanVienService nhanVienService;
    @Autowired
    RepositoryNhanVien repo;
    @Autowired
    RepositoryDiemDanh repositoryDiemDanh;
    @Autowired
    RepositoryLuong repositoryLuong;
    @Autowired
    RepositoryKhenThuong repositoryKhenThuong;
    @GetMapping()
    public ResponseEntity<Page<NhanVien>> pageResponseEntity(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String order,
            @RequestParam(defaultValue = "true") boolean asc
    ){
        Page<NhanVien> pages = nhanVienService.page(
                PageRequest.of(page, size, Sort.by(order)));
        if(!asc)
            pages = nhanVienService.page(
                    PageRequest.of(page, size, Sort.by(order).descending()));
        
        return new ResponseEntity<Page<NhanVien>>(pages, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<NhanVienDTO>> listResponseEntity(){
        List<NhanVien> nv = repo.getAllByChuVuLikeAndActionAndPhongBan_Action("giam đốc",true,true);
        NhanVienDTO dto = new NhanVienDTO();
        nv.stream().map(dto::toDTO).collect(Collectors.toList());
        if (nv.isEmpty()){
            return new  ResponseEntity(new Messager("Khong ton tai"), HttpStatus.NOT_FOUND);
        }
        return new  ResponseEntity(nv.stream().map(dto::toDTO).collect(Collectors.toList()), HttpStatus.OK);
    }
}
