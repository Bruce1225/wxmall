package com.wx.mybatis.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class MethodBuilder {
	private Method m;
	private MethodBuilder(String name) {
		m = new Method(name);
	}
	
	public MethodBuilder param(FullyQualifiedJavaType type, String name) {
		m.addParameter(new Parameter(type, name));
		return this;
	}
	
	public MethodBuilder constructor(boolean constructor) {
		m.setConstructor(constructor);
		return this;
	}
	
	public MethodBuilder param(String type, String name) {
		m.addParameter(new Parameter(new FullyQualifiedJavaType(type), name));
		return this;
	}

	public MethodBuilder param(String type, String name, String annotation) {
		m.addParameter(new Parameter(new FullyQualifiedJavaType(type), name, annotation));
		return this;
	}

	public MethodBuilder line(String line) {
		m.addBodyLine(line);
		return this;
	}
	
	private String format(String formator, String... args) {
		if(args != null) {
			for(int i=0; i<args.length; i++) {
				formator = formator.replace("{"+i+"}", args[i]);
			}
		}
		return formator;
	}
	
	public MethodBuilder line(String line, String... args) {
		m.addBodyLine(format(line, args));
		return this;
	}
	
	public MethodBuilder visibility(JavaVisibility v) {
		m.setVisibility(v);;
		return this;
	}

	public MethodBuilder return_(FullyQualifiedJavaType type) {
		m.setReturnType(type);
		return this;
	}

	public MethodBuilder return_(String type) {
		m.setReturnType(new FullyQualifiedJavaType(type));
		return this;
	}

	public MethodBuilder annotation(String annotation, String... args) {
		m.addAnnotation(format(annotation, args));
		return this;
	}

	public MethodBuilder comment(CommentGenerator generator, IntrospectedTable table) {
		generator.addGeneralMethodComment(m, table); 
		return this;
	}

	public void append(TopLevelClass clazz) {
		clazz.addMethod(m); 
	}
	

	public static MethodBuilder M(String name) {
		return new MethodBuilder(name);
	}

	public void append(InnerClass innerClass) {
		innerClass.addMethod(m);
	}
}