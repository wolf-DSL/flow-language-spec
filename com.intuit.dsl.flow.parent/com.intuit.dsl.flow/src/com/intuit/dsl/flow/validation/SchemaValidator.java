package com.intuit.dsl.flow.validation;

import com.intuit.dsl.flow.flow.Data;
import com.intuit.dsl.flow.flow.Entity;
import com.intuit.dsl.flow.validation.BaseValidator;
import java.util.HashSet;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

@SuppressWarnings("all")
public class SchemaValidator extends BaseValidator {
  
  @Check
  public void checkThatDataHasNoDuplicateNames(final Data data) {
    final HashSet<String> names = new HashSet<String>();
    EList<Entity> _entity = data.getEntity();
    for (final Entity entity : _entity) {
      boolean _add = names.add(entity.getName());
      boolean _not = (!_add);
      if (_not) {
        String _name = entity.getName();
        String _plus = ("Duplicate name in schema: " + _name);
        this.error(_plus, entity);
      }
    }
  }
  
  @Check(CheckType.NORMAL)
  public void checkThatDataHasNoDuplicateNames(final Entity entity) {
    final HashSet<String> names = new HashSet<String>();
    EList<Entity> _entity = entity.getEntity();
    for (final Entity e : _entity) {
      boolean _add = names.add(e.getName());
      boolean _not = (!_add);
      if (_not) {
        String _name = e.getName();
        String _plus = ("Duplicate name in schema: " + _name);
        this.error(_plus, e);
      }
    }
  }
}
