package com.etms.worldline.Repository;

import com.etms.worldline.model.User;
import com.etms.worldline.payload.response.Userdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("select u from User u where u.email=:email")
    Optional<User> findByEmail(@Param("email") String email);
    Optional<User> findById(Long id);
    List<User> findByRoleName(String role_name);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userSkills WHERE u.user_id = :userId")
    Optional<User> findByIdWithSkills(@Param("userId") Long userId);
    @Query("SELECT new com.etms.worldline.payload.response.Userdetails(u.user_id,u.username,u.gender,u.email,u.gbl,u.verify) from User u where u.roleName='USER'")
    List<Userdetails> getUsers();
    @Query("SELECT new com.etms.worldline.payload.response.Userdetails(u.user_id,u.username,u.gender,u.email,u.gbl,u.verify,u.propicPath,u.joiningDate) from User u where u.roleName='USER' and u.joiningDate is not null ORDER BY(u.joiningDate) DESC")
    List<Userdetails> getRecentJoinedUsers();
    @Query("SELECT new com.etms.worldline.payload.response.Userdetails(u.user_id,u.username,u.gender,u.email,u.gbl,u.verify,u.propicPath,u.joiningDate) from User u where u.roleName='USER' and u.joiningDate is not null and u.gbl=:gbl ORDER BY(u.joiningDate) DESC")
    List<Userdetails> getRecentJoinedUsersbyGbl(@Param("gbl") String gbl);
    @Query("SELECT new com.etms.worldline.payload.response.Userdetails(u.user_id,u.username,u.email) from User u where u.roleName='USER' and u.assign_project='Yes' and u.isTraining=false")
    List<Userdetails> getUserslackinginskills();
    @Query("SELECT u from User u where u.manager_id=:managerId")
    List<User> findtheemployeesunderManager(@Param("managerId") Long managerId);
    @Query("select count(u) from User u where u.gbl=:gbl and u.roleName='USER'")
    int findthegblcountofemployees(@Param("gbl") String gbl);
    @Query("select count(u) from User u where u.roleName='USER'")
    int findthecountofemployees();
    @Query("select count(u) from User u where u.roleName='MANAGER'")
    int findthecountemployeesman();
    @Query("select count(u) from User u where u.gbl=:gbl and u.roleName='MANAGER'")
    int findthegblcountemployeesman(@Param("gbl") String gbl);
    @Query("select count(u) from User u where u.gbl=:gbl and u.assign_project='No'")
    int findtheempnotinpro(@Param("gbl") String gbl);
    @Query("select u from User u where u.gbl=:gbl and u.roleName='MANAGER'")
    List<User> findthegblemployeesman(@Param("gbl") String gbl);
}
