package com.wx.mybatis.generator;

import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class MysqlPaginationPlugin extends PluginAdapter{
	
	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
	}
	
	private boolean hasAttribute(XmlElement element, String attributeName, String attributeValue) {
		XmlElement x = (XmlElement) element;
		for(Attribute att : x.getAttributes()) {
			if(att.getName().equalsIgnoreCase(attributeName) && att.getValue().equalsIgnoreCase(attributeValue)) {
				return true;
			}
		}
		return false;
	}
	
	private void removeOrderByFromElementGenerated(XmlElement element) {
		Iterator<Element> it = element.getElements().iterator();
		while(it.hasNext()) {
			Element child = it.next();
			if(child instanceof XmlElement) {
				XmlElement x = (XmlElement) child;
				if(x.getName().equalsIgnoreCase("if") && hasAttribute(x, "test", "orderByClause != null")) {
					it.remove();
				}
			}
		}
	}

	private String getPkColumnList(IntrospectedTable introspectedTable) {
		List<IntrospectedColumn> cols = introspectedTable.getPrimaryKeyColumns() != null && introspectedTable.getPrimaryKeyColumns().size() > 0 ?
				introspectedTable.getPrimaryKeyColumns() :
				introspectedTable.getBaseColumns();
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<cols.size(); i++) {
			if(i>0) 
				sb.append(", ");
			sb.append(cols.get(i).getActualColumnName());
		}
		return sb.toString();
	}
	
	private static final String ROW_NUMBER_OVER = "row_number() over(%s) AS __row_number__ , ";
	private void insertRownumberOver(XmlElement element, IntrospectedTable introspectedTable) {
		for(int i=0; i<element.getElements().size(); i++) {
			Element child = element.getElements().get(i);
			if(child instanceof XmlElement) {
				XmlElement x = (XmlElement) child;
				if(x.getName().equalsIgnoreCase("include") && hasAttribute(x, "refid", "Base_Column_List")) {
					XmlElement choose = new XmlElement("choose");
					
					XmlElement when = new XmlElement("when");
					when.addAttribute(new Attribute("test", "orderByClause != null"));
					when.addElement(new TextElement(String.format(ROW_NUMBER_OVER, "order by ${orderByClause}")));
					choose.addElement(when);
					
					XmlElement otherwise = new XmlElement("otherwise");
					otherwise.addElement(new TextElement(String.format(ROW_NUMBER_OVER, "order by " + getPkColumnList(introspectedTable))));
					choose.addElement(otherwise);
					
					element.addElement(i, choose);
					break;
				}
			}
		}
	}

	private void warpRowNumberBetweenAnd(XmlElement element) {
		element.addElement(0, new TextElement("select * from ("));
		element.addElement(new TextElement(") __t__  where __row_number__ between #{limitStart} and #{limitEnd}"));
	}
	
	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		removeOrderByFromElementGenerated(element);
		insertRownumberOver(element, introspectedTable);
		warpRowNumberBetweenAnd(element);
		return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	
	
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		CommentGenerator commentGenerator = context.getCommentGenerator();
        
		FieldBuilder.F("limitStart")
        	.type(PrimitiveTypeWrapper.getIntegerInstance())
        	.visibility(JavaVisibility.PUBLIC)
        	.comment(commentGenerator, introspectedTable)
        	.append(topLevelClass);
		
		FieldBuilder.F("limitEnd")
	    	.type(PrimitiveTypeWrapper.getIntegerInstance())
	    	.visibility(JavaVisibility.PUBLIC)
	    	.comment(commentGenerator, introspectedTable)
	    	.append(topLevelClass);

		MethodBuilder.M("limit")
			.visibility(JavaVisibility.PUBLIC)
			.param(PrimitiveTypeWrapper.getIntegerInstance(), "start")
			.param(PrimitiveTypeWrapper.getIntegerInstance(), "end")
			.line("this.limitStart = start;")
			.line("this.limitEnd = end;")
			.comment(commentGenerator, introspectedTable)
			.append(topLevelClass);

        MethodBuilder.M("getLimitStart")
    		.visibility(JavaVisibility.PUBLIC)
    		.return_(PrimitiveTypeWrapper.getIntegerInstance())
    		.line("return this.limitStart;")
			.comment(commentGenerator, introspectedTable)
			.append(topLevelClass);

        MethodBuilder.M("getLimitEnd")
    		.visibility(JavaVisibility.PUBLIC)
    		.return_(PrimitiveTypeWrapper.getIntegerInstance())
    		.line("return this.limitEnd;")
			.comment(commentGenerator, introspectedTable)
			.append(topLevelClass);
        
        enhanceExample(topLevelClass);
        enhanceOrder(topLevelClass, introspectedTable);
        
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}
	
	private void enhanceExample(TopLevelClass clazz) {
		InnerClass generatedCriteria = null;
        for(InnerClass inner : clazz.getInnerClasses()) {
        	if(inner.getType().getShortName().equals("GeneratedCriteria")) {
        		generatedCriteria = inner;
        	}
        }
        if(generatedCriteria == null) return;
		
        //clazz.addImportedType("java.util.Map");
        //clazz.addImportedType("java.util.HashMap");
        //FieldBuilder.F("params")
        //	.visibility(JavaVisibility.PROTECTED)
        //	.type("Map<String, Object>")
        //	.init("new HashMap<String, Object>()");
	}
	
	private void enhanceOrder(TopLevelClass clazz, IntrospectedTable table) {
		//remove field orderByClause
		Iterator<Field> orderByClause = clazz.getFields().iterator();
		while(orderByClause.hasNext()) {
			Field field = orderByClause.next();
			if(field.getName().equals("orderByClause")) {
				orderByClause.remove();
			}
		}
		
		Iterator<Method> getOrderByClause = clazz.getMethods().iterator();
		while(getOrderByClause.hasNext()) {
			Method method = getOrderByClause.next();
			if(method.getName().equals("getOrderByClause")) {
				method.getBodyLines().clear();
				method.addBodyLine("if(sorter != null) {");
				method.addBodyLine("return sorter.orderBy();");
				method.addBodyLine("}");
				method.addBodyLine("return null;");
			}
		}
		
		Iterator<Method> setOrderByClause = clazz.getMethods().iterator();
		while(setOrderByClause.hasNext()) {
			Method method = setOrderByClause.next();
			if(method.getName().equals("setOrderByClause")) {
				setOrderByClause.remove();
			}
		}

		Iterator<Method> clear = clazz.getMethods().iterator();
		while(clear.hasNext()) {
			Method method = clear.next();
			if(method.getName().equals("clear")) {
				Iterator<String> line = method.getBodyLines().iterator();
				while(line.hasNext()) {
					String s = line.next();
					if(s.startsWith("orderByClause")) {
						line.remove();
					}
				}
			}
		}

		FieldBuilder.F("sorter").type("Sorter").visibility(JavaVisibility.PRIVATE).append(clazz);
		MethodBuilder.M("orderBy").return_("Sorter").visibility(JavaVisibility.PUBLIC)
			.param("String", "property")
			.line("sorter = new Sorter(property);")
			.line("return sorter;")
			.append(clazz);
		
		InnerClass sorter = new InnerClass("Sorter");
		sorter.setVisibility(JavaVisibility.PUBLIC);
		sorter.setStatic(true);
		
		FieldBuilder.F("column").visibility(JavaVisibility.PRIVATE).type("String").append(sorter);;
		FieldBuilder.F("asc").visibility(JavaVisibility.PRIVATE).type("boolean").init("true").append(sorter);;
		
		MethodBuilder mb = 
		MethodBuilder.M("Sorter").constructor(true).param("String", "property");
		String if_ = "if";
		for(IntrospectedColumn col : table.getPrimaryKeyColumns()) {
			mb.line(if_ + " (\"{0}\".equals(property)) this.column = \"{1}\";", col.getJavaProperty(), col.getActualColumnName());
			if_ = "else if";
		}
		for(IntrospectedColumn col : table.getBaseColumns()) {
			mb.line(if_ + " (\"{0}\".equals(property)) this.column = \"{1}\";", col.getJavaProperty(), col.getActualColumnName());
			if_ = "else if";
		}
		mb.line("else this.column = property;");
		mb.append(sorter);

		MethodBuilder.M("asc").visibility(JavaVisibility.PUBLIC).line("this.asc = true;").append(sorter);
		MethodBuilder.M("desc").visibility(JavaVisibility.PUBLIC).line("this.asc = false;").append(sorter);
		MethodBuilder.M("orderBy").visibility(JavaVisibility.PUBLIC).return_("String")
			.line("if(asc) { return this.column; }")
			.line("else { return this.column + \" desc \"; }")
			.append(sorter);

		clazz.addInnerClass(sorter);
	}
	
	@Override
	public boolean validate(List<String> arg0) {
		return true;
	}
	
	@Override
	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
		try {
			java.lang.reflect.Field f = sqlMap.getClass().getDeclaredField("isMergeable");
			f.setAccessible(true);
			f.set(sqlMap, false);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return super.sqlMapGenerated(sqlMap, introspectedTable);
	}
}
