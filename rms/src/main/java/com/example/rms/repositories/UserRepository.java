package com.example.rms.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.example.rms.dtos.UserDTO;
import com.example.rms.entities.User;

public interface UserRepository extends CrudRepository<User,Integer>{

  @Query(value = "select * from users_ where username_ =:USERNAME and "
                                        +"password_ =:PASSWORD and status_=1",nativeQuery=true)
  public User login(@Param("USERNAME") String username,@Param("PASSWORD") String password);

  @Query(value = "select new com.example.rms.dtos.UserDTO(u.id,u.username,"
  		+ "Cast(null as string),u.firstName,u.lastName,u.status) from User u")
  public List<UserDTO> getAllUsers();

  @Query(value = "select concat(first_name_,' ',last_name_) from users_ where id_=:ID",nativeQuery=true)
  public String getUserFullNameById(@Param("ID") Integer id);
}
