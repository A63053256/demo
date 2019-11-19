package com.dao;

import com.bean.NoteBook_Type;

import java.util.List;

public interface NoteBook_TypeDao {
    NoteBook_Type findById(String id);
    NoteBook_Type findByName(String name);
    List<NoteBook_Type> findAll();
}
