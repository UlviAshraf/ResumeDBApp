/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import com.company.dao.inter.AbstractDao;
import com.company.dao.inter.SkillDaoInter;
import com.company.entity.Country;
import com.company.entity.Skill;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UlviAshraf
 */
public class SkillDaoImpl extends AbstractDao implements SkillDaoInter {

    public Skill getSkill(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Skill skill = new Skill(id, name);
        return skill;
    }

    @Override
    public List<Skill> getAll() {
        List<Skill> result = new ArrayList();
        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute("select * from skill");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                Skill skill = getSkill(rs);
                result.add(skill);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
