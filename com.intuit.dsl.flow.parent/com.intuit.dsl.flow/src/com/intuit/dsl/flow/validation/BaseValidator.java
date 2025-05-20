package com.intuit.dsl.flow.validation;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.EValidatorRegistrar;

@SuppressWarnings("all")
public class BaseValidator extends AbstractDeclarativeValidator {
  @Inject
  @Override
  public void register(final EValidatorRegistrar registrar) {
  }
  
  public void error(final String message, final EObject object) {
    EStructuralFeature feature = object.eClass().getEStructuralFeature("id");
    this.error(message, object, feature);
  }
  
  public void warning(final String message, final EObject object) {
    EStructuralFeature feature = object.eClass().getEStructuralFeature("id");
    this.warning(message, object, feature);
  }
}
