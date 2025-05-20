package com.intuit.dsl.flow.scoping;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.intuit.dsl.flow.FlowDeclarativeQualifiedNameProvider;
import com.intuit.dsl.flow.flow.Flow;
import com.intuit.dsl.flow.flow.FlowPackage;
import com.intuit.dsl.flow.flow.GraphQLService;
import com.intuit.dsl.flow.flow.InPutModel;
import com.intuit.dsl.flow.flow.Mapping;
import com.intuit.dsl.flow.flow.OutPutModel;
import com.intuit.dsl.flow.flow.QueryModel;
import com.intuit.dsl.flow.flow.Service;
import com.intuit.dsl.flow.flow.StringifyExpression;
import com.intuit.dsl.flow.flow.Transition;
import com.intuit.dsl.flow.flow.TransitionState;
import com.intuit.dsl.flow.scoping.AbstractFlowScopeProvider;
import com.intuit.graphql.graphQL.Document;
import com.intuit.graphql.graphQL.FieldDefinition;
import com.intuit.graphql.graphQL.GraphQLPackage;
import com.intuit.graphql.graphQL.NamedType;
import com.intuit.graphql.graphQL.ObjectTypeDefinition;
import com.intuit.graphql.graphQL.OperationDefinition;
import com.intuit.graphql.graphQL.QueryDefinition;
import com.intuit.graphql.graphQL.TypeDefinition;
import com.intuit.graphql.graphQL.TypeSystemDefinition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.SimpleScope;
import org.eclipse.xtext.util.SimpleAttributeResolver;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping on how and when
 * to use it.
 */
@SuppressWarnings("all")
public class FlowScopeProvider extends AbstractFlowScopeProvider {
  private static final Function<EObject, QualifiedName> getQualifiedName =
      new Function<EObject, QualifiedName>() {
        @Override
        public QualifiedName apply(final EObject from) {
          String name = null;
          if (Objects.isNull(from)) {
            return null;
          }
          name = FlowDeclarativeQualifiedNameProvider.NAME_RESOLVER.apply(from);
          if (Objects.isNull(name)) {
            name = SimpleAttributeResolver.NAME_RESOLVER.apply(from);
          }
          if (!Objects.isNull(name)) {
            return QualifiedName.create(name);
          }
          return null;
        }
      };

  @Override
  public IScope getScope(final EObject context, final EReference reference) {
    if (context instanceof Transition) {
      return this.scopeTransition(context);
    }
    if ((context instanceof FieldDefinition || context instanceof NamedType)
        && Objects.equals(reference, GraphQLPackage.Literals.OBJECT_TYPE)) {
      return this.scopeGraphQLModel(context, this.delegateGetScope(context, reference));
    }
    if (context instanceof Mapping
        && Objects.equals(reference, FlowPackage.OUT_PUT_MODEL__SCHEMA)) {
      return new SimpleScope(this.delegateGetScope(context, reference),
          this.scopedElementsFor(Arrays.asList(((Mapping) (context)).getOutputModel())));
    }
    if (context instanceof InPutModel
        || Objects.equals(reference, FlowPackage.IN_PUT_MODEL__SCHEMA)) {
      final ArrayList<EObject> candidates = new ArrayList<>();
      candidates.addAll(getCandidates(context, OutPutModel.class));
      candidates.addAll(getCandidates(context, QueryModel.class));
      candidates.addAll(getCandidates(context, ObjectTypeDefinition.class));
      return new SimpleScope(this.delegateGetScope(context, reference), this.scopedElementsFor(candidates));
    }
    return this.delegateGetScope(context, reference);
  }

  public <T extends EObject> Iterable<IEObjectDescription> scopedElementsFor(
      final List<T> elements) {
    final Iterable<IEObjectDescription> transformed = Iterables
        .<T, IEObjectDescription>transform(elements, new Function<T, IEObjectDescription>() {
          @Override
          public IEObjectDescription apply(final T from) {
            if (from instanceof InPutModel) {
              final QualifiedName qualifiedName = getQualifiedName.apply(from);
              if (Objects.nonNull(qualifiedName)) {
                if (((InPutModel) from).getSchema() instanceof OutPutModel) {
                  return getEOBjectDescriptionForOutputModel(((OutPutModel) from));
                } else {
                  return new EObjectDescription(qualifiedName, ((InPutModel) from).getSchema(),
                      null);
                }
              }
            }
            if (from instanceof OutPutModel) {
              return getEOBjectDescriptionForOutputModel(((OutPutModel) from));
            }
            if (from instanceof QueryModel) {
              return getEOBjectDescriptionForQueryModel((QueryModel) from);
            }
            if (from instanceof QueryDefinition) {
              final QualifiedName qualifiedName =
                  getQualifiedName.apply(((QueryDefinition) from).getOp());
              return getEOBjectDescription(qualifiedName, from);

            }
            return null;
          }
        });
    return Iterables.filter(transformed, Predicates.notNull());
  }

  public EObjectDescription getEOBjectDescription(final QualifiedName name, final EObject from) {
    return Objects.nonNull(name) ? new EObjectDescription(name, from, null) : null;
  }

  public EObjectDescription getEOBjectDescriptionForQueryModel(final QueryModel from) {
    final EObject graphqlService = EcoreUtil2.getContainerOfType(from, GraphQLService.class);
    return getEOBjectDescription(getQualifiedName.apply(from), graphqlService);
  }

  public EObjectDescription getEOBjectDescriptionForOutputModel(final OutPutModel from) {
    final ObjectTypeDefinition schema = from.getSchema();
    return this.getEOBjectDescription(getQualifiedName.apply(from), schema);
  }

  public IScope scopeTransition(final EObject context) {
    final Flow rootElement =
        IterableExtensions.head(Iterables.filter(EcoreUtil2.getAllContainers(context), Flow.class));
    final List<TransitionState> candidates =
        EcoreUtil2.getAllContentsOfType(rootElement, TransitionState.class);
    final Function<TransitionState, QualifiedName> function =
        new Function<TransitionState, QualifiedName>() {
          @Override
          public QualifiedName apply(final TransitionState from) {
            if (Objects.isNull(from)) {
              return null;
            }
            final String name =
                FlowDeclarativeQualifiedNameProvider.NAME_RESOLVER.apply(from.getState());
            if (Objects.isNull(name)) {
              return null;
            }
            return QualifiedName.create(name);
          }
        };
    return Scopes.scopeFor(candidates, function, IScope.NULLSCOPE);
  }

  public IScope scopeGraphQLModel(final EObject context, final IScope parent) {
    final EObject rootElement = EcoreUtil2.getRootContainer(context);
    return Scopes.<EObject>scopeFor(
        getCandidates(rootElement, TypeDefinition.class), getQualifiedName,
        parent);
  }

  private <T extends EObject> List<T> getCandidates(EObject context, Class<T> type) {

    return context.eResource().getResourceSet().getResources().stream()
        .filter(r -> r != null && r.getAllContents() != null && r.getContents().size() > 0)
        .map(resource -> EcoreUtil2.getRootContainer(resource.getContents().get(0)))
        .filter(c -> c != null)
        .flatMap(root -> EcoreUtil2.getAllContentsOfType(root, type).stream())
        .collect(Collectors.toList());

  }

}
