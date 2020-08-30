package com.example.demo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.demo.vo.Address;
import com.example.demo.vo.Settlement;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
	
	private static List<Address> addresses;
	
	static {
		try   {  
			File file = new File("C:\\\\Users\\\\LAPTOPRAUL\\\\Desktop\\\\CPdescargaxml\\\\CPdescarga.xml");    
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
	
	public static void main(String argv[]){  
		try   {  
			//creating a constructor of file class and parsing an XML file  
			File file = new File("C:\\\\Users\\\\LAPTOPRAUL\\\\Desktop\\\\CPdescargaxml\\\\CPdescarga.xml");  
			//an instance of factory that gives a document builder  
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			//an instance of builder to parse the specified xml file  
			DocumentBuilder db = dbf.newDocumentBuilder();  
			Document doc = db.parse(file);  
			doc.getDocumentElement().normalize();  
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
			NodeList nodeList = doc.getElementsByTagName("table"); 
			System.out.println("nodeList.getLength() =" + nodeList.getLength());
			// nodeList is not iterable, so we are using for loop
			List<Address> addresses = new ArrayList<>();
			
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
//			for (int itr = 0; itr < nodeList.getLength(); itr++){  
//				Node node = nodeList.item(itr);  
//				//System.out.println("\nNode Name :" + node.getNodeName());
//				if (node.getNodeType() == Node.ELEMENT_NODE){  
//					Element eElement = (Element) node; 
//					Address address = new Address();
//					address.setZip_code(eElement.getElementsByTagName("d_codigo").item(0).getTextContent());
//					address.setFederal_entity(eElement.getElementsByTagName("d_estado").item(0).getTextContent());
//					if(eElement.getElementsByTagName("d_ciudad").item(0)!=null) {
//						address.setLocality(eElement.getElementsByTagName("d_ciudad").item(0).getTextContent());	
//					}
//					address.setMunicipality(eElement.getElementsByTagName("D_mnpio").item(0).getTextContent());
//					Settlement settlement = new Settlement();
//					settlement.setName(eElement.getElementsByTagName("d_asenta").item(0).getTextContent());
//					settlement.setSettlement_type(eElement.getElementsByTagName("d_tipo_asenta").item(0).getTextContent());
//					settlement.setZone_type(eElement.getElementsByTagName("d_zona").item(0).getTextContent());
//					List<Settlement> settlements = new ArrayList<>();
//					settlements.add(settlement);
//					address.setSettlements(settlements);
//					addresses.add(address);
//				}  
//			}  	
			List<Address> addressesResponse = addresses.stream().filter(l -> l.getZip_code().equals("56346")).collect(Collectors.toList());
			addressesResponse.stream().forEach(p -> {
				System.out.println("getMunicipality = " + p.getMunicipality());
				System.out.println("getFederal_entity = " + p.getFederal_entity());
				System.out.println("getLocality = " + p.getLocality());
				System.out.println("getZip_code = " + p.getZip_code());
				p.getSettlements().stream().forEach(s -> {
					System.out.println("getName = " + s.getName());
					System.out.println("getSettlement_type = " + s.getSettlement_type());
					System.out.println("getZone_type = " + s.getZone_type());
				});
			});
		}catch (Exception e){  
			e.printStackTrace();  
		}  
	}  
}

