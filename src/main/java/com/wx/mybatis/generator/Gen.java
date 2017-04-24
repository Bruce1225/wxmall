package com.wx.mybatis.generator;

import org.mybatis.generator.api.ShellRunner;

/**
 * Created by Bruce on 2017/4/24.
 */
public class Gen {

	public static void generate() {
		String config = Gen.class.getClassLoader().getResource("generatorConfig.xml").getFile();
		String[] arg = { "-configfile", config, "-overwrite" };
		ShellRunner.main(arg);
	}
	
	public static void main(String[] args) {
		generate();
	}

}
