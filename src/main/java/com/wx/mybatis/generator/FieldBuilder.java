package com.wx.mybatis.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class FieldBuilder {
	private Field f;
	
	private FieldBuilder(String name) {
		f = new Field();
		f.setName(name);
	}
	
	public FieldBuilder final_() {
		f.setFinal(true);
		return this;
	}
	
	public FieldBuilder static_() {
		f.setStatic(true);
		return this;
	}
	
	public FieldBuilder type(String type){
		f.setType(new FullyQualifiedJavaType(type));
		return this;
	}
	
	public FieldBuilder type(FullyQualifiedJavaType type) {
		f.setType(type);
		return this;
	}
	
	public FieldBuilder visibility(JavaVisibility visibility) {
		f.setVisibility(visibility);
		return this;
	}
	
	public FieldBuilder init(String formattor, String... args) {
		if(args != null) {
			for(int i=0; i<args.length; i++) {
				formattor = formattor.replace("{"+i+"}", args[i]);
			}
		}
		f.setInitializationString(format(formattor, args));
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
	
	public FieldBuilder annotation(String annotation, String... args) {
		f.addAnnotation(format(annotation, args));
		return this;
	}

	public FieldBuilder comment(CommentGenerator generator, IntrospectedTable table) {
		generator.addFieldComment(f, table); 
		return this;
	}
	
	public void append(TopLevelClass clazz) {
		clazz.addField(f); 
	}
	
	public static FieldBuilder F(String name) {
		return new FieldBuilder(name);
	}

	public void append(InnerClass innerClass) {
		innerClass.addField(f);
	}
}
