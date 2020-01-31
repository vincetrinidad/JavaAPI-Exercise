package com.vince.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.vince.demo.model.Type;
import com.vince.demo.model.User;
import com.vince.demo.repository.TypeRepository;
import com.vince.demo.repository.UserRepository;
import com.vince.demo.service.TypeService;

@RunWith(SpringRunner.class)
@SpringBootTest
class TypeServiceTest {

	@Autowired
	private TypeService typeservice;
	
	@MockBean
	private TypeRepository typerepository;
	
	@MockBean
	private UserRepository userrepository;
	
	
	Type type1 = new Type();
	Type type2 = new Type();
	Type type3 = new Type();
	Type type4 = new Type();
	
	Type newtype = new Type();	
	User user1 = new User();
	List<User> list_users = new ArrayList<>();
	List<Type> list_types = new ArrayList<>();
//	@Autowired
//	Response respo;
	
	@BeforeEach
    void init() {
		
		type1.setId("01");
		type1.setName("shoes");
		type1.setDescription("footwear");
		type1.setCost("500");
		type1.setCreatedBy("Vince");
		type1.setDateCreated(LocalDate.now().toString());
		type1.setActive(true);
		
		
		type2.setId("02");
		type2.setName("gadgets");
		type2.setDescription("entertainment");
		type2.setCost("500");
		type2.setCreatedBy("Vince");
		type2.setDateCreated(LocalDate.now().toString());
		type2.setActive(true);
		
		
		type3.setId("03");
		type3.setName("food");
		type3.setDescription("eligible");
		type3.setCost("500");
		type3.setCreatedBy("Vince");
		type3.setDateCreated(LocalDate.now().toString());
		type3.setActive(true);
		
		type4.setId("04");
		type4.setName("update");
		type4.setDescription("bread");
		type4.setCost("500");
		type4.setCreatedBy("Vince");
		type4.setDateCreated(LocalDate.now().toString());
		type4.setActive(true);
		
		
		newtype.setId("05");
		newtype.setName("test_name");
		newtype.setDescription("test_description");
		newtype.setCost("500");
		newtype.setCreatedBy("Vince");
		newtype.setDateCreated(LocalDate.now().toString());
		newtype.setActive(true);
		
		user1.setUser_id("1");
		user1.setUser_name("Vince");
		
		
		list_users.add(user1);
		
		list_types.add(type1);
		list_types.add(type2);
		list_types.add(type3);
		list_types.add(type4);
    }
	
	@Test
	void createTypeTest() {
		Mockito.when(typerepository.findAll()).thenReturn(list_types);
		Mockito.when(userrepository.findAll()).thenReturn(list_users);
		Mockito.when(typerepository.save(newtype)).thenReturn(newtype);
		
		assertThat(typeservice.createType(newtype, "1").getStatusCodeValue()).isEqualTo(201);
	}

	@Test
	void readTypes() {
		Mockito.when(typerepository.findAll()).thenReturn(list_types);
		
		assertThat(typeservice.readType().getStatusCodeValue()).isEqualTo(302);
	}

	@Test
	void updateType() { 
		String type_id = "01";
		Mockito.when(typerepository.findAll()).thenReturn(list_types);
		Mockito.when(typerepository.findById(type_id)).thenReturn(Optional.of(type1));
		
		Type currentType = typerepository.findById(type_id).orElse(null);
		Map<String, String> map = new HashMap<>();
		map.put("type_name", "name_update");
		map.put("type_description", "description_update");
		map.put("type_cost", "500");
		
		Type updateType = new Type();
		
		updateType.setId(type_id);
		updateType.setName(map.get("type_name"));
		updateType.setDescription("type_description");
		updateType.setCost("type_cost");
		updateType.setDateCreated(currentType.getDateCreated());
		updateType.setCreatedBy(currentType.getCreatedBy());
		updateType.setActive(currentType.getActive());
		
		Mockito.when(typerepository.save(updateType)).thenReturn(updateType);
		assertThat(typeservice.updateType(map, type_id).getStatusCodeValue()).isEqualTo(200);
			
	}
	
//	@Test
//	void deleteType() {
//		String type_id = "06";
//		Mockito.when(typerepository.findAll()).thenReturn(list_types);
//		Mockito.when(typerepository.deleteById(type_id)).thenReturn(list_types);
//		Mockito.ca
//	
//		assertThat(typeservice.deleteType(type_id).getStatusCodeValue()).isEqualTo(200);
//	}
	
	@Test
	void deactivateType() {
		String type_id = "01";
		Mockito.when(typerepository.findAll()).thenReturn(list_types);
		Mockito.when(typerepository.save(type1)).thenReturn(type1);
		
		assertThat(typeservice.deactivateType(type_id).getStatusCodeValue()).isEqualTo(200);
		
	}
	
	@Test
	void activateType() {
		String type_id = "03";
		Mockito.when(typerepository.findAll()).thenReturn(list_types);
		Mockito.when(typerepository.save(type1)).thenReturn(type1);
		
		assertThat(typeservice.deactivateType(type_id).getStatusCodeValue()).isEqualTo(200);
		
	}
	






}
