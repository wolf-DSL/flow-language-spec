/**
 * generated by Xtext 2.13.0.RC1
 */
package com.intuit.dsl.formatting;

import com.intuit.dsl.flow.flow.ArrayValue;
import com.intuit.dsl.flow.flow.Data;
import com.intuit.dsl.flow.flow.Entity;
import com.intuit.dsl.flow.flow.Mapping;
import com.intuit.dsl.flow.flow.Model;
import com.intuit.dsl.flow.flow.ObjectContent;
import com.intuit.dsl.flow.flow.ObjectValue;
import com.intuit.dsl.flow.flow.Schema;
import com.intuit.dsl.flow.flow.SchemaAssignment;
import com.intuit.dsl.flow.flow.SchemaVariable;
import com.intuit.dsl.flow.flow.Select;
import com.intuit.dsl.flow.flow.Statement;
import com.intuit.dsl.flow.flow.TernaryExpression;
import com.intuit.dsl.flow.flow.Value;
import com.intuit.dsl.flow.flow.VariableStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.formatting2.AbstractFormatter2;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatter;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class FlowFormatter extends AbstractFormatter2 {
  protected void _format(final Model model, @Extension final IFormattableDocument document) {
    List<Mapping> _eAllOfType = EcoreUtil2.<Mapping>eAllOfType(model, Mapping.class);
    for (final EObject object : _eAllOfType) {
      document.<EObject>format(object);
    }
    List<Statement> _eAllOfType_1 = EcoreUtil2.<Statement>eAllOfType(model, Statement.class);
    for (final EObject object_1 : _eAllOfType_1) {
      document.<EObject>format(object_1);
    }
    List<Schema> _eAllOfType_2 = EcoreUtil2.<Schema>eAllOfType(model, Schema.class);
    for (final EObject object_2 : _eAllOfType_2) {
      document.<EObject>format(object_2);
    }
    List<SchemaAssignment> _eAllOfType_3 = EcoreUtil2.<SchemaAssignment>eAllOfType(model, SchemaAssignment.class);
    for (final EObject object_3 : _eAllOfType_3) {
      document.<EObject>format(object_3);
    }
    List<SchemaVariable> _eAllOfType_4 = EcoreUtil2.<SchemaVariable>eAllOfType(model, SchemaVariable.class);
    for (final EObject object_4 : _eAllOfType_4) {
      document.<EObject>format(object_4);
    }
  }
  
  protected void _format(final Schema schema, @Extension final IFormattableDocument document) {
    this.indentBlocks(schema, document);
    document.<EObject>format(schema.getSchema());
  }
  
  protected void _format(final Mapping mapping, @Extension final IFormattableDocument document) {
    this.indentBlocks(mapping, document);
  }
  
  protected void _format(final SchemaAssignment value, @Extension final IFormattableDocument document) {
    final Procedure1<IHiddenRegionFormatter> _function = (IHiddenRegionFormatter it) -> {
      it.newLine();
    };
    document.<SchemaAssignment>append(value, _function);
    document.<EObject>format(value.getValue());
  }
  
  protected void _format(final ObjectValue value, @Extension final IFormattableDocument document) {
    this.indentBlocks(value, document);
    List<ObjectContent> _eAllOfType = EcoreUtil2.<ObjectContent>eAllOfType(value, ObjectContent.class);
    for (final EObject object : _eAllOfType) {
      final Procedure1<IHiddenRegionFormatter> _function = (IHiddenRegionFormatter it) -> {
        it.newLine();
      };
      document.<EObject>append(document.<EObject>format(object), _function);
    }
    List<ArrayValue> _eAllOfType_1 = EcoreUtil2.<ArrayValue>eAllOfType(value, ArrayValue.class);
    for (final EObject object_1 : _eAllOfType_1) {
      final Procedure1<IHiddenRegionFormatter> _function_1 = (IHiddenRegionFormatter it) -> {
        it.newLine();
      };
      document.<EObject>append(document.<EObject>format(object_1), _function_1);
    }
  }
  
  protected void _format(final ArrayValue value, @Extension final IFormattableDocument document) {
    this.indentBlocks(value, document);
  }
  
  protected void _format(final ObjectContent value, @Extension final IFormattableDocument document) {
    this.indentBlocks(value, document);
  }
  
  protected void _format(final Value value, @Extension final IFormattableDocument document) {
    this.indentBlocks(value, document);
  }
  
  protected void _format(final Statement statement, @Extension final IFormattableDocument document) {
    final Procedure1<IHiddenRegionFormatter> _function = (IHiddenRegionFormatter it) -> {
      it.newLine();
    };
    document.<Statement>surround(statement, _function);
    document.<EObject>format(statement.getStatement());
    this.indentBlocks(statement, document);
  }
  
  protected void _format(final Select sel, @Extension final IFormattableDocument document) {
    this.indentBlocks(sel, document);
  }
  
  protected void _format(final VariableStatement varStatement, @Extension final IFormattableDocument document) {
    document.<SchemaVariable>format(varStatement.getSchemaVariable());
  }
  
  protected void _format(final SchemaVariable schema, @Extension final IFormattableDocument document) {
    final Procedure1<IHiddenRegionFormatter> _function = (IHiddenRegionFormatter it) -> {
      it.noSpace();
    };
    final Procedure1<IHiddenRegionFormatter> _function_1 = (IHiddenRegionFormatter it) -> {
      it.noSpace();
    };
    document.append(document.prepend(this.textRegionExtensions.regionFor(schema).keyword("."), _function), _function_1);
  }
  
  protected void _format(final TernaryExpression ter, @Extension final IFormattableDocument document) {
    final Procedure1<IHiddenRegionFormatter> _function = (IHiddenRegionFormatter it) -> {
      it.noSpace();
    };
    final Procedure1<IHiddenRegionFormatter> _function_1 = (IHiddenRegionFormatter it) -> {
      it.noSpace();
    };
    document.append(document.prepend(this.textRegionExtensions.regionFor(ter).keyword("."), _function), _function_1);
  }
  
  public void indentBlocks(final EObject schema, @Extension final IFormattableDocument document) {
    final Procedure1<IHiddenRegionFormatter> _function = (IHiddenRegionFormatter it) -> {
      it.indent();
    };
    final Procedure1<IHiddenRegionFormatter> _function_1 = (IHiddenRegionFormatter it) -> {
      it.newLine();
    };
    document.append(this.textRegionExtensions.regionFor(document.<EObject>interior(schema, _function)).keyword("{"), _function_1);
    final Procedure1<IHiddenRegionFormatter> _function_2 = (IHiddenRegionFormatter it) -> {
      it.newLine();
    };
    final Procedure1<IHiddenRegionFormatter> _function_3 = (IHiddenRegionFormatter it) -> {
      it.newLine();
    };
    document.append(document.prepend(this.textRegionExtensions.regionFor(schema).keyword("}"), _function_2), _function_3);
    final Procedure1<IHiddenRegionFormatter> _function_4 = (IHiddenRegionFormatter it) -> {
      it.newLine();
    };
    document.append(this.textRegionExtensions.regionFor(schema).keyword("["), _function_4);
    final Procedure1<IHiddenRegionFormatter> _function_5 = (IHiddenRegionFormatter it) -> {
      it.newLine();
    };
    final Procedure1<IHiddenRegionFormatter> _function_6 = (IHiddenRegionFormatter it) -> {
      it.newLine();
    };
    document.append(document.prepend(this.textRegionExtensions.regionFor(schema).keyword("]"), _function_5), _function_6);
  }
  
  protected void _format(final Data data, @Extension final IFormattableDocument document) {
    EList<Entity> _entity = data.getEntity();
    for (final Entity entity : _entity) {
      document.<Entity>format(entity);
    }
  }
  

  protected void _format(final Entity entity, @Extension final IFormattableDocument document) {
    this.indentBlocks(entity, document);
    boolean _nonNull = Objects.nonNull(entity.getType());
    if (_nonNull) {
      final Procedure1<IHiddenRegionFormatter> _function = (IHiddenRegionFormatter it) -> {
        it.newLine();
      };
      document.<Entity>append(entity, _function);
    }
    EList<Entity> _entity = entity.getEntity();
    for (final Entity childEntity : _entity) {
      document.<Entity>format(childEntity);
    }
  }
  
  public void format(final Object mapping, final IFormattableDocument document) {
    if (mapping instanceof Mapping) {
      _format((Mapping)mapping, document);
      return;
    } else if (mapping instanceof SchemaAssignment) {
      _format((SchemaAssignment)mapping, document);
      return;
    } else if (mapping instanceof Schema) {
      _format((Schema)mapping, document);
      return;
    } else if (mapping instanceof XtextResource) {
      _format((XtextResource)mapping, document);
      return;
    } else if (mapping instanceof ArrayValue) {
      _format((ArrayValue)mapping, document);
      return;
    } else if (mapping instanceof ObjectValue) {
      _format((ObjectValue)mapping, document);
      return;
    } else if (mapping instanceof TernaryExpression) {
      _format((TernaryExpression)mapping, document);
      return;
    } else if (mapping instanceof Value) {
      _format((Value)mapping, document);
      return;
    } else if (mapping instanceof Data) {
      _format((Data)mapping, document);
      return;
    } else if (mapping instanceof Entity) {
      _format((Entity)mapping, document);
      return;
    } else if (mapping instanceof Model) {
      _format((Model)mapping, document);
      return;
    } else if (mapping instanceof ObjectContent) {
      _format((ObjectContent)mapping, document);
      return;
    } else if (mapping instanceof SchemaVariable) {
      _format((SchemaVariable)mapping, document);
      return;
    } else if (mapping instanceof Select) {
      _format((Select)mapping, document);
      return;
    } else if (mapping instanceof Statement) {
      _format((Statement)mapping, document);
      return;
    } else if (mapping instanceof VariableStatement) {
      _format((VariableStatement)mapping, document);
      return;
    } else if (mapping instanceof EObject) {
      _format((EObject)mapping, document);
      return;
    } else if (mapping == null) {
      _format((Void)null, document);
      return;
    } else if (mapping != null) {
      _format(mapping, document);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(mapping, document).toString());
    }
  }
}
