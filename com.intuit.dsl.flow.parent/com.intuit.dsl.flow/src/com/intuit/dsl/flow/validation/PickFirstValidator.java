package com.intuit.dsl.flow.validation;

import com.intuit.dsl.flow.flow.Comparison;
import com.intuit.dsl.flow.flow.Equals;
import com.intuit.dsl.flow.flow.Expression;
import com.intuit.dsl.flow.flow.Key;
import com.intuit.dsl.flow.flow.PickFirst;
import com.intuit.dsl.flow.flow.SchemaVariable;
import com.intuit.dsl.flow.flow.VariableReference;
import com.intuit.dsl.flow.validation.BaseValidator;
import java.util.Objects;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class PickFirstValidator extends BaseValidator {
  @Check
  public void checkonlyItAllowedOnPickFirst(final PickFirst pickFirst) {
    Expression condition = pickFirst.getCondition();
    Expression left = null;
    if ((condition instanceof Comparison)) {
      left = ((Comparison)condition).getLeft();
    }
    if ((condition instanceof Equals)) {
      left = ((Equals)condition).getLeft();
    }
    boolean _isNull = Objects.isNull(left);
    if (_isNull) {
      this.error("Only Equals and Comparison allowed", pickFirst);
      return;
    }
    if ((left instanceof VariableReference)) {
      SchemaVariable variable = ((VariableReference)left).getRef().getSchemaVariable();
      String _id = IterableExtensions.<Key>head(variable.getKey()).getId();
      boolean _notEquals = (!com.google.common.base.Objects.equal(_id, "it"));
      if (_notEquals) {
        this.error("Left side needs to be an \'it\' variable", condition);
        return;
      }
    } else {
      this.error("Left side needs to be variable", condition);
      return;
    }
  }
}
