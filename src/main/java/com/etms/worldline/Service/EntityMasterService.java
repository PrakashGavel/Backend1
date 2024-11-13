package com.etms.worldline.Service;

import com.etms.worldline.Repository.EntityMasterRepository;
import com.etms.worldline.model.EntityMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityMasterService {
     @Autowired
     private EntityMasterRepository entityMasterRepository;

     public List<EntityMaster> getAllentities(){
         return entityMasterRepository.findAll();
     }
     public EntityMaster getEntitymasterbyId(Long en_id){
         EntityMaster entityMaster=entityMasterRepository.getById(en_id);
         return entityMaster;
     }
     public EntityMaster updateEntityMaster(Long en_id,EntityMaster entityMasterinput){
         EntityMaster entityMaster=entityMasterRepository.getById(en_id);
         entityMaster.setGblNames(entityMasterinput.getGblNames());
         return entityMasterRepository.save(entityMaster);
     }
     public void deleteEntityMaster(Long en_id){
         EntityMaster entityMaster=entityMasterRepository.getById(en_id);
         entityMasterRepository.delete(entityMaster);
     }

}
