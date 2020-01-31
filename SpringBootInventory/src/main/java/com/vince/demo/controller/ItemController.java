package com.vince.demo.controller;

import java.util.*;

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

import com.vince.demo.model.Item;
import com.vince.demo.model.Response;
import com.vince.demo.service.ItemService;

@RestController
public class ItemController {
	
	@Autowired
	ItemService itemservice;
	
	Response respo = new Response();
	
	@PostMapping("/items")
	public ResponseEntity<Object> createItem(@RequestBody Item new_item) {
		
			if(new_item.getName()==null||new_item.getName().equals("")||
			   new_item.getType()==null||new_item.getType().equals(""))
			{
				respo.setResponseMessage("Invalid request");
				respo.setResponseCode(400);
				return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
			}
			
			else 
			{
				try {
					  Double.parseDouble(new_item.getCount());
					  return itemservice.createItem(new_item);
				}
				catch(Exception e)
				{
					respo.setResponseMessage("Invalid request");
					respo.setResponseCode(400);
					return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
				}	
					
			}
		
	}

	@GetMapping("/items/{itemId}")
	public ResponseEntity<Object> readItem(@PathVariable("itemId") String itemId){
		
		return itemservice.readItem(itemId);	
		
	}
	
	@GetMapping("/items")
	public ResponseEntity<Object> readItems(){
		return itemservice.readItems();
	}
	
	@GetMapping("/items/count")
	public ResponseEntity<Object> getTotalCountsItem() {
		return itemservice.getTotalCountItems();
	}
	
	@GetMapping("items/cost")
	public ResponseEntity<Object> getTotalCostItem() {
		return itemservice.getTotalCostItem();
	}
	
	@PutMapping("/items/{itemId}")
	public ResponseEntity<Object> updateItem(@PathVariable("itemId")String itemId,
											 @RequestBody Map<String, String> reqbody){
		if(reqbody.get("name").equals("")||reqbody.get("name").equals(null)||reqbody.get("name")==null||
		   reqbody.get("type").equals("")||reqbody.get("type").equals(null)||reqbody.get("type")==null||
		   reqbody.get("count").equals("")||reqbody.get("count").equals(null)||reqbody.get("count")==null)
		{
			respo.setResponseMessage("Invalid request");
			respo.setResponseCode(400);
			return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
		}
		else
		{
			try {
				Double.parseDouble(reqbody.get("count"));
				return itemservice.updateItem(reqbody, itemId);
			}
			catch(Exception e) {
				respo.setResponseMessage("Invalid request");
				respo.setResponseCode(400);
				return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
			}
		
		}
		
	}

	@PutMapping("/items/{itemId}/deactivate")
	public ResponseEntity<Object> deactivateItem(@PathVariable("itemId")String itemId){
		return itemservice.deactivateItem(itemId);
	}
	
	@PutMapping("/items/{itemId}/activate")
	public ResponseEntity<Object> activateItem(@PathVariable("itemId")String itemId){
		return itemservice.activateItem(itemId);
	}
	
	@DeleteMapping("/items/{itemId}")
	public ResponseEntity<Object> deleteItem(@PathVariable("itemId")String itemId){
		return itemservice.deleteItem(itemId);
	}
}
