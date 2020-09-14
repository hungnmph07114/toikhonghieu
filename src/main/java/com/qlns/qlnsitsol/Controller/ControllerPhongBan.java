package com.qlns.qlnsitsol.Controller;

import com.qlns.qlnsitsol.DTO.PhongBanDTO;
import com.qlns.qlnsitsol.Service.PhongBanService;
import com.qlns.qlnsitsol.entity.NhanVien;
import com.qlns.qlnsitsol.entity.PhongBan;
import com.qlns.qlnsitsol.messager.Messager;
import com.qlns.qlnsitsol.repository.RepositoryNhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/phongban")
@CrossOrigin(origins = "http://localhost:4200")
public class ControllerPhongBan {
    @Autowired
    PhongBanService phongBanService;
    @Autowired
    RepositoryNhanVien repo;
    @GetMapping()
    public ResponseEntity<List<PhongBan>> list(){
       List<PhongBan> phongBans = phongBanService.list();
       if (phongBans.isEmpty()){
           return  new ResponseEntity(new Messager("Chưa có Phòng Ban nào"),HttpStatus.NOT_FOUND);
       }

        return new  ResponseEntity( phongBans, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PhongBanDTO>> listResponseEntity(){
        List<NhanVien> phongBans = repo.getAllByChuVuLikeAndActionAndPhongBan_Action("giam đốc",true,true);
        PhongBanDTO dto = new PhongBanDTO();
        phongBans.stream().map(dto::toDTO).collect(Collectors.toList());
        if (phongBans.isEmpty()){
            return new  ResponseEntity(new Messager("Khong ton tai"), HttpStatus.NOT_FOUND);
        }
        return new  ResponseEntity(phongBans.stream().map(dto::toDTO).collect(Collectors.toList()), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<?>add(@RequestBody PhongBan phongBan ,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return  new ResponseEntity( new Messager("không được để trống") ,  HttpStatus.BAD_REQUEST);
        }else if(phongBanService.existsByName(phongBan.getTenPhongBan())){
            return  new ResponseEntity(new Messager("Tên phòng ban đã tồn tại"),HttpStatus.BAD_REQUEST);
        }else
            phongBan.setAction(true);
            phongBanService.save(phongBan);
        return new ResponseEntity<>(new Messager("Thêm phòng ban"), HttpStatus.OK);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<PhongBan> update(@PathVariable("id") long id, @RequestBody PhongBan phongBan){
        if(!phongBanService.existsById(id))
            return new ResponseEntity(new Messager("Khong ton tai"), HttpStatus.NOT_FOUND);
        if(phongBanService.existsByName(phongBan.getTenPhongBan()) && phongBanService.getByName(phongBan.getTenPhongBan()).get().getId() != id)
            return new ResponseEntity(new Messager("tên đó đã tồn tại"), HttpStatus.BAD_REQUEST);
        if (phongBan.getTenPhongBan() == null)
            return new ResponseEntity(new Messager("tên khong de trong"), HttpStatus.BAD_REQUEST);
               Optional<PhongBan> phongBan1 = phongBanService.getOne(id);
               phongBan1.get().setTenPhongBan(phongBan.getTenPhongBan());
               phongBan1.get().setDienThoai(phongBan.getDienThoai());
               phongBan1.get().setGhiChu(phongBan.getGhiChu());
               phongBan1.get().setImg(phongBan.getImg());
               phongBan1.get().setAction(phongBan.getAction());
               phongBanService.save(phongBan1.get());
               return new ResponseEntity<>(phongBan1.get(),HttpStatus.OK);
    }
}
