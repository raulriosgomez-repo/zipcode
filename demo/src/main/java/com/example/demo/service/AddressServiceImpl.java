package com.example.demo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.demo.entity.AddressEntity;
import com.example.demo.repository.AddressRepository;
import com.example.demo.vo.Address;
import com.example.demo.vo.InfoCP;
import com.example.demo.vo.Settlement;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
	
	private static List<Address> addresses;
	
	@Autowired
	private final AddressRepository addressRepository;
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}	
	
	static {
		try   {  
			File file = new File(AddressServiceImpl.class.getClassLoader().getResource("CPdescarga.xml").getFile());    
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			DocumentBuilder db = dbf.newDocumentBuilder();  
			Document doc = db.parse(file);  
			doc.getDocumentElement().normalize();   
			NodeList nodeList = doc.getElementsByTagName("table"); 
			addresses = new ArrayList<>();
			Stream<Node> nodeStream = IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item);
			nodeStream.forEach(ns -> {
				if(ns.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) ns; 
					Address address = new Address();
					address.setZip_code(eElement.getElementsByTagName("d_codigo").item(0).getTextContent());
					address.setFederal_entity(eElement.getElementsByTagName("d_estado").item(0).getTextContent());
					if(null != eElement.getElementsByTagName("d_ciudad").item(0)) {
						address.setLocality(eElement.getElementsByTagName("d_ciudad").item(0).getTextContent());	
					}
					address.setMunicipality(eElement.getElementsByTagName("D_mnpio").item(0).getTextContent());
					Settlement settlement = new Settlement();
					settlement.setName(eElement.getElementsByTagName("d_asenta").item(0).getTextContent());
					settlement.setSettlement_type(eElement.getElementsByTagName("d_tipo_asenta").item(0).getTextContent());
					settlement.setZone_type(eElement.getElementsByTagName("d_zona").item(0).getTextContent());
					List<Settlement> settlements = new ArrayList<>();
					settlements.add(settlement);
					address.setSettlements(settlements);
					addresses.add(address);
				}
			});
		}catch (Exception e){  
			e.printStackTrace();  
		}   
	}

	@Override
	public List<Address> getAddress(String zipCode) {
		List<Address> addressesMemoria = addresses.stream().filter(l -> l.getZip_code().equals(zipCode)).collect(Collectors.toList());
		List<Address> addressesResponse = new ArrayList<>();
		addressesMemoria.stream().forEach(p -> {
			List<Settlement> settlements = new ArrayList<>();
			p.getSettlements().stream().forEach(s -> {
				Settlement settlement = new Settlement().builder()
						.name(s.getName())
						.settlement_type(s.getSettlement_type())
						.zone_type(s.getZone_type()).build();
				settlements.add(settlement);
			});
			Address address = new Address().builder()
				.federal_entity(p.getFederal_entity())
				.locality(p.getLocality())
				.municipality(p.getMunicipality())
				.zip_code(p.getZip_code())
				.settlements(settlements).build();
			addressesResponse.add(address);
		});
		return addressesResponse;
	}

	@Override
	public InfoCP getAddressByWebService(String zipCode) {
		return restTemplate().getForObject("https://api-sepomex.hckdrk.mx/query/info_cp/"+zipCode+"?type=simplified",InfoCP.class);
	}

	@Override
	public List<Address> getAddressEntity(String zipCode) {
		List<AddressEntity> addressesDataBase = addressRepository.findByzipcode(zipCode);
		List<Address> addressesResponse = new ArrayList<>();
		addressesDataBase.stream().forEach(p -> {
			List<Settlement> settlements = new ArrayList<>();
				Settlement settlement = new Settlement().builder()
						.name(p.getName())
						.settlement_type(p.getSettlement_type())
						.zone_type(p.getZone_type()).build();
				settlements.add(settlement);
			Address address = new Address().builder()
				.federal_entity(p.getFederal_entity())
				.locality(p.getLocality())
				.municipality(p.getMunicipality())
				.zip_code(p.getZipcode())
				.settlements(settlements).build();
			addressesResponse.add(address);
		});
		return addressesResponse;
	}  
}

