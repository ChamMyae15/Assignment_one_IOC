package com.jdc.assignment.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdc.assignment.domain.Course;
import com.jdc.assignment.domain.OpenClass;
import com.jdc.assignment.domain.Registration;
import com.jdc.assignment.model.RegistrationModel;

public class RegistrationModelImpl implements RegistrationModel{
	
	private static final String SELECT_SQL = "select c.name courseName,c.duration,c.fees,c.description,oc.start_date,oc.teacher,r.id,r.student,r.phone,r.email from course c join open_class oc on c.id=oc.course_id join registration r on oc.id=r.open_class_id where oc.id=?";
	private static final String SAVE_SQL = "insert into registration(open_class_id,student,phone,email) values (?,?,?,?)";
	private DataSource dataSource;
	
	public RegistrationModelImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Registration> getRegistrationById(int id) {
		List<Registration> list = new ArrayList<>();
		try(var conn = dataSource.getConnection()){
			
			var stmt = conn.prepareStatement(SELECT_SQL);
			
			stmt.setInt(1, id);
			var rs = stmt.executeQuery();
			
			while(rs.next()) {
				Course course = new Course();
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("courseName"));
				course.setDuration(rs.getInt("duration"));
				course.setFees(rs.getInt("fees"));
				course.setDescription(rs.getString("description"));
				
				OpenClass oc = new OpenClass();
				oc.setCourse(course);
				oc.setId(rs.getInt("id"));
				oc.setStartDate(rs.getDate("start_date").toLocalDate());
				oc.setTeacher(rs.getString("teacher"));
				
				Registration r = new Registration();
				r.setId(rs.getInt("id"));
				r.setOpenClass(oc);
				r.setStudent(rs.getString("student"));
				r.setPhone(rs.getString("phone"));
				r.setEmail(rs.getString("email"));
				
				list.add(r);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void save(Registration r) {
		
		try(var conn = dataSource.getConnection()) {
			
			var stmt = conn.prepareStatement(SAVE_SQL);
			
			stmt.setInt(1, r.getOpenClass().getId());
			stmt.setString(2, r.getStudent());
			stmt.setString(3, r.getPhone());
			stmt.setString(4, r.getEmail());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/*select c.name courseName,oc.start_date,oc.teacher,r.id,r.student,r.phone,r.email from course c join open_class
	oc on c.id=oc.course_id join registration r on oc.id=r.open_class_id where oc.id=*/
	

}
