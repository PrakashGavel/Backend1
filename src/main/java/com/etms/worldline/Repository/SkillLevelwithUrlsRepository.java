package com.etms.worldline.Repository;

import com.etms.worldline.model.SkillLevelwithUrls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillLevelwithUrlsRepository extends JpaRepository<SkillLevelwithUrls,Long> {

}
