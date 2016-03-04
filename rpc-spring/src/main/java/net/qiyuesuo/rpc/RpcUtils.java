package net.qiyuesuo.rpc;

import net.qiyuesuo.rpc.common.Constants;

public class RpcUtils {

	public static String determineGroup(String globalGroup, String annotationGroup) {
		if (annotationGroup.equals(Constants.DEFAULT_GROUP)) {// 若注解中的group为默认值，则使用globalGroup（不为空）
			return globalGroup != null ? globalGroup : Constants.DEFAULT_GROUP;
		}
		return annotationGroup;
	}

}
