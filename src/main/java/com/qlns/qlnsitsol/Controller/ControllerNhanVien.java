package com.qlns.qlnsitsol.Controller;

import com.qlns.qlnsitsol.DTO.*;
import com.qlns.qlnsitsol.Service.DiemDanhService;
import com.qlns.qlnsitsol.Service.NhanVienService;
import com.qlns.qlnsitsol.entity.*;
import com.qlns.qlnsitsol.messager.Messager;
import com.qlns.qlnsitsol.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nhanvien")
@CrossOrigin(origins = "*")
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
    @Autowired
    RepositoryNhanVien repositoryNhanVien;
    @Autowired
    RepositoryPhongBan repositoryPhongBan;
    @Autowired
    DiemDanhService diemDanhService;
    CreNhanVienDTO creNhanVienDTO;
    @GetMapping("/pages")
    public ResponseEntity<Page<NhanVien>> pageResponseEntity(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "tenNhanVien") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @RequestParam long phongbanid
    ){
        Page<NhanVien> pages = repositoryNhanVien.findAllByPhongBanId(phongbanid,PageRequest.of(page, size, Sort.by(order)));
        if(!asc)
            pages = repositoryNhanVien.findAllByPhongBanId(phongbanid,PageRequest.of(page, size, Sort.by(order).descending()));
        System.out.println(phongbanid);
        return new ResponseEntity<Page<NhanVien>>(pages, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<?> getoneNhanVien(@PathVariable("id") long id){
        Optional<NhanVien> nhanVien = nhanVienService.getOne(id);
        if (nhanVien.isEmpty()){
            return  new ResponseEntity(new Message("Nhân viên Không tồn tại"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(nhanVien,HttpStatus.OK);
    }
    @GetMapping("/timkiem")
    public  ResponseEntity<?> tiemkiem( @RequestParam(defaultValue = "") String ten){
        System.out.println(ten);
      if (ten == null){
          List<NhanVien> nv = repo.getAllByAction(true);
          NhanVienDTO dto = new NhanVienDTO();
//        nv.stream().map(dto::toDTO).collect(Collectors.toList());
          if (nv.isEmpty()){
              return new  ResponseEntity(new Messager("Khong ton tai"), HttpStatus.NOT_FOUND);
          }
          return new  ResponseEntity(nv, HttpStatus.OK);
      }
        List<NhanVien> nhanVien = nhanVienService.timkiem(ten);
        if (nhanVien.isEmpty()){
            return  new ResponseEntity(new Message("Nhân viên Không tồn tại"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(nhanVien,HttpStatus.OK);
    }


    @GetMapping("/list")
    public ResponseEntity<List<NhanVien>> listResponseEntity(){
        List<NhanVien> nv = repo.getAllByAction(true);
        NhanVienDTO dto = new NhanVienDTO();
//        nv.stream().map(dto::toDTO).collect(Collectors.toList());
        if (nv.isEmpty()){
            return new  ResponseEntity(new Messager("Khong ton tai"), HttpStatus.NOT_FOUND);
        }
        return new  ResponseEntity(nv, HttpStatus.OK);
    }
    @PostMapping("/newNhanVien")
    public ResponseEntity<?> create(@RequestBody CreNhanVienDTO dto){
        System.out.println(dto.getTenNhanVien());
        PhongBan pb = repositoryPhongBan.findById(dto.getPhongbanid()).get();
        NhanVien nv = new NhanVien();
        nv.setAction(dto.getAction());
        nv.setTenNhanVien(dto.getTenNhanVien());
        nv.setNgaySinh(dto.getNgaySinh());
        nv.setGioiTinh(dto.getGioiTinh());
        nv.setDiaChi(dto.getDiaChi());
        nv.setSoCmnnd(dto.getSoCmnnd());
        nv.setDienThoai(dto.getDienThoai());
        nv.setChuVu(dto.getChuVu());
        nv.setEmail(dto.getEmail());
        nv.setImg(dto.getImg());
        nv.setPhongBan(pb);
        nhanVienService.save(nv);
        return new ResponseEntity(new Message("Them nhan vien moi thanh cong"), HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")Long id, @RequestBody CreNhanVienDTO dto){
        System.out.println(id);
        if(!nhanVienService.existsById(id)) {
            return new ResponseEntity(new Message("no exists"), HttpStatus.NOT_FOUND);
        }
        PhongBan pb = repositoryPhongBan.findById(dto.getPhongbanid()).get();
        NhanVien nv = nhanVienService.getOne(id).get();
        nv.setAction(dto.getAction());
        nv.setTenNhanVien(dto.getTenNhanVien());
        nv.setNgaySinh(dto.getNgaySinh());
        nv.setGioiTinh(dto.getGioiTinh());
        nv.setDiaChi(dto.getDiaChi());
        nv.setSoCmnnd(dto.getSoCmnnd());
        nv.setDienThoai(dto.getDienThoai());
        nv.setChuVu(dto.getChuVu());
        nv.setEmail(dto.getEmail());
        nv.setImg(dto.getImg());
        nv.setPhongBan(pb);
        nhanVienService.save(nv);
        return new ResponseEntity(new Message("Cap nhat thanh cong"), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        if(!nhanVienService.existsById(id)) {
            return new ResponseEntity(new Message("no exists"), HttpStatus.NOT_FOUND);
        }
        NhanVien nv = nhanVienService.getOne(id).get();
        nv.setAction(false);
        nhanVienService.save(nv);
        return new ResponseEntity(new Message("Xoa thanh cong"), HttpStatus.OK);
    }
    @PostMapping("/diemdanh")
    public ResponseEntity<?> create1(@RequestBody DiemDanhDTO dto){

//        Date date = Calendar.getInstance().getTime();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
//        String strDate = dateFormat.format(date);
        DiemDanh dd = new DiemDanh();
        NhanVien nv = nhanVienService.getOne(dto.getNhanvienid()).get();
        DiemDanhId diemDanhId = new DiemDanhId();
        diemDanhId.setDate(dto.getDate());
        diemDanhId.setNhanVien(nhanVienService.getOne(dto.getNhanvienid()).get());
        dd.setId(diemDanhId);
        dd.setDilam(dto.isDilam());
        diemDanhService.save(dd);

        return new ResponseEntity(new Message("Điểm danh thành công"), HttpStatus.OK);
    }
}
