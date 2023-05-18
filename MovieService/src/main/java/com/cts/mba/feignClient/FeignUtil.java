package com.cts.mba.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.cts.mba.dao.JwtExpired;


@FeignClient(name="movie-service-util" , url="http://localhost:7001/api/v1/auth")
public interface FeignUtil {
	
	@GetMapping("/token/expired")
	JwtExpired validToken(@RequestHeader(name="auth" , required=false) String header);

}