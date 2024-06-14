package com.ddukddak.manager.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
public class ManagerServiceImpl implements ManagerService {
	
}
