package cn.xn.freamwork.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ShortMessageUtil {

	public ShortMessageUtil() {
	}

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	public static boolean isEmpty(Object o) {
		return o == null || (o instanceof String)
				&& ((String) o).trim().length() == 0;
	}

	public static String coalesce(String args[]) {
		String arrayOfString[] = args;
		int j = args.length;
		for (int i = 0; i < j; i++) {
			String arg = arrayOfString[i];
			if (!isEmpty(arg))
				return arg;
		}

		return null;
	}

	public static int count(String s, char c) {
		int count = 0;
		int i = 0;
		for (int n = s.length(); i < n; i++)
			if (s.charAt(i) == c)
				count++;

		return count;
	}

	public static String padLeft(String s, int length) {
		return s.length() >= length ? s : (new StringBuilder(
				String.valueOf(repeat(' ', length - s.length())))).append(s)
				.toString();
	}

	public static String padRight(String s, int length) {
		return s.length() >= length ? s
				: (new StringBuilder(String.valueOf(s))).append(
						repeat(' ', length - s.length())).toString();
	}

	public static String repeat(char c, int count) {
		StringBuilder sb = new StringBuilder(count);
		for (int i = 0; i < count; i++)
			sb.append(c);

		return sb.toString();
	}

	public static String repeat(String s, int count) {
		StringBuilder sb = new StringBuilder(s.length() * count);
		for (int i = 0; i < count; i++)
			sb.append(s);

		return sb.toString();
	}

	public static String escapeJs(String s) {
		if (s == null)
			return "";
		else
			return s.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\n", "\\\\n")
					.replaceAll("\\r", "\\\\r").replaceAll("'", "\\\\'")
					.replaceAll("\"", "\\\\\"");
	}

	public static String escapeHtml(String s) {
		return escapeXml(s);
	}

	public static String escapeXml(String s) {
		if (s == null)
			return "";
		else
			return s.replaceAll("&", "&amp;").replaceAll(" ", "&nbsp;")
					.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	public static String escapeSql(String s) {
		if (s == null)
			return s;
		else
			return s.replaceAll("'", "''");
	}

	public static String replace(String s, String text, String replacement) {
		int index = s.indexOf(text);
		if (index == -1)
			return s;
		int LEN = text.length();
		StringBuffer sb = new StringBuffer((s.length() * 6) / 5);
		sb.append(s.substring(0, index));
		for (int i = index; i != -1; i = index) {
			sb.append(replacement);
			index = s.indexOf(text, index + LEN);
			sb.append(s.substring(i + LEN, index != -1 ? index : s.length()));
		}

		return sb.toString();
	}

	public static String replace(String s, String pairs[]) {
		for (int i = 0; i < pairs.length; i += 2)
			s = replace(s, pairs[i], pairs[i + 1]);

		return s;
	}

	public static String join(Object items[]) {
		return join(items, ", ", false, null, null, null);
	}

	public static String join(Object items[], String joiner) {
		return join(items, joiner, false, null, null, null);
	}

	public static String joinIgnoreEmpty(Object items[], String joiner) {
		return join(items, joiner, true, null, null, null);
	}

	public static String join(Object items[], String joiner, String defaultValue) {
		return join(items, joiner, false, defaultValue, null, null);
	}

	public static String join(Object items[], String joiner,
			String defaultValue, String quote) {
		return join(items, joiner, false, defaultValue, quote, quote);
	}

	public static String join(Object items[], String joiner,
			String defaultValue, String quote1, String quote2) {
		return join(items, joiner, false, defaultValue, quote1, quote2);
	}

	public static String join(Object items[], String joiner,
			boolean ignoreEmpty, String defaultValue, String quote1,
			String quote2) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < items.length; i++)
			if (!ignoreEmpty || !isEmpty(items[i])) {
				if (i > 0)
					sb.append(joiner);
				Object item = items[i] != null ? items[i]
						: ((Object) (defaultValue));
				if (quote1 != null)
					sb.append(quote1);
				sb.append(item);
				if (quote2 != null)
					sb.append(quote2);
			}

		return sb.toString();
	}

	public static String join(Iterable items) {
		return join(items, ", ", null, null);
	}

	public static String join(Iterable items, String joiner) {
		return join(items, joiner, null, null);
	}

	public static String join(Iterable items, String joiner, String defaultValue) {
		return join(items, joiner, defaultValue, null);
	}

	public static String join(Iterable items, String joiner,
			String defaultValue, String quote) {
		return join(items, joiner, defaultValue, quote, quote);
	}

	public static String join(Iterable items, String joiner,
			String defaultValue, String quote1, String quote2) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (Iterator localIterator = items.iterator(); localIterator.hasNext();) {
			Object item = localIterator.next();
			if (isFirst)
				isFirst = false;
			else
				sb.append(joiner);
			if (quote1 != null)
				sb.append(quote1);
			sb.append(item != null ? item : ((Object) (defaultValue)));
			if (quote2 != null)
				sb.append(quote2);
		}

		return sb.toString();
	}

	public static String join(String item, int count) {
		return join(item, count, ", ");
	}

	public static String join(String item, int count, String joiner) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			if (i > 0)
				sb.append(joiner);
			sb.append(item);
		}

		return sb.toString();
	}

	public static String[] split(String s, String splitter) throws Exception {
		return split(s, splitter, false);
	}

	public static String[] splitIgnoreCase(String s, String splitter)
			throws Exception {
		return split(s, splitter, true);
	}

	private static String[] split(String s, String splitter, boolean ignoreCase)
			throws Exception {
		if (s == null)
			return new String[0];
		if (splitter == null || splitter.length() == 0)
			throw new Exception("Basic.String.SplitterCannotBeEmpty"
					+ new Object[0]);
		String target = s;
		if (ignoreCase) {
			target = s.toLowerCase();
			splitter = splitter.toLowerCase();
		}
		List list = new ArrayList();
		int n = splitter.length();
		int i = 0;
		for (int j = target.indexOf(splitter); j != -1; j = target.indexOf(
				splitter, i)) {
			list.add(s.substring(i, j));
			i = j + n;
		}

		list.add(s.substring(i));
		String array[] = new String[list.size()];
		return (String[]) list.toArray(array);
	}

	public static UUID[] splitUuid(String s, String splitter) throws Exception {
		String texts[] = split(s, splitter, false);
		UUID uuids[] = new UUID[texts.length];
		for (int i = 0; i < texts.length; i++)
			uuids[i] = UUID.fromString(texts[i]);

		return uuids;
	}

	public static String formatByIndex(String template, Object args[]) {
		String result = template;
		for (int i = 0; i < args.length; i++) {
			String s = args[i] != null ? args[i].toString() : "null";
			result = replace(result, (new StringBuilder("${")).append(i)
					.append("}").toString(), s);
		}

		return result;
	}

	public static String formatByKey(String template, Map pairs) {
		if (pairs == null)
			return template;
		StringBuffer sb = new StringBuffer((template.length() * 6) / 5);
		int i = 0;
		do {
			int start = template.indexOf("${", i);
			int end = start != -1 ? template.indexOf("}", start + 2) : -1;
			if (start == -1 || end == -1) {
				sb.append(template.substring(i));
				break;
			}
			String key = template.substring(start + 2, end);
			if (pairs.containsKey(key)) {
				sb.append(template.substring(i, start)).append(
						(String) pairs.get(key));
				i = end + 1;
			} else {
				sb.append(template.substring(i, start + 2));
				i = start + 2;
			}
		} while (true);
		return sb.toString();
	}

	public static String formatByKey(String template, String pairs[]) {
		Map map = new HashMap();
		for (int i = 0; i < pairs.length; i += 2)
			map.put(pairs[i], pairs[i + 1]);

		return formatByKey(template, map);
	}

	public static String[] trim(String items[]) {
		String result[] = new String[items.length];
		for (int i = 0; i < items.length; i++)
			result[i] = items[i] != null ? items[i].trim() : null;

		return result;
	}

	public static String trimToLength(String s, int length) {
		return s != null && s.length() > length ? s.substring(0, length) : s;
	}

	public static String trimToLength(String s, int length, int nonAsciiCharSize) {
		if (s == null || s.length() <= length / nonAsciiCharSize)
			return s;
		int index = 0;
		int count = 0;
		int i = 0;
		for (int n = s.length(); i < n; i++) {
			count += s.charAt(i) >= '\177' ? nonAsciiCharSize : 1;
			if (count > length)
				break;
			index++;
		}

		return s.substring(0, index);
	}

	public static String trim(String s, Character characters[]) {
		return ltrim(rtrim(s, characters), characters);
	}

	public static String ltrim(String s, Character characters[]) {
		if (characters.length == 0)
			return s.replace("^\\s+", "");
		Set set = new HashSet();
		Character acharacter[];
		int j = (acharacter = characters).length;
		for (int i = 0; i < j; i++) {
			Character c = acharacter[i];
			if (c != null)
				set.add(c);
		}

		int start = 0;
		int end;
		for (end = s.length(); start < end
				&& set.contains(Character.valueOf(s.charAt(start))); start++)
			;
		return start != end ? s.substring(start, end) : "";
	}

	public static String rtrim(String s, Character characters[]) {
		if (characters.length == 0)
			return s.replace("\\s+$", "");
		Set set = new HashSet();
		Character acharacter[];
		int j = (acharacter = characters).length;
		for (int i = 0; i < j; i++) {
			Character c = acharacter[i];
			if (c != null)
				set.add(c);
		}

		int end;
		for (end = s.length(); end > 0
				&& set.contains(Character.valueOf(s.charAt(end - 1))); end--)
			;
		return s.substring(0, end);
	}

	public static String[] splitAndTrim(String s, String splitter)
			throws Exception {
		return trim(split(s, splitter));
	}
}
