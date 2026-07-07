package classes;

import java.util.ArrayList;
import java.util.List;

import d3e.core.ListExt;
import store.DatabaseObject;
import store.EntityHelperService;

public class ContextHelper {

	public static String createContext(Object context) {
		if (context == null) {
			return "n:";
		}
		if (context instanceof List) {
			List coll = (List) context;
			List<String> res = new ArrayList<>();
			coll.forEach(a -> res.add(createContext(a)));
			return "c:" + ListExt.join(res, ",");
		}
		if (context instanceof DatabaseObject) {
			return "m:" + ((DatabaseObject) context).getIdent();
		}
		if (context instanceof String) {
			return "s:" + context;
		}
		if (context instanceof Integer) {
			return "i:" + context;
		}
		if (context instanceof Long) {
			return "l:" + context;
		}
		if (context instanceof Double) {
			return "d:" + context;
		}
		if (context instanceof Boolean) {
			return "b:" + context;
		}
		throw new RuntimeException("Invalid Context" + context.getClass());
	}

	public static Object extractContext(String context) {
		if (context.startsWith("c:")) {
			List<String> res = new ArrayList<>();
			String[] split = context.substring(2).split(",");
			for (String s : split) {
				res.add(s);
			}
			return res;
		}
		if (context.startsWith("m:")) {
			String ident = context.substring(2);
			String[] split = ident.split("-");
			return EntityHelperService.getInstance().get(split[0], Long.parseLong(split[1]));
		}
		if (context.startsWith("s:")) {
			return context.substring(2);
		}
		if (context.startsWith("i:")) {
			return Integer.parseInt(context.substring(2));
		}
		if (context.startsWith("l:")) {
			return Long.parseLong(context.substring(2));
		}
		if (context.startsWith("d:")) {
			return Double.parseDouble(context.substring(2));
		}
		if (context.startsWith("b:")) {
			return Boolean.parseBoolean(context.substring(2));
		}
		if (context.startsWith("n:")) {
			return null;
		}
		return null;
	}
}
