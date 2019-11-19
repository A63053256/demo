package com.service;

import com.bean.NoteBook_Type;
import com.dao.NoteBook_TypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class NoteBook_TypeService {
    @Autowired
    private NoteBook_TypeDao dao;


    public List<NoteBook_Type> NoteBook_TypeList(){
        return dao.findAll();
    }
    public NoteBook_Type findByName(String name) {
        return dao.findByName(name);
    }
    public NoteBook_Type findById(String id){return dao.findById(id);};
}
