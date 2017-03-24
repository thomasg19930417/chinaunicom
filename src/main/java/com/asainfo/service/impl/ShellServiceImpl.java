package com.asainfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asainfo.mapper.ShellMapper;
import com.asainfo.pojo.Message;
import com.asainfo.pojo.PageBean;
import com.asainfo.pojo.Shell;
import com.asainfo.service.ShellService;
@Service
public class ShellServiceImpl implements ShellService {
	@Autowired
	private ShellMapper shellMapper;
	public Message save(Shell shell) {
		int id = shellMapper.save(shell);
		Message  message = new Message();
		message.setData(shell);
		message.setStatus(true);
		message.setMsg("条件成功");
		return message;
	}
	public Message getlist(PageBean pageBean) {
		pageBean.setCurrentPage((pageBean.getCurrentPage() - 1)
				* pageBean.getPageNum());
		List<Shell> task = this.shellMapper.getPage(pageBean);
		int count = this.shellMapper.getcount();
		count = count % pageBean.getPageNum() == 0 ? count
				/ pageBean.getPageNum() : count / pageBean.getPageNum() + 1;
		pageBean.setCount(count);
		pageBean.setData(task);
		Message message = new Message();
		message.setData(pageBean);
		return message;
	}
	public Shell getShellById(Integer id) {
		return shellMapper.getShellById(id);
	}

}
