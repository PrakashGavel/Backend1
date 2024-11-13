package com.etms.worldline.Repository;

import com.etms.worldline.model.ProjectAssign;
import com.etms.worldline.payload.response.Projectdetails;
import com.etms.worldline.payload.response.Userdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectAssign,Long> {
    Optional<ProjectAssign> findById(Long id);
    Optional<ProjectAssign> findByProName(String proName);
    @Query("SELECT new com.etms.worldline.payload.response.Projectdetails(u.pro_id,u.proName,u.gbl,u.proDesc) from ProjectAssign u")
    List<Projectdetails> getProjects();
    @Query("SELECT new com.etms.worldline.payload.response.Projectdetails(u.pro_id,u.proName,u.gbl,u.proDesc) from ProjectAssign u ORDER BY(u.creationDate) DESC ")
    List<Projectdetails> getProjectsByDate();
    @Query("SELECT new com.etms.worldline.payload.response.Projectdetails(u.pro_id,u.proName,u.proDesc) from ProjectAssign u WHERE u.gbl=:gbl ORDER BY(u.creationDate) DESC")
    List<Projectdetails> getProjectsByDatebygbl(@Param("gbl") String gbl);

    @Query("SELECT new com.etms.worldline.payload.response.Projectdetails(u.pro_id,u.proName,u.gbl) from ProjectAssign u where u.proName=:proName")
    Projectdetails getProjectskById(@Param("proName") String proName);
    @Query("SELECT u from ProjectAssign u WHERE u.pro_id=:proId")
    ProjectAssign findtheDocumentUrlsById(@Param("proId") Long proId);
    @Query("select count(u) from ProjectAssign u where u.gbl=:gbl")
    int findthegblcountofprojects(@Param("gbl") String gbl);
    @Query("select count(u) from ProjectAssign u")
    int findthecountofprojects();
    @Query("SELECT new com.etms.worldline.payload.response.Projectdetails(u.pro_id,u.proName,u.gbl,u.proDesc) from ProjectAssign u where u.man_id=:man_id")
    List<Projectdetails> getProjectsByManager(@Param("man_id") Long man_id);
    @Query("SELECT new com.etms.worldline.payload.response.Projectdetails(u.pro_id,u.proName,u.proDesc) from ProjectAssign u where u.gbl=:gbl")
    List<Projectdetails> getProjectsBygl(@Param("gbl") String gbl);
}
