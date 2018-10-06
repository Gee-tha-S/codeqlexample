package com.todo.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AmazonServices", url = "http://localhost:8765")
public interface FeingService {

	@PostMapping("/s3services/createbucket")
	public String createBucket( String bucketName);
	/*
	 * @PostMapping("/s3services/saveobject") public Object saveObject(@RequestBody
	 * Object ob); Files.
	 */
}
