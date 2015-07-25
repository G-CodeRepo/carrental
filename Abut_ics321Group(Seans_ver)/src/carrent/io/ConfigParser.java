package carrent.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;

public class ConfigParser {
	
	private HashMap<String, String> stringFields;
	private HashMap<String, Integer> intFields;
	private boolean loadedOkay;
	
	public ConfigParser(File file){
		
		stringFields = new HashMap<>();
		intFields = new HashMap<>();
		
		try{
			populateMaps(file);
			loadedOkay = true;
		}
		catch(IOException e){
			System.out.println("WARNING - Unable to read from config file");
			loadedOkay = false;
		}
		
	}
	
	public String getString(String key){
		return stringFields.get(key);
	}
	
	public Integer getInt(String key){
		return intFields.get(key);
	}
	
	public boolean getBooleanFromInt(String key, boolean defaultValue){
		if(intFields.get(key) == null){
			System.out.println("WARNING - Unrecognized cofig key \"" + key + "\"");
			return defaultValue;
		}
		return intFields.get(key) != 0;
	}
	
	public boolean loadedOkay(){
		return loadedOkay;
	}
	
	private void populateMaps(File file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String next;
		int line = 1;
		
		while((next = reader.readLine()) != null){
			if(next.length() == 0 || next.startsWith(";")){
				line++;
				continue;
			}
			String[] data = next.split("=");
			if(data.length != 2){
				System.out.println("WARNING - Malformed config entry on line " + line + " in " + file.getName());
				line++;
				continue;
			}
			try{
				intFields.put(data[0].trim(), Integer.parseInt(data[1].trim()));
			}
			catch(NumberFormatException e){
				stringFields.put(data[0].trim(), data[1].trim());
			}
			line++;
		}
		reader.close();
	}
	
}
