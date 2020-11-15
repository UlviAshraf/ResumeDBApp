/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.inter;

import com.company.bean.User;
import java.util.List;

/**
 *
 * @author UlviAshraf
 */
public interface UserDaoInter {

    public List<User> getAllUser();

    public boolean update(User u);

    public void remove(int id);

    public User getById(int userId);
}
