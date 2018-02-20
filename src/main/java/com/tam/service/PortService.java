package com.tam.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tam.model.Port;
import com.tam.repositories.PortRepository;

@Service("portService")
public class PortService {
	@Autowired
	private PortRepository portRepository;
	
	@Transactional
	public List<Port> findAll(){
		 return portRepository.findAll();
		
	}

	@Transactional
	public List<String> listPortNames() {
		List<String> ports = new ArrayList<String>();
		for(Port port : findAll())
			ports.add(port.getName());
		return ports;
	}
	
	@Transactional
	public List<String> listPortCodes() {
		List<String> codes = new ArrayList<String>();
		for(Port port : findAll())
			codes.add(port.getCode());
		return codes;
	}

}
