package com.kryha.springjwt.service;


import com.kryha.springjwt.models.Activity;
import com.kryha.springjwt.models.User;
import com.kryha.springjwt.payload.response.ActivityResponse;
import com.kryha.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByName(String name){
       return userRepository.findByUsername(name)
               .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + name));
    }

    public List<ActivityResponse> getUserActivitiesResponseByName(String name){
        Set<Activity> userActivitiesResponseSet =userRepository
                .findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + name))
                .getActivities();
        List<ActivityResponse> userActivitiesResponseList=new ArrayList<>();
        for (Activity activity :
                userActivitiesResponseSet) {
            userActivitiesResponseList.
                    add(new ActivityResponse(activity.getName(),activity.getCategory().getName()));
        }
        return  userActivitiesResponseList;
    }


}
