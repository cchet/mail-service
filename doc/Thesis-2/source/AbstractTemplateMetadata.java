package com.clevercure.mailing.template.logic.api.metadata;

import com.clevercure.mailing.template.logic.api.processor.TemplateProcessor;
import com.clevercure.mailing.template.logic.api.variable.VariableContract;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractTemplateMetadata<I> implements Serializable {

    protected I id;
    protected String template;
    protected long version;
    protected TemplateProcessor processor;
    protected Set<VariableContract> variables = new HashSet<>(0);
    protected Set<String> invalidVariables = new HashSet<>(0);

    public AbstractTemplateMetadata(final TemplateProcessor processor) {
        Objects.requireNonNull(processor, 
		                       "Cannot build metadata with null processor");

        this.processor = processor;
    }

    public void init(final I id,
                     final String template,
                     final long version) {
        Objects.requireNonNull(id, 
		                       "TemplateMetadata needs to define an id");
        Objects.requireNonNull(version, 
		                       "Cannot build metadata for null version");
        Objects.requireNonNull(template, 
		                       "Cannot build metadata for null template");

        Set<VariableContract> vSet = processor.resolveExpressions(template);
        Set<String> ivSet          = processor.resolveInvalidVariables(template);
        this.id                    = id;
        this.version               = version;
        this.template              = template;
        variables 	               = Collections.unmodifiableSet(vSet);
        invalidVariables = Collections.unmodifiableSet(ivSet);
    }

    public VariableContract getById(String id) {
        Objects.requireNonNull(id, 
		                       "Cannot get contract for null id");

        return variables.stream().filter(c -> id.equals(c.getId()))
                                 .findFirst()
                                 .orElseGet(() -> null);
    }

    public I getId() {
        return id;
    }

    public String getTemplate() {
        return template;
    }

    public long getVersion() {
        return version;
    }

    public long getTemplateLength() {
        return template.length();
    }

    public long getVariableCount() {
        return variables.size();
    }

    public long getInvalidVariableCount() {
        return invalidVariables.size();
    }

    public Set<VariableContract> getVariables() {
        return variables;
    }

    public Set<String> getInvalidVariables() {
        return invalidVariables;
    }

    public TemplateProcessor getProcessor() {
        return processor;
    }
	
    @Override
    public String toString() {
        String separator = "================================"
                         + "================================";
        String ls = System.lineSeparator();
        return new StringBuilder(100).append(separator)
          .append(ls)
          .append(this.getClass().getSimpleName())
          .append("").append(ls).append(separator)
          .append(ls)
          .append("id                  : ").append(id)
          .append(ls)
          .append("version             : ").append(version)
          .append(ls)
          .append("length              : ").append(getTemplateLength())
          .append(ls)
          .append("variables (valid)   : ").append(getVariableCount())
          .append(ls)
          .append(StringUtils.join(variables, (ls + ls)))
          .append(ls)
          .append("variables (invalid) : ").append(getInvalidVariableCount())
          .append(ls)
          .append(StringUtils.join(invalidVariables, ls))
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AbstractTemplateMetadata<?> that = (AbstractTemplateMetadata<?>) o;
        return version == that.version &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
