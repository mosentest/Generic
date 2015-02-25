package org.moziqi.generic.common.util;


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "moziqi/files/";
		} else {
			return CommonUtil.getRootFilePath() + "moziqi/files/";
		}
	}
}
