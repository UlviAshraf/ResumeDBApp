/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.inter;

import com.company.entity.User;
import com.company.entity.UserSkill;

import java.util.List;

/**
 * @author UlviAshraf
 */
public interface UserDaoInter {

    public List<User> getAllUser(String name, String surname, Integer nationalityId);

    public boolean update(User u);

    public boolean addUser(User u);

    public void remove(int id);

    public User getById(int userId);


}
