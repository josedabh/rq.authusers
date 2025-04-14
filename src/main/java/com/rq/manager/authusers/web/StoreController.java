package com.rq.manager.authusers.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/store")
@AllArgsConstructor
public class StoreController {
	
	//private StoreService storeService;
	
	public void getStore() {
//		storeService.getStore();
	}

}
