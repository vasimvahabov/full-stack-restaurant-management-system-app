package com.example.rms.repositories;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param; 
import com.example.rms.dtos.AdminDashboardDTO;
import com.example.rms.entities.Admin;
 
public interface AdminRepository extends CrudRepository<Admin,Integer>{
    
  @Query(value="select * from admins_ where username_=:USERNAME and password_=:PASSWORD",nativeQuery=true)
  public Admin checkLogin(@Param("USERNAME") String username,@Param("PASSWORD") String password);

  @Query(value="select new com.example.rms.dtos.AdminDashboardDTO("
			  	   +"(select count(c) from Category c),"
			  	   +"(select count(p) from Product p),"
			  	   +"(select count(o) from Order o where o.completed=true),"
			  	   +"(select count(p) from Position p),"
			  	   +"(select count(e) from Employee e),"
			  	   +"(select count(a) from Admin a),"
		  		   +"(select count(u) from User u))")
  public AdminDashboardDTO getAdminDashboard();
}
