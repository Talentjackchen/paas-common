package com.wondersgroup.cloud.paas.common.utils;

import com.wondersgroup.cloud.paas.common.pojo.PageVO;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {
	public static Integer getParamToInt(Integer param, Integer defaultValue) {
		Integer num = defaultValue;
		if (param != null && param > 0) {
			num = param;
		}
		return num;
	}

	public static String getOrderByClause(String orderColumn, String orderRule, String defaultOrderByClause) {
		// orderColumn 排序字段
		// orderRule 排序规则：ASC/DESC缺省为：DESC
		// 获取排序信息
		String orderByClause = defaultOrderByClause;
		// 构造排序子句
		if (StringUtils.isNotBlank(orderColumn)) {
			orderByClause = orderColumn + " DESC";
			if (StringUtils.isNotBlank(orderRule)) {
				orderByClause = orderColumn + " " + orderRule;
			}
		}
		return orderByClause;
	}

	public static <E> PageVO<E> pageByList(int pageNum, int pageSize, List<E> infoList) {
		List<E> iList = new ArrayList<E>();
		int begin = (pageNum - 1) * pageSize;
		int end = pageNum * pageSize;

		for (int i = 0; i < infoList.size(); i++) {
			if (i >= begin && i < end) {
				iList.add(infoList.get(i));
			}
		}

		PageVO<E> pageVO = new PageVO<E>();
		pageVO.setList(iList);
		pageVO.setPageNum(pageNum);
		pageVO.setPageSize(pageSize);
		if (infoList.size() % pageSize == 0) {
			pageVO.setPages(infoList.size() / pageSize);
			pageVO.setSize(infoList.size() / pageSize);
		} else {
			pageVO.setPages(infoList.size() / pageSize + 1);
			pageVO.setSize(infoList.size() / pageSize + 1);
		}
		pageVO.setTotal(infoList.size());
		return pageVO;
	}

}
