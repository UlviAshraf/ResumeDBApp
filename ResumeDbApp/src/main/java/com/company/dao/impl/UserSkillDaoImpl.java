/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import com.company.entity.Skill;
import com.company.entity.User;
import com.company.entity.UserSkill;
import com.company.dao.inter.AbstractDao;

import static com.company.dao.inter.AbstractDao.connect;

import com.company.dao.inter.UserSkillDaoInter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author UlviAshraf
 */
public class UserSkillDaoImpl extends AbstractDao implements UserSkillDaoInter {

    private UserSkill getUserSkill(ResultSet rs) throws Exception {
        int userSkillId = rs.getInt("userSkillID");
        int userId = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        int power = rs.getInt("power");
        String skillName = rs.getString("skill_name");
        return new UserSkill(null, new User(userId), new Skill(skillId, skillName), power);
    }

    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        List<UserSkill> result = new ArrayList();
        try {
            Connection c = connect();
            PreparedStatement stmt = c.prepareStatement("SELECT "
                    + "us.id as UserSkillId,	"
                    + "u.*, "
                    + "	us.skill_id, "
                    + "	s.NAME AS skill_name, "
                    + "	us.power "
                    + "FROM "
                    + "	user_skill us "
                    + "	LEFT JOIN USER u ON us.user_id = u.id "
                    + "	LEFT JOIN skill s ON us.skill_id = s.id  "
                    + "WHERE "
                    + "	us.user_id=?");
            stmt.setInt(1, userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                UserSkill u = getUserSkill(rs);
                result.add(u);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    @Override
    public void remove(int id) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("delete  from user_skill where id=? ");
            stmt.setInt(1, id);
            stmt.execute();
        } catch (Exception ex) {
        }
    }

    @Override
    public void add(UserSkill u) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("insert into user_skill (skill_id,user_id,power)values(?,?,?) ");
            stmt.setInt(1, u.getSkill().getId());
            stmt.setInt(2, u.getUser().getId());
            stmt.setInt(3, u.getPower());
            stmt.execute();
        } catch (Exception ex) {
        }
    }

    @Override
    public boolean update(UserSkill u) {
        try (Connection con = connect()) {
            PreparedStatement stmt = con.prepareStatement("update user_skill set user_id=?,skill_id=?,power=? where id=?");
            stmt.setInt(1, u.getUser().getId());
            stmt.setInt(2, u.getSkill().getId());
            stmt.setInt(3, u.getPower());
            stmt.setInt(4, u.getId());
            return stmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
