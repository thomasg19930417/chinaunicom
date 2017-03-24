package com.asainfo.mapper;

import java.util.List;

import com.asainfo.pojo.PageBean;
import com.asainfo.pojo.Shell;

public interface ShellMapper {

	int save(Shell shell);

	List<Shell> getPage(PageBean pageBean);

	int getcount();

	Shell getShellById(Integer id);

}
