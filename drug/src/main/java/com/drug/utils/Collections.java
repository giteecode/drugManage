/**
 * 
 */
package com.drug.utils;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.*;

public class Collections {

	/**
	 * 
	 * @return
	 */
	public static final <K, V> Map<K, V> emptyMap() {
		return new LinkedHashMap<K, V>();
	}

	/**
	 * @return
	 */
	public static <T> List<T> emptyList() {
		return new LinkedList<T>();
	}
	
	/**
	 * @return
	 */
	public static <T> Set<T> emptySet() {
		return new LinkedHashSet<T>();
	}

	/**
	 * 
	 * @param objs
	 * @return
	 */
	public static <T> List<T> asList(T... objs) {
		if (isEmpty(objs))
			return emptyList();
		
		List<T> list = emptyList();
		for (T obj : objs)
			list.add(obj);
		
		return list;
	}

	/**
	 * 
	 * @param objs
	 * @return
	 */
	public static <T> List<T> asList(Collection<T> objs) {
		if (isEmpty(objs))
			return emptyList();
		
		List<T> list = emptyList();
		list.addAll(objs);
		
		return list;
	}

	/**
	 * 
	 * @return
	 */
	public static <T> Set<T> asSet(T... ts) {
		Set<T> set = emptySet();
		if (isNotEmpty(ts)) {
			for (T t : ts)
				set.add(t);
		}
		return set;
	}

	/**
	 * @return
	 */
	public static <T> Set<T> toSet(List<T> ts) {
		Set<T> set = new TreeSet<>(new Comparator<T>() {

			@Override
			public int compare(T o1, T o2) {
				return 1;
			}
		});
		set.addAll(ts);
		return set;
	}

	/**
	 * @param args even param is the key, odd param is the value
	 * @return
	 */
	public static <K, V> Map<K, V> asMap(Object... args) {
		Map<K, V> map = emptyMap();
		if (args == null || args.length == 0) {
			return map;
		} else if (args.length % 2 != 0) {
			throw new IllegalArgumentException("args must completely match: even param is key, odd param is value, please check your arguments.");
		}
		for (int i=0; i<args.length;i+=2) {
			Object key = args[i];
			Object val = i + 1 < args.length ? args[i+1] : null;
			map.put( (K) key, (V) val);
		}
		return map;
	}
	
	/**
	 * @param args even param is the key, odd param is the value
	 * @return
	 */
	public static <K, V> Map<String, Object> asMapParams(Object... args) {
		Map<String, Object> map = emptyMap();
		if (args == null || args.length == 0) {
			return map;
		} else if (args.length % 2 != 0) {
			throw new IllegalArgumentException("args must completely match: even param is key, odd param is value, please check your arguments.");
		}
		for (int i=0; i<args.length;i+=2) {
			String key = String.valueOf(args[i]);
			Object val = i + 1 < args.length ? args[i+1] : null;
			map.put(key, val);
		}
		return map;
	}
	
	public static <A> Collection<A> addAll(Collection<A> collection) {
		List<A> list = emptyList();
		list.addAll(collection);
		return list;
	}
	
	public static <K, V> Map<K,V> addAll(Map<K,V> map, Object... args) {
		Map<K,V> ret = asMap(args);
		ret.putAll(map);
		return ret;
	}

	/**
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(Collection<?> list) {
		return list == null || list.size() == 0;
	}
	
	/**
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.size() == 0;
	}

	/**
	 * 
	 * @param ts
	 * @return
	 */
	public static <T> boolean isEmpty(T[] ts) {
		return ts == null || ts.length == 0;
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return ! isEmpty(map);
	}
	
	/**
	 * 
	 * @param ts
	 * @return
	 */
	public static <T> boolean isNotEmpty(T[] ts) {
		return ! isEmpty(ts);
	}

	/**
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return ! (isEmpty(collection));
	}

	public static int size(Collection<?> collection) {
		return isEmpty(collection) ? 0 : collection.size();
	}


	/**
	 * 
	 * @param arr
	 * @param splitor
	 * @return
	 */
	public static String join(Object[] arr, String splitor)  {
		try {
			StringBuilder sb = new StringBuilder();
			if (arr.length == 1 && arr[0] != null) {
				sb.append(arr[0]).append(splitor);
			} else {
				for (Object o : arr) {
					if (o != null)
						sb.append(o).append(splitor);
				}
			}
			String str = sb.toString();
			return str.length() == 0 ? "" : str.substring(0, str.length() - splitor.length());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	/**
	 */
	public static void print(Collection<?> datas) {
		print(datas, System.out);
	}

	/**
	 * @param datas
	 * @param out
	 */
	public static void print(Collection<?> datas, PrintStream out) {
		out.println("Collections.print: ");
		if (isNotEmpty(datas)) {
			for (Object data : datas) {
				out.print("\t");
				if (data.getClass().isArray()){
					out.println(Arrays.toString((Object[]) data));
				}else if (data instanceof Collection){
					print((Collection<?>) data);
				}else if (data instanceof Date) {
					Date date = (Date) data;
					out.println(DateUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
				}else{
					out.println(ToStringBuilder.reflectionToString(data, ToStringStyle.MULTI_LINE_STYLE));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param targetList
	 * @param sortField
	 * @param sortMode
	 *            排序模式 升序 降序
	 */
	public static <T> void sort(List<T> targetList, final String sortField, final boolean sortMode) {
		if (targetList == null || targetList.size() < 2 || sortField == null || sortField.length() == 0) {
			return;
		}
		java.util.Collections.sort(targetList, new Comparator<Object>() {
			@Override
			public int compare(Object obj1, Object obj2) {
				int retVal = 0;
				if(obj1 == null || obj2== null){
					return -1;
				}
				try {
					//
					String methodStr = "get" + sortField.substring(0, 1).toUpperCase() + sortField.substring(1);
					Method method1 = ((T) obj1).getClass().getMethod(methodStr);
					Method method2 = ((T) obj2).getClass().getMethod(methodStr);
					if (sortMode) {
						retVal = method1.invoke(((T) obj1)).toString().compareTo(method2.invoke(((T) obj2)).toString());
					} else {
						retVal = method2.invoke(((T) obj2)).toString().compareTo(method1.invoke(((T) obj1)).toString());
					}
				} catch (Exception e) {
					System.out.println("List<" + ((T) obj1).getClass().getName() + ">排序异常！");
					e.printStackTrace();
				}
				return retVal;
			}
		});
	}
	
}
