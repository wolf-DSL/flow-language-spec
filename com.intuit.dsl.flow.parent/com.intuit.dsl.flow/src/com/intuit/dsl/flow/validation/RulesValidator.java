package com.intuit.dsl.flow.validation;

import com.intuit.dsl.flow.flow.NewRule;
import com.intuit.dsl.flow.flow.Rulebase;
import com.intuit.dsl.flow.validation.BaseValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class RulesValidator extends BaseValidator {
  @Check
  public void checkRuleBaseIdForWhiteSpace(final Rulebase rulebase) {
    boolean _containsWhitespace = this.containsWhitespace(rulebase.getId());
    if (_containsWhitespace) {
      this.error("Rulebase ID cannot contain a whitespace", rulebase);
    }
  }
  
  @Check
  public void checkNewRuleForWhiteSpace(final NewRule rule) {
    boolean _containsWhitespace = this.containsWhitespace(rule.getId());
    if (_containsWhitespace) {
      this.error("Rule ID cannot contain a whitespace", rule);
    }
    final Function1<String, Boolean> _function = (String it) -> {
      return Boolean.valueOf(this.containsWhitespace(it));
    };
    boolean _exists = IterableExtensions.<String>exists(rule.getModifiers(), _function);
    if (_exists) {
      this.error("Rule modifiers cannot contain a whitespace", rule);
    }
    boolean _containsWhitespace_1 = this.containsWhitespace(rule.getInjectedTopicId());
    if (_containsWhitespace_1) {
      this.error("Rule injectedTopicId cannot contain a whitespace", rule);
    }
  }
  
  public boolean containsWhitespace(final String str) {
    return ((str != null) && str.contains(" "));
  }
}
