package com.vince.demo.service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vince.demo.model.Item;
import com.vince.demo.model.ItemResponse;
import com.vince.demo.model.Response;
import com.vince.demo.model.Type;
import com.vince.demo.repository.ItemRepository;
import com.vince.demo.repository.TypeRepository;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemrepository;
	
	@Autowired
	TypeRepository typerepository;
	
	Response respo = new Response();
	
	public ResponseEntity<Object> createItem(Item new_item) {
		
		try {
				List<Type> types = typerepository.findAll();
				//Item new_item = new Item();
				UUID uuid = UUID.randomUUID();	
				int flag=0;
		
				for(int x=0;x<types.size();x++)
				{
					if(new_item.getType()==types.get(x).getName()||
					   new_item.getType().equalsIgnoreCase(types.get(x).getName())) {
						flag=1;
						break;	
					}
					else
					{
						respo.setResponseMessage("Select valid and active Item type");
						respo.setResponseCode(400);
						flag=0;
						continue;
						
					}
				}
				
				if(flag==1) 
				{
					new_item.setId(uuid.toString());
					new_item.setDateCreated(LocalDate.now().toString());
					itemrepository.save(new_item);
					
					respo.setResponseMessage("Successfully created Item");
					respo.setResponseCode(201);
					return new ResponseEntity<>(respo,HttpStatus.CREATED);
				}
				else
				{
					return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
				}
					
			}
		
		catch(Exception e) {
			System.out.println(e);
			respo.setResponseMessage("Server error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}

	
	public ResponseEntity<Object> readItem(String itemId){
		try {
				Item item = new Item();
				item=null;
				Type type = new Type();
				List<Item> items = itemrepository.findAll();
				
				ItemResponse final_item = new ItemResponse();
				
				for(int x=0;x<items.size();x++) 
				{
					if(!itemId.equals(items.get(x).getId()))
					{
						continue;
					}
					else 
					{
						item = itemrepository.findById(itemId).orElse(null);
						break;
					}	
				}
				
				
				if(item==null)
				{
					respo.setResponseMessage("No Item with id = "+itemId);
					respo.setResponseCode(404);
					return new ResponseEntity<>(respo,HttpStatus.NOT_FOUND);
				}
				else
				{
					if(item.getActive()==true) 
					{
						type = typerepository.findByname(item.getType());
						
						final_item.setId(item.getId());
						final_item.setName(item.getName());
						final_item.setType(item.getType());
						final_item.setDescription(type.getDescription());
						final_item.setCost(Double.parseDouble(type.getCost()));
						final_item.setDateCreated(item.getDateCreated());
						final_item.setCount(Integer.parseInt(item.getCount()));
						
						return new ResponseEntity<>(final_item,HttpStatus.FOUND);
					}
					else 
					{
						respo.setResponseMessage("Item with id = "+itemId + " is inactive");
						respo.setResponseCode(404);
						return new ResponseEntity<>(respo,HttpStatus.NOT_FOUND);
					}
				}
				
		}
		catch(Exception e) {
			System.out.println(e);
			respo.setResponseMessage("Server error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public ResponseEntity<Object> readItems(){
		try {
			List<Item> items = itemrepository.findAll();
			List<Item> active_items = new ArrayList<>();
			
			for(int x=0;x<items.size();x++) 
			{
				if(items.get(x).getActive()==false)
				{
					continue;
				}
				else
				{
					active_items.add(items.get(x));
				}
			}
			if(active_items.isEmpty())
			{
				respo.setResponseMessage("Currently no active items");
				respo.setResponseCode(404);
			
				return new ResponseEntity<>(respo,HttpStatus.NOT_FOUND);
			}
			else
			{
				return new ResponseEntity<>(active_items,HttpStatus.FOUND);
			}
			
		}
		catch(Exception e) {
			System.out.println(e);
			respo.setResponseMessage("Server error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	public ResponseEntity<Object> getTotalCountItems() {
		try {
			int total_counts = 0;
			List<Item> items = itemrepository.findAll();
			for(int x=0;x<items.size();x++) {
				if(items.get(x).getActive()==true) 
				{
					total_counts += Integer.parseInt(items.get(x).getCount());
				}
				else 
				{
					continue;
				}
			}
			
			Map<String, String> map = new HashMap<String, String>();
		    map.put("total items count: ", String.valueOf(total_counts));

			return new ResponseEntity<>(map,HttpStatus.OK);
		}
		catch(Exception e) {
			System.out.println(e);	
			respo.setResponseMessage("Server error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public ResponseEntity<Object> getTotalCostItem() {
		try {
			double total_cost = 0.00;
			List<Item> items = itemrepository.findAll();
			
			for(int x=0;x<items.size();x++) {
				if(items.get(x).getActive()==true) 
				{
					Type type = new Type();
					type = typerepository.findByname(items.get(x).getType());
					total_cost += Double.parseDouble(type.getCost())*Integer.parseInt(items.get(x).getCount());
				}
				else 
				{
					continue;
				}
			}
			
			Map<String, String> map = new HashMap<String, String>();
			NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
			String formattedtotal_cost = nf.format(total_cost);
		    map.put("total items cost: ", String.valueOf(formattedtotal_cost.substring(1, String.valueOf(formattedtotal_cost).length())));

			return new ResponseEntity<>(map,HttpStatus.OK);
		}
		catch(Exception e) {
			System.out.println(e);
			respo.setResponseMessage("Server error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<Object> updateItem(Map<String, String> reqbody, String itemId) {
		
		try {
				List<Item> list_items = itemrepository.findAll();
				List<Type> list_types = typerepository.findAll();
				Item updated_item = new Item();
				ItemResponse item_response = new ItemResponse();
				Type current_type = new Type();
				Item current_item = new Item();
				int flag1 = 0,flag2 = 0;
				
				
				for(int x=0;x<list_items.size();x++)
				{
					if(itemId.equals(list_items.get(x).getId()))
						{
							current_item = list_items.get(x);
							flag1=1;
							break;
						}
					
					else 
						{
							respo.setResponseMessage("Invalid request");
							respo.setResponseCode(400);
							flag1=0;
							continue;
						}
				}
				
				for(int x=0;x<list_types.size();x++) 
				{
					if(reqbody.get("type").equalsIgnoreCase(list_types.get(x).getName()))
					{
						current_type = list_types.get(x);
						flag2=1;
						break;
					}
					else
					{
						respo.setResponseMessage("Invalid request");
						respo.setResponseCode(400);
						flag2=0;
						continue;
					}
				}
			
			
				if(flag1==1&&flag2==1)
					{
						updated_item.setId(current_item.getId());
						updated_item.setName(reqbody.get("name"));
						updated_item.setType(current_type.getName());
						updated_item.setDateCreated(current_item.getDateCreated());
						updated_item.setCount(reqbody.get("count"));
						updated_item.setActive(current_item.getActive());
						itemrepository.save(updated_item);
							
						item_response.setId(updated_item.getId());
						item_response.setName(updated_item.getName());
						item_response.setType(updated_item.getType());
						item_response.setDescription(current_type.getDescription());
						item_response.setCost(Double.parseDouble(current_type.getCost()));
						item_response.setDateCreated(updated_item.getDateCreated());
						item_response.setCount(Integer.parseInt(updated_item.getCount()));
			
						return new ResponseEntity<>(item_response,HttpStatus.OK);
					}
				else
					{
						return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
					}
				
			
		}
		catch(Exception e) {
			System.out.println("service " + e);
			respo.setResponseMessage("Server error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> deactivateItem(String itemId) {
		try {
				List<Item> list_items = itemrepository.findAll();
				Item current_item = new Item();
				int flag=0;
				
				for(int x=0;x<list_items.size();x++)
				{
					if(itemId.equals(list_items.get(x).getId())&&list_items.get(x).getActive()==true)
					{
						current_item = list_items.get(x);
						current_item.setActive(false);
						itemrepository.save(current_item);
						
						respo.setResponseMessage("Item with id = " +itemId+ " deactivated");
						respo.setResponseCode(200);
						flag=1;
						break;
					}
					else
					{
						respo.setResponseMessage("Item with id = " +itemId+ " not found or already deactivated");
						respo.setResponseCode(404);
						flag=0;
						continue;
					}
				}
				
				if(flag==1)
				{
					return new ResponseEntity<>(respo,HttpStatus.OK);
				}
				
				else
				{
					return new ResponseEntity<>(respo,HttpStatus.NOT_FOUND);
				}
			
					

		}
		catch(Exception e) {
			System.out.println("(service) " + e);
			respo.setResponseMessage("Server error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<Object> activateItem(String itemId) {
		try {
				List<Item> list_items = itemrepository.findAll();
				Item current_item = new Item();
				int flag=0;
				
				for(int x=0;x<list_items.size();x++)
				{
					if(itemId.equals(list_items.get(x).getId())&&list_items.get(x).getActive()==false)
					{
						current_item = list_items.get(x);
						current_item.setActive(true);
						itemrepository.save(current_item);
						
						respo.setResponseMessage("Item with id = " +itemId+ " activated");
						respo.setResponseCode(200);
						flag=1;
						break;
					}
					else
					{
						respo.setResponseMessage("Item with id = " +itemId+ " not found or already activated");
						respo.setResponseCode(400);
						flag=0;
						continue;
					}
				}
				
				if(flag==1)
				{
					return new ResponseEntity<>(respo,HttpStatus.OK);
				}
				
				else
				{
					return new ResponseEntity<>(respo,HttpStatus.NOT_FOUND);
				}
			
		}
		catch(Exception e) {
			System.out.println("(service) " + e);
			respo.setResponseMessage("Server error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<Object> deleteItem(String itemId) {
		try {
				List<Item> list_items = itemrepository.findAll();
				
				for(int x=0;x<list_items.size();x++)
				{
					if(itemId.equals(list_items.get(x).getId()))
					{
						itemrepository.deleteById(list_items.get(x).getId());
						respo.setResponseMessage("Item with id = "+itemId+" is successfully deleted");
						respo.setResponseCode(200);
					}
					else
					{
						respo.setResponseMessage("No Item with id = "+itemId);
						respo.setResponseCode(200);
					}
				}
				return new ResponseEntity<>(respo,HttpStatus.OK);
			
		}
		catch(Exception e) {
			System.out.println("(service )" + e);
			respo.setResponseMessage("Server error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
	
}
