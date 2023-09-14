package com.qxbase.blog.server.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qxbase.blog.server.data.result.Result;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

// 第一种方式
//			{
//				"pageIndex"
//				"trims" : {
//					"asd": asd
//					},
//				"range": {
//					"lt" : {
//						"a": a,
//						"b": b
//					},
//					"gt": {
//                      "a": a,
//  					"b": b
//					},
//                  "between" : {
//                      "a": [b, c]
//                  }
//				}
//			}
// {

// 第二种方式
// 	"eq-stu": asd,
//  "lt-stu": asd,

/**
 * @author ALsW
 */
@Data
@Accessors(chain = true)
@Slf4j
@ApiModel("分页参数")
public class PageParam {

	@ApiModelProperty(value = "当前页", example = "1", required = true)
	private Integer pageIndex;
	@ApiModelProperty(value = "页大小", example = "10", position = 1, required = true)
	private Integer pageSize;

	@ApiModelProperty(value = "精确查询", position = 2, example = "{\"sampleName\": \"sampleValue\"}")
	private HashMap<String, Object> trims;

	@ApiModelProperty(value = "模糊查询", position = 3, example = "{\"sampleName\": \"sampleValue\"}")
	private HashMap<String, Object> likes;

	@ApiModelProperty(value = "范围查询", position = 4, example = "{\"sampleName\": [\"sampleValue1\",\"sampleValue2\"]}")
	private HashMap<String, Object[]> ranges;

	@ApiModelProperty(value = "排序 true为正序， false为倒序", position = 5, example = "{\"true\": [\"sampleValue1\", \"sampleValue2\"]}")
	private HashMap<Boolean, ArrayList<String>> orderBys;

	private static final Pattern UPPER_REG = Pattern.compile("[A-Z]");
	private static final Pattern TIME_REG = Pattern.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|" +
			"((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|" +
			"((0[48]|[2468][048]|[3579][26])00))-02-29)$");


	public static <T> QueryWrapper<T> buildSearchWrapper(PageParam pageParam) {
		return buildSearchWrapper(pageParam, null);
	}

	public static <T> QueryWrapper<T> buildSearchWrapper(PageParam pageParam, Class<?> aliaClazz) {
		// 构建条件查询 wrapper 获取精准查询字段与条件
		HashMap<String, Object> trims = pageParam.getTrims();
		// 获取模糊查询字段与条件
		HashMap<String, Object> likes = pageParam.getLikes();
		HashMap<String, Object[]> ranges = pageParam.getRanges();
		// 获取范围查询字段与条件
		HashMap<Boolean, ArrayList<String>> orderBys = pageParam.getOrderBys();

		QueryWrapper<T> wrapper = new QueryWrapper<>();

		if (trims != null) {
			trims(wrapper, trims, aliaClazz);
		}

		if (likes != null) {
			likes(wrapper, likes, aliaClazz);
		}

		if (ranges != null) {
			ranges(wrapper, ranges, aliaClazz);
		}

		if (orderBys != null) {
			orderBys(wrapper, orderBys, aliaClazz);
		}

		return wrapper;
	}

	private static <T> void trims(QueryWrapper<T> wrapper, HashMap<String, Object> trims, Class<?> aliaClazz) {
		for (Map.Entry<String, Object> t : trims.entrySet()) {
			if (t != null) {
				wrapper.eq(getFiledAlia(t.getKey(), aliaClazz), t.getValue());
			}
		}
	}


	private static <T> void likes(QueryWrapper<T> wrapper, HashMap<String, Object> likes, Class<?> aliaClazz) {
		for (Map.Entry<String, Object> t : likes.entrySet()) {
			if (t != null && (t.getValue() != null && t.getValue() != "")) {
				wrapper.like(getFiledAlia(t.getKey(), aliaClazz), t.getValue());
			}
		}
	}

	private static <T> void ranges(QueryWrapper<T> wrapper, HashMap<String, Object[]> ranges, Class<?> aliaClazz) {
		for (Map.Entry<String, Object[]> r : ranges.entrySet()) {
			if (r == null) {
				continue;
			}

			for (int i = 0; (r.getValue() != null && i < r.getValue().length); i++) {
				if (r.getValue()[i] != null && !TIME_REG.matcher(r.getValue()[i] + "").find()) {
					if (i != 0) {
						wrapper.lt(getFiledAlia(r.getKey(), aliaClazz), r.getValue()[i] + " 23:59:59");
						continue;
					}
					wrapper.gt(getFiledAlia(r.getKey(), aliaClazz), r.getValue()[i] + " 00:00:00");
				} else {
					if (i != 0) {
						wrapper.lt(getFiledAlia(r.getKey(), aliaClazz), r.getValue()[i]);
						continue;
					}
					wrapper.gt(getFiledAlia(r.getKey(), aliaClazz), r.getValue()[i]);
				}
			}
		}
	}


	private static <T> void orderBys(QueryWrapper<T> wrapper, HashMap<Boolean, ArrayList<String>> orderBys, Class<?> aliaClazz) {
		for (Map.Entry<Boolean, ArrayList<String>> orderEntry : orderBys.entrySet()) {
			if (orderEntry.getKey()) {
				wrapper.orderByAsc(getFiledAliaList(orderEntry.getValue(), aliaClazz));
			} else {
				wrapper.orderByDesc(getFiledAliaList(orderEntry.getValue(), aliaClazz));
			}
		}
	}


	public static <T> String getFiledAlia(String filedName, Class<T> aliaClazz) {
		String key = filedName;
		if (aliaClazz == null) {
			return StringUtils.camelToUnderline(filedName);
		}
		try {
			Field field = aliaClazz.getDeclaredField(filedName);
			if (field.isAnnotationPresent(TableField.class)) {
				TableField tableField = field.getAnnotation(TableField.class);
				key = tableField.value();
			} else {
				key = StringUtils.camelToUnderline(filedName);
			}
		} catch (NoSuchFieldException e) {
			log.error("[分页搜索] 转换属性别名失败", e);
		}

		return key;
	}

	public static <T> List<String> getFiledAliaList(List<String> filedList, Class<T> aliaClazz) {
		List<String> aliaList = new ArrayList<>(filedList.size());
		for (String fieldName : filedList) {
			aliaList.add(getFiledAlia(fieldName, aliaClazz));
		}
		return aliaList;
	}

	public static <T> Result wrapperPage2Result(IPage<T> page) {
		Result result = new Result();
		result.setData(page.getRecords());
		result.putIn("rowCount", page.getTotal());
		return result;
	}

	public <T> IPage<T> getIPage() {
		return new Page<T>(this.getPageIndex(),
				this.getPageSize() <= 100 ? this.getPageSize() : 100);
	}

	public <T> IPage<T> getIPage(long count) {
		return new Page<>(this.getPageIndex(),
				this.getPageSize() <= 100 ? this.getPageSize() : 100
				, count);
	}

}