package com.gx.job;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gx.service.IUserService;

@Component
public class TimerTask {

	@Autowired
	private IUserService userService;

	// 在项目启动、每天的0:0:0执行
	@PostConstruct
	@Scheduled(cron = "0 0 0 * * ?")
	public void updateUserInfor() {
		int num = userService.selectAllUserInfo();
		if (num == 1) {
			System.out.println("用户详情每日更新成功：");
		} else {
			System.out.println("用户详情每日更新失败：");
		}
	}

}
