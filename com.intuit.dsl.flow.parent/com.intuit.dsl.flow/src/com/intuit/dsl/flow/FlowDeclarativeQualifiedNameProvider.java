package com.intuit.dsl.flow;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.common.base.Function;

public class FlowDeclarativeQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {
	public static Function<EObject, String> NAME_RESOLVER = SimpleAttributeResolver.newResolver(String.class, "id");

	protected Function<EObject, String> getResolver() {
			return NAME_RESOLVER;
		}


}
