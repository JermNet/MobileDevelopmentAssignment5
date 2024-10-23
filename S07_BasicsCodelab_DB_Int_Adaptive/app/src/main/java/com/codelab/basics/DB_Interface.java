package com.codelab.basics;

import android.content.Context;

import java.util.List;
import java.util.Map;

public interface DB_Interface {

    public int count();

    public int save(DataModel dataModel);

    public void update(DataModel dataModel);  // Not implemented

    public int deleteById(Long id);  // Not implemented

    public List<DataModel> findAll();

    public String getNameById(Long id);  // Not implemented

}
