package com.vince.demo.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vince.demo.model.Response;
import com.vince.demo.model.Type;
import com.vince.demo.service.TypeService;

@RestController
public class TypeController {
	
	@Autowired
	TypeService typeservice;
	
	Response respo = new Response();
	ResponseEntity<Object> response;
	
//	@GetMapping("/test")
//	public String test(HttpSession session,HttpServletRequest req) {
//		
//		HttpSession ses = req.getSession();
//		String w = (String)ses.getAttribute("Session");
//		String q = (String)ses.getAttribute("id");
//		String v = (String) ses.getValue("1");
//		String userId = req.getHeader("userId");
//		System.out.println(q +" "+w+" "+v+" "+userId);
//		return "";
//		typeservice.tryy("shoes");
//		return "";
//	}
	
	@PostMapping("/types")
	public ResponseEntity<Object> createType(@RequestBody Type type,HttpServletRequest req){
		if(type==null) { 
			respo.setResponseMessage("Invalid request body");
			respo.setResponseCode(400);
			return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
		}
		else {
			return response = typeservice.createType(type, req.getHeader("userId"));	
		}
	}
	
	@GetMapping("/types")
	public ResponseEntity<Object> readType(){
		return typeservice.readType();
	}
	
	@PutMapping("/types/{typeId}")
	public ResponseEntity<Object> updateType(@RequestBody Map<String,String> body, 
											 @PathVariable("typeId") String type_id){
		
		return response = typeservice.updateType(body, type_id);
		
	}
	
	@DeleteMapping("/types/{typeId}")
	public ResponseEntity<Object> deleteType(@PathVariable("typeId") String type_id){
		return response = typeservice.deleteType(type_id);
	}
	
	@PutMapping("/types/{typeId}/deactivate")
	public ResponseEntity<Object> deactivateType(@PathVariable("typeId")String type_id){
		return response = typeservice.deactivateType(type_id);
	}
	
	@PutMapping("/types/{typeId}/activate")
	public ResponseEntity<Object> activateType(@PathVariable("typeId")String type_id){
		return response = typeservice.activateType(type_id);
	}
}
