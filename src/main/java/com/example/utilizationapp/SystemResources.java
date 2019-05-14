package com.example.utilizationapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Ankit
 *
 */
@RestController
public class SystemResources {

	@RequestMapping("/get")
	public String getUsers() {
		return "Test Spring boot";
	}
	
	@RequestMapping(value = "/retrieve", consumes = "application/json", method = RequestMethod.POST)
	public  String getUsersData(@RequestBody String obj) throws IOException {
		/* { album: , filename} */
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map1 = new HashMap<String, Object>();
		try {
			map1 = mapper.readValue(obj, new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(map1.get("album"));
		String ablum= (String) map1.get("album");
		
		
		SystemResourcesDB db = new SystemResourcesDB();
		List<ImageStore> list = db.showImagebasedonAlbum(ablum);
		
		
//		
//		String path="\\images\\";
//		if(ablum != "")
//		{
//			path = path.concat(ablum+"\\");
//		}
//		File folder = new File(path); // C:\images\
//	
//		System.out.println("Path"+folder.getAbsolutePath());
//		File[] listOfFiles = folder.listFiles();
//		
//		List<ImageStore> list = new ArrayList<>();
//
//		Map<String, Object> filemap = new HashMap<String, Object>();
//		
//		if(listOfFiles==null){
//			return "folder not found";
//		}
//		
//		for (int i = 0; i < listOfFiles.length; i++) {
//	
//			if (listOfFiles[i].isFile()) {
//				File temp = listOfFiles[i];
//				// System.out.println("File " + listOfFiles[i].getName());
//				
//				String filePath = temp.getAbsolutePath();
//				ImageStore img = new ImageStore(temp.getName(), temp.getParent(),  Files.readAllBytes( Paths.get(filePath)));
//				list.add(img);
//			} 
//			else if (listOfFiles[i].isDirectory()) {
//				System.out.println("Directory " + listOfFiles[i].getName());
//				
//				
//			}
//		}
//	
		mapper = new ObjectMapper();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);

		String jsonResp = null;
		try {
			jsonResp = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonResp;
	}

	
	@RequestMapping(value = "/delete", consumes = "application/json", method = RequestMethod.POST)
	public static String delete(@RequestBody String obj) {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = mapper.readValue(obj, new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(map.get("filename"));
		System.out.println(map.get("foldername"));
		System.out.println(map.get("level")); //one or all(true)= album delete
		
		String flag =  (String) map.get("level");
		String filename= (String) map.get("filename");
		String album = (String) map.get("foldername");
		SystemResourcesDB db = new SystemResourcesDB();
		
		boolean result=false;
		if(flag.equals("true")){
			result= db.deleteImageFolder(album);
		}else{
			result= db.deleteImage(album,filename);
		}
		
	//	File file = new File("\\images\\" +map.get("foldername")+"\\"+ map.get("filename").toString()); // C:\images\aaa.jpg
	//	System.out.println("here :"+file.getAbsolutePath());
		if (!result) {
			return "Deleted successfully";
		} else {
			return "Failed to delete";
		}
	}

	    @RequestMapping(value = "/storeImage", consumes = "application/json" , produces="application/json",  method = RequestMethod.POST)
	    public static String storeImage(@RequestBody String  obj) throws IOException {
		 
		 ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				map = mapper.readValue(obj, new TypeReference<Map<String, Object>>() {
				});
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(map.get("filename"));
			System.out.println(map.get("album"));
			
			String filename =(String) map.get("filename");
			String album = (String) map.get("album");
			String content =(String) map.get("content");
			
			String ar[] = content.split("base64,");
			
	       byte[] decoded = DatatypeConverter.parseBase64Binary(ar[1]);
//	        
//			File directory = new File("\\images\\"+album);
//			if (! directory.exists()){
//		        directory.mkdir();
//		    }
			
			SystemResourcesDB db = new SystemResourcesDB();
			db.storeImage(album, decoded, filename);
			
			
//			System.out.println(">>>>>>>>>>>>"+new ByteArrayInputStream(decoded));
//			
//			BufferedImage image = ImageIO.read(new ByteArrayInputStream(decoded));
//
//			ImageIO.write(image, "JPEG", new File("\\images\\"+album+"\\"+filename)); // C:\images\
		
			return "success";
	    }
	
	 
	
	
	
}