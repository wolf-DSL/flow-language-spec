package com.intuit.dsl.flow.validation;

import com.intuit.dsl.flow.flow.ArrayValue;
import com.intuit.dsl.flow.flow.Data;
import com.intuit.dsl.flow.flow.Entity;
import com.intuit.dsl.flow.flow.ObjectContent;
import com.intuit.dsl.flow.flow.ObjectValue;
import com.intuit.dsl.flow.flow.Schema;
import com.intuit.dsl.flow.flow.SchemaAssignment;
import com.intuit.dsl.flow.validation.BaseValidator;
import com.intuit.graphql.graphQL.ObjectTypeDefinition;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ValueValidator extends BaseValidator {
  @Check(CheckType.NORMAL)
  public void checkForCyclicReference(final SchemaAssignment schemaAssignment) {
    final Set<SchemaAssignment> set = new HashSet<SchemaAssignment>();
    this.checkForCyclicReference(schemaAssignment, set);
  }
  
  public void checkForCyclicReference(final SchemaAssignment sa, final Set<SchemaAssignment> set) {
    boolean _add = set.add(sa);
    boolean _not = (!_add);
    if (_not) {
      String _name = sa.getName();
      String _plus = ("Cyclic reference found in value " + _name);
      this.error(_plus, sa);
      return;
    }
    final EObject value = sa.getValue();
    if ((value instanceof ObjectValue)) {
      this.checkforCyclicReferenceObject(((ObjectValue)value), set);
    } else {
      if ((value instanceof ArrayValue)) {
        this.checkforCyclicReferenceArray(((ArrayValue)value), set);
      }
    }
  }
  
  public void checkforCyclicReferenceObject(final ObjectValue value, final Set<SchemaAssignment> set) {
    EList<ObjectContent> _values = value.getValues();
    for (final ObjectContent content : _values) {
      {
        boolean _nonNull = Objects.nonNull(content.getObjectValue());
        if (_nonNull) {
          this.checkforCyclicReferenceObject(content.getObjectValue(), set);
        }
        boolean _nonNull_1 = Objects.nonNull(content.getArrayValue());
        if (_nonNull_1) {
          this.checkforCyclicReferenceArray(content.getArrayValue(), set);
        }
        boolean _nonNull_2 = Objects.nonNull(content.getSchemaAssignment());
        if (_nonNull_2) {
          final HashSet<SchemaAssignment> newSet = new HashSet<SchemaAssignment>(set);
          this.checkForCyclicReference(content.getSchemaAssignment(), newSet);
        }
      }
    }
  }
  
  public void checkforCyclicReferenceArray(final ArrayValue arrayValue, final Set<SchemaAssignment> set) {
    boolean _isEmpty = arrayValue.getObjectValue().isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      final Iterator<ObjectValue> valueIterator = arrayValue.getObjectValue().iterator();
      while (valueIterator.hasNext()) {
        this.checkforCyclicReferenceObject(valueIterator.next(), set);
      }
    } else {
      boolean _isEmpty_1 = arrayValue.getArrayValue().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        final Iterator<ArrayValue> valueIterator_1 = arrayValue.getArrayValue().iterator();
        while (valueIterator_1.hasNext()) {
          this.checkforCyclicReferenceArray(valueIterator_1.next(), set);
        }
      } else {
        boolean _isEmpty_2 = arrayValue.getSchemaAssignment().isEmpty();
        boolean _not_2 = (!_isEmpty_2);
        if (_not_2) {
          final Iterator<SchemaAssignment> schemaIterator = arrayValue.getSchemaAssignment().iterator();
          while (schemaIterator.hasNext()) {
            {
              final HashSet<SchemaAssignment> newSet = new HashSet<SchemaAssignment>(set);
              this.checkForCyclicReference(schemaIterator.next(), newSet);
            }
          }
        }
      }
    }
  }
  
  @Check(CheckType.NORMAL)
  public void checkforDuplicateKeys(final ObjectValue objectValue) {
    final HashSet<String> names = new HashSet<String>();
    EList<ObjectContent> _values = objectValue.getValues();
    for (final ObjectContent o : _values) {
      if ((Objects.nonNull(o.getId()) && (!names.add(o.getId())))) {
        this.error("Duplicate keys in value", o);
      }
    }
  }
  
  @Check(CheckType.NORMAL)
  public Object checkifKeysExist(final SchemaAssignment schemaAssignment) {
    return this.checkifKeysExist(schemaAssignment.getSchema(), schemaAssignment);
  }
  
  protected Object _checkifKeysExist(final Schema schema, final SchemaAssignment schemaAssignment) {
    Object _xblockexpression = null;
    {
      EList<Entity> _xifexpression = null;
      EObject _schema = schema.getSchema();
      if ((_schema instanceof Data)) {
        EObject _schema_1 = schema.getSchema();
        _xifexpression = ((Data) _schema_1).getEntity();
      }
      final EList<Entity> entities = _xifexpression;
      _xblockexpression = this.checkifKeysExist(entities, schemaAssignment);
    }
    return _xblockexpression;
  }
  
  protected Object _checkifKeysExist(final ObjectTypeDefinition schema, final SchemaAssignment schemaAssignment) {
    return null;
  }
  
  protected Object _checkifKeysExist(final EList<Entity> entities, final SchemaAssignment schemaAssignment) {
    EObject value = schemaAssignment.getValue();
    if ((value instanceof ObjectValue)) {
      this.checkObjectValue(entities, ((ObjectValue)value));
    }
    return null;
  }
  
  public void checkObjectValue(final EList<Entity> entities, final ObjectValue objectValue) {
    final EList<ObjectContent> objectContents = objectValue.getValues();
    Iterator<ObjectContent> objectIterator = objectContents.iterator();
    while (objectIterator.hasNext()) {
      {
        final ObjectContent objectContent = objectIterator.next();
        final String id = objectContent.getId();
        final Function1<Entity, Boolean> _function = (Entity e) -> {
          String _name = e.getName();
          return Boolean.valueOf(com.google.common.base.Objects.equal(_name, id));
        };
        final Entity entity = IterableExtensions.<Entity>head(IterableExtensions.<Entity>filter(entities, _function));
        if ((Objects.nonNull(id) && Objects.isNull(entity))) {
          this.error((("No key with name " + id) + " is defined in schema"), objectContent);
          return;
        }
        boolean _nonNull = Objects.nonNull(entity);
        if (_nonNull) {
          final EList<Entity> newEntities = this.getEntities(entity);
          String _op = entity.getOp();
          boolean _equals = com.google.common.base.Objects.equal(_op, "[]");
          if (_equals) {
            this.checkForPrimitiveOrSchemaArray(entity, objectContent);
          }
          boolean _nonNull_1 = Objects.nonNull(objectContent.getObjectValue());
          if (_nonNull_1) {
            this.checkObjectValue(newEntities, objectContent.getObjectValue());
          } else {
            boolean _nonNull_2 = Objects.nonNull(objectContent.getArrayValue());
            if (_nonNull_2) {
              this.checkArrayValue(newEntities, objectContent.getArrayValue());
            } else {
              boolean _nonNull_3 = Objects.nonNull(objectContent.getSchemaAssignment());
              if (_nonNull_3) {
                this.checkifKeysExist(newEntities, objectContent.getSchemaAssignment());
              }
            }
          }
        }
      }
    }
  }
  
  public EList<Entity> getEntities(final Entity entity) {
    EList<Entity> _xifexpression = null;
    if ((com.google.common.base.Objects.equal(entity.getOp(), "->") && (entity.getSchema() instanceof Schema))) {
      ObjectTypeDefinition _schema = entity.getSchema();
      EObject _schema_1 = ((Schema) _schema).getSchema();
      _xifexpression = ((Data) _schema_1).getEntity();
    } else {
      _xifexpression = entity.getEntity();
    }
    return _xifexpression;
  }
  
  public void checkForPrimitiveOrSchemaArray(final Entity entity, final ObjectContent objectContent) {
    final ArrayValue arrayValue = objectContent.getArrayValue();
    boolean _isNull = Objects.isNull(arrayValue);
    if (_isNull) {
      String _id = objectContent.getId();
      String _plus = ("Type of " + _id);
      String _plus_1 = (_plus + " is an array");
      this.error(_plus_1, objectContent);
      return;
    }
    final String type = entity.getType();
    if ((Objects.nonNull(type) && IterableExtensions.isNullOrEmpty(objectContent.getArrayValue().getPrimitiveValue()))) {
      String _id_1 = objectContent.getId();
      String _plus_2 = (_id_1 + " needs to be a primitive array");
      this.error(_plus_2, objectContent);
      return;
    }
    final ObjectTypeDefinition schemaType = entity.getSchema();
    boolean _nonNull = Objects.nonNull(schemaType);
    if (_nonNull) {
      final EList<SchemaAssignment> schemaAssignments = objectContent.getArrayValue().getSchemaAssignment();
      boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(schemaAssignments);
      if (_isNullOrEmpty) {
        String _id_2 = objectContent.getId();
        String _plus_3 = (_id_2 + " needs to be a schema array");
        this.error(_plus_3, objectContent);
        return;
      }
      final Iterator<SchemaAssignment> iterator = schemaAssignments.iterator();
      while (iterator.hasNext()) {
        {
          final SchemaAssignment cur = iterator.next();
          String _name = schemaType.getName();
          String _name_1 = cur.getSchema().getName();
          boolean _notEquals = (!com.google.common.base.Objects.equal(_name, _name_1));
          if (_notEquals) {
            String _name_2 = schemaType.getName();
            String _plus_4 = ("Type mismatch: type needs to be of " + _name_2);
            this.error(_plus_4, objectContent);
            return;
          }
          this.checkifKeysExist(schemaType, cur);
        }
      }
    }
  }
  
  public void checkArrayValue(final EList<Entity> entities, final ArrayValue arrayValue) {
    boolean _isEmpty = arrayValue.getObjectValue().isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      Iterator<ObjectValue> valueIterator = arrayValue.getObjectValue().iterator();
      while (valueIterator.hasNext()) {
        this.checkObjectValue(entities, valueIterator.next());
      }
    } else {
      boolean _isEmpty_1 = arrayValue.getPrimitiveValue().isEmpty();
      boolean _not_1 = (!_isEmpty_1);
      if (_not_1) {
        int _size = entities.size();
        boolean _greaterThan = (_size > 1);
        if (_greaterThan) {
          this.error("Needs to be an object array", arrayValue);
          return;
        }
      }
    }
  }
  
  public Object checkifKeysExist(final Object schema, final SchemaAssignment schemaAssignment) {
    if (schema instanceof Schema) {
      return _checkifKeysExist((Schema)schema, schemaAssignment);
    } else if (schema instanceof ObjectTypeDefinition) {
      return _checkifKeysExist((ObjectTypeDefinition)schema, schemaAssignment);
    } else if (schema instanceof EList) {
      return _checkifKeysExist((EList<Entity>)schema, schemaAssignment);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(schema, schemaAssignment).toString());
    }
  }
}
