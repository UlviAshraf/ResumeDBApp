/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.dao.impl;

import com.company.dao.inter.AbstractDao;
import static com.company.dao.inter.AbstractDao.connect;
import com.company.entity.EmploymentHistory;
import com.company.entity.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.company.dao.inter.EmploymentHistoryDaoInter;

/**
 *
 * @author UlviAshraf
 */
public class EmploymentHistoryDaoImpl extends AbstractDao implements EmploymentHistoryDaoInter {

    private EmploymentHistory getEmploymentHistory(ResultSet rs) throws Exception {
        String header = rs.getString("header");
        String jobDescription = rs.getString("job_description");
        Date beginDate = rs.getDate("begin_date");
        Date endDate = rs.getDate("end_date");
        int userId = rs.getInt("user_id");
        EmploymentHistory emp = new EmploymentHistory(null, header, beginDate, endDate, jobDescription, new User(userId));
        return emp;
    }

    @Override
    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId) {
        List<EmploymentHistory> result = new ArrayList();
        try (Connection c = connect()) {
            PreparedStatement s = c.prepareStatement("select * from employment_history where user_id=?");
            s.setInt(1, userId);
            s.execute();
            ResultSet rs = s.getResultSet();
            while (rs.next()) {
                EmploymentHistory u = getEmploymentHistory(rs);
                result.add(u);
            }
        } catch (Exception ex) {
        }
        return result;
    }
}
