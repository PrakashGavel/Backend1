package com.etms.worldline.Repository;

import com.etms.worldline.model.UserProject;
import com.etms.worldline.payload.response.UserProjectDetails;
import com.etms.worldline.payload.response.Userdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject,Long> {
     @Query("select new com.etms.worldline.payload.response.UserProjectDetails(u.projectAssign.pro_id,u.projectAssign.proName,u.projectAssign.gbl,u.projectAssign.proDesc,u.isActive) from UserProject u where u.user.user_id=:user_id")
     List<UserProjectDetails> userProjectDetails(@Param("user_id") Long user_id);
     @Query("select new com.etms.worldline.payload.response.Userdetails(u.user.user_id,u.user.username,u.user.gender,u.user.email,u.user.gbl,u.user.roleName) from UserProject u where u.projectAssign.proName=:proName")
     List<Userdetails> usersInProject2(@Param("proName") String proName);
     @Modifying
     @Query("update UserProject u set u.isActive=:isActive where u.user.user_id=:user_id and u.projectAssign.pro_id=:pro_id")
     int updateUserprojectDet(@Param("user_id") Long user_id,@Param("pro_id") Long pro_id,@Param("isActive") boolean isActive);
//     @Query("SELECT up.projectAssign.proName,count(up.user.user_id) FROM UserProject up GROUP BY up.projectAssign.proName")
//     List<Object[]> getProjectMembers();
     @Query("SELECT up.projectAssign.proName,count(up.user.user_id) FROM UserProject up GROUP BY up.projectAssign.proName order by count(up.user.user_id) desc")
     List<Object[]> getProjectMembers();
     @Query("SELECT up.projectAssign.proName,count(up.user.user_id) FROM UserProject up WHERE up.user.gbl=:gbl GROUP BY up.projectAssign.proName order by count(up.user.user_id) desc")
     List<Object[]> getProjectMembersbyGbl(@Param("gbl") String gbl);
     @Query("select new com.etms.worldline.payload.response.Userdetails(u.user.user_id,u.user.username,u.user.email) from UserProject u where u.projectAssign.proName=:proName and u.roleName='MANAGER'")
     List<Userdetails> managerInProject(@Param("proName") String proName);
     @Query("select new com.etms.worldline.payload.response.Userdetails(u.user.user_id,u.user.username,u.user.email) from UserProject u where u.projectAssign.proName=:proName and u.roleName='USER' and u.man_id=:man_id")
     List<Userdetails> usersInProject(@Param("proName") String proName,@Param("man_id") Long man_id);
}
