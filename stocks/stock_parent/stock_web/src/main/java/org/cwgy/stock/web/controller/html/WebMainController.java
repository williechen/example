package org.cwgy.stock.web.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLObjectType.Builder;

@Controller
public class WebMainController {

	@RequestMapping(value = "/testError", method = { RequestMethod.GET, RequestMethod.POST })
	public String mainIndex() {
		Builder builder = GraphQLObjectType.newObject();
		builder.name("Foo");
		
		GraphQLFieldDefinition.Builder fieldBuilder = GraphQLFieldDefinition.newFieldDefinition();
		fieldBuilder.name("bar");
		fieldBuilder.type(graphql.Scalars.GraphQLString);
		
		builder.field(fieldBuilder);
		
		return "test";
	}
}
