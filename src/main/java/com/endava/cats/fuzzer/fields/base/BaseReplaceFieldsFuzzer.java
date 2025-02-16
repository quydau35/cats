package com.endava.cats.fuzzer.fields.base;

import com.endava.cats.fuzzer.api.Fuzzer;
import com.endava.cats.fuzzer.executor.FieldsIteratorExecutor;
import com.endava.cats.fuzzer.executor.FieldsIteratorExecutorContext;
import com.endava.cats.http.HttpMethod;
import com.endava.cats.http.ResponseCodeFamily;
import com.endava.cats.model.FuzzingData;
import com.endava.cats.strategy.FuzzingStrategy;
import com.endava.cats.util.ConsoleUtils;
import io.github.ludovicianul.prettylogger.PrettyLogger;
import io.github.ludovicianul.prettylogger.PrettyLoggerFactory;
import io.swagger.v3.oas.models.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public abstract class BaseReplaceFieldsFuzzer implements Fuzzer {
    protected final PrettyLogger logger = PrettyLoggerFactory.getLogger(getClass());
    private final FieldsIteratorExecutor catsExecutor;
    protected BaseReplaceFieldsFuzzer(FieldsIteratorExecutor ce) {
        this.catsExecutor = ce;
    }

    @Override
    public void fuzz(FuzzingData data) {
        BaseReplaceFieldsContext context = this.getContext(data);
        catsExecutor.execute(
                FieldsIteratorExecutorContext.builder()
                        .scenario("Replace %s fields with %s values. ".formatted(context.replaceWhat, context.replaceWith))
                        .fuzzingData(data).fuzzingStrategy(FuzzingStrategy.replace())
                        .expectedResponseCode(ResponseCodeFamily.FOURXX)
                        .skipMessage(context.skipMessage)
                        .fieldFilter(context.fieldFilter)
                        .fuzzValueProducer(context.fuzzValueProducer)
                        .replaceRefData(false)
                        .simpleReplaceField(true)
                        .logger(logger)
                        .fuzzer(this)
                        .build());
    }

    @Override
    public String description() {
        BaseReplaceFieldsContext context = this.getContext(null);
        return "iterate through each %s field and replace it with %s values".formatted(context.replaceWhat, context.replaceWith);
    }

    @Override
    public String toString() {
        return ConsoleUtils.sanitizeFuzzerName(this.getClass().getSimpleName());
    }

    @Override
    public List<HttpMethod> skipForHttpMethods() {
        return List.of(HttpMethod.HEAD, HttpMethod.GET, HttpMethod.DELETE);
    }

    @Builder
    public static class BaseReplaceFieldsContext {
        private final String replaceWhat;
        private final String replaceWith;
        private final String skipMessage;
        private final Predicate<String> fieldFilter;
        private final BiFunction<Schema<?>, String, List<String>> fuzzValueProducer;
    }

    public abstract BaseReplaceFieldsContext getContext(FuzzingData data);
}