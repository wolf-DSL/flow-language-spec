package com.intuit.dsl.flow.validation;

import com.intuit.dsl.flow.validation.AbstractFlowValidator;
import com.intuit.dsl.flow.validation.MappingValidator;
import com.intuit.dsl.flow.validation.PickFirstValidator;
import com.intuit.dsl.flow.validation.RulesValidator;
import com.intuit.dsl.flow.validation.SchemaValidator;
import com.intuit.dsl.flow.validation.ServiceValidator;
import com.intuit.dsl.flow.validation.ValueValidator;
import org.eclipse.xtext.validation.ComposedChecks;

/**
 * This class contains custom validation rules.
 * 
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */

@ComposedChecks(validators = { ValueValidator.class, PickFirstValidator.class, RulesValidator.class,
		MappingValidator.class, SchemaValidator.class, ServiceValidator.class })
@SuppressWarnings("all")
public class FlowValidator extends AbstractFlowValidator {
}
