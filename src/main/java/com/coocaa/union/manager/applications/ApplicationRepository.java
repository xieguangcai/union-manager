package com.coocaa.union.manager.applications;

import com.coocaa.union.manager.NoCudRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface ApplicationRepository extends NoCudRepository<Application, Integer> {

    Optional<Application> findByAppKey(@RequestParam("appKey") String appId);

}
