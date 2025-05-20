package com.intuit.dsl.flow.validation;

import com.intuit.dsl.flow.flow.Key;
import com.intuit.dsl.flow.flow.RequestArgument;
import com.intuit.dsl.flow.flow.SchemaVariable;
import com.intuit.dsl.flow.flow.Service;
import com.intuit.dsl.flow.flow.VariableReference;
import com.intuit.dsl.flow.validation.BaseValidator;
import java.util.List;
import java.util.Objects;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ServiceValidator extends BaseValidator {
  @Check(CheckType.NORMAL)
  public void checkIfIteratorIsUsedProperly(final Service service) {
    final String iteratorAlias = service.getIteratoralias();
    boolean _nonNull = Objects.nonNull(iteratorAlias);
    if (_nonNull) {
      final Function1<RequestArgument, List<VariableReference>> _function = (RequestArgument r) -> {
        return EcoreUtil2.<VariableReference>getAllContentsOfType(r, VariableReference.class);
      };
      final Function1<VariableReference, SchemaVariable> _function_1 = (VariableReference v) -> {
        return v.getRef().getSchemaVariable();
      };
      final Function1<SchemaVariable, Boolean> _function_2 = (SchemaVariable s) -> {
        String _id = IterableExtensions.<Key>head(s.getKey()).getId();
        return Boolean.valueOf(com.google.common.base.Objects.equal(_id, iteratorAlias));
      };
      boolean _exists = IterableExtensions.<SchemaVariable>exists(IterableExtensions.<VariableReference, SchemaVariable>map(IterableExtensions.<RequestArgument, VariableReference>flatMap(service.getRequestArguments(), _function), _function_1), _function_2);
      boolean _not = (!_exists);
      if (_not) {
        this.error("Iterator not being used in any of the request argument", service);
      }
    }
  }
  
  @Check(CheckType.NORMAL)
  public void checkIfServicePostHasBodyOrInput(final Service service) {
    if ((((com.google.common.base.Objects.equal(service.getMethod(), "POST") || com.google.common.base.Objects.equal(service.getMethod(), "PUT")) && 
      (service.getInputModel() == null)) && 
      (!IterableExtensions.<RequestArgument>exists(service.getRequestArguments(), ((Function1<RequestArgument, Boolean>) (RequestArgument s) -> {
        String _type = s.getType();
        return Boolean.valueOf(com.google.common.base.Objects.equal(_type, "Body"));
      }))))) {
      this.error("@Body or input required for methods POST or PUT", service);
    }
  }
}
