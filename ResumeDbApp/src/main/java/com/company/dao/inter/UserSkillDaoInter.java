/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.inter;

import com.company.entity.UserSkill;
import java.util.List;

/**
 *
 * @author UlviAshraf
 */
public interface UserSkillDaoInter {

    public List<UserSkill> getAllSkillByUserId(int userId);

    public void remove(int id);

    public void add(UserSkill u);

    public boolean update(UserSkill u);
}
