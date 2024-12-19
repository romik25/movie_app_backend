package com.cts.mba.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.cts.mba.dao.JwtExpired;


@FeignClient(name="movie-service-util" , url="https://user-auth-service-a3fpedgah7amefh4.centralus-01.azurewebsites.net/api/v1/auth")
public interface FeignUtil {
	
	@GetMapping("/token/expired")
	JwtExpired validToken(@RequestHeader(name="auth" , required=false) String header);

}