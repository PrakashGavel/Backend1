package com.etms.worldline.Repository;

import com.etms.worldline.model.EntityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityMasterRepository extends JpaRepository<EntityMaster,Long> {
}
