package com.jdc.assignment.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdc.assignment.domain.Course;
import com.jdc.assignment.domain.OpenClass;
import com.jdc.assignment.model.OpenClassModel;

public class OpenClassModelImpl implements OpenClassModel {
	
	private static final String SELECT_SQL = "select oc.id id,oc.start_date,oc.teacher,c.id courseId,c.name,c.duration,c.fees,c.description from open_class oc join course c on oc.course_id=c.id where c.id=?";

	private static final String SAVE_SQL = "insert into open_class(course_id,start_date,teacher) values (?,?,?)";

	private static final String SELECT_BY_ID = "select c.id courseId,c.name,c.fees,c.duration,c.description,oc.id,oc.start_date,oc.teacher from course c join open_class oc on c.id=oc.course_id where oc.id=?";
	
	private DataSource dataSource;

	public OpenClassModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<OpenClass> findByCourse(int courseId) {
		
		List<OpenClass> list = new ArrayList<OpenClass>();
		try(var conn = dataSource.getConnection()){
			
			var stmt = conn.prepareStatement(SELECT_SQL);
			
			stmt.setInt(1, courseId);
			var rs = stmt.executeQuery();
			
			while (rs.next()) {
				var course = new Course();
				
				course.setId(rs.getInt("courseId"));
				course.setName(rs.getString("name"));
				course.setDuration(rs.getInt("duration"));
				course.setFees(rs.getInt("fees"));
				course.setDescription(rs.getString("description"));
				
				var oc = new OpenClass();
				oc.setCourse(course);
				oc.setId(rs.getInt("id"));
				oc.setStartDate(rs.getDate("start_date").toLocalDate());
				oc.setTeacher(rs.getString("teacher"));
				
				list.add(oc);
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	
	}

	@Override
	public void create(OpenClass openClass) {
		try(var conn= dataSource.getConnection()) {
			
			var stmt = conn.prepareStatement(SAVE_SQL);
			
			stmt.setInt(1, openClass.getCourse().getId());
			stmt.setDate(2,Date.valueOf(openClass.getStartDate()));
			stmt.setString(3, openClass.getTeacher());
			
			stmt.executeUpdate();
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public OpenClass findById(int id) {
		
		OpenClass oc = new OpenClass();
		
		try(var conn = dataSource.getConnection()) {
			
			var stmt = conn.prepareStatement(SELECT_BY_ID);
			
			stmt.setInt(1, id);
			var result = stmt.executeQuery();
			
			while(result.next()) {
				
				var course = new Course();
				course.setId(result.getInt("courseId"));
				course.setName(result.getString("name"));
				course.setFees(result.getInt("fees"));
				course.setDuration(result.getInt("duration"));
				course.setDescription(result.getString("description"));
				
				oc.setId(result.getInt("id"));
				oc.setCourse(course);
				oc.setStartDate(result.getDate("start_date").toLocalDate());
				oc.setTeacher(result.getString("teacher"));
				
				return oc;
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return oc;
	}
	
}
