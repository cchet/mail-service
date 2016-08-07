package com.clevercure.mailing.template.logic.api.builder;

import com.clevercure.mailing.template.logic.api.metadata.AbstractTemplateMetadata;
import com.clevercure.mailing.template.logic.api.variable.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractTemplateDataJsonBuilder<I, 
                                                      M extends AbstractTemplateMetadata<I>, 
													  B extends AbstractTemplateDataJsonBuilder<I, M, B>>
        implements TemplateDataJsonBuilder<I, M, B> {

    //<editor-fold desc="Private Members">
    protected String emptyValue = "!!not_found!!";
    protected Locale locale;
    protected ZoneId zoneId;
    protected Boolean initialized = Boolean.FALSE;
    protected Boolean strictMode;
    protected M templateMeta;
    protected ObjectMapper mapper;
    protected VariableResolverFactoryProvider variableResolverFactoryFactory;
    protected VariableResolverFactory variableResolverFactory;
    protected Map<VariableContract, VariableResolver> contractResolverMap;
    protected Map<VariableContract, Object> contractValueMap;
    protected Map<Object, Object> userData;

    protected static final Logger log = LoggerFactory.getLogger(AbstractTemplateDataJsonBuilder.class);

    //<editor-fold desc="Builder Configuration Methods">
    public B withWeakMode() {
        throwIfInitialized();

        strictMode = Boolean.FALSE;

        return (B) this;
    }

    public B withStrictMode() {
        throwIfInitialized();

        strictMode = Boolean.TRUE;

        return (B) this;
    }

    public B withLocalization(Locale locale,
                              ZoneId zoneId) {
        Objects.requireNonNull(locale, "Locale must be given");
        Objects.requireNonNull(zoneId, "ZoneId must be given");

        this.locale = locale;
        this.zoneId = zoneId;

        return (B) this;
    }

    @Override
    public B withUserData(final Map<Object, Object> userData) {
        this.userData = userData;

        return (B) this;
    }

    @Override
    public B withVariableResolverFactoryProvider(VariableResolverFactoryProvider factory) {
        Objects.requireNonNull(factory, "Cannot register null factory");

        this.variableResolverFactoryFactory = factory;

        return (B) this;
    }

    @Override
    public B withVariableResolverFactory(VariableResolverFactory factory) {
        Objects.requireNonNull(factory, "Cannot register null factory");

        this.variableResolverFactory = factory;

        return (B) this;
    }
    //</editor-fold>

    //<editor-fold desc="Builder Lifecycle Methods">
    public B withTemplate(final M metadata) {
        Objects.requireNonNull(metadata, "Template metadata must not be null");

        templateMeta = metadata;
        initCommon();

        return (B) this;
    }

    @Override
    public void end() {
        templateMeta = null;
        mapper = null;
        variableResolverFactoryFactory = null;
        variableResolverFactory = null;
        contractResolverMap = null;
        contractValueMap = null;
        userData = null;

        initialized = Boolean.FALSE;
    }
    //</editor-fold>

    public B addVariable(final VariableContract contract,
                         final Object value) {
        Objects.requireNonNull(contract, "Cannot add value to null variable contract");
        throwIfNotInitialized();

        if (isVariableValid(contract)) {
            contractValueMap.put(contract, value);
        }

        return (B) this;
    }

    public B addVariableResolver(final VariableContract contract,
                                 final VariableResolver resolver) {
        Objects.requireNonNull(contract, "Cannot add resolver to null variable contract");
        Objects.requireNonNull(resolver, "Cannot add null resolver");
        throwIfNotInitialized();

        if (isVariableValid(contract)) {
            contractResolverMap.put(contract, resolver);
        }

        return (B) this;
    }

    //<editor-fold desc="Helpers">

    /**
     * Initializes the common resources.
     * Shall be used for reset too.
     */
    protected void initCommon() {
        if (!initialized) {
            contractResolverMap = new HashMap<>();
            contractValueMap = new HashMap<>();
            mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        }

        // default is strict mode
        if (strictMode == null) {
            strictMode = Boolean.TRUE;
        }
        // use system default locale instead
        if (locale == null) {
            locale = Locale.getDefault();
        }
        // use system default zoneId
        if (zoneId == null) {
            zoneId = ZoneId.systemDefault();
        }

        initialized = Boolean.TRUE;
    }

    /**
     * Throws a {@link IllegalStateException} if the builder hasn't been started yet.
     */
    protected void throwIfNotInitialized() {
        if (!initialized) {
            throw new IllegalStateException("Builder not initialized");
        }
    }

    /**
     * Throws a {@link IllegalStateException} if the builder has already been started yet.
     */
    protected void throwIfInitialized() {
        if (initialized) {
            throw new IllegalStateException("Builder already initialized");
        }
    }

    /**
     * @param contract the variable to check if part of the template
     * @return true if part of the template, false otherwise
     */
    protected boolean isVariablePresentInTemplate(final VariableContract contract) {
        Objects.requireNonNull(contract, "VariableContract must not be null");

        return templateMeta.getVariables()
                           .stream()
                           .filter(contract::equals)
                           .findFirst()
                           .isPresent();
    }

    /**
     * Validates the variable depending on the currently set mode.
     * <ol>
     * <li>Variable part of template</li>
     * </ol>
     *
     * @param contract the VariableContract to validate
     * @return only in weak mode. true if valid, false otherwise.
     * @throws NullPointerException     if the contract is null
     * @throws IllegalArgumentException if the variable is not part of the template and strict mode has been enabled
     */
    protected boolean isVariableValid(final VariableContract contract) {
        Objects.requireNonNull(contract, "VariableContract must not be null");
        boolean valid = Boolean.TRUE;
        final boolean validVariable = isVariablePresentInTemplate(contract);
        if (strictMode) {
            if (!validVariable) {
                throw new IllegalArgumentException("VariableContract: '" + contract.toString() + "' is not part of this template");
            }
        } else {
            if (!validVariable) {
                log.warn("Variable '{}' is not part of the template with id: '{}'",
                         contract.toString(),
                         templateMeta.getId());
                valid = Boolean.FALSE;
            }
        }

        return valid;
    }

    /**
     * Resolvers the given variable contract to its value.
     * <ol>
     * <li>explicitly defined value for variable contract</li>
     * <li>explicitly defined variable resolver</li>
     * <li>variable resolver from factory</li>
     * <li>the defined empty value</li>
     * </ol>
     *
     * @param contract the variable contract to resolver value for
     * @return the resolver variable contract value
     */
    protected Object resolveVariable(VariableContract contract) {
        Objects.requireNonNull(contract, "Cannot resolver value for null contract");

        final VariableContractResolverCtx ctx = new VariableContractResolverCtxImpl(locale, zoneId, userData);
        Object value = null;
        // 1. explicit value
        if (contractValueMap.containsKey(contract)) {
            value = contractValueMap.get(contract);
        }
        // 2. explicit resolver
        else if (contractResolverMap.containsKey(contract)) {
            value = contractResolverMap.get(contract).resolve(contract, ctx);
        }
        // 3. resolver from factory
        else if (variableResolverFactory != null) {
            final VariableResolver resolver = variableResolverFactory.getVariableResolver(contract, null);
            if (resolver != null) {
                value = resolver.resolve(contract, ctx);
            }
        }
        // 4. resolver from resolverFactory from resolverFactoryFactory
        // TODO: Maybe consider to register the variable resolver factories on definition of this factory
        else if (variableResolverFactoryFactory != null) {
            final VariableResolverFactory resolverFactory = variableResolverFactoryFactory.getVariableResolverFactory(contract.getClass());
            if (resolverFactory != null) {
                final VariableResolver resolver = resolverFactory.getVariableResolver(contract, ctx);
                if (resolver != null) {
                    value = resolver.resolve(contract, ctx);
                }
            }
        }

        // default empty value if unresolved
        if (value == null) {
            value = emptyValue;
        }

        return value;
    }

    protected void validateIfValidToBuild() {
        // Filter all contracts which haven't been defined explicitly
        final Set<VariableContract> missingVariableContracts = (Set<VariableContract>) templateMeta.getVariables()
                                                                                                   .stream()
                                                                                                   .filter(item -> !contractValueMap
                                                                                                           .containsKey(
                                                                                                                   item))
                                                                                                   .collect(Collectors.toSet());
        // if resolvers present, remove contracts which are handled by resolver
        missingVariableContracts.removeAll(contractResolverMap.entrySet().stream().map(entry -> entry.getKey()).collect(
                Collectors.toList()));
        // if resolver factory is present, remove contracts which are handled by this factory
        if (variableResolverFactory != null) {
            templateMeta.getVariables().forEach(contract -> {
                if (variableResolverFactory.getVariableResolver(contract, null) != null) {
                    missingVariableContracts.remove(contract);
                }
            });
        }

        if (strictMode) {
            if (!missingVariableContracts.isEmpty()) {
                throw new IllegalStateException("Some variables have not been defined. " + System.lineSeparator() + StringUtils
                        .join(missingVariableContracts, System.lineSeparator()));
            }
        } else {
            if (!missingVariableContracts.isEmpty()) {
                log.warn("Some variables have not been defined. {}",
                         (System.lineSeparator() + StringUtils.join(missingVariableContracts, System.lineSeparator())));
                // Add missing variables with empty value
                contractValueMap.putAll(missingVariableContracts.stream()
                                                                .collect(Collectors.toMap(variable -> variable,
                                                                                          variable -> "")));
            }
        }
    }
    //</editor-fold>
}
