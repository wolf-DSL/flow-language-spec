package com.intuit.dsl.flow.validation;

import com.google.common.base.Objects;
import com.intuit.dsl.flow.FlowDeclarativeQualifiedNameProvider;
import com.intuit.dsl.flow.flow.FlowPackage;
import com.intuit.dsl.flow.flow.Key;
import com.intuit.dsl.flow.flow.Mapping;
import com.intuit.dsl.flow.flow.OutPutModel;
import com.intuit.dsl.flow.flow.VariableStatement;
import com.intuit.dsl.flow.validation.BaseValidator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class MappingValidator extends BaseValidator {
  @Check
  public void checkonlyOutputInLValue(final VariableStatement varStatement) {
    Key key = varStatement.getSchemaVariable().getKey().get(0);
    EObject parent = varStatement.eContainer();
    EObject grandParent = parent.eContainer();
    if (grandParent instanceof Mapping) {
      this.checkForNameMatches(key, ((Mapping) grandParent).getOutputModel());
    } 
  }
  
  public void checkForNameMatches(final Key key, final OutPutModel outputModel) {
    String name = outputModel.getId();
    if (name == null) {
      name = FlowDeclarativeQualifiedNameProvider.NAME_RESOLVER.apply(outputModel.getSchema());
    }
    if (name == null) {
      name = outputModel.getSchema().getName();
    }
    if (!Objects.equal(name, key.getId())) {
      this.error("\'" + key.getId() + "\' is not a valid l-value, only output variables can be a valid l-value", key.eContainingFeature(), FlowPackage.KEY__ID);
    }
    return;
  }
}
