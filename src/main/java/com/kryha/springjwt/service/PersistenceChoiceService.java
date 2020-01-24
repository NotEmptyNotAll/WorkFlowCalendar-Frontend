package com.kryha.springjwt.service;

import com.kryha.springjwt.models.Activity;
import com.kryha.springjwt.models.PersistenceChoice;
import com.kryha.springjwt.payload.request.ActivityRequest;
import com.kryha.springjwt.repository.PersistenceChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistenceChoiceService {

    @Autowired
    private PersistenceChoiceRepository persistenceChoiceRepository;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    public void save(ActivityRequest activityRequest){
        PersistenceChoice persistenceChoice = new PersistenceChoice();
        Activity activity=  activityService.getActivityByName(activityRequest.getNameAct());
        persistenceChoice.setActivity_id(activity.getId());
        persistenceChoice.setUser_id(userService
                .getUserByName(activityRequest.getUserName()).getId());
        persistenceChoiceRepository.save(persistenceChoice);
    }
}
