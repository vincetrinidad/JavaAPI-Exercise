package com.vince.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vince.demo.model.Response;
import com.vince.demo.model.Type;
import com.vince.demo.model.User;
import com.vince.demo.repository.TypeRepository;
import com.vince.demo.repository.UserRepository;

@Service
public class TypeService {

	@Autowired
	TypeRepository typerepository;
	
	@Autowired
	UserRepository userrepository;
	
	Response respo = new Response();
	
	public ResponseEntity<Object> createType(Type type,String id) { 
		
		List<User> users = new ArrayList<>();
		users = userrepository.findAll();
		
		List<Type> types = new ArrayList<>();
		types = typerepository.findAll();
		
		User currentUser = new User();
		
		int flag=0;
		System.out.println(type);
		try {
				for(int x=0;x<users.size();x++)
				{ 
					if(!id.equals(users.get(x).getUser_id()))
					{
						currentUser = null;
						continue;
						
					}
					else {
						currentUser = users.get(x);
						break;
					}
				}
				
				for(int x=0;x<types.size();x++)
				{
					if(type.getId().equals(types.get(x).getId()))
					{
						flag=0;
						break;
					}
					else 
					{
						flag=1;
						continue;
					}
				}
				
				if(flag==1)
				{
					if(currentUser==null)
					{
						respo.setResponseMessage("No user with id = " + id);
						respo.setResponseCode(400);
						return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
					}
					else
					{
						if(type.getId()==null||type.getId().equals("")||
						   type.getName()==null||type.getName().equals("")||
						   type.getDescription()==null||type.getDescription().equals("")||
						   type.getCost()==null||type.getCost().equals(""))
							{
								respo.setResponseMessage("Invalid request");
								respo.setResponseCode(400);
								return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
							}
						
						else {
								try {
									Double.parseDouble(type.getCost());
									
									Type newType = new Type();
									newType.setId(type.getId());
									newType.setName(type.getName());
									newType.setDescription(type.getDescription());
									newType.setCost(type.getCost());
									
									newType.setDateCreated(LocalDate.now().toString());
									newType.setCreatedBy(currentUser.getUser_name());
									newType.setActive(true);
									
									typerepository.save(newType);
									
									respo.setResponseMessage("Type created successfully");
									respo.setResponseCode(201);
									return new ResponseEntity<>(respo,HttpStatus.CREATED);
								}
								catch(Exception e)
								{
									System.out.println(e);
									respo.setResponseMessage("Invalid request");
									respo.setResponseCode(400);
									return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
								}
								
							 }
					}
				}
				
				else
				{
					respo.setResponseMessage("Type with id = "+id+" already exists");
					respo.setResponseCode(400);
					return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
				}
				
				
		}
		
		catch(Exception e) {
				System.out.println(e);
				respo.setResponseMessage("System error");
				respo.setResponseCode(500);
				return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	public ResponseEntity<Object> readType(){
		try {
			List<Type> types = new ArrayList<>();
			types = typerepository.findAll();
			
			List<Type> activetypes = new ArrayList<>();
			
			for(int x=0;x<types.size();x++)
			{
				if(types.get(x).getActive()==false) {
					continue;
				}
				else {
					activetypes.add(types.get(x));
				}
			}
			return new ResponseEntity<>(activetypes,HttpStatus.FOUND);
		}
		catch(Exception e) {
			respo.setResponseMessage("Server error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.FOUND);
		}
		
	}

	public ResponseEntity<Object> updateType(Map<String,String> body,String type_id) {
		
		try {
				List<Type> list_types = typerepository.findAll();
				int flag=0;
				for(int x=0;x<list_types.size();x++)
				{
					if(type_id.equals(list_types.get(x).getId())&&list_types.get(x).getActive()==true)
					{
						flag=1;
						break;
					}
					else
					{
						flag=0;
						continue;
					}
				}
				
				if(flag==1)
				{
					Type currentType = typerepository.findById(type_id).orElse(null);
					Type updatedType = new Type();
					
					String new_name = body.get("name");
					String new_description = body.get("description");
					String new_cost = body.get("cost");
				
					try {
							Double.parseDouble(new_cost);
							if(new_name==null||new_name.equals("")||
						       new_description==null||new_description.equals("")||
							   new_cost==null||new_cost.equals("")) 
							{
								respo.setResponseMessage("Invalid request");
								respo.setResponseCode(400);
								return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
							}
					
						}
						catch(Exception e) {
							respo.setResponseMessage("Invalid request");
							respo.setResponseCode(400);
							return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
						}
					
					updatedType.setId(type_id);
					updatedType.setName(new_name);
					updatedType.setDescription(new_description);
					updatedType.setCost(new_cost);
					updatedType.setDateCreated(currentType.getDateCreated());
					updatedType.setCreatedBy(currentType.getCreatedBy());
					updatedType.setActive(currentType.getActive());
					
					typerepository.save(updatedType);
					
					return new ResponseEntity<>(updatedType,HttpStatus.OK);
				}
				else
				{
					respo.setResponseMessage("No Type with id = "+type_id+" or type is inactive");
					respo.setResponseCode(400);
					return new ResponseEntity<>(respo,HttpStatus.BAD_REQUEST);
				}
				
			}
		catch(Exception e){
			System.out.println(e);
			respo.setResponseMessage("System error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	public ResponseEntity<Object> deleteType(String type_id){
		List<Type> types = new ArrayList<>();
		types = typerepository.findAll();
		
		try {
			for(int x=0;x<types.size();x++) {
				if(type_id.equals(types.get(x).getId())) {
					
					typerepository.deleteById(type_id);
					respo.setResponseMessage("Successfully deleted");
					respo.setResponseCode(200);
					
				}
				else {
					respo.setResponseMessage("No Type with id = "+type_id);
					respo.setResponseCode(200);
				}
			}
			return new ResponseEntity<>(respo,HttpStatus.OK);
		}
		catch(Exception e) {
			System.out.println(e);
			respo.setResponseMessage("Server error");
			respo.setResponseCode(500);
			return new ResponseEntity<>(respo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	public ResponseEntity<Object> deactivateType(String type_id) { 
		List<Type> types = new ArrayList<>();
		types = typerepository.findAll();
		Type currentType = new Type();
		int flag=0;
		
		try {
			for(int x=0;x<types.size();x++) {
				if(type_id.equals(types.get(x).getId())&&types.get(x).getActive()==true) {
					
					currentType = types.get(x);
					currentType.setActive(false);
					typerepository.save(currentType);
					respo.setResponseMessage("Type with id = "+type_id+" deactivated");
					respo.setResponseCode(200);
					flag=1;
					break;
					
				}
				else {
					respo.setResponseMessage("Type with id = "+type_id+" not found or already deactivated");
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
	
	public ResponseEntity<Object> activateType(String type_id){
		List<Type> types = new ArrayList<>();
		types = typerepository.findAll();
		Type currentType = new Type();
		int flag=0;
		
		try {
			for(int x=0;x<types.size();x++) {
				if(type_id.equals(types.get(x).getId())&&types.get(x).getActive()==false) {
					
					currentType = types.get(x);
					currentType.setActive(true);
					typerepository.save(currentType);
					respo.setResponseMessage("Type with id = "+type_id+" activated");
					respo.setResponseCode(200);
					flag=1;
					break;
					
				}
				else {
					respo.setResponseMessage("Type with id = "+type_id+" not found or already activated");
					respo.setResponseCode(200);
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

//	public void tryy(String type_name) {
//		System.out.println(typerepository.findByname(type_name));
//	}
}


