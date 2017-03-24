package com.asainfo.service;

import com.asainfo.pojo.Message;
import com.asainfo.pojo.PageBean;
import com.asainfo.pojo.Shell;

public interface ShellService {

	Message save(Shell shell);

	Message getlist(PageBean pageBean);

	Shell getShellById(Integer id);

}
