package com.vince.demo;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.vince.demo.model.Item;
import com.vince.demo.model.Type;
import com.vince.demo.repository.ItemRepository;
import com.vince.demo.repository.TypeRepository;
import com.vince.demo.service.ItemService;

@RunWith(SpringRunner.class)
@SpringBootTest
class ItemServiceTest {
	
	@Autowired
	private ItemService itemservice;
	
	@MockBean
	private ItemRepository itemrepository;
	
	@MockBean
	private TypeRepository typerepository;
	
	Type type1 = new Type();
	Type type2 = new Type();
	Type type3 = new Type();
	Type type4 = new Type();
	List<Type> list_types = new ArrayList<>();
	
	Item item1 = new Item();
	Item item2 = new Item();
	Item item3 = new Item();
	List<Item> list_items = new ArrayList<>();
	
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
		
		list_types.add(type1);
		list_types.add(type2);
		list_types.add(type3);
		list_types.add(type4);
		
		item1.setId("5445b34b-cd1a-4de1-960e-bb42e7a8c4c1");
		item1.setName("disruptor");
		item1.setType("shoes");
		item1.setDateCreated(LocalDate.now().toString());
		item1.setCount("15");
		item1.setActive(false);
		
		item2.setId("5d931ed0-e7fd-4d27-92cc-c7dcdfc2afa4");
		item2.setName("airforce1_low");
		item2.setType("shoes");
		item2.setDateCreated(LocalDate.now().toString());
		item2.setCount("5");
		item2.setActive(true);
		
		list_items.add(item1);
		list_items.add(item2);
	}
	

	@Test
	void createItem() {
		Item new_item = new Item();
		new_item.setId(UUID.randomUUID().toString());
		new_item.setName("newITEM");
		new_item.setType("shoes");
		new_item.setDateCreated(LocalDate.now().toString());
		new_item.setCount("15");
		new_item.setActive(true);
		
		Mockito.when(typerepository.findAll()).thenReturn(list_types);
		Mockito.when(itemrepository.save(new_item)).thenReturn(new_item);
		
		assertThat(itemservice.createItem(new_item).getStatusCodeValue()).isEqualTo(200);
		
	}
	
	@Test
	void readItem() {
		String item_id = "5d931ed0-e7fd-4d27-92cc-c7dcdfc2afa4";
		Mockito.when(itemrepository.findAll()).thenReturn(list_items);
		Mockito.when(itemrepository.findById(item_id)).thenReturn(Optional.of(item2));
		Mockito.when(typerepository.findByname(item1.getType())).thenReturn(type1);
		
		assertThat(itemservice.readItem(item_id).getStatusCodeValue()).isEqualTo(302);
	}
	
	@Test
	void readItems() {
		Mockito.when(itemrepository.findAll()).thenReturn(list_items);
		assertThat(itemservice.readItems().getStatusCodeValue()).isEqualTo(302);
	}
	
	@Test
	void updateItem() {
		String item_id = "5d931ed0-e7fd-4d27-92cc-c7dcdfc2afa4";
		Mockito.when(itemrepository.findAll()).thenReturn(list_items);
		Mockito.when(typerepository.findAll()).thenReturn(list_types);
		
		Map<String, String> map = new HashMap<>();
		map.put("item_name", "updated");
		map.put("item_type", "food");
		map.put("item_count", "15");
		
		Mockito.when(itemrepository.save(item2)).thenReturn(item2);
		
		assertThat(itemservice.updateItem(map,item_id).getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	void deactivateItem() {
		String item_id = "5d931ed0-e7fd-4d27-92cc-c7dcdfc2afa4";
		Mockito.when(itemrepository.findAll()).thenReturn(list_items);
		Mockito.when(itemrepository.save(item2)).thenReturn(item2);
		
		assertThat(itemservice.deactivateItem(item_id).getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	void activateItem() {
		String item_id = "5445b34b-cd1a-4de1-960e-bb42e7a8c4c1";
		Mockito.when(itemrepository.findAll()).thenReturn(list_items);
		Mockito.when(itemrepository.save(item1)).thenReturn(item1);
		
		assertThat(itemservice.activateItem(item_id).getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	void getTotalCountItems() {
		Mockito.when(itemrepository.findAll()).thenReturn(list_items);
		
		assertThat(itemservice.getTotalCountItems().getStatusCodeValue()).isEqualTo(200);
	}

}
